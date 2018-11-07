
import re
from collections import namedtuple

from utils import closed_range, s2b

__all__ = ['TruthTable']

VARIABLES = 'nodetype terminal child_left child_right parent value child_value_left child_value_right'


class ParseTree:

    class Node:

        names = None

        def __init__(self, nodetype, terminal):
            assert 0 <= nodetype <= 4
            self.nodetype = nodetype
            self.terminal = terminal
            self.parent = self.child_left = self.child_right = None

        def eval(self, input_values):
            # input_values :: [1..X]:Bool
            if self.nodetype == 0:  # Terminal
                assert self.terminal != 0
                return input_values[self.terminal]

            elif self.nodetype == 1:  # AND
                assert self.child_left is not None
                assert self.child_right is not None
                return self.child_left.eval(input_values) and self.child_right.eval(input_values)

            elif self.nodetype == 2:  # OR
                assert self.child_left is not None
                assert self.child_right is not None
                return self.child_left.eval(input_values) or self.child_right.eval(input_values)

            elif self.nodetype == 3:  # NOT
                assert self.child_left is not None
                return not self.child_left.eval(input_values)

            elif self.nodetype == 4:  # None
                # return False
                raise ValueError('Maybe think again?')

        def size(self):
            if self.nodetype == 0:
                return 1
            elif self.nodetype == 1 or self.nodetype == 2:
                return 1 + self.child_left.size() + self.child_right.size()
            elif self.nodetype == 3:
                return 1 + self.child_left.size()
            elif self.nodetype == 4:
                # return 0
                raise ValueError('Maybe think again?')

        def __str__(self):
            if self.nodetype == 0:  # Terminal
                if self.names is not None:
                    return self.names[self.terminal - 1]
                else:
                    return f'x{self.terminal}'
            elif self.nodetype == 1:  # AND
                left = str(self.child_left)
                right = str(self.child_right)
                if self.child_left.nodetype == 2:  # Left child is OR
                    left = f'({left})'
                if self.child_right.nodetype == 2:  # Right child is OR
                    right = f'({right})'
                return f'{left} & {right}'
            elif self.nodetype == 2:  # OR
                left = str(self.child_left)
                right = str(self.child_right)
                if self.child_left.nodetype == 1:  # Left child is AND
                    left = f'({left})'
                if self.child_right.nodetype == 1:  # Right child is AND
                    right = f'({right})'
                return f'{left} | {right}'
            elif self.nodetype == 3:  # NOT
                if self.child_left.nodetype == 0:
                    return f'~{self.child_left}'
                else:
                    return f'~({self.child_left})'
            elif self.nodetype == 4:  # None
                raise ValueError(f'why are you trying to display None-typed node?')

    def __init__(self, nodetype, terminal, parent, child_left, child_right):
        # Note: all arguments are 1-based
        assert len(nodetype) == len(terminal) == len(parent) == len(child_left) == len(child_right)

        P = len(nodetype) - 1
        nodes = [None] + [self.Node(nt, tn) for nt, tn in zip(nodetype[1:], terminal[1:])]  # 1-based
        for p in closed_range(1, P):
            nodes[p].parent = nodes[parent[p]]
            nodes[p].child_left = nodes[child_left[p]]
            nodes[p].child_right = nodes[child_right[p]]
        self.root = nodes[1]

    def eval(self, input_values):
        # input_values :: [1..X]:Bool
        return self.root.eval(input_values)

    def size(self):
        return self.root.size()

    def __str__(self):
        return str(self.root)


class TruthTable:

    Reduction = namedtuple('Reduction', VARIABLES)
    Assignment = namedtuple('Assignment', VARIABLES + ' P')

    def __init__(self, inputs, values, names=None):
        # inputs::[U,X] -- variables values
        # values::[U] -- Boolean function values
        U = len(inputs)
        X = len(inputs[0])
        assert len(values) == U
        assert all(len(input_) == X for input_ in inputs)
        # Note: arguments are 0-based, but main fields are 1-based
        self.inputs = [None] + [s2b(input_, True) for input_ in inputs]
        self.values = s2b(values)
        self.vars_count = X;
        # Note: names are left 0-based
        if names is not None:
            assert len(names) == X
            self.names = names
        else:
            self.names = [f'x{i}' for i in closed_range(1, X)]

    @classmethod
    def from_file(cls, filename):
        inputs = []
        values = []
        pattern = re.compile(r'(?P<lhs>[01]+).+(?P<value>[01])')

        def process_line(line):
            m = pattern.match(re.sub(r'\s*', '', line))
            if m:
                lhs, value = m.groups()
                inputs.append(tuple(lhs))
                values.append(value)

        with open(filename) as f:
            first_line = f.readline()
            if first_line.startswith('0') or first_line.startswith('1'):
                names = None
                process_line(first_line)
            else:
                names = first_line.split()

            for line in f:
                process_line(line)

        return cls(inputs, values, names=names)
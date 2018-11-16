import itertools
import sys

from pyeda.boolalg.bfarray import ttvars
from pyeda.boolalg.minimization import espresso_tts
from pyeda.boolalg.table import truthtable
from truth_table import TruthTable
from utils import b2s

#input_path = "/home/eskimos/code/python/tt_minimization/tmp/input"
#input_path = "/home/eskimos/tmp/BfsLearning/tmp/input"
input_path = sys.argv[1];
truth_table = TruthTable.from_file(input_path)
f = {b2s(x): value for x, value in zip(truth_table.inputs[1:], truth_table.values[1:])}
values_for_pyeda_tt = [f.get(x, '-') for x in map(''.join, itertools.product('01', repeat=truth_table.vars_count))]
X = ttvars('x', truth_table.vars_count)
f1 = truthtable(X, values_for_pyeda_tt)
f1m = espresso_tts(f1)
str_f1 = f1m.__str__()
for i in range(len(truth_table.names) - 1,-1, -1):
    str_f1 = str_f1.replace('x[' + i.__str__() + ']', truth_table.names[len(truth_table.names) - i - 1])

print(str_f1)


# cli("ololo")

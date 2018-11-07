import itertools
import sys

from pyeda.boolalg.bfarray import ttvars
from pyeda.boolalg.minimization import espresso_tts
from pyeda.boolalg.table import truthtable
from truth_table import TruthTable
from utils import b2s

# input_path = "/home/eskimos/code/python/tt_minimization/tmp/input"
input_path = sys.argv[1];
truth_table = TruthTable.from_file(input_path)
f = {b2s(x): value for x, value in zip(truth_table.inputs[1:], truth_table.values[1:])}
values_for_pyeda_tt = [f.get(x, '-') for x in map(''.join, itertools.product('01', repeat=truth_table.vars_count))]
X = ttvars('x', truth_table.vars_count)
f1 = truthtable(X, values_for_pyeda_tt)
f1m = espresso_tts(f1)
print(f1m)

# cli("ololo")

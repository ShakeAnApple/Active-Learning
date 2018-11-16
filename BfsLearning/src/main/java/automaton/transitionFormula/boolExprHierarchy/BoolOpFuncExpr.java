package automaton.transitionFormula.boolExprHierarchy;

import java.util.List;

abstract class BoolOpFuncExpr extends BoolExpr{
    private List<BoolExpr> _children;

    public BoolOpFuncExpr(List<BoolExpr> children) {
        _children = children;
    }

    public List<BoolExpr> getChildren() {
        return _children;
    }
}

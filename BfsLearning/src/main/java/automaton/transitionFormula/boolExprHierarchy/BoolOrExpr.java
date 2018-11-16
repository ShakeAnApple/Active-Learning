package automaton.transitionFormula.boolExprHierarchy;

import java.util.List;

class BoolOrExpr extends BoolOpFuncExpr {

    public BoolOrExpr(List<BoolExpr> children) {
        super(children);
    }

    @Override
    protected <T> T applyImpl(BoolExprVisitor<T> visitor) {
        return visitor.visitOr(this);
    }
}
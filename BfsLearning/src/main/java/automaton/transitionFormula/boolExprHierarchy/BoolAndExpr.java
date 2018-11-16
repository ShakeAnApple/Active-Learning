package automaton.transitionFormula.boolExprHierarchy;

import java.util.List;

class BoolAndExpr extends BoolOpFuncExpr {

    public BoolAndExpr(List<BoolExpr> children) {
        super(children);
    }

    @Override
    protected <T> T applyImpl(BoolExprVisitor<T> visitor) {
        return visitor.visitAnd(this);
    }
}

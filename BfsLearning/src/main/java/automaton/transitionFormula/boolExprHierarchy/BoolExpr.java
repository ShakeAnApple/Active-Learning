package automaton.transitionFormula.boolExprHierarchy;

import java.io.Serializable;

public abstract class BoolExpr implements Serializable {
    public BoolExpr() {
    }

    public<T> T apply(BoolExprVisitor<T> visitor)
    {
        return applyImpl(visitor);
    }

    protected abstract <T> T applyImpl(BoolExprVisitor<T> visitor);


    @Override
    public String toString()
    {
        return this.<String>apply(BoolExprNusmvRepresenter.Instance);
    }
}

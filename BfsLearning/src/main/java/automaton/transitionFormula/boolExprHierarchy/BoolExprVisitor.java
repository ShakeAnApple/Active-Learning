package automaton.transitionFormula.boolExprHierarchy;


public interface BoolExprVisitor<T> {
    T visitAnd(BoolAndExpr boolAndExpr);

    T visitOr(BoolOrExpr boolOrExpr);

    T visitVar(BoolVarExpr boolExprVar);
}

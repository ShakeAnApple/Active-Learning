package automaton.transitionFormula.boolExprHierarchy;

import java.util.stream.Collectors;

public class BoolExprNusmvRepresenter implements BoolExprVisitor<String> {
    public static final BoolExprNusmvRepresenter Instance = new BoolExprNusmvRepresenter();

    private BoolExprNusmvRepresenter() { }

    @Override
    public String visitAnd(BoolAndExpr boolAndExpr) {
        return "(" + String.join(" & ", boolAndExpr.getChildren().stream().map(c -> c.apply(this)).collect(Collectors.toList())) + ")";
    }

    @Override
    public String visitOr(BoolOrExpr boolOrExpr) {
        return "(" + String.join(" | ", boolOrExpr.getChildren().stream().map(c -> c.apply(this)).collect(Collectors.toList())) + ")";
    }

    @Override
    public String visitVar(BoolVarExpr boolExprVar) {
        return boolExprVar.isInverted() ? boolExprVar.getName() + " = 0 " : boolExprVar.getName() + " = 1 ";
    }
}

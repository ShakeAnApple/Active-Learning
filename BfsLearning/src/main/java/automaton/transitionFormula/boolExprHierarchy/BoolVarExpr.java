package automaton.transitionFormula.boolExprHierarchy;

class BoolVarExpr extends BoolExpr{
    private String _name;
    private Integer _index;
    private boolean _inverted;

    public String getName() {
        return _name;
    }

    public Integer getIndex() {
        return _index;
    }

    public boolean isInverted() {
        return _inverted;
    }

    public BoolVarExpr(String name, Integer index, boolean inverted) {
        _name = name;
        _index = index;
        _inverted = inverted;
    }

    @Override
    protected <T> T applyImpl(BoolExprVisitor<T> visitor) {
        return visitor.visitVar(this);
    }
}


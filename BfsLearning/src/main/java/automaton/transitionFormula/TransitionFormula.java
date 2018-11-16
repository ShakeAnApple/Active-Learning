package automaton.transitionFormula;

import automaton.transitionFormula.boolExprHierarchy.BoolExpr;
import automaton.transitionFormula.boolExprHierarchy.BooleanExprParser;
import utils.logging.Log;

import java.io.Serializable;

public class TransitionFormula implements Serializable {

    private BoolExpr _root;

    private TransitionFormula(BoolExpr root){
        _root = root;
    }

    @Override
    public String toString() {
        return _root.toString();
    }

    public static TransitionFormula parse(String str){
        BooleanExprParser parser = new BooleanExprParser();
        BoolExpr expr = parser.tryParse(str);
        if (expr == null){
            Log.msg("Cannot parse transition " + str);
        }
        return new TransitionFormula(expr);
    }
}

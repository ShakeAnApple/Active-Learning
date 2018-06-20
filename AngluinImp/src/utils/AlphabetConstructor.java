package utils;

import values.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AlphabetConstructor {
    public static List<Symbol> construct(List<VariableValue> variableValues){
        List<Symbol> result = new ArrayList<Symbol>();

        List<VariableValue> sortedVarValues = variableValues
                .stream()
                .sorted(Comparator.comparing(v -> v.getVarInfo().getOrder()))
                .collect(Collectors.toList());

        int capacity = 1;

        for (int i = 0; i < sortedVarValues.size(); i++) {
            capacity *= sortedVarValues.get(i).getVarInfo().getPossibleValues().size();
        }

        for (int i = 0; i < capacity; i++) {
            result.add(new Symbol(sortedVarValues.stream().map(v -> v.clone()).collect(Collectors.toList())));
        }

        for (int varIdx = 0, repeatCnt = 1; varIdx < sortedVarValues.size(); varIdx++) {

            AbstractVariableInfo curVar = sortedVarValues.get(varIdx).getVarInfo();
            List<AbstractValueHolder> possibleValues = curVar.getPossibleValues();

            int curSymbIdx = 0;
            for (int i = 0; i < result.size(); i+=possibleValues.size()*repeatCnt) {

                for (int posValueIdx = 0; posValueIdx < possibleValues.size(); posValueIdx++) {

                    for (int counter = 0; counter<repeatCnt; counter++) {
                        result.get(curSymbIdx).setVariableValueByName(curVar.getName(), possibleValues.get(posValueIdx));
                        curSymbIdx ++;
                    }
                }
            }

            repeatCnt *= possibleValues.size();

        }

        return result;
    }
}

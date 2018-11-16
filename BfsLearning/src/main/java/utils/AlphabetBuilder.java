package utils;

import values.AbstractValueHolder;
import values.Symbol;
import values.AbstractVariableInfo;
import values.VariableValue;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AlphabetBuilder {

    public List<Symbol> build(List<AbstractVariableInfo> variableInfos){
        List<Symbol> result = new ArrayList<Symbol>();

        List<AbstractVariableInfo> sortedVariableInfos = variableInfos
                .stream()
                .sorted(Comparator.comparing(AbstractVariableInfo::getOrder))
                .collect(Collectors.toList());

        int capacity = 1;

        for (int i = 0; i < sortedVariableInfos.size(); i++) {
            capacity *= sortedVariableInfos.get(i).getPossibleValues().size();
        }

        for (int i = 0; i < capacity; i++) {
            List<VariableValue> varVals = sortedVariableInfos.stream()
                    .map(vInfo -> new VariableValue(vInfo))
                    .collect(Collectors.toList());
            result.add(new Symbol(varVals));
        }

        for (int varIdx = 0, repeatCnt = 1; varIdx < sortedVariableInfos.size(); varIdx++) {

            AbstractVariableInfo curVar = sortedVariableInfos.get(varIdx);
            List<AbstractValueHolder> possibleValues = curVar.getPossibleValues();

            int curSymbIdx = 0;
            for (int i = 0; i < result.size(); i+=possibleValues.size()*repeatCnt) {

                for (int posValueIdx = 0; posValueIdx < possibleValues.size(); posValueIdx++) {

                    for (int counter = 0; counter<repeatCnt; counter++) {
                        result.get(curSymbIdx).setVariableValueByName(curVar.getName(), possibleValues.get(posValueIdx).clone());
                        curSymbIdx ++;
                    }
                }
            }

            repeatCnt *= possibleValues.size();
        }

        return result;
    }
}

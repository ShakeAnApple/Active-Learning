package simulation;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StateHistoryItem {
    private List<VariableHistoryItem> _variableHistoryItems;
    private int _iterationsCount;

    public StateHistoryItem(List<VariableHistoryItem> variableHistoryItems, int iterationsCount){
        _variableHistoryItems = variableHistoryItems
                .stream()
                .sorted(Comparator.comparing(v -> v.getVariableValue().getVarInfo().getOrder()))
                .collect(Collectors.toList());

        _iterationsCount = iterationsCount;
    }

    public int getIterationsCount() {
        return _iterationsCount;
    }

    public boolean areStateContinuousVarsDifferent(StateHistoryItem otherState){
        List<VariableHistoryItem> otherVars = otherState.getVariableHistoryItems();

        for (int i = 0; i < otherVars.size(); i++){
            if (!otherVars.get(i).getValue().getCurrentInterval().equals(_variableHistoryItems.get(i).getValue().getCurrentInterval())){
                return true;
            }
        }

        return false;
    }

    public List<VariableHistoryItem> getVariableHistoryItems(){
        return _variableHistoryItems;
    }
}

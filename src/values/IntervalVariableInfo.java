package values;

import config.VariableInfoDefinition;
import utils.Tuple;

public class IntervalVariableInfo extends AbstractVariableInfo<IntervalValueHolder> {

    private int _valIdx;

    public IntervalVariableInfo(VariableInfoDefinition def){
        _valIdx = -1;
        init(def);
    }

    private Tuple<Interval, Integer> getCorrespondingInterval(double val){
        for(int i = 0; i< _possibleValues.size(); i++){
            Interval curInterval = _possibleValues.get(i).getValue();
            if (curInterval.contains(val)){
                return new Tuple<>(curInterval, i);
            }
        }
        return null;
    }

    @Override
    public IntervalValueHolder tryParseValue(Object obj) {
        if (obj instanceof String){
            String v = (String)obj;
            Double concreteValue = Double.parseDouble(v);
            Tuple<Interval, Integer> intervalAndNum = getCorrespondingInterval(concreteValue);
            return new IntervalValueHolder(intervalAndNum.getObj1(), concreteValue, intervalAndNum.getObj2());
        }
        return null;
    }

    @Override
    public IntervalValueHolder tryParseConfigValue(String s) {
        // order type name [a,b) [c,d);

        _valIdx ++;
        String[] intervalStrMembers = s.replaceAll("\\[|\\)", "").split(",");
        Double from = Double.parseDouble(intervalStrMembers[0]);
        Double to = Double.parseDouble(intervalStrMembers[1]);
        return new IntervalValueHolder(new Interval(from, to), from, _valIdx);
    }
}

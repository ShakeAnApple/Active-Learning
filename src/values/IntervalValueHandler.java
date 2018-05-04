package values;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * Created by Eskimos on 17.01.2018.
 */
public class IntervalValueHandler extends AbstractValueHandler<Interval> {

    private Interval[] _intervals;
    private Interval _currentInterval;
    private Double _concreteValue;

    private Integer _currentIntervalNum;

    public IntervalValueHandler(){}

    public IntervalValueHandler(Interval[] intervals, Double value){
        _intervals = intervals;
        _concreteValue = value;

        setCurrentInterval();
    }

    public IntervalValueHandler(Interval[] intervals, Interval interval){
        _intervals = intervals;

        setCurrentInterval(interval);
    }

    private void setCurrentInterval() {
        if (_concreteValue == null){
            _currentInterval = null;
            return;
        }

        for(int i = 0; i< _intervals.length; i++){
            if (_intervals[i].contains(_concreteValue)){
                _currentInterval = _intervals[i];
                _currentIntervalNum = i;
                break;
            }
        }
    }

    public void setCurrentInterval(Interval interval){
        _currentInterval = interval;
        _concreteValue = interval.getFrom();

        for(int i = 0; i< _intervals.length; i++){
            if (_currentInterval.equals(_intervals[i])){
                _currentIntervalNum = i;
            }
        }
    }

    public Interval getCurrentInterval() {
        return _currentInterval;
    }

    @Override
    protected void parseAndSetImpl(Object val) {
        if (val instanceof String){
            String v = (String)val;
            _concreteValue = Double.parseDouble(v);

            setCurrentInterval();
        }
    }

    @Override
    public String toString(){
        return _concreteValue.toString();
    }

    @Override
    public AbstractValueHandler<Interval> clone() {
        return new IntervalValueHandler(_intervals, Double.valueOf(_concreteValue));
    }

    @Override
    protected VariableInfo<AbstractValueHandler<Interval>> tryParseImpl(String val) {
        // order type name [a,b) [c,d);
        String[] sMembers = val.split(" ");

        int order = Integer.parseInt(sMembers[0]);
        String name = sMembers[2];

        Interval[] intervals = new Interval[sMembers.length - 3];
        for (int i = 3; i < sMembers.length; i++) {
            String[] intervalStrMembers = sMembers[i].replaceAll("\\[|\\)", "").split(",");
            intervals[i - 3] = new Interval(Double.parseDouble(intervalStrMembers[0]), Double.parseDouble(intervalStrMembers[1]));
        }
        List<AbstractValueHandler<Interval>> possibleValues = new ArrayList<>();
        for (Interval interval : intervals){
            possibleValues.add(new IntervalValueHandler(intervals, interval));
        }

        return new VariableInfo<AbstractValueHandler<Interval>>(name, order, possibleValues, () -> new IntervalValueHandler(intervals, intervals[0]));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof IntervalValueHandler)){
            return false;
        }

        IntervalValueHandler otherInterval = (IntervalValueHandler) obj;
        return _currentInterval.equals(otherInterval._currentInterval);
    }

    public Double getConcreteValue() {
        return _concreteValue;
    }

    public Integer getCurrentIntervalNum() {
        return _currentIntervalNum;
    }
}

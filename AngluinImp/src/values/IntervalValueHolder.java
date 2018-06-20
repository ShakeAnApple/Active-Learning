package values;

import java.io.Serializable;

/**
 * Created by Eskimos on 17.01.2018.
 */
public class IntervalValueHolder extends AbstractValueHolder<Interval> implements Serializable {

    private Interval _currentInterval;
    private Double _concreteValue;

    private Integer _currentIntervalNum;

    public Interval getCurrentInterval() {
        return _currentInterval;
    }

    public IntervalValueHolder(Interval interval, Double concreteValue, int curIntervalNum){
        _currentInterval = interval;
        _concreteValue = concreteValue;
        _currentIntervalNum = curIntervalNum;
    }

    @Override
    public Interval getValue() {
        return _currentInterval;
    }

    @Override
    public String toString(){
        return _concreteValue.toString();
    }

    @Override
    public String toStringWithIntervalNum(){
        return _currentIntervalNum.toString();
    }

    @Override
    public AbstractValueHolder<Interval> clone() {
        return new IntervalValueHolder(_currentInterval, Double.valueOf(_concreteValue), _currentIntervalNum);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof IntervalValueHolder)){
            return false;
        }

        IntervalValueHolder otherInterval = (IntervalValueHolder) obj;
        return _currentInterval.equals(otherInterval._currentInterval);
    }

    public Double getConcreteValue() {
        return _concreteValue;
    }

    public Integer getCurrentIntervalNum() {
        return _currentIntervalNum;
    }
}

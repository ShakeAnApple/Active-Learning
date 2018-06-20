package values;

import java.io.Serializable;

/**
 * Created by Eskimos on 17.01.2018.
 */
public class Interval implements Serializable {
    private Double _from;
    private Double _to;

    public Interval(Double from, Double to){
        _from = from;
        _to = to;
    }

    public Double getFrom(){
        return _from;
    }

    public Double getTo(){
        return _to;
    }

    public boolean contains(Double value) {
        return value >= _from && value < _to;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Interval)){
            return false;
        }
        Interval otherInterval = (Interval)obj;

        return this._from.equals(otherInterval._from) && this._to.equals(otherInterval._to);
    }
}

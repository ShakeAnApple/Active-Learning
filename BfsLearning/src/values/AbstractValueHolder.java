package values;

public abstract class AbstractValueHolder<VType>{

    public abstract VType getValue();

    public abstract AbstractValueHolder<VType> clone();
}

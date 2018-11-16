package values;

import java.io.Serializable;

public abstract class AbstractValueHolder<VType> implements Serializable {

    public abstract VType getValue();

    public abstract AbstractValueHolder<VType> clone();
}

package cognitive.provider;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class AbstractDataProvider<T> {

    public void saveParameter(T data)
    {

    }

    public List<T> getDataInRange(Date start,Date end){
        return new ArrayList<>();
    }

}

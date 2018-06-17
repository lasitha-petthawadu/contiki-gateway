package cognitive;

public class AnomalyException extends Exception {

    private AnomalyDataItem anomalyDataItem;
    public AnomalyException(AnomalyDataItem anomalyDataPoint){
        this.anomalyDataItem = anomalyDataPoint;
    }
}

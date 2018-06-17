package cognitive;

import org.jfree.data.general.Dataset;
import org.jfree.data.xy.IntervalXYDataset;
import sensors.SimData;

import java.util.List;
import java.util.Set;

public interface DataClassificationStrategyInterface {
    void classifyData(SimData datapoint) throws AnomalyException;
    Set<String> getFeatures();
    IntervalXYDataset getChartUIDatasetForFeature(String feature);
}

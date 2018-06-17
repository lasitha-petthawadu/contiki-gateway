package cognitive;

import cognitive.models.HistogramModel;
import org.jfree.data.statistics.SimpleHistogramBin;
import org.jfree.data.statistics.SimpleHistogramDataset;
import org.jfree.data.xy.IntervalXYDataset;
import sensors.SimData;

import java.util.HashMap;
import java.util.Set;

public class HistogramClassificationStrategy implements DataClassificationStrategyInterface {
    HashMap<String,SimpleHistogramDataset> featureHistograms = new HashMap<>();
    HashMap<String,HistogramModel> featureHistogramModel = new HashMap<>();

    public HistogramClassificationStrategy(){
        System.out.println("Created Strategy");
    }
    private synchronized void checkAndCreateFeatureHistogram(SimData datapoint) {
        System.out.println("Number of Features "+featureHistograms.keySet().size());
        System.out.println("Current Type :"+datapoint.getType());

        if (!featureHistograms.containsKey(datapoint.getType())){
            System.out.println("Doesn't contain feature...");
            SimpleHistogramDataset simpleHistogramDataset = new SimpleHistogramDataset(datapoint.getType());
            featureHistograms.put(datapoint.getType(),simpleHistogramDataset);
            createBins(simpleHistogramDataset);

            //featureHistogramModel.put(datapoint.getType(),new HistogramModel());
        }
    }

    private synchronized void createBins(SimpleHistogramDataset simpleHistogramDataset) {
        int j=10;
        simpleHistogramDataset.clearObservations();
        simpleHistogramDataset.removeAllBins();
        simpleHistogramDataset.setAdjustForBinSize(false);
        for(int i=0;i<10;i++){
            simpleHistogramDataset.addBin(new SimpleHistogramBin((j-10),j-1,true,true));
            j=j+10;
        }
    }

    @Override
    public synchronized void classifyData(SimData datapoint) throws AnomalyException {
        checkAndCreateFeatureHistogram(datapoint);

        SimpleHistogramDataset histogram = featureHistograms.get(datapoint.getType());
        histogram.addObservation(datapoint.getDataPoint());
        //System.out.println("Observation Included :"+datapoint.getDataPoint());

        //HistogramModel histogramModel = featureHistogramModel.get(datapoint.getType());

        // Compare against learned data
        // throw anomalyexception if histogram changes
        //
    }

    private void identifyStage(){

    }

    private void executeState(){

    }


    @Override
    public Set<String> getFeatures(){
        return featureHistograms.keySet();
    }

    @Override
    public IntervalXYDataset getChartUIDatasetForFeature(String feature) {
        return featureHistograms.get(feature);
    }
}

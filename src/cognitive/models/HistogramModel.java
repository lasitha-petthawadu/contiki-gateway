package cognitive.models;

import cognitive.provider.AbstractDataModel;
import org.jfree.data.statistics.SimpleHistogramBin;
import sensors.SimData;

import java.util.ArrayList;

public class HistogramModel extends AbstractDataModel<SimData> {
    private String type;
    private ArrayList<SimpleHistogramBin> bins;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<SimpleHistogramBin> getBins() {
        return bins;
    }

    public void setBins(ArrayList<SimpleHistogramBin> bins) {
        this.bins = bins;
    }
}

package cognitive.models;

import org.jfree.data.statistics.SimpleHistogramBin;

import java.util.ArrayList;

public class HistogramModel {
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

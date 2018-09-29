package cognitive.stages;

import cognitive.HistogramClassificationStrategy;
import com.itemanalysis.psychometrics.histogram.BinCalculationType;
import com.itemanalysis.psychometrics.histogram.Histogram;
import com.itemanalysis.psychometrics.histogram.HistogramType;
import gatewayhub.GatewayHubDataModel;
import gatewayhub.GatewayState;
import sensors.SimData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

public class LearningStage implements DetectionStageInterface {

    private String dataPath = "/home/user/data-files/gateway-data/";
    private GatewayHubDataModel dataModel;
    private HistogramClassificationStrategy histogramClassificationStrategy = new HistogramClassificationStrategy();
    private String startTime;
    private Date dt_windowsEnd;
    private int repeatCycle = 1*60; // 1 Minutes
    private Histogram histogram =new Histogram(HistogramType.DENSITY, BinCalculationType.FREEDMAN_DIACONIS,false);

    public LearningStage() {

    }

    @Override
    public void captureData(SimData datapoint) {


                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd.hh:mm:ss");
                try {
                    dt_windowsEnd = (dt_windowsEnd == null)? new Date(dateFormat.parse(datapoint.getTime()).getTime()+(repeatCycle*1000)) : dt_windowsEnd;
                    if (dt_windowsEnd.before(dateFormat.parse(datapoint.getTime()))){
                            // Query the data set from InfluxDB specifying the timestamp
                            // Read Stabilization data.
                            // Read Variance.
                            // Calculate chi square
                            // Throw exception
                            dt_windowsEnd = null;
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
    }

}



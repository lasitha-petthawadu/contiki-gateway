package cognitive.stages;

import cognitive.HistogramClassificationStrategy;
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
    private Date dt_startTime;
    private int repeatCycle = 10*60; // 10 Minutes


    public LearningStage(GatewayHubDataModel dataModel) {
        this.dataModel = dataModel;
        // Init State
        dataModel.setStateDataItem(GatewayState.LEARNING, "rawLearnDataSet", new LinkedList());
    }

    @Override
    public void captureData(SimData datapoint) {

        if (isLearningCycleCompleted()) {
            LinkedList list = (LinkedList) dataModel.getStateDataItem(GatewayState.LEARNING, "rawLearnDataSet");

            if (list.isEmpty()) {
                startTime = datapoint.getTime();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd.hh:mm:ss");
                try {
                    dt_startTime = dateFormat.parse(startTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }else{
                // Create histogram


            }

            list.add(datapoint);

            // Check if

        /* String path = dataPath+"learning/"+String.valueOf(datapoint.getSimId())+"/"+datapoint.getType()+"/"+"data.csv";
        File f =new File(path);
        if (!f.exists()){
            if(!f.getParentFile().exists()){
                f.getParentFile().mkdirs();
            }
            f.createNewFile();
            FileWriter writer = new FileWriter(f);
            writer.write("simId,attribute,value\r\n");
            writer.flush();
            writer.close();
        }
        try {
            histogramClassificationStrategy.classifyData(datapoint);
        } catch (AnomalyException e) {
            e.printStackTrace();
        }
        try {
            Files.write(f.toPath(),(datapoint.getSimId()+","+datapoint.getType()+","+datapoint.getDataPoint()+"\r\n").getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
        }
    }

    public boolean isLearningCycleCompleted() {
        return false;
    }
}



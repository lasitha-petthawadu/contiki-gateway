package gatewayhub;

import cognitive.stages.DetectionStageInterface;
import cognitive.stages.LearningStage;
import sensors.SimData;

import java.io.IOException;
import java.util.HashMap;


public class GatewayDataCapture {
    private GatewayHubDataModel dataModel;
    private HashMap<GatewayState,DetectionStageInterface> stageHandlers = new HashMap<>();

    public GatewayDataCapture() {
        createDataModel();
        stageHandlers.put(GatewayState.LEARNING,new LearningStage(dataModel));

        System.out.println("Created Data Capture");

    }


    public void captureData(SimData datapoint) {
        stageHandlers.get(dataModel.getGatewayStatus()).captureData(datapoint);
    }

    private void createDataModel() {
        setDataModel(new GatewayHubDataModel());
        getDataModel().setGatewayStatus(GatewayState.LEARNING);
    }


    public GatewayHubDataModel getDataModel() {
        return dataModel;
    }

    public void setDataModel(GatewayHubDataModel dataModel) {
        this.dataModel = dataModel;
    }
}

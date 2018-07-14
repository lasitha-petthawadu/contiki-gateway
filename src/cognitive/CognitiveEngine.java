package cognitive;

import cognitive.configuration.EngineConfiguration;
import cognitive.event.CognitiveEngineEventListener;
import cognitive.stages.DetectionStageInterface;
import cognitive.stages.LearningStage;
import cognitive.stages.MonitoringStage;
import sensors.SimData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CognitiveEngine {

    private CognitiveEngineEventListener eventListener;
    private HashMap<String,List<SimData>> nodeData;
    private HashMap<String,Object> statisticsCollection = new HashMap<>();
    private List<DetectionStageInterface> stageList;

    public void setupEngine(EngineConfiguration configuration){
        stageList.add(new LearningStage(null));
        stageList.add(new MonitoringStage());
    }

    public void setEventListener(CognitiveEngineEventListener e){
        eventListener = e;

    }

    public void startEngine(){

    }

    public void ingestDataItem(SimData data){
        List<SimData> simData= new ArrayList<>();
        if (nodeData.containsKey(data.getNodeId())){
            simData = nodeData.get(data.getNodeId());
        }
        simData.add(data);
        analyzeDataPoint(data);
    }

    private void analyzeDataPoint(SimData data) {

    }


}

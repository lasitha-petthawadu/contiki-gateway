package cognitive.event;

import java.awt.event.ActionListener;

public interface CognitiveEngineEventListener {
    void onEngineStarted(CognitiveActionEvent event);
    void onAnomalyDetected(CognitiveActionEvent event);
    void onLearningStabalized(CognitiveActionEvent event);
}



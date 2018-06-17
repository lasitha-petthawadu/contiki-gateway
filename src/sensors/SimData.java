package sensors;

public class SimData {
    private int simId;
    private double dataPoint;
    private String time;
    private String type;
    private String nodeId;
    private String parameterName;


    public int getSimId() {
        return simId;
    }

    public void setSimId(int simId) {
        this.simId = simId;
    }

    public double getDataPoint() {
        return dataPoint;
    }

    public void setDataPoint(double dataPoint) {
        this.dataPoint = dataPoint;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }
}

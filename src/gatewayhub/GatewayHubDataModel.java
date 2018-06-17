package gatewayhub;

import java.util.HashMap;

public class GatewayHubDataModel {
    private GatewayState gatewayStatus;
    private HashMap<GatewayState,HashMap<String,Object>> stateData=new HashMap<>();

    public GatewayState getGatewayStatus() {
        return gatewayStatus;
    }

    public void setGatewayStatus(GatewayState gatewayStatus) {
        this.gatewayStatus = gatewayStatus;
    }

    public void setStateDataItem(GatewayState state,String key, Object value){
        if (!(stateData.containsKey(state))) stateData.put(state, new HashMap<>());
        stateData.get(state).put(key,value);
    }

    public Object getStateDataItem(GatewayState state,String key){
        return stateData.get(state).get(key);
    }
}

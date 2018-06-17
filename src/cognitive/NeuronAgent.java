package cognitive;

import sun.security.provider.MD5;

import java.util.LinkedHashMap;
import java.util.Random;

public class NeuronAgent {

    private int neuronAgentTransmissionInterval = 1;
    public Random rand=new Random();
    private int agentId;

    private LinkedHashMap<Long,byte[]> capturedData = new LinkedHashMap<>();

    public NeuronAgent(){
        this.agentId = rand.nextInt();
    }

    public byte[] getAgentData(){
        return null;
    }

    public int getAgentId(){
        return agentId;
    }

    public int getNeuronAgentTransmissionInterval(){
        return neuronAgentTransmissionInterval;
    }

    public void collectReceivedData(byte[] data){
        capturedData.put(System.currentTimeMillis(),data);
    }
}

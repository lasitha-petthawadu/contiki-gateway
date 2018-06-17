package sensors;

import cognitive.NeuronAgent;
import se.sics.cooja.*;
import se.sics.cooja.interfaces.ApplicationLED;
import se.sics.cooja.interfaces.ApplicationRadio;
import se.sics.cooja.motes.AbstractApplicationMote;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public abstract class AbstractSimSensor extends AbstractApplicationMote {
    protected String sensorType;
    protected SensorConfig config;
    protected NeuronAgent neuronAgent;

    private ApplicationRadio radio = null;
    private ApplicationLED leds = null;
    private DataGenerator generator = new DataGenerator();
    private BufferedReader dataReader;
    private SimData dataPoint = new SimData();

    public AbstractSimSensor(String sensorType) {
        super();
        this.sensorType = sensorType;
        this.neuronAgent = new NeuronAgent();
        log(sensorType+" Mote");
    }

    public AbstractSimSensor(String sensorType,MoteType moteType, Simulation simulation) {
        super(moteType, simulation);
        this.sensorType = sensorType;
        this.neuronAgent = new NeuronAgent();
        initConfig();
    }

    private void initConfig(){
        this.config = new SensorConfig();
        this.config.setDataFilePath("/home/user/data-files/temp-data.txt");
        this.config.setInterval(1000*2);
        this.dataPoint.setSimId(neuronAgent.getAgentId());
        this.dataPoint.setType("temperature");
    }


    @Override
    public void execute(long l) {
        if (radio == null)
        {
            radio = (ApplicationRadio) getInterfaces().getRadio();
            leds=(ApplicationLED) getInterfaces().getLED();
            if (dataReader == null) {
                try {
                    dataReader = new BufferedReader(new FileReader(config.getDataFilePath()));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        simulation.scheduleEvent(
                transmitEvent,
                simulation.getSimulationTime() +
                        ((config.getInterval())*Simulation.MILLISECOND)
        );
    }

    private MoteTimeEvent transmitEvent = new MoteTimeEvent(this, 0) {
        public void execute(long t) {
            try {

                String data;
                if ((data = dataReader.readLine())!=null) {
                    double val = generator.getDataPointWithRandomVariance(Double.parseDouble(data),5);
                    dataPoint.setDataPoint(val);
                 //   log("Transmitting message : "+ msg);
                    radio.startTransmittingPacket(
                            new COOJARadioPacket(generator.createRadioPacket(dataPoint)), 1*Simulation.MILLISECOND);
                }else {
                    log(" End of stream....");
                    dataReader.close();
                }
                tick();
                requestImmediateWakeup();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    public abstract void tick();
    public abstract void packetReceived(byte[] data);
    public abstract void packetSent(byte[] data);


    @Override
    public void receivedPacket(RadioPacket var1){
        neuronAgent.collectReceivedData(var1.getPacketData());
        log("Received Packet");
        packetReceived(var1.getPacketData());
    }

    @Override
    public void sentPacket(RadioPacket var1){
        packetSent(var1.getPacketData());
    }

    public String toString() {
        return "App Rime ABC " + getID();
    }



}

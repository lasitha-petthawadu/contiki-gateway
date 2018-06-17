package gatewayhub;

import se.sics.cooja.MoteType;
import se.sics.cooja.RadioPacket;
import se.sics.cooja.Simulation;
import se.sics.cooja.motes.AbstractApplicationMote;
import sensors.DataGenerator;

import javax.swing.*;
import java.io.IOException;

public class GatewayHub extends AbstractApplicationMote {

    DataGenerator generator = new DataGenerator();
    private sensors.SimData simData;
    private GatewayDataCapture gatewayDataCapture = new GatewayDataCapture();
    private dialoghubControl dialog = new dialoghubControl(simulation.getGUI().getDesktopPane(),gatewayDataCapture);

    public GatewayHub() throws IOException {
        super();

    }

    public GatewayHub(MoteType moteType, Simulation simulation) throws IOException {
        super(moteType, simulation);

        //dialog.setModalityType(Dialog.ModalityType.MODELESS);

        dialog.pack();
        dialog.setVisible(true);
        dialog.setClosable(true);
        dialog.setIconifiable(true);
        dialog.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

        simulation.getGUI().getDesktopPane().add(dialog);

        dialog.moveToFront();
    }

    @Override
    public void execute(long l) {
    }

    @Override
    public void receivedPacket(RadioPacket packet){
        //
        byte[] dataPacket = packet.getPacketData();
        try {

            simData = generator.getObjFromRadioPacket(dataPacket);
            gatewayDataCapture.captureData(simData);

        } catch (Exception e) {
            log("Invalid Data Packet");
        }
        log("Received Data from "+simData.getSimId());
        log("Message : "+simData.getType()+", "+simData.getDataPoint());
    }

    @Override
    public void sentPacket(RadioPacket var1){

    }


}

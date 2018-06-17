package sensors;

import se.sics.cooja.*;
import se.sics.cooja.interfaces.ApplicationLED;
import se.sics.cooja.interfaces.ApplicationRadio;
import se.sics.cooja.motes.AbstractApplicationMote;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class SimTempSensor extends AbstractSimSensor {

    public SimTempSensor() {
        super("temperature");
    }

    public SimTempSensor(MoteType moteType, Simulation simulation) {
        super("temperature", moteType, simulation);
    }

    @Override
    public void tick() {

    }

    @Override
    public void packetReceived(byte[] data) {

    }

    @Override
    public void packetSent(byte[] data) {

    }

}

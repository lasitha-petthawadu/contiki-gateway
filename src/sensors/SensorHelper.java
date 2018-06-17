package sensors;

import tools.HistogramDemo1;
import se.sics.cooja.motes.AbstractApplicationMote;

import javax.swing.*;
import java.awt.*;

public class SensorHelper {
    AbstractApplicationMote mote;
    JDialog panel;
    JPanel chart;
    HistogramDemo1 demo = new HistogramDemo1(
            "JFreeChart: HistogramDemo1.java");
    public SensorHelper(AbstractApplicationMote mote)
    {
        this.mote = mote;
    }

    public void showFrame(){



        if (panel == null) {
            panel = new JDialog(SwingUtilities.getWindowAncestor(this.mote.getSimulation().getGUI().getDesktopPane()));
            chart=HistogramDemo1.createDemoPanel();
        }
        panel.setLayout(new BorderLayout());
        panel.add(chart);
        panel.setBounds(110,110,200,200);
        panel.setVisible(true);
    }

}


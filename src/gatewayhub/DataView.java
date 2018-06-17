package gatewayhub;

import com.sun.xml.internal.ws.util.StringUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.general.Dataset;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.xy.IntervalXYDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class DataView extends JInternalFrame {
    private JPanel contentPane;
    private JButton showChartButton;
    private JComboBox cmbfeatures;
    private JPanel histogramPanel;
    private GatewayDataCapture dataCapture;
    private ChartPanel panel;

    public DataView() {
        setContentPane(contentPane);
        // call onCancel() when cross is clicked
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        // call onCancel() on ESCAPE

        showChartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                addChart(cmbfeatures.getSelectedItem().toString());
            }
        });
    }

    public void setCaptureData(GatewayDataCapture dataCapture){
        this.dataCapture = dataCapture;
    }

    public void addChart(String feature){
      //  IntervalXYDataset data = dataCapture.getClassificationStrategy().getChartUIDatasetForFeature(feature);

       // JFreeChart chart = createHistogram(StringUtils.capitalize(feature), data);
      //  panel = new ChartPanel(chart);
        panel.setMouseWheelEnabled(true);
        panel.setPreferredSize(new java.awt.Dimension(500, 270));

        histogramPanel.add(panel);
        this.pack();
        this.repaint();
      //  this.add(panel);
    }

    private JFreeChart createHistogram(String title, IntervalXYDataset dataset) {
        JFreeChart chart = ChartFactory.createHistogram(
                title,
                null,
                null,
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false);
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setDomainPannable(true);
        plot.setRangePannable(true);
        plot.setForegroundAlpha(0.85f);
        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
        yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        XYBarRenderer renderer = (XYBarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        // flat bars look best...
        renderer.setBarPainter(new StandardXYBarPainter());
        renderer.setShadowVisible(false);
        return chart;
    }


    public static void main(String[] args) {
        DataView dialog = new DataView();
        dialog.setPreferredSize(new Dimension(900,500));
        dialog.pack();
        dialog.setVisible(true);

       // System.exit(0);
    }

}

package gatewayhub;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class dialoghubControl extends JInternalFrame {
    private JPanel contentPane;
    private JButton dataViewButton;
    private JButton anormalyResultsViewButton;
    private JTextArea textArea1;
    private JButton stopLearningButton;
    private JLabel lblStatus;
    private JTextField textField1;
    private JButton loadLearningDataButton;
    private DataView dataView;
    private GatewayDataCapture dataCapture;
    private JDesktopPane desktop;

    public dialoghubControl(JDesktopPane desktop,GatewayDataCapture dataCapture) {
        this.dataCapture = dataCapture;
        setContentPane(contentPane);
        this.desktop = desktop;

      //  lblStatus.setText(this.dataCapture.getDataModel().getGatewayStatus());
        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);


        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        final GatewayDataCapture cp = dataCapture;
        dataViewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dataView = new DataView();
                dataView.setCaptureData(cp);
                dataView.pack();
                dataView.setVisible(true);
                dataView.setClosable(true);
                dataView.setIconifiable(true);
                desktop.add(dataView);
                dataView.moveToFront();
            }
        });
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

}

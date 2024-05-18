package ButtonActionFiles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

public class ReturnPatientCount extends JFrame {
    static JFrame returnPatientCountFrame;
    JPanel returnPatientCountPanel;
    JLabel countLabel;
    JButton closeButton;
    public ReturnPatientCount() throws FileNotFoundException {
        returnPatientCountFrame = new JFrame("Patient System Application - Return Patient Count");
        returnPatientCountFrame.setPreferredSize(new Dimension(500, 500));
        returnPatientCountFrame.setLocation(500, 200);

        returnPatientCountPanel = new JPanel();

        countLabel = new JLabel("Sick count: " + Utils.countPatients("sick", Utils.fileName) + "\nRecovered count: " + Utils.countPatients("recover", Utils.fileName));

        closeButton = new JButton("Close");
        closeButton.addActionListener(new CloseButtonAction());

        returnPatientCountPanel.add(countLabel);
        returnPatientCountPanel.add(closeButton);

        returnPatientCountFrame.add(returnPatientCountPanel);
        returnPatientCountFrame.setResizable(true);
        returnPatientCountFrame.pack();
        returnPatientCountFrame.setVisible(true);
    }
    private static class CloseButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            returnPatientCountFrame.dispose();
        }
    }

}
package ButtonActionFiles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public class ReturnPatientCount extends JFrame {
    public static JFrame returnPatientCountFrame;
    JPanel returnPatientCountPanel;
    JLabel countLabel;
    JButton closeButton;
    public ReturnPatientCount() throws FileNotFoundException, SQLException {
        returnPatientCountFrame = new JFrame("Patient System Application - Return Patient Count");
        returnPatientCountFrame.setPreferredSize(new Dimension(500, 275));
        returnPatientCountFrame.setLocation(500, 200);

        returnPatientCountPanel = new JPanel();

        int[] counts = DatabaseUtils.getPatientCount();
        countLabel = new JLabel("<html>Sick count: " + counts[0] + "<br><br>Recover count: " + counts[1] + "<br><br>Total count: " + counts[2] + "</html>");
        countLabel.setPreferredSize(new Dimension(500, 175));

        closeButton = new JButton("Close");
        closeButton.setPreferredSize(new Dimension(100, 50));
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
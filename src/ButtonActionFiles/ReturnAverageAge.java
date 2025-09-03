package ButtonActionFiles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public class ReturnAverageAge extends JFrame {
    public static JFrame returnAvgAgeFrame;
    JPanel returnAvgAgePanel;
    JLabel avgAgeLabel;
    JButton closeButton;
    public ReturnAverageAge() throws FileNotFoundException, SQLException {
        returnAvgAgeFrame = new JFrame("Patient System Application - Return Average Patient Age");
        returnAvgAgeFrame.setPreferredSize(new Dimension(500, 200));
        returnAvgAgeFrame.setLocation(500, 200);

        returnAvgAgePanel = new JPanel();

        avgAgeLabel = new JLabel("Average Patient Age: " + String.format("%.2f", DatabaseUtils.returnAverageAge()));
        closeButton = new JButton("Close");
        closeButton.addActionListener(new CloseButtonAction());

        returnAvgAgePanel.add(avgAgeLabel);
        returnAvgAgePanel.add(closeButton);

        returnAvgAgeFrame.add(returnAvgAgePanel);
        returnAvgAgeFrame.setResizable(true);
        returnAvgAgeFrame.pack();
        returnAvgAgeFrame.setVisible(true);
    }

    private static class CloseButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            returnAvgAgeFrame.dispose();
        }
    }
}
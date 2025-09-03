package ButtonActionFiles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

public class DownloadCSV extends JFrame {
    public static JFrame downloadCSVFrame;
    JPanel downloadCSVPanel;
    JButton downloadCSVButton;
    JButton closeButton;
    public DownloadCSV() {
        downloadCSVFrame = new JFrame("Patient System Application - Download CSV File");
        downloadCSVFrame.setPreferredSize(new Dimension(500, 500));
        downloadCSVFrame.setLocation(500, 200);

        downloadCSVPanel = new JPanel();

        downloadCSVButton = new JButton("Download CSV");
        downloadCSVButton.addActionListener(new DownloadCSVButtonAction());
        closeButton = new JButton("Close");
        closeButton.addActionListener(new CloseButtonAction());

        downloadCSVPanel.add(downloadCSVButton);
        downloadCSVPanel.add(closeButton);
        downloadCSVFrame.add(downloadCSVPanel);
        downloadCSVFrame.setResizable(true);
        downloadCSVFrame.pack();
        downloadCSVFrame.setVisible(true);
    }

    private static class DownloadCSVButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
//            if (Utils.convertTextToCSV(Utils.FILE_NAME, "patientRecords.csv")) {
//                Utils.copyFileToDownloads("patientRecords.csv");
//                new DownloadCSVSuccess();
//            }
//            else {
//                new DownloadCSVFailure();
//            }
            try {
                if (DatabaseUtils.exportDatabaseEntriesToCSV()) {
                    new DownloadCSVSuccess();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static class CloseButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            downloadCSVFrame.dispose();
        }
    }
}

class DownloadCSVSuccess extends JFrame {
    static JFrame downloadCSVSuccessFrame;

    public DownloadCSVSuccess() {
        downloadCSVSuccessFrame = new JFrame("Patient System Application - Download CSV - Success");
        downloadCSVSuccessFrame.setPreferredSize(new Dimension(500, 100));
        downloadCSVSuccessFrame.setResizable(false);

        JPanel downloadCSVSuccessPanel = new JPanel();
        JLabel downloadCSVSuccessLabel = new JLabel("Success - CSV Downloaded");
        downloadCSVSuccessPanel.setFont(new Font("Calibri", Font.PLAIN, 15));
        downloadCSVSuccessPanel.add(downloadCSVSuccessLabel);

        JButton closeButton = new JButton("Close");
        closeButton.setSize(new Dimension(50, 30));
        closeButton.addActionListener(new CloseButtonClicked());
        downloadCSVSuccessPanel.add(closeButton);

        downloadCSVSuccessFrame.setLocation(600, 402);
        downloadCSVSuccessFrame.add(downloadCSVSuccessPanel);
        downloadCSVSuccessFrame.pack();
        downloadCSVSuccessFrame.setVisible(true);
    }

    private static class CloseButtonClicked implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            downloadCSVSuccessFrame.dispose();
        }
    }
}

class DownloadCSVFailure extends JFrame {
    static JFrame downloadCSVFailureFrame;
    public DownloadCSVFailure() {
        downloadCSVFailureFrame = new JFrame("Patient System Application - Download CSV - Failure");
        downloadCSVFailureFrame.setPreferredSize(new Dimension(500, 100));
        downloadCSVFailureFrame.setResizable(false);

        JPanel downloadCSVFailurePanel = new JPanel();
        JLabel downloadCSVFailureLabel = new JLabel("Failure - CSV not Downloaded");
        downloadCSVFailurePanel.setFont(new Font("Calibri", Font.PLAIN, 15));
        downloadCSVFailurePanel.add(downloadCSVFailureLabel);

        JButton closeButton = new JButton("Close");
        closeButton.setSize(new Dimension(75, 30));
        closeButton.addActionListener(new CloseButtonClicked());
        downloadCSVFailurePanel.add(closeButton);

        downloadCSVFailureFrame.setLocation(600, 402);
        downloadCSVFailureFrame.add(downloadCSVFailurePanel);
        downloadCSVFailureFrame.pack();
        downloadCSVFailureFrame.setVisible(true);
    }

    private static class CloseButtonClicked implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            downloadCSVFailureFrame.dispose();
        }
    }
}

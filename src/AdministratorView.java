import ButtonActionFiles.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public class AdministratorView extends JFrame {
    public static AddPatient addPatientScreen;
    public static DeletePatient deletePatientScreen;
    public static TransferPatient transferPatientScreen;
    public static ReturnPatientCount returnPatientCountScreen;
    public static ReturnAverageAge returnAverageAgeScreen;
    public static GetPatientList getPatientListScreen;
    public static SortPatients sortPatientsScreen;
    public static DownloadCSV downloadCSVScreen;
    public static LogoutScreen logoutScreen;

    JButton addPatientButton;
    JButton deletePatientButton;
    JButton transferPatientButton;
    JButton returnPatientCountButton;
    JButton returnAvgAgeButton;
    JButton getPatientListButton;
    JButton sortPatientButton;
    JButton downloadCSVButton;
    JButton logoutButton;
    JFrame administratorViewFrame;
    static JPanel administratorButtonPanel;
    static JPanel administratorButtonViewPanel;
    public AdministratorView() {
        administratorViewFrame = new JFrame();
        administratorViewFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        administratorViewFrame.setTitle("Patient System Application - Administrator View");

        addPatientButton = new JButton("Add Patient");
        addPatientButton.setFont(new Font("Add Patient", Font.PLAIN, 14));
        addPatientButton.setPreferredSize(new Dimension(123, 40));
        addPatientButton.addActionListener(new AddPatientButtonAction());

        deletePatientButton = new JButton("Delete Patient");
        deletePatientButton.setFont(new Font("Delete Patient", Font.PLAIN, 14));
        deletePatientButton.setPreferredSize(new Dimension(138, 40));
        deletePatientButton.addActionListener(new DeletePatientButtonAction());

        transferPatientButton = new JButton("Transfer Patient");
        transferPatientButton.setFont(new Font("Transfer Patient", Font.PLAIN, 14));
        transferPatientButton.setPreferredSize(new Dimension(153, 40));
        transferPatientButton.addActionListener(new TransferPatientButtonAction());

        returnPatientCountButton = new JButton("Return Patient Count");
        returnPatientCountButton.setFont(new Font("Return Patient Count", Font.PLAIN, 14));
        returnPatientCountButton.setPreferredSize(new Dimension(187, 40));
        returnPatientCountButton.addActionListener(new ReturnPatientCountButtonAction());

        returnAvgAgeButton = new JButton("Return Average Age of Patients");
        returnAvgAgeButton.setFont(new Font("Return Average Age of Patients", Font.PLAIN, 14));
        returnAvgAgeButton.setPreferredSize(new Dimension(257, 40));
        returnAvgAgeButton.addActionListener(new ReturnAvgAgeButtonAction());

        getPatientListButton = new JButton("Get Patient List");
        getPatientListButton.setFont(new Font("Get Patient List", Font.PLAIN, 14));
        getPatientListButton.setPreferredSize(new Dimension(145, 40));
        getPatientListButton.addActionListener(new GetPatientListButtonAction());

        sortPatientButton = new JButton("Sort Patients");
        sortPatientButton.setFont(new Font("Sort Patients", Font.PLAIN, 14));
        sortPatientButton.setPreferredSize(new Dimension(130, 40));
        sortPatientButton.addActionListener(new SortPatientButtonAction());

        downloadCSVButton = new JButton("Download CSV");
        downloadCSVButton.setFont(new Font("Download CSV", Font.PLAIN, 14));
        downloadCSVButton.setPreferredSize(new Dimension(144, 40));
        downloadCSVButton.addActionListener(new DownloadCSVButtonAction());

        logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Logout", Font.PLAIN, 14));
        logoutButton.setPreferredSize(new Dimension(92, 40));
        logoutButton.addActionListener(new LogoutButtonAction());

        administratorButtonPanel = new JPanel();
        administratorButtonPanel.setSize(new Dimension(1440, 50));
        administratorButtonPanel.setBackground(new Color(235, 65, 50));
        administratorButtonPanel.add(addPatientButton);
        administratorButtonPanel.add(deletePatientButton);
        administratorButtonPanel.add(transferPatientButton);
        administratorButtonPanel.add(returnPatientCountButton);
        administratorButtonPanel.add(returnAvgAgeButton);
        administratorButtonPanel.add(getPatientListButton);
        administratorButtonPanel.add(sortPatientButton);
        administratorButtonPanel.add(downloadCSVButton);
        administratorButtonPanel.add(logoutButton);

        JScrollPane administratorButtonPanelScrollPane = new JScrollPane(administratorButtonPanel);
        administratorButtonPanelScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        administratorButtonPanelScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

        JScrollBar horizontalScrollBar = administratorButtonPanelScrollPane.getHorizontalScrollBar();
        horizontalScrollBar.setUnitIncrement(16);
        horizontalScrollBar.setBlockIncrement(32);

        administratorViewFrame.add(administratorButtonPanelScrollPane, BorderLayout.NORTH);
//        administratorViewFrame.add(administratorButtonPanelScrollPane);

        administratorButtonViewPanel = new JPanel();
        administratorButtonViewPanel.setSize(new Dimension(1440, 755));
        administratorButtonViewPanel.setBackground(Color.LIGHT_GRAY);

//        administratorViewFrame.add(administratorButtonPanel);
        administratorViewFrame.add(administratorButtonViewPanel, BorderLayout.CENTER);
        administratorViewFrame.setResizable(true);
        administratorViewFrame.pack();
        administratorViewFrame.setVisible(true);
    }

    private static class AddPatientButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (addPatientScreen == null || !addPatientScreen.addPatientFrame.isShowing()) {
                addPatientScreen = new AddPatient();
            }
            else {
                addPatientScreen.addPatientFrame.toFront();
                addPatientScreen.addPatientFrame.requestFocus();
            }
        }
    }

    private static class DeletePatientButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (deletePatientScreen == null || !deletePatientScreen.deletePatientFrame.isShowing()) {
                deletePatientScreen = new DeletePatient();
            }
            else {
                deletePatientScreen.deletePatientFrame.toFront();
                deletePatientScreen.deletePatientFrame.requestFocus();
            }
        }
    }

    private static class TransferPatientButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (transferPatientScreen == null || !transferPatientScreen.transferPatientFrame.isShowing()) {
                transferPatientScreen = new TransferPatient();
            }
            else {
                transferPatientScreen.transferPatientFrame.toFront();
                transferPatientScreen.transferPatientFrame.requestFocus();
            }
        }
    }

    private static class ReturnPatientCountButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (returnPatientCountScreen == null || !returnPatientCountScreen.returnPatientCountFrame.isShowing()) {
                try {
                    returnPatientCountScreen = new ReturnPatientCount();
                } catch (FileNotFoundException | SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            else {
                returnPatientCountScreen.returnPatientCountFrame.toFront();
                returnPatientCountScreen.returnPatientCountFrame.requestFocus();
            }
        }
    }

    private static class ReturnAvgAgeButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (returnAverageAgeScreen == null || !returnAverageAgeScreen.returnAvgAgeFrame.isShowing()) {
                try {
                    returnAverageAgeScreen = new ReturnAverageAge();
                } catch (FileNotFoundException | SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            else {
                returnAverageAgeScreen.returnAvgAgeFrame.toFront();
                returnAverageAgeScreen.returnAvgAgeFrame.requestFocus();
            }
        }
    }

    private static class GetPatientListButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (getPatientListScreen == null || !getPatientListScreen.getPatientListFrame.isShowing()) {
                try {
                    getPatientListScreen = new GetPatientList();
                } catch (FileNotFoundException | SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            else {
                getPatientListScreen.getPatientListFrame.toFront();
                getPatientListScreen.getPatientListFrame.requestFocus();
            }
        }
    }

    private static class SortPatientButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (sortPatientsScreen == null || !sortPatientsScreen.sortPatientsFrame.isShowing()) {
                sortPatientsScreen = new SortPatients();
            }
            else {
                sortPatientsScreen.sortPatientsFrame.toFront();
                sortPatientsScreen.sortPatientsFrame.requestFocus();
            }
        }
    }

    private static class DownloadCSVButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (downloadCSVScreen == null || !downloadCSVScreen.downloadCSVFrame.isShowing()) {
                downloadCSVScreen = new DownloadCSV();
            }
            else {
                downloadCSVScreen.downloadCSVFrame.toFront();
                downloadCSVScreen.downloadCSVFrame.requestFocus();
            }
        }
    }

    private static class LogoutButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (logoutScreen == null || !logoutScreen.logoutFrame.isShowing()) {
                logoutScreen = new LogoutScreen();
            }
            else {
                logoutScreen.logoutFrame.toFront();
                logoutScreen.logoutFrame.requestFocus();
            }
        }
    }
}
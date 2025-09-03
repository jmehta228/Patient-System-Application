package ButtonActionFiles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.sql.SQLException;

import static ButtonActionFiles.DatabaseUtils.*;

public class SortPatients extends JFrame {
    public static JFrame sortPatientsFrame;
    JPanel sortPatientsPanel;
    JButton sortPatientsByAgeButton;
    JButton sortPatientsByIDButton;
    JButton sortPatientsByFirstNameButton;
    JButton sortPatientsByLastNameButton;
    JButton closeButton;
    public SortPatients() {
        sortPatientsFrame = new JFrame("Patient System Application - Sort Patients");
        sortPatientsFrame.setPreferredSize(new Dimension(500, 500));
        sortPatientsFrame.setLocation(500, 200);

        sortPatientsPanel = new JPanel();

        sortPatientsByIDButton = new JButton("Sort by ID");
        sortPatientsByIDButton.setPreferredSize(new Dimension(400, 50));

        sortPatientsByAgeButton = new JButton("Sort by age");
        sortPatientsByAgeButton.setPreferredSize(new Dimension(400, 50));

        sortPatientsByFirstNameButton = new JButton("Sort by First Name");
        sortPatientsByFirstNameButton.setPreferredSize(new Dimension(400, 50));

        sortPatientsByLastNameButton = new JButton("Sort by Last Name");
        sortPatientsByLastNameButton.setPreferredSize(new Dimension(400, 50));

        closeButton = new JButton("Close");
        closeButton.setPreferredSize(new Dimension(400, 75));

        sortPatientsByIDButton.addActionListener(new SortPatientsByIDButtonAction());
        sortPatientsByAgeButton.addActionListener(new SortPatientsByAgeButtonAction());
        sortPatientsByFirstNameButton.addActionListener(new SortPatientsByFirstName());
        sortPatientsByLastNameButton.addActionListener(new SortPatientsByLastName());
        closeButton.addActionListener(new CloseButtonAction());

        sortPatientsPanel.add(sortPatientsByIDButton);
        sortPatientsPanel.add(sortPatientsByAgeButton);
        sortPatientsPanel.add(sortPatientsByFirstNameButton);
        sortPatientsPanel.add(sortPatientsByLastNameButton);
        sortPatientsPanel.add(closeButton);

        sortPatientsFrame.add(sortPatientsPanel);
        sortPatientsFrame.setResizable(true);
        sortPatientsFrame.pack();
        sortPatientsFrame.setVisible(true);
    }

    private static class SortPatientsByIDButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                sortPatientsByID();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private static class SortPatientsByAgeButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {

        }
    }
    private static class SortPatientsByFirstName implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                sortPatientsByName("First");
            } catch (SQLException | FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private static class SortPatientsByLastName implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                sortPatientsByName("Last");
            } catch (SQLException | FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private static class CloseButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            sortPatientsFrame.dispose();
        }
    }
}

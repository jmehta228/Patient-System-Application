package ButtonActionFiles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

public class SortPatients extends JFrame {
    static JFrame sortPatientsFrame;
    JPanel sortPatientsPanel;
    JButton sortPatientsByAgeButton;
    JButton sortPatientsByFirstNameButton;
    JButton sortPatientsByLastNameButton;
    JButton closeButton;
    public SortPatients() {
        sortPatientsFrame = new JFrame("Patient System Application - Sort Patients");
        sortPatientsFrame.setPreferredSize(new Dimension(500, 500));
        sortPatientsFrame.setLocation(500, 200);

        sortPatientsPanel = new JPanel();

        sortPatientsByAgeButton = new JButton("Sort by age");
        sortPatientsByFirstNameButton = new JButton("Sort by first name");
        sortPatientsByLastNameButton = new JButton("Sort by last name");
        closeButton = new JButton("Close");

        sortPatientsByAgeButton.addActionListener(new SortPatientsByAgeButtonAction());
        sortPatientsByFirstNameButton.addActionListener(new SortPatientsByFirstName());
        sortPatientsByLastNameButton.addActionListener(new SortPatientsByLastName());
        closeButton.addActionListener(new CloseButtonAction());

//        sortPatientsPanel.add(sortPatientsByAgeButton);
        sortPatientsPanel.add(sortPatientsByFirstNameButton);
        sortPatientsPanel.add(sortPatientsByLastNameButton);
        sortPatientsPanel.add(closeButton);

        sortPatientsFrame.add(sortPatientsPanel);
        sortPatientsFrame.setResizable(true);
        sortPatientsFrame.pack();
        sortPatientsFrame.setVisible(true);
    }

    private static class SortPatientsByAgeButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                Utils.sortPatientsByAge(Utils.fileName);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private static class SortPatientsByFirstName implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                Utils.sortPatientsByName("first", Utils.fileName);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private static class SortPatientsByLastName implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                Utils.sortPatientsByName("last", Utils.fileName);
            } catch (FileNotFoundException e) {
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

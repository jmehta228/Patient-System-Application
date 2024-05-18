import ButtonActionFiles.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

public class AdministratorView extends JFrame {
    JButton addPatientButton;
    JButton deletePatientButton;
    JButton transferPatientButton;
    JButton returnPatientCountButton;
    JButton returnAvgAgeButton;
    JButton getPatientListButton;
    JButton sortPatientButton;
    JButton shufflePatientsButton;
    JButton logoutButton;
    JFrame administratorViewFrame;
    static JPanel administratorButtonPanel;
    static JPanel administratorButtonViewPanel;
    public AdministratorView() {
        administratorViewFrame = new JFrame();
        administratorViewFrame.setPreferredSize(new Dimension(1440, 805));
        administratorViewFrame.setResizable(false);
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

        shufflePatientsButton = new JButton("Shuffle Patients");
        shufflePatientsButton.setFont(new Font("Shuffle Patients", Font.PLAIN, 14));
        shufflePatientsButton.setPreferredSize(new Dimension(150, 40));
        shufflePatientsButton.addActionListener(new ShufflePatientButtonAction());

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
        administratorButtonPanel.add(shufflePatientsButton);
        administratorButtonPanel.add(logoutButton);

        administratorButtonViewPanel = new JPanel();
        administratorButtonViewPanel.setSize(new Dimension(1440, 755));
        administratorButtonViewPanel.setBackground(Color.LIGHT_GRAY);

        administratorViewFrame.add(administratorButtonPanel);
        administratorViewFrame.add(administratorButtonViewPanel);
        administratorViewFrame.setResizable(true);
        administratorViewFrame.pack();
        administratorViewFrame.setVisible(true);
    }

    private static class AddPatientButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            new AddPatient();
        }
    }

    private static class DeletePatientButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            new DeletePatient();
        }
    }

    private static class TransferPatientButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            new TransferPatient();
        }
    }

    private static class ReturnPatientCountButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                new ReturnPatientCount();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static class ReturnAvgAgeButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                new ReturnAvgAge();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static class GetPatientListButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                new GetPatientList();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static class SortPatientButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {

        }
    }

    private static class ShufflePatientButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {

        }
    }

    private static class LogoutButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            System.exit(0);
        }
    }

}

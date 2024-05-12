import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdministratorView extends JFrame {
    JButton addPatientButton;
    JButton deletePatientButton;
    JButton transferPatientButton;
    JButton returnCountOfPatientButton;
    JButton returnAvgAgeButton;
    JButton sortPatientButton;
    JButton shufflePatientsButton;
    JButton logoutButton;
    JFrame administratorViewFrame;
    JPanel administratorButtonPanel;
    JPanel administratorButtonActionViewPanel;
    public AdministratorView() {
        administratorViewFrame = new JFrame();
        administratorViewFrame.setPreferredSize(new Dimension(1440, 805));
        administratorViewFrame.setResizable(false);
        administratorViewFrame.setTitle("Patient System Application - Administrator View");

        addPatientButton = new JButton("Add Patient");
        addPatientButton.setBounds(50, 50, 120, 50);
        addPatientButton.addActionListener(new AddPatientButtonAction());

        deletePatientButton = new JButton("Delete Patient");
        deletePatientButton.setBounds(50, 120, 120, 50);
        deletePatientButton.addActionListener(new DeletePatientButtonAction());

        transferPatientButton = new JButton("Transfer Patient");
        transferPatientButton.setBounds(50, 190, 120, 50);
        transferPatientButton.addActionListener(new TransferPatientButtonAction());

        returnCountOfPatientButton = new JButton("Return Patient Count");
        returnCountOfPatientButton.setBounds(50, 260, 120, 50);
        returnCountOfPatientButton.addActionListener(new ReturnCountOfPatientButtonAction());

        returnAvgAgeButton = new JButton("Return Average Age of Patients");
        returnAvgAgeButton.setBounds(50, 330, 120, 50);
        returnAvgAgeButton.addActionListener(new ReturnAvgAgeButtonAction());

        sortPatientButton = new JButton("Sort Patients");
        sortPatientButton.setBounds(50, 400, 120, 50);
        sortPatientButton.addActionListener(new SortPatientButtonAction());

        shufflePatientsButton = new JButton("Shuffle Patients");
        shufflePatientsButton.setBounds(50, 470, 120, 50);
        shufflePatientsButton.addActionListener(new ShufflePatientButtonAction());

        logoutButton = new JButton("Logout");
        logoutButton.setBounds(50, 540, 120, 50);
        logoutButton.addActionListener(new LogoutButtonAction());

        administratorButtonPanel = new JPanel();
        administratorButtonPanel.setSize(new Dimension(1440, 50));
        administratorButtonPanel.setBackground(Color.GRAY);
        administratorButtonPanel.add(addPatientButton);
        administratorButtonPanel.add(deletePatientButton);
        administratorButtonPanel.add(transferPatientButton);
        administratorButtonPanel.add(returnCountOfPatientButton);
        administratorButtonPanel.add(returnAvgAgeButton);
        administratorButtonPanel.add(sortPatientButton);
        administratorButtonPanel.add(shufflePatientsButton);
        administratorButtonPanel.add(logoutButton);

        administratorButtonActionViewPanel = new JPanel();
        administratorButtonActionViewPanel.setSize(new Dimension(1440, 755));
        administratorButtonActionViewPanel.setBackground(Color.LIGHT_GRAY);

        administratorViewFrame.add(administratorButtonPanel);
        administratorViewFrame.add(administratorButtonActionViewPanel);
        administratorViewFrame.setResizable(true);
        administratorViewFrame.pack();
        administratorViewFrame.setVisible(true);
    }

    private static class AddPatientButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {

        }
    }

    private static class DeletePatientButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {

        }
    }

    private static class TransferPatientButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {

        }
    }

    private static class ReturnCountOfPatientButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {

        }
    }

    private static class ReturnAvgAgeButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {

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
            
        }
    }

}

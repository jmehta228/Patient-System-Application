package ButtonActionFiles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class DeletePatient extends JFrame {
    public static JFrame deletePatientFrame;
    JLabel IDLabel;
    static JTextField IDField;
    JLabel firstNameLabel;
    static JTextField firstNameField;
    JLabel lastNameLabel;
    static JTextField lastNameField;
    JLabel birthdateLabel;
    static JTextField birthdateField;
    JButton submitButton;
    JButton closeButton;
    JPanel deletePatientPanel;
    public DeletePatient() {
        deletePatientFrame = new JFrame("Patient System Application - Delete Patient");
        deletePatientFrame.setPreferredSize(new Dimension(500, 500));
        deletePatientFrame.setLocation(500, 200);

        deletePatientPanel = new JPanel();

        IDLabel = new JLabel("ID");
        IDField = new JTextField(40);

        firstNameLabel = new JLabel("First Name");
        firstNameField = new JTextField(40);

        lastNameLabel = new JLabel("Last Name");
        lastNameField = new JTextField(40);

        birthdateLabel = new JLabel("Birthdate (MM/DD/YYYY)");
        birthdateField = new JTextField(40);

        submitButton = new JButton("Submit");
        submitButton.setPreferredSize(new Dimension(100, 50));
        submitButton.addActionListener(new SubmitButtonAction());

        closeButton = new JButton("Close");
        closeButton.setPreferredSize(new Dimension(100, 50));
        closeButton.addActionListener(new CloseButtonAction());

        deletePatientPanel = new JPanel();
        deletePatientPanel.add(IDLabel);
        deletePatientPanel.add(IDField);
        deletePatientPanel.add(firstNameLabel);
        deletePatientPanel.add(firstNameField);
        deletePatientPanel.add(lastNameLabel);
        deletePatientPanel.add(lastNameField);
        deletePatientPanel.add(birthdateLabel);
        deletePatientPanel.add(birthdateField);
        deletePatientPanel.add(submitButton);
        deletePatientPanel.add(closeButton);

        deletePatientFrame.add(deletePatientPanel);
        deletePatientFrame.setResizable(true);
        deletePatientFrame.pack();
        deletePatientFrame.setVisible(true);
    }

    private static class SubmitButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            String IDText = IDField.getText();
            String firstNameText = firstNameField.getText();
            String lastNameText = lastNameField.getText();
            String birthdateText = birthdateField.getText();
            if (IDText.isEmpty() || firstNameText.isEmpty() || lastNameText.isEmpty() || birthdateText.isEmpty()) {
                new DeletePatientFailure();
            }
            else {
                try {
                    if (DatabaseUtils.deletePatientFromDatabase(IDText, firstNameText, lastNameText, birthdateText) > 0) {
                        new DeletePatientSuccess();
                    }
                    else {
                        new DeletePatientFailure();
                    }
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            IDField.setText("");
            firstNameField.setText("");
            lastNameField.setText("");
            birthdateField.setText("");
        }
    }

    private static class CloseButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            deletePatientFrame.dispose();
        }
    }
}

class DeletePatientSuccess extends JFrame {
    static JFrame deletePatientSuccessFrame;
    public DeletePatientSuccess() {
        deletePatientSuccessFrame = new JFrame("Patient System Application - Delete Patient - Success");
        deletePatientSuccessFrame.setPreferredSize(new Dimension(400, 100));
        deletePatientSuccessFrame.setResizable(false);

        JPanel deletePatientSuccessPanel = new JPanel();
        JLabel deletePatientSuccessLabel = new JLabel("Success - Patient deleted");
        deletePatientSuccessPanel.setFont(new Font("Calibri", Font.PLAIN, 15));
        deletePatientSuccessPanel.add(deletePatientSuccessLabel);

        JButton closeButton = new JButton("Close");
        closeButton.setSize(new Dimension(50, 30));
        closeButton.addActionListener(new CloseButtonClicked());
        deletePatientSuccessPanel.add(closeButton);

        deletePatientSuccessFrame.setLocation(600, 402);
        deletePatientSuccessFrame.add(deletePatientSuccessPanel);
        deletePatientSuccessFrame.pack();
        deletePatientSuccessFrame.setVisible(true);
    }

    private static class CloseButtonClicked implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            deletePatientSuccessFrame.dispose();
        }
    }
}

class DeletePatientFailure extends JFrame {
    static JFrame deletePatientFailureFrame;
    public DeletePatientFailure() {
        deletePatientFailureFrame = new JFrame("Patient System Application - Delete Patient - Failure");
        deletePatientFailureFrame.setPreferredSize(new Dimension(400, 100));
        deletePatientFailureFrame.setResizable(false);

        JPanel deletePatientFailurePanel = new JPanel();
        JLabel deletePatientFailureLabel = new JLabel("Failure - Patient not deleted");
        deletePatientFailurePanel.setFont(new Font("Calibri", Font.PLAIN, 15));
        deletePatientFailurePanel.add(deletePatientFailureLabel);

        JButton closeButton = new JButton("Close");
        closeButton.setSize(new Dimension(75, 30));
        closeButton.addActionListener(new CloseButtonClicked());
        deletePatientFailurePanel.add(closeButton);

        deletePatientFailureFrame.setLocation(600, 402);
        deletePatientFailureFrame.add(deletePatientFailurePanel);
        deletePatientFailureFrame.pack();
        deletePatientFailureFrame.setVisible(true);
    }

    private static class CloseButtonClicked implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            deletePatientFailureFrame.dispose();
        }
    }
}
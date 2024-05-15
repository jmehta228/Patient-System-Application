package ButtonActionFiles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class AddPatient extends JFrame {
    static JFrame addPatientFrame;
    JLabel nameLabel;
    static JTextField nameField;
    JLabel birthdateLabel;
    static JTextField birthdateField;
    JButton submitButton;
    static JPanel addPatientPanel;
    public AddPatient() {
        addPatientFrame = new JFrame("Patient System Application - Add Patient");
        addPatientFrame.setPreferredSize(new Dimension(500, 500));
        addPatientFrame.setLocation(500, 200);

        addPatientPanel = new JPanel(new GridLayout(8, 0));

        nameLabel = new JLabel("Name");
        nameField = new JTextField(30);
        nameField.setPreferredSize(new Dimension(30, 20));

        birthdateLabel = new JLabel("Birthdate (MM/DD/YYYY)");
        birthdateField = new JTextField(30);
        birthdateField.setPreferredSize(new Dimension(30, 20));

        submitButton = new JButton("Submit");
        submitButton.addActionListener(new SubmitButtonAction());

        addPatientPanel = new JPanel();
        addPatientPanel.add(nameLabel);
        addPatientPanel.add(nameField);
        addPatientPanel.add(birthdateLabel);
        addPatientPanel.add(birthdateField);
        addPatientPanel.add(submitButton);

        addPatientFrame.add(addPatientPanel);
        addPatientFrame.setResizable(false);
        addPatientFrame.pack();
        addPatientFrame.setVisible(true);
    }

    private static class SubmitButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                if (Utils.addPatient(nameField.getText(), birthdateField.getText(), Utils.fileName).equals("Patient added")) {
                    new AddPatientSuccess();
                }
                else {
                    new AddPatientFailure();
                }
                nameField.setText("");
                birthdateField.setText("");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class AddPatientSuccess extends JFrame {
    static JFrame addPatientSuccessFrame;

    public AddPatientSuccess() {
        addPatientSuccessFrame = new JFrame("Patient System Application - Add Patient - Success");
        addPatientSuccessFrame.setPreferredSize(new Dimension(400, 100));
        addPatientSuccessFrame.setResizable(false);

        JPanel addPatientSuccessPanel = new JPanel();
        JLabel addPatientSuccessLabel = new JLabel("Correct username/password");
        addPatientSuccessPanel.setFont(new Font("Calibri", Font.PLAIN, 15));
        addPatientSuccessPanel.add(addPatientSuccessLabel);

        JButton closeButton = new JButton("Close");
        closeButton.setSize(new Dimension(50, 30));
        closeButton.addActionListener(new CloseButtonClicked());
        addPatientSuccessPanel.add(closeButton);

        addPatientSuccessFrame.setLocation(600, 402);
        addPatientSuccessFrame.add(addPatientSuccessPanel);
        addPatientSuccessFrame.pack();
        addPatientSuccessFrame.setVisible(true);
    }

    private static class CloseButtonClicked implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            addPatientSuccessFrame.dispose();
        }
    }
}

class AddPatientFailure extends JFrame {
    static JFrame addPatientFailureFrame;
    public AddPatientFailure() {
        addPatientFailureFrame = new JFrame("Patient System Application - Add Patient - Failure");
        addPatientFailureFrame.setPreferredSize(new Dimension(400, 100));
        addPatientFailureFrame.setResizable(false);

        JPanel addPatientFailurePanel = new JPanel();
        JLabel addPatientFailureLabel = new JLabel("Incorrect username/password");
        addPatientFailurePanel.setFont(new Font("Calibri", Font.PLAIN, 15));
        addPatientFailurePanel.add(addPatientFailureLabel);

        JButton closeButton = new JButton("Close");
        closeButton.setSize(new Dimension(75, 30));
        closeButton.addActionListener(new CloseButtonClicked());
        addPatientFailurePanel.add(closeButton);

        addPatientFailureFrame.setLocation(600, 402);
        addPatientFailureFrame.add(addPatientFailurePanel);
        addPatientFailureFrame.pack();
        addPatientFailureFrame.setVisible(true);
    }

    private static class CloseButtonClicked implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            addPatientFailureFrame.dispose();
        }
    }
}

package ButtonActionFiles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class TransferPatient extends JFrame {
    JFrame transferPatientFrame;
    JLabel nameLabel;
    static JTextField nameField;
    JLabel birthdateLabel;
    static JTextField birthdateField;
    JButton submitButton;
    JPanel transferPatientPanel;
    public TransferPatient() {
        transferPatientFrame = new JFrame("Patient System Application - Transfer Patient");
        transferPatientFrame.setPreferredSize(new Dimension(500, 500));
        transferPatientFrame.setLocation(500, 200);

        transferPatientPanel = new JPanel();

        nameLabel = new JLabel("Name");
        nameField = new JTextField(40);

        birthdateLabel = new JLabel("Birthdate (MM/DD/YYYY)");
        birthdateField = new JTextField(40);

        submitButton = new JButton("Submit");
        submitButton.setPreferredSize(new Dimension(100, 50));
        submitButton.addActionListener(new SubmitButtonAction());

        transferPatientPanel = new JPanel();
        transferPatientPanel.add(nameLabel);
        transferPatientPanel.add(nameField);
        transferPatientPanel.add(birthdateLabel);
        transferPatientPanel.add(birthdateField);
        transferPatientPanel.add(submitButton);

        transferPatientFrame.add(transferPatientPanel);
        transferPatientFrame.setResizable(true);
        transferPatientFrame.pack();
        transferPatientFrame.setVisible(true);
    }

    private static class SubmitButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                String nameText = nameField.getText();
                String birthdateText = birthdateField.getText();
                if (nameText.isEmpty() || birthdateText.isEmpty()) {
                    new TransferPatientFailure();
                }
                else if (Utils.transferPatient(nameText, birthdateText, Utils.fileName)) {
                    new TransferPatientSuccess();
                }
                else if (!Utils.transferPatient(nameText, birthdateText, Utils.fileName)) {
                    new TransferPatientFailure();
                }
                nameField.setText("");
                birthdateField.setText("");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class TransferPatientSuccess extends JFrame {
    static JFrame transferPatientSuccessFrame;
    public TransferPatientSuccess() {
        transferPatientSuccessFrame = new JFrame("Patient System Application - Transfer Patient - Success");
        transferPatientSuccessFrame.setPreferredSize(new Dimension(500, 100));
        transferPatientSuccessFrame.setResizable(false);

        JPanel transferPatientSuccessPanel = new JPanel();
        JLabel transferPatientSuccessLabel = new JLabel("Success - Patient transferred");
        transferPatientSuccessPanel.setFont(new Font("Calibri", Font.PLAIN, 15));
        transferPatientSuccessPanel.add(transferPatientSuccessLabel);

        JButton closeButton = new JButton("Close");
        closeButton.setSize(new Dimension(50, 30));
        closeButton.addActionListener(new CloseButtonClicked());
        transferPatientSuccessPanel.add(closeButton);

        transferPatientSuccessFrame.setLocation(600, 402);
        transferPatientSuccessFrame.add(transferPatientSuccessPanel);
        transferPatientSuccessFrame.pack();
        transferPatientSuccessFrame.setVisible(true);
    }

    private static class CloseButtonClicked implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            transferPatientSuccessFrame.dispose();
        }
    }
}

class TransferPatientFailure extends JFrame {
    static JFrame transferPatientFailureFrame;
    public TransferPatientFailure() {
        transferPatientFailureFrame = new JFrame("Patient System Application - Transfer Patient - Failure");
        transferPatientFailureFrame.setPreferredSize(new Dimension(400, 100));
        transferPatientFailureFrame.setResizable(false);

        JPanel transferPatientFailurePanel = new JPanel();
        JLabel transferPatientFailureLabel = new JLabel("Failure - Patient not transferred");
        transferPatientFailurePanel.setFont(new Font("Calibri", Font.PLAIN, 15));
        transferPatientFailurePanel.add(transferPatientFailureLabel);

        JButton closeButton = new JButton("Close");
        closeButton.setSize(new Dimension(75, 30));
        closeButton.addActionListener(new CloseButtonClicked());
        transferPatientFailurePanel.add(closeButton);

        transferPatientFailureFrame.setLocation(600, 402);
        transferPatientFailureFrame.add(transferPatientFailurePanel);
        transferPatientFailureFrame.pack();
        transferPatientFailureFrame.setVisible(true);
    }

    private static class CloseButtonClicked implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            transferPatientFailureFrame.dispose();
        }
    }
}

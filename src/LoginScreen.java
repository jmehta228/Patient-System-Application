import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LoginScreen extends JPanel {
    static JTextField usernameField;
    static JPasswordField passwordField;
    static JButton loginButton;
    static JButton closeButton;
    JPanel loginPanel;

    public LoginScreen() {
        loginPanel = new JPanel(new GridLayout(6, 0));
        JLabel usernameFieldLabel = new JLabel("Username");
        usernameField = new JTextField(30);
        usernameField.setPreferredSize(new Dimension(75, 30));

        JLabel passwordFieldLabel = new JLabel("Password");
        passwordField = new JPasswordField(30);
        passwordField.setPreferredSize(new Dimension(75, 30));

        loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(75, 30));
        loginButton.addActionListener(new LoginButtonAction());

        closeButton = new JButton("Close");
        closeButton.setPreferredSize(new Dimension(75, 30));
        closeButton.addActionListener(new CloseButtonAction());

        loginPanel.add(usernameFieldLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordFieldLabel);
        loginPanel.add(passwordField);
        loginPanel.add(loginButton);
        loginPanel.add(closeButton);

        add(loginPanel);
    }

    private boolean validLogin(String username, String password) throws FileNotFoundException {
        String fileName = "logins.txt";
        File file = new File(fileName);
        Scanner fileReader = new Scanner(file);
        while (fileReader.hasNextLine()) {
            String line = fileReader.nextLine();
            String[] lineItems = line.split("\\s+");
            if (lineItems[0].equals(username) && lineItems[1].equals(password)) {
                return true;
            }
        }
        return false;
    }

    private class LoginButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            String usernameString = usernameField.getText();
            String passwordString = passwordField.getText();
            try {
                if (validLogin(usernameString, passwordString)) {
                    usernameField.setEnabled(false);
                    passwordField.setEnabled(false);
                    loginButton.setEnabled(false);
                    new AdministratorView();
                    PatientSystemApplication.mainFrame.dispose();
                }
                else {
                    usernameField.setEnabled(false);
                    passwordField.setEnabled(false);
                    loginButton.setEnabled(false);
                    new LoginFailureScreen();
                }
            }
            catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            usernameField.setText("");
            passwordField.setText("");
        }
    }

    private static class CloseButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            System.exit(0);
        }
    }
}

class LoginFailureScreen extends JPanel {
    JFrame loginFailureFrame;
    public LoginFailureScreen() {
        loginFailureFrame = new JFrame();
        loginFailureFrame.setPreferredSize(new Dimension(250, 100));
        loginFailureFrame.setResizable(false);
        loginFailureFrame.setTitle("Patient System Application");

        JPanel loginFailurePanel = new JPanel();
        JLabel loginFailureLabel = new JLabel("Incorrect username/password");
        loginFailureLabel.setFont(new Font("Calibri", Font.PLAIN, 15));
        loginFailurePanel.add(loginFailureLabel);

        JButton closeButton = new JButton("Close");
        closeButton.setSize(new Dimension(75, 30));
        closeButton.addActionListener(new CloseButtonClicked());
        loginFailurePanel.add(closeButton);

        loginFailureFrame.setLocation(625, 402);
        loginFailureFrame.add(loginFailurePanel);
        loginFailureFrame.pack();
        loginFailureFrame.setVisible(true);
    }

    private class CloseButtonClicked implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            loginFailureFrame.dispose();
            LoginScreen.usernameField.setEnabled(true);
            LoginScreen.passwordField.setEnabled(true);
            LoginScreen.loginButton.setEnabled(true);
        }
    }
}

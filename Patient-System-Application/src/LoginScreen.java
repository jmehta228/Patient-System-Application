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
    JPanel loginPanel = new JPanel(new GridLayout(5, 0));

    public LoginScreen() {
        setBackground(new Color(240, 240, 230));
        loginPanel.setBackground(new Color(240, 240, 230));

        JLabel usernameFieldLabel = new JLabel("Username");
        usernameField = new JTextField(30);
        usernameField.setSize(new Dimension(75, 30));

        JLabel passwordFieldLabel = new JLabel("Password");
        passwordField = new JPasswordField(30);
        passwordField.setSize(new Dimension(75, 30));

        loginButton = new JButton("Login");
        loginButton.setSize(new Dimension(75, 30));
        loginButton.addActionListener(new loginButtonAction());

        add(loginPanel);

        loginPanel.add(usernameFieldLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordFieldLabel);
        loginPanel.add(passwordField);
        loginPanel.add(loginButton);
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

    private class loginButtonAction implements ActionListener {
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
}

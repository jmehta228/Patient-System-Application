import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LoginScreen extends JPanel {
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    JPanel loginPanel = new JPanel(new GridLayout(5, 0));

    public LoginScreen() {
        // initialize text-fields/labels/buttons
        JLabel usernameFieldLabel = new JLabel("Username");
        usernameField = new JTextField(30);
        usernameField.setSize(new Dimension(75, 30));

        JLabel passwordFieldLabel = new JLabel("Password");
        passwordField = new JPasswordField(30);
        passwordField.setSize(new Dimension(75, 30));

        JButton loginButton = new JButton("Login");
        loginButton.setSize(new Dimension(75, 30));
        loginButton.addActionListener(new loginButtonAction());

        add(loginPanel);

        loginPanel.add(usernameField);
        loginPanel.add(usernameFieldLabel);
        loginPanel.add(passwordField);
        loginPanel.add(passwordFieldLabel);
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
                System.out.println("Login found"); // comment out
                return true;
            }
        }
        System.out.println("Login not found"); // comment out
        return false;
    }

    private class loginButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            String usernameString = usernameField.getText();
            String passwordString = passwordField.getText();
            try {
                if (validLogin(usernameString, passwordString)) {
                    JOptionPane.showMessageDialog(null, "Login Successful");
                    new AdministratorView();
                } else {
                    JOptionPane.showMessageDialog(null, "Login Failed. Please try again.");
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

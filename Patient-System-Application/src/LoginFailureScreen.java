import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFailureScreen extends JPanel {
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
        closeButton.addActionListener(new closeButtonClicked());
        loginFailurePanel.add(closeButton);

        loginFailureFrame.setLocation(625, 402);
        loginFailureFrame.add(loginFailurePanel);
        loginFailureFrame.pack();
        loginFailureFrame.setVisible(true);
    }

    private class closeButtonClicked implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            loginFailureFrame.dispose();
            LoginScreen.usernameField.setEnabled(true);
            LoginScreen.passwordField.setEnabled(true);
            LoginScreen.loginButton.setEnabled(true);
        }
    }
}

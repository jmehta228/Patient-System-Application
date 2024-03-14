import javax.swing.*;
import java.awt.*;

public class LoginFailureScreen extends JPanel {
    public LoginFailureScreen() {
        JFrame loginFailureFrame = new JFrame();
        loginFailureFrame.setPreferredSize(new Dimension(250, 200));
        loginFailureFrame.setResizable(false);
        loginFailureFrame.setTitle("Patient System Application Version 1.0");

        JPanel loginFailurePanel = new JPanel();
        JLabel loginFailureLabel = new JLabel("Login failure!");
        loginFailureLabel.setFont(new Font("Calibri", Font.PLAIN, 20));
        loginFailurePanel.add(loginFailureLabel);

        loginFailureFrame.add(loginFailurePanel);
        loginFailureFrame.setResizable(true);
        loginFailureFrame.pack();
        loginFailureFrame.setVisible(true);
    }
}

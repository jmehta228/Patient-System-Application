import javax.swing.*;
import java.awt.*;

public class PatientSystemApplication extends JFrame {
    public static void main(String[] args) {
        new LoadingWindow();
        JFrame mainFrame = new JFrame("Patient System Application");
        mainFrame.setPreferredSize(new Dimension(500, 500));
        mainFrame.setLocation(500, 200);
        mainFrame.add(new LoginScreen());
        mainFrame.setResizable(false);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }
}

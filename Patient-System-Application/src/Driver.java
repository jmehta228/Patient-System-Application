import javax.swing.*;
import javax.xml.datatype.Duration;
import java.awt.*;

public class PatientSystemApplication extends JFrame {
    public static void main(String[] args) {
        new LoadingWindow();
        JFrame mainFrame = new JFrame("Patient System Application");
        mainFrame.setPreferredSize(new Dimension(500, 500)); // 1440 805
        mainFrame.setLocation(500, 200);
        mainFrame.add(new LoginScreen());
        mainFrame.setBackground(new Color(150, 150, 150));
        mainFrame.setResizable(false);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }
}

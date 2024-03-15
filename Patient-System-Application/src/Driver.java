import javax.swing.*;
import java.awt.*;

public class Driver extends JFrame {
    public static void main(String[] args) {
        new LoadingWindow();
        JFrame mainFrame = new JFrame("Patient System Application");
        mainFrame.setPreferredSize(new Dimension(500, 500)); // 1440 805
        mainFrame.setLocation(500, 200);
        mainFrame.add(new LoginScreen());
//        mainFrame.setBackground(new Color(0, 255, 180));
        mainFrame.setBackground(new Color(245, 245, 220));
        mainFrame.setResizable(false);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }
}

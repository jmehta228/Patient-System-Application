import javax.swing.*;
import java.awt.*;

public class PatientSystemApplication {
    public static void main(String[] args) {
        JFrame mainScreen = new JFrame("Patient System Application Version 1.0");
        mainScreen.setPreferredSize(new Dimension(500, 500)); // 1440 805
        mainScreen.setLocation(500, 200);
        mainScreen.add(new LoginScreen());
//        mainScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainScreen.setResizable(true);
        mainScreen.pack();
        mainScreen.setVisible(true);
    }
}

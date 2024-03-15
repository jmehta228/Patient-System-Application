import javax.swing.*;
import java.awt.*;

public class LoadingWindow extends JWindow {

    public LoadingWindow() {
        JWindow loadingWindow = new JWindow();
        String loadingWindowString = "<html><center>Patient System Application</center><p style='font-size: 12px;'>March 2024<br>Version 1.0</p></html>";
        JLabel loadingWindowLabel = new JLabel(loadingWindowString, SwingConstants.CENTER);
        loadingWindowLabel.setFont(new Font("Calibri", Font.PLAIN, 20));
        loadingWindow.getContentPane().add(loadingWindowLabel);
        loadingWindow.setBounds(500, 150, 525, 350);
        loadingWindow.setVisible(true);
        try {
            Thread.sleep(3000);
        }
        catch (InterruptedException ignored) {}
        loadingWindow.setVisible(false);
        loadingWindow.dispose();
    }
}

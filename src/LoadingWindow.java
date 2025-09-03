import javax.swing.*;
import java.awt.*;

public class LoadingWindow extends JWindow {

    public LoadingWindow() {
        JWindow loadingWindow = new JWindow();
        JPanel contentPane = new JPanel();
        loadingWindow.setContentPane(contentPane);
        JLabel loadingWindowLabel = new JLabel(getLoadingWindowString(), SwingConstants.CENTER);
        loadingWindowLabel.setFont(new Font("Calibri", Font.PLAIN, 20));
        loadingWindow.add(loadingWindowLabel);
        loadingWindow.setBounds(495, 150, 530, 350);
        loadingWindow.getContentPane().setBackground(new Color(255, 80, 80, 255));
        loadingWindow.setVisible(true);

        ImageIcon imageIcon = new ImageIcon("patient-system-application-logo.png");
        JLabel label = new JLabel(imageIcon);
        loadingWindow.getContentPane().add(label);

        try {
            Thread.sleep(3000);
        }
        catch (InterruptedException ignored) {}
        loadingWindow.setVisible(false);
        loadingWindow.dispose();
    }

    private String getLoadingWindowString() {
        return
                "<html>" +
                        "<br><br><br><br>" +
                        "<center>" +
                            "<p style='font-size: 25px;'>" +
                                "Patient System Application" +
                            "<p>" +
                        "</center>" +
                        "<p style='font-size: 10px;'>" +
                            "Version 3.0" +
                        "</p>" +
                "</html>";
    }
}

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
        loadingWindow.setVisible(true);

        ImageIcon imageIcon = new ImageIcon("./images/patient-system-application-logo.png");
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
                            "Version 2.0" +
                        "</p>" +
                "</html>";
    }
}

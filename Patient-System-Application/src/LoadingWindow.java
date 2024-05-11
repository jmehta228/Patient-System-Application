import javax.swing.*;
import java.awt.*;

public class LoadingWindow extends JWindow {

    public LoadingWindow() {
        JWindow loadingWindow = new JWindow();
        JPanel contentPane = new JPanel();
        contentPane.setBackground(new Color(240, 240, 230));
        loadingWindow.setContentPane(contentPane);
        JLabel loadingWindowLabel = new JLabel(getLoadingWindowString(), SwingConstants.CENTER);
        loadingWindowLabel.setFont(new Font("Calibri", Font.PLAIN, 20));
        loadingWindow.getContentPane().add(loadingWindowLabel);
        loadingWindow.setBounds(495, 150, 530, 350);
        loadingWindow.setVisible(true);
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

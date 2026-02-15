import ButtonActionFiles.AppTheme;

import javax.swing.*;
import java.awt.*;

/**
 * Main entry point for the Patient System Application.
 * Initializes the application with modern look and feel.
 */
public class PatientSystemApplication extends JFrame {
    static JFrame mainFrame;

    public static void main(String[] args) {
        // Apply modern look and feel before creating any components
        SwingUtilities.invokeLater(() -> {
            try {
                // Set system look and feel
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

                // Apply custom theme
                AppTheme.applyLookAndFeel();

                // Enable anti-aliasing for text
                System.setProperty("awt.useSystemAAFontSettings", "on");
                System.setProperty("swing.aatext", "true");

            } catch (Exception e) {
                e.printStackTrace();
            }

            // Show splash screen, then open login window
            new LoadingWindow(() -> {
                mainFrame = new JFrame("Patient System Application");
                mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                mainFrame.setPreferredSize(new Dimension(480, 600));
                mainFrame.setMinimumSize(new Dimension(400, 500));
                mainFrame.setBackground(AppTheme.BG_PRIMARY);
                mainFrame.add(new LoginScreen());
                mainFrame.setResizable(false);
                mainFrame.pack();

                // Center on screen
                mainFrame.setLocationRelativeTo(null);

                mainFrame.setVisible(true);
            });
        });
    }
}

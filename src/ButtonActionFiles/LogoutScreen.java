package ButtonActionFiles;

import javax.swing.*;
import java.awt.*;

/**
 * Modern logout confirmation dialog.
 */
public class LogoutScreen {
    public static JFrame logoutFrame;

    public LogoutScreen() {
        createFrame();
    }

    private void createFrame() {
        logoutFrame = new JFrame("Logout");
        logoutFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        logoutFrame.setSize(380, 280);
        logoutFrame.setResizable(false);
        logoutFrame.setLocationRelativeTo(null);
        logoutFrame.setBackground(AppTheme.BG_PRIMARY);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(AppTheme.BG_PRIMARY);

        mainPanel.add(createContent(), BorderLayout.CENTER);
        mainPanel.add(createFooter(), BorderLayout.SOUTH);

        logoutFrame.add(mainPanel);
        logoutFrame.setVisible(true);
    }

    private JPanel createContent() {
        JPanel content = new JPanel();
        content.setBackground(AppTheme.BG_PRIMARY);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(BorderFactory.createEmptyBorder(40, 40, 20, 40));

        // Icon
        JPanel iconPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Circle background
                g2.setColor(new Color(AppTheme.WARNING.getRed(), AppTheme.WARNING.getGreen(),
                    AppTheme.WARNING.getBlue(), 30));
                g2.fillOval(0, 0, 60, 60);

                g2.dispose();
            }
        };
        iconPanel.setOpaque(false);
        iconPanel.setPreferredSize(new Dimension(60, 60));
        iconPanel.setMaximumSize(new Dimension(60, 60));
        iconPanel.setLayout(new GridBagLayout());
        iconPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel iconLabel = new JLabel("\u2190");
        iconLabel.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 28));
        iconLabel.setForeground(AppTheme.WARNING);
        iconPanel.add(iconLabel);

        // Title
        JLabel titleLabel = new JLabel("Logout?");
        titleLabel.setFont(AppTheme.FONT_SUBTITLE);
        titleLabel.setForeground(AppTheme.TEXT_PRIMARY);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Message
        JLabel messageLabel = new JLabel("Are you sure you want to logout?");
        messageLabel.setFont(AppTheme.FONT_BODY);
        messageLabel.setForeground(AppTheme.TEXT_SECONDARY);
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        content.add(iconPanel);
        content.add(Box.createVerticalStrut(20));
        content.add(titleLabel);
        content.add(Box.createVerticalStrut(10));
        content.add(messageLabel);
        content.add(Box.createVerticalGlue());

        return content;
    }

    private JPanel createFooter() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 20));
        footer.setBackground(AppTheme.BG_PRIMARY);
        footer.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, AppTheme.BORDER));

        ModernComponents.SecondaryButton cancelButton = new ModernComponents.SecondaryButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(120, AppTheme.BUTTON_HEIGHT));
        cancelButton.addActionListener(e -> logoutFrame.dispose());

        ModernComponents.ModernButton logoutButton = new ModernComponents.ModernButton("Logout");
        logoutButton.setButtonColors(AppTheme.WARNING, Color.WHITE);
        logoutButton.setPreferredSize(new Dimension(120, AppTheme.BUTTON_HEIGHT));
        logoutButton.addActionListener(e -> {
            logoutFrame.dispose();
            System.exit(0);
        });

        footer.add(cancelButton);
        footer.add(logoutButton);

        return footer;
    }
}

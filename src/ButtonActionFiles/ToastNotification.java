package ButtonActionFiles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Modern toast notification system for displaying success, error, warning, and info messages.
 */
public class ToastNotification extends JWindow {

    public enum ToastType {
        SUCCESS, ERROR, WARNING, INFO
    }

    private static final int TOAST_WIDTH = 350;
    private static final int TOAST_HEIGHT = 60;
    private static final int ANIMATION_DURATION = 300;
    private static final int DISPLAY_DURATION = 3000;

    private float opacity = 0f;
    private Timer fadeInTimer;
    private Timer fadeOutTimer;
    private Timer displayTimer;
    private final ToastType type;
    private final String message;

    public ToastNotification(Window parent, String message, ToastType type) {
        super(parent);
        this.message = message;
        this.type = type;
        init(parent);
    }

    private void init(Window parent) {
        setSize(TOAST_WIDTH, TOAST_HEIGHT);
        setBackground(new Color(0, 0, 0, 0));
        setAlwaysOnTop(true);

        // Position at bottom-right of parent or screen
        if (parent != null && parent.isVisible()) {
            int x = parent.getX() + parent.getWidth() - TOAST_WIDTH - 20;
            int y = parent.getY() + parent.getHeight() - TOAST_HEIGHT - 40;
            setLocation(x, y);
        } else {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            setLocation(screenSize.width - TOAST_WIDTH - 20, screenSize.height - TOAST_HEIGHT - 60);
        }

        // Make the window rounded
        try {
            setShape(new RoundRectangle2D.Double(0, 0, TOAST_WIDTH, TOAST_HEIGHT, 12, 12));
        } catch (UnsupportedOperationException e) {
            // Shaping not supported on this platform
        }

        // Content panel
        JPanel contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Background
                g2.setColor(getBackgroundColor());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);

                // Left accent bar
                g2.setColor(getAccentColor());
                g2.fillRoundRect(0, 0, 4, getHeight(), 4, 4);

                g2.dispose();
            }
        };
        contentPanel.setLayout(new BorderLayout(AppTheme.SPACING_MD, 0));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(
            AppTheme.SPACING_MD, AppTheme.SPACING_LG,
            AppTheme.SPACING_MD, AppTheme.SPACING_MD));
        contentPanel.setOpaque(false);

        // Icon
        JLabel iconLabel = new JLabel(getIcon());
        iconLabel.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 20));
        iconLabel.setForeground(getAccentColor());

        // Message
        JLabel messageLabel = new JLabel("<html><body style='width: 240px'>" + message + "</body></html>");
        messageLabel.setFont(AppTheme.FONT_BODY);
        messageLabel.setForeground(AppTheme.TEXT_PRIMARY);

        // Close button
        JButton closeButton = new JButton("\u2715");
        closeButton.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 12));
        closeButton.setForeground(AppTheme.TEXT_MUTED);
        closeButton.setBorderPainted(false);
        closeButton.setContentAreaFilled(false);
        closeButton.setFocusPainted(false);
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeButton.setPreferredSize(new Dimension(24, 24));
        closeButton.addActionListener(e -> fadeOut());
        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                closeButton.setForeground(AppTheme.TEXT_PRIMARY);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                closeButton.setForeground(AppTheme.TEXT_MUTED);
            }
        });

        contentPanel.add(iconLabel, BorderLayout.WEST);
        contentPanel.add(messageLabel, BorderLayout.CENTER);
        contentPanel.add(closeButton, BorderLayout.EAST);

        add(contentPanel);
    }

    private Color getBackgroundColor() {
        switch (type) {
            case SUCCESS: return AppTheme.SUCCESS_LIGHT;
            case ERROR: return AppTheme.ERROR_LIGHT;
            case WARNING: return AppTheme.WARNING_LIGHT;
            case INFO: return AppTheme.INFO_LIGHT;
            default: return AppTheme.BG_CARD;
        }
    }

    private Color getAccentColor() {
        switch (type) {
            case SUCCESS: return AppTheme.SUCCESS;
            case ERROR: return AppTheme.ERROR;
            case WARNING: return AppTheme.WARNING;
            case INFO: return AppTheme.INFO;
            default: return AppTheme.PRIMARY;
        }
    }

    private String getIcon() {
        switch (type) {
            case SUCCESS: return "\u2713"; // Checkmark
            case ERROR: return "\u2717";   // X mark
            case WARNING: return "\u26A0"; // Warning triangle
            case INFO: return "\u2139";    // Info
            default: return "\u2139";
        }
    }

    public void showToast() {
        setVisible(true);
        fadeIn();
    }

    private void fadeIn() {
        fadeInTimer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                opacity += 0.1f;
                if (opacity >= 1f) {
                    opacity = 1f;
                    fadeInTimer.stop();
                    startDisplayTimer();
                }
                try {
                    setOpacity(opacity);
                } catch (Exception ex) {
                    // Opacity not supported
                    fadeInTimer.stop();
                    startDisplayTimer();
                }
            }
        });
        fadeInTimer.start();
    }

    private void startDisplayTimer() {
        displayTimer = new Timer(DISPLAY_DURATION, e -> fadeOut());
        displayTimer.setRepeats(false);
        displayTimer.start();
    }

    private void fadeOut() {
        if (displayTimer != null) {
            displayTimer.stop();
        }
        if (fadeInTimer != null) {
            fadeInTimer.stop();
        }

        fadeOutTimer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                opacity -= 0.1f;
                if (opacity <= 0f) {
                    opacity = 0f;
                    fadeOutTimer.stop();
                    dispose();
                }
                try {
                    setOpacity(opacity);
                } catch (Exception ex) {
                    // Opacity not supported
                    fadeOutTimer.stop();
                    dispose();
                }
            }
        });
        fadeOutTimer.start();
    }

    // ===== Static convenience methods =====

    public static void showSuccess(Window parent, String message) {
        SwingUtilities.invokeLater(() -> {
            ToastNotification toast = new ToastNotification(parent, message, ToastType.SUCCESS);
            toast.showToast();
        });
    }

    public static void showError(Window parent, String message) {
        SwingUtilities.invokeLater(() -> {
            ToastNotification toast = new ToastNotification(parent, message, ToastType.ERROR);
            toast.showToast();
        });
    }

    public static void showWarning(Window parent, String message) {
        SwingUtilities.invokeLater(() -> {
            ToastNotification toast = new ToastNotification(parent, message, ToastType.WARNING);
            toast.showToast();
        });
    }

    public static void showInfo(Window parent, String message) {
        SwingUtilities.invokeLater(() -> {
            ToastNotification toast = new ToastNotification(parent, message, ToastType.INFO);
            toast.showToast();
        });
    }

    // For showing toast without parent (uses screen position)
    public static void showSuccess(String message) {
        showSuccess(null, message);
    }

    public static void showError(String message) {
        showError(null, message);
    }

    public static void showWarning(String message) {
        showWarning(null, message);
    }

    public static void showInfo(String message) {
        showInfo(null, message);
    }
}

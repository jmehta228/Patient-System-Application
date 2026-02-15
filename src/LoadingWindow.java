import ButtonActionFiles.AppTheme;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Modern splash screen displayed during application startup.
 */
public class LoadingWindow extends JWindow {

    private static final int WIDTH = 500;
    private static final int HEIGHT = 350;
    private static final int UPDATE_INTERVAL_MS = 25;
    private static final int MIN_DISPLAY_MS = 2500;

    private final JProgressBar progressBar = new JProgressBar(0, 100);
    private Timer progressTimer;
    private long startTimeMs;
    private final Runnable onFinished;

    public LoadingWindow() {
        this(null);
    }

    public LoadingWindow(Runnable onFinished) {
        this.onFinished = onFinished;
        this.startTimeMs = System.currentTimeMillis();

        // Create main panel with gradient background
        JPanel contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                // Gradient background
                GradientPaint gradient = new GradientPaint(
                    0, 0, AppTheme.PRIMARY,
                    0, getHeight(), AppTheme.PRIMARY_DARK
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                // Decorative circles
                g2d.setColor(new Color(255, 255, 255, 15));
                g2d.fillOval(-50, -50, 200, 200);
                g2d.fillOval(getWidth() - 100, getHeight() - 150, 200, 200);

                g2d.dispose();
            }
        };
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        // Center panel for content
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 30, 50));

        // Logo/Icon area
        JLabel iconLabel = new JLabel();
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        try {
            ImageIcon originalIcon = new ImageIcon("images/patient-system-application-logo.png");
            if (originalIcon.getIconWidth() > 0) {
                Image scaledImage = originalIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
                iconLabel.setIcon(new ImageIcon(scaledImage));
            } else {
                // Fallback: create a medical cross icon
                iconLabel.setText("+");
                iconLabel.setFont(new Font("Arial", Font.BOLD, 60));
                iconLabel.setForeground(Color.WHITE);
            }
        } catch (Exception e) {
            // Fallback: create a medical cross icon
            iconLabel.setText("+");
            iconLabel.setFont(new Font("Arial", Font.BOLD, 60));
            iconLabel.setForeground(Color.WHITE);
        }

        // Application title
        JLabel titleLabel = new JLabel("Patient System");
        titleLabel.setFont(new Font(AppTheme.FONT_FAMILY, Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Subtitle
        JLabel subtitleLabel = new JLabel("Healthcare Management Solution");
        subtitleLabel.setFont(new Font(AppTheme.FONT_FAMILY, Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(255, 255, 255, 180));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Progress bar
        progressBar.setPreferredSize(new Dimension(300, 4));
        progressBar.setMaximumSize(new Dimension(300, 4));
        progressBar.setBorderPainted(false);
        progressBar.setBackground(new Color(255, 255, 255, 50));
        progressBar.setForeground(Color.WHITE);
        progressBar.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Loading text
        JLabel loadingLabel = new JLabel("Loading...");
        loadingLabel.setFont(new Font(AppTheme.FONT_FAMILY, Font.PLAIN, 12));
        loadingLabel.setForeground(new Color(255, 255, 255, 150));
        loadingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Version label
        JLabel versionLabel = new JLabel("Version 3.0");
        versionLabel.setFont(new Font(AppTheme.FONT_FAMILY, Font.PLAIN, 11));
        versionLabel.setForeground(new Color(255, 255, 255, 100));
        versionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add components
        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(iconLabel);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(titleLabel);
        centerPanel.add(Box.createVerticalStrut(8));
        centerPanel.add(subtitleLabel);
        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(progressBar);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(loadingLabel);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(versionLabel);
        centerPanel.add(Box.createVerticalStrut(10));

        contentPane.add(centerPanel, BorderLayout.CENTER);

        // Window setup
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);

        // Make window rounded
        try {
            setShape(new RoundRectangle2D.Double(0, 0, WIDTH, HEIGHT, 20, 20));
        } catch (UnsupportedOperationException e) {
            // Shaping not supported
        }

        setVisible(true);

        // Animate progress bar without blocking the EDT
        progressTimer = new Timer(UPDATE_INTERVAL_MS, e -> {
            long elapsed = System.currentTimeMillis() - startTimeMs;
            int progress = (int) Math.min(100, (elapsed * 100) / MIN_DISPLAY_MS);
            progressBar.setValue(progress);

            if (elapsed >= MIN_DISPLAY_MS) {
                progressTimer.stop();
                setVisible(false);
                dispose();
                if (onFinished != null) {
                    SwingUtilities.invokeLater(onFinished);
                }
            }
        });
        progressTimer.start();
    }
}

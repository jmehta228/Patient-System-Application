package ButtonActionFiles;

import javax.swing.*;
import java.awt.*;

/**
 * Modern Export Data dialog for downloading patient data as CSV.
 */
public class DownloadCSV extends JFrame {
    public static JFrame downloadCSVFrame;
    private ModernComponents.ModernButton downloadButton;

    public DownloadCSV() {
        createFrame();
    }

    private void createFrame() {
        downloadCSVFrame = new JFrame("Export Data");
        downloadCSVFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        downloadCSVFrame.setSize(420, 320);
        downloadCSVFrame.setResizable(false);
        downloadCSVFrame.setLocationRelativeTo(null);
        downloadCSVFrame.setBackground(AppTheme.BG_PRIMARY);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(AppTheme.BG_PRIMARY);

        mainPanel.add(createHeader(), BorderLayout.NORTH);
        mainPanel.add(createContent(), BorderLayout.CENTER);
        mainPanel.add(createFooter(), BorderLayout.SOUTH);

        downloadCSVFrame.add(mainPanel);
        downloadCSVFrame.setVisible(true);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel();
        header.setBackground(AppTheme.PRIMARY);
        header.setPreferredSize(new Dimension(0, 80));
        header.setLayout(new BorderLayout());
        header.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JLabel titleLabel = new JLabel("Export Data");
        titleLabel.setFont(AppTheme.FONT_SUBTITLE);
        titleLabel.setForeground(Color.WHITE);

        JLabel subtitleLabel = new JLabel("Download patient records as CSV file");
        subtitleLabel.setFont(AppTheme.FONT_SMALL);
        subtitleLabel.setForeground(new Color(255, 255, 255, 180));

        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.add(titleLabel);
        textPanel.add(Box.createVerticalStrut(5));
        textPanel.add(subtitleLabel);

        header.add(textPanel, BorderLayout.WEST);
        return header;
    }

    private JPanel createContent() {
        JPanel content = new JPanel();
        content.setBackground(AppTheme.BG_PRIMARY);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(BorderFactory.createEmptyBorder(30, 30, 20, 30));

        // Info card
        ModernComponents.CardPanel infoCard = new ModernComponents.CardPanel();
        infoCard.setLayout(new BorderLayout(15, 0));
        infoCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        infoCard.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Icon
        JPanel iconPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(AppTheme.PRIMARY.getRed(), AppTheme.PRIMARY.getGreen(),
                    AppTheme.PRIMARY.getBlue(), 30));
                g2.fillRoundRect(0, 0, 50, 50, 10, 10);
                g2.dispose();
            }
        };
        iconPanel.setOpaque(false);
        iconPanel.setPreferredSize(new Dimension(50, 50));
        iconPanel.setLayout(new GridBagLayout());

        JLabel iconLabel = new JLabel("\u21E9");
        iconLabel.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 24));
        iconLabel.setForeground(AppTheme.PRIMARY);
        iconPanel.add(iconLabel);

        // Text
        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Export to CSV");
        titleLabel.setFont(AppTheme.FONT_BODY_BOLD);
        titleLabel.setForeground(AppTheme.TEXT_PRIMARY);

        JLabel descLabel = new JLabel("<html>The file will be saved to your<br>Downloads folder.</html>");
        descLabel.setFont(AppTheme.FONT_SMALL);
        descLabel.setForeground(AppTheme.TEXT_SECONDARY);

        textPanel.add(titleLabel);
        textPanel.add(Box.createVerticalStrut(5));
        textPanel.add(descLabel);

        infoCard.add(iconPanel, BorderLayout.WEST);
        infoCard.add(textPanel, BorderLayout.CENTER);

        content.add(infoCard);
        content.add(Box.createVerticalGlue());

        return content;
    }

    private JPanel createFooter() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        footer.setBackground(AppTheme.BG_PRIMARY);
        footer.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, AppTheme.BORDER));

        ModernComponents.SecondaryButton cancelButton = new ModernComponents.SecondaryButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(AppTheme.BUTTON_WIDTH_SM, AppTheme.BUTTON_HEIGHT));
        cancelButton.addActionListener(e -> downloadCSVFrame.dispose());

        downloadButton = new ModernComponents.ModernButton("Download CSV");
        downloadButton.setPreferredSize(new Dimension(AppTheme.BUTTON_WIDTH_LG, AppTheme.BUTTON_HEIGHT));
        downloadButton.addActionListener(e -> downloadCSV());

        footer.add(cancelButton);
        footer.add(downloadButton);

        return footer;
    }

    private void downloadCSV() {
        downloadButton.setEnabled(false);
        downloadButton.setText("Exporting...");

        SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() {
                try {
                    return DatabaseUtils.exportDatabaseEntriesToCSV();
                } catch (Exception e) {
                    return false;
                }
            }

            @Override
            protected void done() {
                try {
                    if (get()) {
                        ToastNotification.showSuccess(downloadCSVFrame,
                            "CSV file exported to Downloads folder!");
                        downloadCSVFrame.dispose();
                    } else {
                        ToastNotification.showError(downloadCSVFrame,
                            "Failed to export CSV file.");
                    }
                } catch (Exception e) {
                    ToastNotification.showError(downloadCSVFrame,
                        "Error: " + e.getMessage());
                } finally {
                    downloadButton.setEnabled(true);
                    downloadButton.setText("Download CSV");
                }
            }
        };
        worker.execute();
    }
}

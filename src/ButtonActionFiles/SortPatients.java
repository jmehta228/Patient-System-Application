package ButtonActionFiles;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

/**
 * Modern Sort Patients dialog with improved UI.
 */
public class SortPatients extends JFrame {
    public static JFrame sortPatientsFrame;

    public SortPatients() {
        createFrame();
    }

    private void createFrame() {
        sortPatientsFrame = new JFrame("Sort Patients");
        sortPatientsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        sortPatientsFrame.setSize(400, 420);
        sortPatientsFrame.setResizable(false);
        sortPatientsFrame.setLocationRelativeTo(null);
        sortPatientsFrame.setBackground(AppTheme.BG_PRIMARY);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(AppTheme.BG_PRIMARY);

        mainPanel.add(createHeader(), BorderLayout.NORTH);
        mainPanel.add(createContent(), BorderLayout.CENTER);
        mainPanel.add(createFooter(), BorderLayout.SOUTH);

        sortPatientsFrame.add(mainPanel);
        sortPatientsFrame.setVisible(true);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel();
        header.setBackground(AppTheme.INFO);
        header.setPreferredSize(new Dimension(0, 80));
        header.setLayout(new BorderLayout());
        header.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JLabel titleLabel = new JLabel("Sort Patients");
        titleLabel.setFont(AppTheme.FONT_SUBTITLE);
        titleLabel.setForeground(Color.WHITE);

        JLabel subtitleLabel = new JLabel("Choose how to sort the patient list");
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
        content.setBorder(BorderFactory.createEmptyBorder(25, 30, 20, 30));

        // Sort options
        content.add(createSortOption("Sort by ID", "Order patients by their ID number",
            "\u0023", this::sortByID));
        content.add(Box.createVerticalStrut(12));

        content.add(createSortOption("Sort by Age", "Order patients from youngest to oldest",
            "\u2195", this::sortByAge));
        content.add(Box.createVerticalStrut(12));

        content.add(createSortOption("Sort by First Name", "Order patients A-Z by first name",
            "\u0041", this::sortByFirstName));
        content.add(Box.createVerticalStrut(12));

        content.add(createSortOption("Sort by Last Name", "Order patients A-Z by last name",
            "\u0041", this::sortByLastName));

        content.add(Box.createVerticalGlue());

        return content;
    }

    private JPanel createSortOption(String title, String description, String icon, Runnable action) {
        JPanel option = new JPanel() {
            private boolean isHovered = false;
            {
                setBackground(AppTheme.BG_CARD);
                setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(AppTheme.BORDER, 1),
                    BorderFactory.createEmptyBorder(15, 15, 15, 15)
                ));
                setCursor(new Cursor(Cursor.HAND_CURSOR));
                setLayout(new BorderLayout(15, 0));
                setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
                setAlignmentX(Component.LEFT_ALIGNMENT);

                addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseEntered(java.awt.event.MouseEvent e) {
                        isHovered = true;
                        setBackground(AppTheme.BG_HOVER);
                        setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(AppTheme.PRIMARY, 1),
                            BorderFactory.createEmptyBorder(15, 15, 15, 15)
                        ));
                    }

                    @Override
                    public void mouseExited(java.awt.event.MouseEvent e) {
                        isHovered = false;
                        setBackground(AppTheme.BG_CARD);
                        setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(AppTheme.BORDER, 1),
                            BorderFactory.createEmptyBorder(15, 15, 15, 15)
                        ));
                    }

                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent e) {
                        action.run();
                    }
                });
            }
        };

        // Icon
        JPanel iconPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(AppTheme.PRIMARY.getRed(), AppTheme.PRIMARY.getGreen(),
                    AppTheme.PRIMARY.getBlue(), 30));
                g2.fillRoundRect(0, 0, 40, 40, 8, 8);
                g2.dispose();
            }
        };
        iconPanel.setOpaque(false);
        iconPanel.setPreferredSize(new Dimension(40, 40));
        iconPanel.setLayout(new GridBagLayout());

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Symbol", Font.BOLD, 16));
        iconLabel.setForeground(AppTheme.PRIMARY);
        iconPanel.add(iconLabel);

        // Text
        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(AppTheme.FONT_BODY_BOLD);
        titleLabel.setForeground(AppTheme.TEXT_PRIMARY);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel descLabel = new JLabel(description);
        descLabel.setFont(AppTheme.FONT_SMALL);
        descLabel.setForeground(AppTheme.TEXT_SECONDARY);
        descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        textPanel.add(titleLabel);
        textPanel.add(Box.createVerticalStrut(3));
        textPanel.add(descLabel);

        // Arrow
        JLabel arrowLabel = new JLabel("\u203A");
        arrowLabel.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 20));
        arrowLabel.setForeground(AppTheme.TEXT_MUTED);

        option.add(iconPanel, BorderLayout.WEST);
        option.add(textPanel, BorderLayout.CENTER);
        option.add(arrowLabel, BorderLayout.EAST);

        return option;
    }

    private JPanel createFooter() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        footer.setBackground(AppTheme.BG_PRIMARY);
        footer.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, AppTheme.BORDER));

        ModernComponents.SecondaryButton closeButton = new ModernComponents.SecondaryButton("Close");
        closeButton.setPreferredSize(new Dimension(AppTheme.BUTTON_WIDTH_MD, AppTheme.BUTTON_HEIGHT));
        closeButton.addActionListener(e -> sortPatientsFrame.dispose());

        footer.add(closeButton);

        return footer;
    }

    private void sortByID() {
        performSort("ID", () -> {
            try {
                DatabaseUtils.sortPatientsByID();
                return true;
            } catch (Exception e) {
                return false;
            }
        });
    }

    private void sortByAge() {
        performSort("Age", () -> {
            try {
                DatabaseUtils.sortPatientsByAge();
                return true;
            } catch (Exception e) {
                return false;
            }
        });
    }

    private void sortByFirstName() {
        performSort("First Name", () -> {
            try {
                DatabaseUtils.sortPatientsByName("First");
                return true;
            } catch (Exception e) {
                return false;
            }
        });
    }

    private void sortByLastName() {
        performSort("Last Name", () -> {
            try {
                DatabaseUtils.sortPatientsByName("Last");
                return true;
            } catch (Exception e) {
                return false;
            }
        });
    }

    private void performSort(String sortType, java.util.function.Supplier<Boolean> sortAction) {
        SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() {
                return sortAction.get();
            }

            @Override
            protected void done() {
                try {
                    if (get()) {
                        ToastNotification.showSuccess(sortPatientsFrame,
                            "Patients sorted by " + sortType);
                    } else {
                        ToastNotification.showError(sortPatientsFrame,
                            "Failed to sort patients");
                    }
                } catch (Exception e) {
                    ToastNotification.showError(sortPatientsFrame,
                        "Error: " + e.getMessage());
                }
            }
        };
        worker.execute();
    }
}

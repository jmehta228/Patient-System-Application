package ButtonActionFiles;

import javax.swing.*;
import java.awt.*;

/**
 * Dialog for auto-generating random patients for testing and demonstration.
 */
public class AutoGeneratePatients {
    public JFrame autoGenerateFrame;
    private ModernComponents.ModernTextField countField;
    private ModernComponents.ModernTextField minAgeField;
    private ModernComponents.ModernTextField maxAgeField;
    private JLabel statusLabel;

    public AutoGeneratePatients() {
        initializeFrame();
        createContent();
        autoGenerateFrame.setVisible(true);
    }

    private void initializeFrame() {
        autoGenerateFrame = new JFrame();
        autoGenerateFrame.setTitle("Auto-Generate Patients");
        autoGenerateFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        autoGenerateFrame.setSize(500, 550);
        autoGenerateFrame.setLocationRelativeTo(null);
        autoGenerateFrame.setResizable(false);
        autoGenerateFrame.setLayout(new BorderLayout());
    }

    private void createContent() {
        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(AppTheme.PRIMARY);
        headerPanel.setPreferredSize(new Dimension(500, 80));
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 25, 20));

        JLabel titleLabel = new JLabel("Auto-Generate Patients");
        titleLabel.setFont(AppTheme.FONT_TITLE);
        titleLabel.setForeground(Color.WHITE);

        JLabel subtitleLabel = new JLabel("Automatically generate random patient records");
        subtitleLabel.setFont(AppTheme.FONT_BODY);
        subtitleLabel.setForeground(new Color(255, 255, 255, 200));

        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(5));
        titlePanel.add(subtitleLabel);

        headerPanel.add(titlePanel);

        // Info banner
        JPanel infoBanner = new JPanel();
        infoBanner.setBackground(new Color(33, 150, 243, 30));
        infoBanner.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 1, 0, new Color(33, 150, 243, 100)),
            BorderFactory.createEmptyBorder(15, 25, 15, 25)
        ));

        JLabel infoIcon = new JLabel("ℹ");
        infoIcon.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
        infoIcon.setForeground(AppTheme.INFO);

        JLabel infoText = new JLabel("<html><body style='width: 380px'>" +
            "This feature generates random patient data with realistic names and birthdates. " +
            "All patients are initially marked as 'Sick' status." +
            "</body></html>");
        infoText.setFont(AppTheme.FONT_SMALL);
        infoText.setForeground(AppTheme.TEXT_SECONDARY);

        infoBanner.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        infoBanner.add(infoIcon);
        infoBanner.add(infoText);

        // Form panel
        JPanel formPanel = new JPanel();
        formPanel.setBackground(AppTheme.BG_CARD);
        formPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

        // Number of patients field
        ModernComponents.FormField countFormField = new ModernComponents.FormField(
            "Number of Patients",
            "How many patients to generate",
            true
        );
        countField = countFormField.getTextField();
        countField.setText("10");

        // Min age field
        ModernComponents.FormField minAgeFormField = new ModernComponents.FormField(
            "Minimum Age",
            "Minimum age in years (default: 0)",
            false
        );
        minAgeField = minAgeFormField.getTextField();
        minAgeField.setText("0");

        // Max age field
        ModernComponents.FormField maxAgeFormField = new ModernComponents.FormField(
            "Maximum Age",
            "Maximum age in years (default: 100)",
            false
        );
        maxAgeField = maxAgeFormField.getTextField();
        maxAgeField.setText("100");

        // Status label
        statusLabel = new JLabel(" ");
        statusLabel.setFont(AppTheme.FONT_SMALL);
        statusLabel.setForeground(AppTheme.TEXT_SECONDARY);
        statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        formPanel.add(countFormField);
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(minAgeFormField);
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(maxAgeFormField);
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(statusLabel);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        ModernComponents.SecondaryButton closeButton = new ModernComponents.SecondaryButton("Cancel");
        closeButton.setPreferredSize(new Dimension(100, 40));
        closeButton.addActionListener(e -> autoGenerateFrame.dispose());

        ModernComponents.ModernButton generateButton = new ModernComponents.ModernButton("Generate");
        generateButton.setPreferredSize(new Dimension(120, 40));
        generateButton.addActionListener(e -> generatePatients());

        buttonPanel.add(closeButton);
        buttonPanel.add(generateButton);

        formPanel.add(buttonPanel);

        // Main container
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(AppTheme.BG_PRIMARY);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(headerPanel);
        topPanel.add(infoBanner);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);

        autoGenerateFrame.add(mainPanel);
    }

    private void generatePatients() {
        // Validate count
        String countText = countField.getText().trim();
        if (countText.isEmpty()) {
            ToastNotification.showError(autoGenerateFrame, "Please enter the number of patients");
            return;
        }

        int count;
        try {
            count = Integer.parseInt(countText);
            if (count <= 0) {
                ToastNotification.showError(autoGenerateFrame, "Number of patients must be positive");
                return;
            }
            if (count > 1000) {
                ToastNotification.showWarning(autoGenerateFrame, "Maximum 1000 patients at a time");
                return;
            }
        } catch (NumberFormatException e) {
            ToastNotification.showError(autoGenerateFrame, "Invalid number of patients");
            return;
        }

        // Validate ages
        int minAge = 0;
        int maxAge = 100;

        String minAgeText = minAgeField.getText().trim();
        if (!minAgeText.isEmpty()) {
            try {
                minAge = Integer.parseInt(minAgeText);
                if (minAge < 0) {
                    ToastNotification.showError(autoGenerateFrame, "Minimum age cannot be negative");
                    return;
                }
            } catch (NumberFormatException e) {
                ToastNotification.showError(autoGenerateFrame, "Invalid minimum age");
                return;
            }
        }

        String maxAgeText = maxAgeField.getText().trim();
        if (!maxAgeText.isEmpty()) {
            try {
                maxAge = Integer.parseInt(maxAgeText);
                if (maxAge < 0) {
                    ToastNotification.showError(autoGenerateFrame, "Maximum age cannot be negative");
                    return;
                }
            } catch (NumberFormatException e) {
                ToastNotification.showError(autoGenerateFrame, "Invalid maximum age");
                return;
            }
        }

        if (minAge > maxAge) {
            ToastNotification.showError(autoGenerateFrame, "Minimum age cannot exceed maximum age");
            return;
        }

        // Disable button during generation
        Component[] components = ((JPanel) ((JPanel) autoGenerateFrame.getContentPane()
            .getComponent(0)).getComponent(1)).getComponents();
        JButton generateButton = null;
        for (Component comp : components) {
            if (comp instanceof JPanel) {
                for (Component innerComp : ((JPanel) comp).getComponents()) {
                    if (innerComp instanceof ModernComponents.ModernButton) {
                        generateButton = (JButton) innerComp;
                        break;
                    }
                }
            }
        }

        if (generateButton != null) {
            generateButton.setEnabled(false);
            generateButton.setText("Generating...");
        }

        final JButton finalGenerateButton = generateButton;
        final int finalMinAge = minAge;
        final int finalMaxAge = maxAge;

        // Generate in background thread
        SwingWorker<Integer, Void> worker = new SwingWorker<>() {
            @Override
            protected Integer doInBackground() {
                statusLabel.setText("Generating " + count + " patients...");
                return PatientDataGenerator.generateAndInsertPatients(count, finalMinAge, finalMaxAge);
            }

            @Override
            protected void done() {
                try {
                    int successCount = get();
                    if (successCount == count) {
                        statusLabel.setText("Successfully generated " + successCount + " patients!");
                        statusLabel.setForeground(AppTheme.SUCCESS);
                        ToastNotification.showSuccess(autoGenerateFrame,
                            "Generated " + successCount + " patients successfully");

                        // Close after short delay
                        Timer timer = new Timer(1500, e -> autoGenerateFrame.dispose());
                        timer.setRepeats(false);
                        timer.start();
                    } else {
                        statusLabel.setText("Generated " + successCount + " of " + count + " patients");
                        statusLabel.setForeground(AppTheme.WARNING);
                        ToastNotification.showWarning(autoGenerateFrame,
                            "Some patients failed to generate");

                        if (finalGenerateButton != null) {
                            finalGenerateButton.setEnabled(true);
                            finalGenerateButton.setText("Generate");
                        }
                    }
                } catch (Exception e) {
                    statusLabel.setText("Error: " + e.getMessage());
                    statusLabel.setForeground(AppTheme.ERROR);
                    ToastNotification.showError(autoGenerateFrame, "Failed to generate patients");

                    if (finalGenerateButton != null) {
                        finalGenerateButton.setEnabled(true);
                        finalGenerateButton.setText("Generate");
                    }
                }
            }
        };

        worker.execute();
    }
}

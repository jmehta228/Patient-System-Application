package ButtonActionFiles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Modern Add Patient form with improved UX and validation.
 */
public class AddPatient extends JFrame {
    public static JFrame addPatientFrame;
    private ModernComponents.ModernTextField firstNameField;
    private ModernComponents.ModernTextField lastNameField;
    private ModernComponents.ModernTextField birthdateField;
    private JLabel firstNameError;
    private JLabel lastNameError;
    private JLabel birthdateError;
    private ModernComponents.ModernButton submitButton;

    public AddPatient() {
        createFrame();
    }

    private void createFrame() {
        addPatientFrame = new JFrame("Add New Patient");
        addPatientFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addPatientFrame.setSize(450, 480);
        addPatientFrame.setMinimumSize(new Dimension(400, 450));
        addPatientFrame.setLocationRelativeTo(null);
        addPatientFrame.setBackground(AppTheme.BG_PRIMARY);

        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(AppTheme.BG_PRIMARY);

        // Header
        JPanel headerPanel = createHeader();

        // Form panel
        JPanel formPanel = createForm();

        // Button panel
        JPanel buttonPanel = createButtonPanel();

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        addPatientFrame.add(mainPanel);
        addPatientFrame.setVisible(true);

        // Focus first field
        SwingUtilities.invokeLater(() -> firstNameField.requestFocus());
    }

    private JPanel createHeader() {
        JPanel header = new JPanel();
        header.setBackground(AppTheme.PRIMARY);
        header.setPreferredSize(new Dimension(0, 80));
        header.setLayout(new BorderLayout());
        header.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JLabel titleLabel = new JLabel("Add New Patient");
        titleLabel.setFont(AppTheme.FONT_SUBTITLE);
        titleLabel.setForeground(Color.WHITE);

        JLabel subtitleLabel = new JLabel("Fill in the patient information below");
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

    private JPanel createForm() {
        JPanel formPanel = new JPanel();
        formPanel.setBackground(AppTheme.BG_PRIMARY);
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 20, 30));

        // First Name field
        firstNameField = new ModernComponents.ModernTextField("Enter patient's first name");
        JPanel firstNamePanel = createFieldPanel("First Name", firstNameField, true);
        firstNameError = (JLabel) firstNamePanel.getComponent(firstNamePanel.getComponentCount() - 1);

        // Last Name field
        lastNameField = new ModernComponents.ModernTextField("Enter patient's last name");
        JPanel lastNamePanel = createFieldPanel("Last Name", lastNameField, true);
        lastNameError = (JLabel) lastNamePanel.getComponent(lastNamePanel.getComponentCount() - 1);

        // Birthdate field
        birthdateField = new ModernComponents.ModernTextField("MM/DD/YYYY");
        JPanel birthdatePanel = createFieldPanel("Birthdate", birthdateField, true);
        birthdateError = (JLabel) birthdatePanel.getComponent(birthdatePanel.getComponentCount() - 1);

        // Add hint for birthdate format
        JLabel formatHint = new JLabel("Format: MM/DD/YYYY (e.g., 01/15/1990)");
        formatHint.setFont(AppTheme.FONT_CAPTION);
        formatHint.setForeground(AppTheme.TEXT_MUTED);
        formatHint.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Add enter key listeners
        KeyAdapter enterListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    submitForm();
                }
            }
        };
        firstNameField.addKeyListener(enterListener);
        lastNameField.addKeyListener(enterListener);
        birthdateField.addKeyListener(enterListener);

        formPanel.add(firstNamePanel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(lastNamePanel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(birthdatePanel);
        formPanel.add(formatHint);
        formPanel.add(Box.createVerticalGlue());

        return formPanel;
    }

    private JPanel createFieldPanel(String label, ModernComponents.ModernTextField textField, boolean required) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, AppTheme.FORM_FIELD_HEIGHT));

        // Label with required indicator
        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        labelPanel.setOpaque(false);
        labelPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel fieldLabel = new JLabel(label);
        fieldLabel.setFont(AppTheme.FONT_BODY_BOLD);
        fieldLabel.setForeground(AppTheme.TEXT_PRIMARY);
        labelPanel.add(fieldLabel);

        if (required) {
            JLabel reqLabel = new JLabel(" *");
            reqLabel.setFont(AppTheme.FONT_BODY_BOLD);
            reqLabel.setForeground(AppTheme.ERROR);
            labelPanel.add(reqLabel);
        }

        // Configure text field
        textField.setAlignmentX(Component.LEFT_ALIGNMENT);
        textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, AppTheme.INPUT_HEIGHT));

        // Error label
        JLabel errorLabel = new JLabel(" ");
        errorLabel.setFont(AppTheme.FONT_SMALL);
        errorLabel.setForeground(AppTheme.ERROR);
        errorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(labelPanel);
        panel.add(Box.createVerticalStrut(AppTheme.SPACING_SM));
        panel.add(textField);
        panel.add(Box.createVerticalStrut(AppTheme.SPACING_XS));
        panel.add(errorLabel);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(AppTheme.BG_PRIMARY);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 20));
        buttonPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, AppTheme.BORDER),
            BorderFactory.createEmptyBorder(0, 20, 0, 20)
        ));

        ModernComponents.SecondaryButton cancelButton = new ModernComponents.SecondaryButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(AppTheme.BUTTON_WIDTH_SM, AppTheme.BUTTON_HEIGHT));
        cancelButton.addActionListener(e -> addPatientFrame.dispose());

        submitButton = new ModernComponents.ModernButton("Add Patient");
        submitButton.setPreferredSize(new Dimension(AppTheme.BUTTON_WIDTH_LG, AppTheme.BUTTON_HEIGHT));
        submitButton.addActionListener(e -> submitForm());

        buttonPanel.add(cancelButton);
        buttonPanel.add(submitButton);

        return buttonPanel;
    }

    private void submitForm() {
        clearErrors();

        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String birthdate = birthdateField.getText().trim();

        boolean hasErrors = false;

        // Validate first name
        if (firstName.isEmpty()) {
            firstNameError.setText("First name is required");
            hasErrors = true;
        } else if (!firstName.matches("^[a-zA-Z\\s\\-']+$")) {
            firstNameError.setText("First name can only contain letters");
            hasErrors = true;
        }

        // Validate last name
        if (lastName.isEmpty()) {
            lastNameError.setText("Last name is required");
            hasErrors = true;
        } else if (!lastName.matches("^[a-zA-Z\\s\\-']+$")) {
            lastNameError.setText("Last name can only contain letters");
            hasErrors = true;
        }

        // Validate birthdate
        if (birthdate.isEmpty()) {
            birthdateError.setText("Birthdate is required");
            hasErrors = true;
        } else if (!birthdate.matches("^\\d{2}/\\d{2}/\\d{4}$")) {
            birthdateError.setText("Use format MM/DD/YYYY");
            hasErrors = true;
        } else {
            // Validate date values
            try {
                int month = Integer.parseInt(birthdate.substring(0, 2));
                int day = Integer.parseInt(birthdate.substring(3, 5));
                int year = Integer.parseInt(birthdate.substring(6, 10));

                String validationResult = Utils.isBirthValid(month, day, year);
                if (validationResult.equals("Invalid")) {
                    birthdateError.setText("Invalid date. Please check values.");
                    hasErrors = true;
                }
            } catch (NumberFormatException e) {
                birthdateError.setText("Invalid date format");
                hasErrors = true;
            }
        }

        if (hasErrors) {
            return;
        }

        // Disable button during submission
        submitButton.setEnabled(false);
        submitButton.setText("Adding...");

        // Submit to database
        SwingWorker<Integer, Void> worker = new SwingWorker<Integer, Void>() {
            @Override
            protected Integer doInBackground() {
                return DatabaseUtils.insertPatientToDatabase(firstName, lastName, birthdate);
            }

            @Override
            protected void done() {
                try {
                    int result = get();
                    if (result > 0) {
                        ToastNotification.showSuccess(addPatientFrame, "Patient \"" + firstName + " " + lastName + "\" added successfully!");
                        clearForm();
                        firstNameField.requestFocus();
                    } else {
                        ToastNotification.showError(addPatientFrame, "Failed to add patient. Please try again.");
                    }
                } catch (Exception e) {
                    ToastNotification.showError(addPatientFrame, "An error occurred: " + e.getMessage());
                } finally {
                    submitButton.setEnabled(true);
                    submitButton.setText("Add Patient");
                }
            }
        };
        worker.execute();
    }

    private void clearErrors() {
        firstNameError.setText(" ");
        lastNameError.setText(" ");
        birthdateError.setText(" ");
    }

    private void clearForm() {
        firstNameField.setText("");
        lastNameField.setText("");
        birthdateField.setText("");
        clearErrors();
    }
}

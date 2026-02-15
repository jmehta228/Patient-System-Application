package ButtonActionFiles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;

/**
 * Modern Delete Patient form with improved UX and validation.
 */
public class DeletePatient extends JFrame {
    public static JFrame deletePatientFrame;
    private ModernComponents.ModernTextField idField;
    private ModernComponents.ModernTextField firstNameField;
    private ModernComponents.ModernTextField lastNameField;
    private ModernComponents.ModernTextField birthdateField;
    private JLabel idError;
    private JLabel firstNameError;
    private JLabel lastNameError;
    private JLabel birthdateError;
    private ModernComponents.ModernButton deleteButton;

    public DeletePatient() {
        createFrame();
    }

    private void createFrame() {
        deletePatientFrame = new JFrame("Delete Patient");
        deletePatientFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        deletePatientFrame.setSize(450, 580);
        deletePatientFrame.setMinimumSize(new Dimension(400, 550));
        deletePatientFrame.setLocationRelativeTo(null);
        deletePatientFrame.setBackground(AppTheme.BG_PRIMARY);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(AppTheme.BG_PRIMARY);

        mainPanel.add(createHeader(), BorderLayout.NORTH);
        mainPanel.add(createForm(), BorderLayout.CENTER);
        mainPanel.add(createButtonPanel(), BorderLayout.SOUTH);

        deletePatientFrame.add(mainPanel);
        deletePatientFrame.setVisible(true);

        SwingUtilities.invokeLater(() -> idField.requestFocus());
    }

    private JPanel createHeader() {
        JPanel header = new JPanel();
        header.setBackground(AppTheme.ERROR);
        header.setPreferredSize(new Dimension(0, 80));
        header.setLayout(new BorderLayout());
        header.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JLabel titleLabel = new JLabel("Delete Patient");
        titleLabel.setFont(AppTheme.FONT_SUBTITLE);
        titleLabel.setForeground(Color.WHITE);

        JLabel subtitleLabel = new JLabel("Enter patient details to remove from system");
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
        formPanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 20, 30));

        // Warning message
        JPanel warningPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        warningPanel.setBackground(AppTheme.WARNING_LIGHT);
        warningPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(AppTheme.WARNING, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        warningPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        warningPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel warningIcon = new JLabel("\u26A0");
        warningIcon.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 16));
        warningIcon.setForeground(AppTheme.WARNING);

        JLabel warningLabel = new JLabel("This action cannot be undone.");
        warningLabel.setFont(AppTheme.FONT_SMALL);
        warningLabel.setForeground(new Color(150, 100, 0));

        warningPanel.add(warningIcon);
        warningPanel.add(warningLabel);

        // ID field
        idField = new ModernComponents.ModernTextField("Enter patient ID");
        JPanel idPanel = createFieldPanel("Patient ID", idField, true);
        idError = (JLabel) idPanel.getComponent(idPanel.getComponentCount() - 1);

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

        // Add enter key listeners
        KeyAdapter enterListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    submitForm();
                }
            }
        };
        idField.addKeyListener(enterListener);
        firstNameField.addKeyListener(enterListener);
        lastNameField.addKeyListener(enterListener);
        birthdateField.addKeyListener(enterListener);

        formPanel.add(warningPanel);
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(idPanel);
        formPanel.add(firstNamePanel);
        formPanel.add(lastNamePanel);
        formPanel.add(birthdatePanel);
        formPanel.add(Box.createVerticalGlue());

        return formPanel;
    }

    private JPanel createFieldPanel(String label, ModernComponents.ModernTextField textField, boolean required) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, AppTheme.FORM_FIELD_HEIGHT));

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

        textField.setAlignmentX(Component.LEFT_ALIGNMENT);
        textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, AppTheme.INPUT_HEIGHT));

        JLabel errorLabel = new JLabel(" ");
        errorLabel.setFont(AppTheme.FONT_SMALL);
        errorLabel.setForeground(AppTheme.ERROR);
        errorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(labelPanel);
        panel.add(Box.createVerticalStrut(6));
        panel.add(textField);
        panel.add(Box.createVerticalStrut(2));
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
        cancelButton.addActionListener(e -> deletePatientFrame.dispose());

        deleteButton = new ModernComponents.ModernButton("Delete Patient");
        deleteButton.setButtonColors(AppTheme.ERROR, Color.WHITE);
        deleteButton.setPreferredSize(new Dimension(AppTheme.BUTTON_WIDTH_LG, AppTheme.BUTTON_HEIGHT));
        deleteButton.addActionListener(e -> submitForm());

        buttonPanel.add(cancelButton);
        buttonPanel.add(deleteButton);

        return buttonPanel;
    }

    private void submitForm() {
        clearErrors();

        String id = idField.getText().trim();
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String birthdate = birthdateField.getText().trim();

        boolean hasErrors = false;

        if (id.isEmpty()) {
            idError.setText("Patient ID is required");
            hasErrors = true;
        } else if (!id.matches("^\\d+$")) {
            idError.setText("ID must be a number");
            hasErrors = true;
        }

        if (firstName.isEmpty()) {
            firstNameError.setText("First name is required");
            hasErrors = true;
        }

        if (lastName.isEmpty()) {
            lastNameError.setText("Last name is required");
            hasErrors = true;
        }

        if (birthdate.isEmpty()) {
            birthdateError.setText("Birthdate is required");
            hasErrors = true;
        } else if (!birthdate.matches("^\\d{2}/\\d{2}/\\d{4}$")) {
            birthdateError.setText("Use format MM/DD/YYYY");
            hasErrors = true;
        }

        if (hasErrors) {
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
            deletePatientFrame,
            "Are you sure you want to delete patient:\n" + firstName + " " + lastName + " (ID: " + id + ")?",
            "Confirm Deletion",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        deleteButton.setEnabled(false);
        deleteButton.setText("Deleting...");

        SwingWorker<Integer, Void> worker = new SwingWorker<Integer, Void>() {
            @Override
            protected Integer doInBackground() throws FileNotFoundException {
                return DatabaseUtils.deletePatientFromDatabase(id, firstName, lastName, birthdate);
            }

            @Override
            protected void done() {
                try {
                    int result = get();
                    if (result > 0) {
                        ToastNotification.showSuccess(deletePatientFrame, "Patient deleted successfully!");
                        clearForm();
                    } else {
                        ToastNotification.showError(deletePatientFrame, "Patient not found. Please verify the details.");
                    }
                } catch (Exception e) {
                    ToastNotification.showError(deletePatientFrame, "An error occurred: " + e.getMessage());
                } finally {
                    deleteButton.setEnabled(true);
                    deleteButton.setText("Delete Patient");
                }
            }
        };
        worker.execute();
    }

    private void clearErrors() {
        idError.setText(" ");
        firstNameError.setText(" ");
        lastNameError.setText(" ");
        birthdateError.setText(" ");
    }

    private void clearForm() {
        idField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        birthdateField.setText("");
        clearErrors();
    }
}

package ButtonActionFiles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;

/**
 * Modern Transfer Patient form - marks patient as recovered.
 */
public class TransferPatient extends JFrame {
    public static JFrame transferPatientFrame;
    private ModernComponents.ModernTextField firstNameField;
    private ModernComponents.ModernTextField lastNameField;
    private ModernComponents.ModernTextField birthdateField;
    private JLabel firstNameError;
    private JLabel lastNameError;
    private JLabel birthdateError;
    private ModernComponents.ModernButton transferButton;

    public TransferPatient() {
        createFrame();
    }

    private void createFrame() {
        transferPatientFrame = new JFrame("Transfer Patient");
        transferPatientFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        transferPatientFrame.setSize(450, 480);
        transferPatientFrame.setMinimumSize(new Dimension(400, 450));
        transferPatientFrame.setLocationRelativeTo(null);
        transferPatientFrame.setBackground(AppTheme.BG_PRIMARY);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(AppTheme.BG_PRIMARY);

        mainPanel.add(createHeader(), BorderLayout.NORTH);
        mainPanel.add(createForm(), BorderLayout.CENTER);
        mainPanel.add(createButtonPanel(), BorderLayout.SOUTH);

        transferPatientFrame.add(mainPanel);
        transferPatientFrame.setVisible(true);

        SwingUtilities.invokeLater(() -> firstNameField.requestFocus());
    }

    private JPanel createHeader() {
        JPanel header = new JPanel();
        header.setBackground(AppTheme.SUCCESS);
        header.setPreferredSize(new Dimension(0, 80));
        header.setLayout(new BorderLayout());
        header.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JLabel titleLabel = new JLabel("Transfer Patient");
        titleLabel.setFont(AppTheme.FONT_SUBTITLE);
        titleLabel.setForeground(Color.WHITE);

        JLabel subtitleLabel = new JLabel("Mark patient status as Recovered");
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

        // Info message
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        infoPanel.setBackground(AppTheme.SUCCESS_LIGHT);
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(AppTheme.SUCCESS, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        infoPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        infoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel infoIcon = new JLabel("\u2713");
        infoIcon.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 16));
        infoIcon.setForeground(AppTheme.SUCCESS);

        JLabel infoLabel = new JLabel("Patient will be marked as Recovered");
        infoLabel.setFont(AppTheme.FONT_SMALL);
        infoLabel.setForeground(new Color(50, 120, 50));

        infoPanel.add(infoIcon);
        infoPanel.add(infoLabel);

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
        firstNameField.addKeyListener(enterListener);
        lastNameField.addKeyListener(enterListener);
        birthdateField.addKeyListener(enterListener);

        formPanel.add(infoPanel);
        formPanel.add(Box.createVerticalStrut(25));
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
        cancelButton.addActionListener(e -> transferPatientFrame.dispose());

        transferButton = new ModernComponents.ModernButton("Transfer Patient");
        transferButton.setButtonColors(AppTheme.SUCCESS, Color.WHITE);
        transferButton.setPreferredSize(new Dimension(AppTheme.BUTTON_WIDTH_XL, AppTheme.BUTTON_HEIGHT));
        transferButton.addActionListener(e -> submitForm());

        buttonPanel.add(cancelButton);
        buttonPanel.add(transferButton);

        return buttonPanel;
    }

    private void submitForm() {
        clearErrors();

        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String birthdate = birthdateField.getText().trim();

        boolean hasErrors = false;

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

        transferButton.setEnabled(false);
        transferButton.setText("Transferring...");

        SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() throws FileNotFoundException {
                return DatabaseUtils.transferPatientDatabase(firstName, lastName, birthdate);
            }

            @Override
            protected void done() {
                try {
                    boolean result = get();
                    if (result) {
                        ToastNotification.showSuccess(transferPatientFrame,
                            "Patient \"" + firstName + " " + lastName + "\" marked as Recovered!");
                        clearForm();
                    } else {
                        ToastNotification.showError(transferPatientFrame,
                            "Patient not found or already recovered.");
                    }
                } catch (Exception e) {
                    ToastNotification.showError(transferPatientFrame, "An error occurred: " + e.getMessage());
                } finally {
                    transferButton.setEnabled(true);
                    transferButton.setText("Transfer Patient");
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

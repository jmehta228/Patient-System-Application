import ButtonActionFiles.AppTheme;
import ButtonActionFiles.ModernComponents;
import ButtonActionFiles.ToastNotification;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Modern login screen with improved UX and styling.
 */
public class LoginScreen extends JPanel {
    static ModernComponents.ModernTextField usernameField;
    static ModernComponents.ModernPasswordField passwordField;
    static ModernComponents.ModernButton loginButton;
    static ModernComponents.SecondaryButton closeButton;
    private JLabel errorLabel;

    public LoginScreen() {
        setLayout(new BorderLayout());
        setBackground(AppTheme.BG_PRIMARY);

        // Main container
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(AppTheme.BG_PRIMARY);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));

        // Header section with icon and title
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setOpaque(false);
        headerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // App icon/logo
        JPanel iconContainer = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw circular background
                g2d.setColor(AppTheme.PRIMARY);
                g2d.fillOval(0, 0, 70, 70);

                // Draw medical cross
                g2d.setColor(Color.WHITE);
                g2d.fillRect(30, 15, 10, 40);
                g2d.fillRect(15, 30, 40, 10);

                g2d.dispose();
            }
        };
        iconContainer.setPreferredSize(new Dimension(70, 70));
        iconContainer.setMaximumSize(new Dimension(70, 70));
        iconContainer.setOpaque(false);
        iconContainer.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Title
        JLabel titleLabel = new JLabel("Welcome Back");
        titleLabel.setFont(AppTheme.FONT_TITLE);
        titleLabel.setForeground(AppTheme.TEXT_PRIMARY);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Subtitle
        JLabel subtitleLabel = new JLabel("Sign in to Patient System");
        subtitleLabel.setFont(AppTheme.FONT_BODY);
        subtitleLabel.setForeground(AppTheme.TEXT_SECONDARY);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(iconContainer);
        headerPanel.add(Box.createVerticalStrut(20));
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(8));
        headerPanel.add(subtitleLabel);

        // Form card
        ModernComponents.CardPanel formCard = new ModernComponents.CardPanel();
        formCard.setLayout(new BoxLayout(formCard, BoxLayout.Y_AXIS));
        formCard.setAlignmentX(Component.CENTER_ALIGNMENT);
        formCard.setMaximumSize(new Dimension(350, 320));

        // Username field
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(AppTheme.FONT_BODY_BOLD);
        usernameLabel.setForeground(AppTheme.TEXT_PRIMARY);
        usernameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        usernameField = new ModernComponents.ModernTextField("Enter your username");
        usernameField.setAlignmentX(Component.LEFT_ALIGNMENT);
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, AppTheme.INPUT_HEIGHT));

        // Password field
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(AppTheme.FONT_BODY_BOLD);
        passwordLabel.setForeground(AppTheme.TEXT_PRIMARY);
        passwordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        passwordField = new ModernComponents.ModernPasswordField("Enter your password");
        passwordField.setAlignmentX(Component.LEFT_ALIGNMENT);
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, AppTheme.INPUT_HEIGHT));

        // Error label
        errorLabel = new JLabel(" ");
        errorLabel.setFont(AppTheme.FONT_SMALL);
        errorLabel.setForeground(AppTheme.ERROR);
        errorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Login button
        loginButton = new ModernComponents.ModernButton("Sign In");
        loginButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        loginButton.setMaximumSize(new Dimension(400, AppTheme.BUTTON_HEIGHT));
        loginButton.setPreferredSize(new Dimension(400, AppTheme.BUTTON_HEIGHT));
        loginButton.addActionListener(new LoginButtonAction());

        // Add form components
        formCard.add(usernameLabel);
        formCard.add(Box.createVerticalStrut(8));
        formCard.add(usernameField);
        formCard.add(Box.createVerticalStrut(16));
        formCard.add(passwordLabel);
        formCard.add(Box.createVerticalStrut(8));
        formCard.add(passwordField);
        formCard.add(Box.createVerticalStrut(8));
        formCard.add(errorLabel);
        formCard.add(Box.createVerticalStrut(16));
        formCard.add(loginButton);

        // Close button
        closeButton = new ModernComponents.SecondaryButton("Exit Application");
        closeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        closeButton.setMaximumSize(new Dimension(AppTheme.BUTTON_WIDTH_XL, AppTheme.BUTTON_HEIGHT_SM));
        closeButton.setPreferredSize(new Dimension(AppTheme.BUTTON_WIDTH_XL, AppTheme.BUTTON_HEIGHT_SM));
        closeButton.addActionListener(new CloseButtonAction());

        // Add key listener for Enter key
        KeyAdapter enterKeyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    loginButton.doClick();
                }
            }
        };
        usernameField.addKeyListener(enterKeyListener);
        passwordField.addKeyListener(enterKeyListener);

        // Assemble main panel
        mainPanel.add(headerPanel);
        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(formCard);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(closeButton);
        mainPanel.add(Box.createVerticalGlue());

        // Footer
        JLabel footerLabel = new JLabel("Patient System Application v3.0");
        footerLabel.setFont(AppTheme.FONT_CAPTION);
        footerLabel.setForeground(AppTheme.TEXT_MUTED);
        footerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        add(mainPanel, BorderLayout.CENTER);
        add(footerLabel, BorderLayout.SOUTH);
    }

    private boolean validLogin(String username, String password) throws FileNotFoundException {
        File file = new File("logins.txt");
        try (Scanner fileReader = new Scanner(file)) {
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine().trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }
                String[] lineItems = line.split("\\s+");
                if (lineItems.length >= 2 && lineItems[0].equals(username) && lineItems[1].equals(password)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void setFormEnabled(boolean enabled) {
        usernameField.setEnabled(enabled);
        passwordField.setEnabled(enabled);
        loginButton.setEnabled(enabled);
    }

    private void showError(String message) {
        errorLabel.setText(message);
    }

    private void clearError() {
        errorLabel.setText(" ");
    }

    private class LoginButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            clearError();

            String usernameString = usernameField.getText().trim();
            String passwordString = new String(passwordField.getPassword());

            // Validate input
            if (usernameString.isEmpty()) {
                showError("Please enter your username");
                usernameField.requestFocus();
                return;
            }

            if (passwordString.isEmpty()) {
                showError("Please enter your password");
                passwordField.requestFocus();
                return;
            }

            // Disable form during login attempt
            setFormEnabled(false);
            loginButton.setText("Signing in...");

            // Use SwingWorker for async login
            SwingWorker<Boolean, Void> loginWorker = new SwingWorker<Boolean, Void>() {
                @Override
                protected Boolean doInBackground() throws Exception {
                    Thread.sleep(500); // Simulate network delay for better UX
                    return validLogin(usernameString, passwordString);
                }

                @Override
                protected void done() {
                    try {
                        boolean success = get();
                        if (success) {
                            // Clear fields
                            usernameField.setText("");
                            passwordField.setText("");

                            // Open administrator view
                            new AdministratorView();
                            PatientSystemApplication.mainFrame.dispose();
                        } else {
                            showError("Invalid username or password");
                            setFormEnabled(true);
                            loginButton.setText("Sign In");
                            passwordField.setText("");
                            passwordField.requestFocus();
                        }
                    } catch (Exception e) {
                        showError("Login failed. Please try again.");
                        setFormEnabled(true);
                        loginButton.setText("Sign In");
                    }
                }
            };
            loginWorker.execute();
        }
    }

    private static class CloseButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            int result = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to exit?",
                "Confirm Exit",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );
            if (result == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }
}

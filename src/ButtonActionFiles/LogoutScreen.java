package ButtonActionFiles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogoutScreen {
    public static JFrame logoutFrame;
    JPanel logoutPanel;

    public LogoutScreen() {
        logoutFrame = new JFrame("Patient System Application - Logout");
        logoutFrame.setPreferredSize(new Dimension(500, 500));
        logoutFrame.setLocation(500, 200);

        JLabel confirmationLabel = new JLabel("Are you sure you would like to logout?", SwingConstants.CENTER);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setPreferredSize(new Dimension(400, 100));
        logoutButton.addActionListener(new LogoutButtonAction());

        JButton notLogoutButton = new JButton("No, do not logout");
        notLogoutButton.setPreferredSize(new Dimension(400, 100));
        notLogoutButton.addActionListener(new NotLogoutButtonAction());

        logoutPanel = new JPanel();
        logoutPanel.setLayout(new BoxLayout(logoutPanel, BoxLayout.Y_AXIS));

        confirmationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        notLogoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        logoutPanel.add(Box.createVerticalStrut(20)); // Add vertical space at the top
        logoutPanel.add(confirmationLabel);
        logoutPanel.add(Box.createVerticalStrut(20)); // Add vertical space between components
        logoutPanel.add(logoutButton);
        logoutPanel.add(Box.createVerticalStrut(10)); // Add vertical space between components
        logoutPanel.add(notLogoutButton);
        logoutPanel.add(Box.createVerticalStrut(100)); // Add vertical space at the bottom

        logoutFrame.add(logoutPanel);
        logoutFrame.setResizable(true);
        logoutFrame.pack();
        logoutFrame.setVisible(true);
    }

    private static class LogoutButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    private static class NotLogoutButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            logoutFrame.dispose();
        }
    }
}
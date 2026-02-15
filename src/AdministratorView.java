import ButtonActionFiles.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.sql.SQLException;

/**
 * Modern administrator dashboard with sidebar navigation.
 */
public class AdministratorView extends JFrame {
    public static AddPatient addPatientScreen;
    public static DeletePatient deletePatientScreen;
    public static TransferPatient transferPatientScreen;
    public static ReturnPatientCount returnPatientCountScreen;
    public static ReturnAverageAge returnAverageAgeScreen;
    public static GetPatientList getPatientListScreen;
    public static SortPatients sortPatientsScreen;
    public static DownloadCSV downloadCSVScreen;
    public static LogoutScreen logoutScreen;

    private JFrame administratorViewFrame;
    private JPanel contentPanel;
    private JPanel mainContentArea;
    private ModernComponents.SidebarButton[] sidebarButtons;
    private JLabel welcomeLabel;
    private JLabel dateLabel;

    // Stat cards for dashboard
    private ModernComponents.StatCard sickCountCard;
    private ModernComponents.StatCard recoverCountCard;
    private ModernComponents.StatCard totalCountCard;
    private ModernComponents.StatCard avgAgeCard;

    public AdministratorView() {
        initializeFrame();
        createSidebar();
        createMainContent();
        showDashboard();

        administratorViewFrame.setVisible(true);
    }

    private void initializeFrame() {
        administratorViewFrame = new JFrame();
        administratorViewFrame.setTitle("Patient System Application");
        administratorViewFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        administratorViewFrame.setMinimumSize(new Dimension(1100, 700));
        administratorViewFrame.setSize(1200, 800);
        administratorViewFrame.setLocationRelativeTo(null);
        administratorViewFrame.setBackground(AppTheme.BG_PRIMARY);
        administratorViewFrame.setLayout(new BorderLayout());
    }

    private void createSidebar() {
        // Sidebar panel
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(AppTheme.SIDEBAR_WIDTH, 0));
        sidebar.setBackground(AppTheme.BG_SIDEBAR);
        sidebar.setLayout(new BorderLayout());

        // Logo/Brand section
        JPanel brandPanel = new JPanel();
        brandPanel.setBackground(AppTheme.BG_DARK);
        brandPanel.setPreferredSize(new Dimension(AppTheme.SIDEBAR_WIDTH, 80));
        brandPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 20));

        // Logo icon
        JPanel logoIcon = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(AppTheme.PRIMARY);
                g2d.fillRoundRect(0, 0, 40, 40, 10, 10);
                g2d.setColor(Color.WHITE);
                g2d.fillRect(17, 8, 6, 24);
                g2d.fillRect(8, 17, 24, 6);
                g2d.dispose();
            }
        };
        logoIcon.setPreferredSize(new Dimension(40, 40));
        logoIcon.setOpaque(false);

        JLabel brandLabel = new JLabel("Patient System");
        brandLabel.setFont(new Font(AppTheme.FONT_FAMILY, Font.BOLD, 16));
        brandLabel.setForeground(Color.WHITE);

        brandPanel.add(logoIcon);
        brandPanel.add(brandLabel);

        // Navigation menu
        JPanel navPanel = new JPanel();
        navPanel.setOpaque(false);
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));
        navPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        // Menu section label
        JLabel menuLabel = new JLabel("MENU");
        menuLabel.setFont(new Font(AppTheme.FONT_FAMILY, Font.BOLD, 11));
        menuLabel.setForeground(new Color(120, 120, 120));
        menuLabel.setBorder(BorderFactory.createEmptyBorder(0, 15, 10, 0));
        menuLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        navPanel.add(menuLabel);

        // Create sidebar buttons
        String[][] menuItems = {
            {"Dashboard", "\u2302"},           // Home icon
            {"View Patients", "\u2630"},       // List icon
            {"Add Patient", "\u002B"},         // Plus icon
            {"Transfer Patient", "\u21C4"},    // Transfer icon
            {"Delete Patient", "\u2717"},      // X icon
            {"Sort Patients", "\u2195"},       // Sort icon
            {"Statistics", "\u2637"},          // Stats icon
            {"Export Data", "\u21E9"},         // Download icon
        };

        sidebarButtons = new ModernComponents.SidebarButton[menuItems.length];

        for (int i = 0; i < menuItems.length; i++) {
            sidebarButtons[i] = new ModernComponents.SidebarButton(menuItems[i][0], menuItems[i][1]);
            sidebarButtons[i].setAlignmentX(Component.LEFT_ALIGNMENT);
            final int index = i;
            sidebarButtons[i].addActionListener(e -> handleNavigation(index));
            navPanel.add(sidebarButtons[i]);
            navPanel.add(Box.createVerticalStrut(5));
        }

        // Settings section
        navPanel.add(Box.createVerticalGlue());

        JLabel settingsLabel = new JLabel("ACCOUNT");
        settingsLabel.setFont(new Font(AppTheme.FONT_FAMILY, Font.BOLD, 11));
        settingsLabel.setForeground(new Color(120, 120, 120));
        settingsLabel.setBorder(BorderFactory.createEmptyBorder(20, 15, 10, 0));
        settingsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        navPanel.add(settingsLabel);

        ModernComponents.SidebarButton logoutBtn = new ModernComponents.SidebarButton("Logout", "\u2190");
        logoutBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        logoutBtn.addActionListener(e -> showLogoutDialog());
        navPanel.add(logoutBtn);

        // User info panel at bottom
        JPanel userPanel = new JPanel();
        userPanel.setBackground(new Color(255, 255, 255, 10));
        userPanel.setPreferredSize(new Dimension(AppTheme.SIDEBAR_WIDTH, 70));
        userPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));

        JPanel avatar = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(AppTheme.PRIMARY);
                g2d.fillOval(0, 0, 40, 40);
                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font(AppTheme.FONT_FAMILY, Font.BOLD, 16));
                g2d.drawString("A", 14, 26);
                g2d.dispose();
            }
        };
        avatar.setPreferredSize(new Dimension(40, 40));
        avatar.setOpaque(false);

        JPanel userInfo = new JPanel();
        userInfo.setOpaque(false);
        userInfo.setLayout(new BoxLayout(userInfo, BoxLayout.Y_AXIS));

        JLabel userName = new JLabel("Administrator");
        userName.setFont(AppTheme.FONT_BODY_BOLD);
        userName.setForeground(Color.WHITE);

        JLabel userRole = new JLabel("Healthcare Admin");
        userRole.setFont(AppTheme.FONT_SMALL);
        userRole.setForeground(new Color(150, 150, 150));

        userInfo.add(userName);
        userInfo.add(userRole);

        userPanel.add(avatar);
        userPanel.add(userInfo);

        sidebar.add(brandPanel, BorderLayout.NORTH);
        sidebar.add(navPanel, BorderLayout.CENTER);
        sidebar.add(userPanel, BorderLayout.SOUTH);

        administratorViewFrame.add(sidebar, BorderLayout.WEST);
    }

    private void createMainContent() {
        // Main content area
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(AppTheme.BG_PRIMARY);

        // Top header bar
        JPanel headerBar = new JPanel(new BorderLayout());
        headerBar.setBackground(AppTheme.BG_SECONDARY);
        headerBar.setPreferredSize(new Dimension(0, 70));
        headerBar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, AppTheme.BORDER),
            BorderFactory.createEmptyBorder(15, 25, 15, 25)
        ));

        // Page title area
        JPanel titleArea = new JPanel();
        titleArea.setOpaque(false);
        titleArea.setLayout(new BoxLayout(titleArea, BoxLayout.Y_AXIS));

        welcomeLabel = new JLabel("Dashboard");
        welcomeLabel.setFont(AppTheme.FONT_SUBTITLE);
        welcomeLabel.setForeground(AppTheme.TEXT_PRIMARY);

        dateLabel = new JLabel(getCurrentDate());
        dateLabel.setFont(AppTheme.FONT_SMALL);
        dateLabel.setForeground(AppTheme.TEXT_SECONDARY);

        titleArea.add(welcomeLabel);
        titleArea.add(dateLabel);

        // Quick actions
        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actionsPanel.setOpaque(false);

        ModernComponents.ModernButton quickAddBtn = new ModernComponents.ModernButton("+ Add Patient");
        quickAddBtn.setPreferredSize(new Dimension(AppTheme.BUTTON_WIDTH_LG, AppTheme.BUTTON_HEIGHT));
        quickAddBtn.addActionListener(e -> openAddPatient());

        ModernComponents.SecondaryButton refreshBtn = new ModernComponents.SecondaryButton("Refresh");
        refreshBtn.setPreferredSize(new Dimension(AppTheme.BUTTON_WIDTH_SM, AppTheme.BUTTON_HEIGHT));
        refreshBtn.addActionListener(e -> refreshDashboard());

        actionsPanel.add(refreshBtn);
        actionsPanel.add(quickAddBtn);

        headerBar.add(titleArea, BorderLayout.WEST);
        headerBar.add(actionsPanel, BorderLayout.EAST);

        // Main content area (scrollable)
        mainContentArea = new JPanel();
        mainContentArea.setBackground(AppTheme.BG_PRIMARY);
        mainContentArea.setLayout(new BorderLayout());
        mainContentArea.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        JScrollPane scrollPane = new JScrollPane(mainContentArea);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        contentPanel.add(headerBar, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        administratorViewFrame.add(contentPanel, BorderLayout.CENTER);
    }

    private void showDashboard() {
        setActiveButton(0);
        welcomeLabel.setText("Dashboard");

        mainContentArea.removeAll();

        JPanel dashboardPanel = new JPanel();
        dashboardPanel.setOpaque(false);
        dashboardPanel.setLayout(new BoxLayout(dashboardPanel, BoxLayout.Y_AXIS));

        // Stats cards row
        JPanel statsRow = new JPanel(new GridLayout(1, 4, 20, 0));
        statsRow.setOpaque(false);
        statsRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        statsRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Get stats from database
        int[] counts = {0, 0, 0};
        double avgAge = 0;
        try {
            counts = DatabaseUtils.getPatientCount();
            avgAge = DatabaseUtils.returnAverageAge();
        } catch (Exception e) {
            // Use default values
        }

        sickCountCard = new ModernComponents.StatCard("Sick Patients", String.valueOf(counts[0]), AppTheme.ERROR);
        recoverCountCard = new ModernComponents.StatCard("Recovered", String.valueOf(counts[1]), AppTheme.SUCCESS);
        totalCountCard = new ModernComponents.StatCard("Total Patients", String.valueOf(counts[2]), AppTheme.PRIMARY);
        avgAgeCard = new ModernComponents.StatCard("Average Age", String.format("%.1f", avgAge), AppTheme.INFO);

        statsRow.add(sickCountCard);
        statsRow.add(recoverCountCard);
        statsRow.add(totalCountCard);
        statsRow.add(avgAgeCard);

        // Quick actions section
        JLabel quickActionsLabel = new JLabel("Quick Actions");
        quickActionsLabel.setFont(AppTheme.FONT_HEADING);
        quickActionsLabel.setForeground(AppTheme.TEXT_PRIMARY);
        quickActionsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        quickActionsLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 15, 0));

        JPanel quickActionsRow = new JPanel(new GridLayout(1, 4, 20, 0));
        quickActionsRow.setOpaque(false);
        quickActionsRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        quickActionsRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        quickActionsRow.add(createQuickActionCard("View Patients", "Browse all patient records", "\u2630", () -> handleNavigation(1)));
        quickActionsRow.add(createQuickActionCard("Add Patient", "Register a new patient", "\u002B", () -> handleNavigation(2)));
        quickActionsRow.add(createQuickActionCard("Transfer Patient", "Mark patient as recovered", "\u21C4", () -> handleNavigation(3)));
        quickActionsRow.add(createQuickActionCard("Export Data", "Download patient data as CSV", "\u21E9", () -> handleNavigation(7)));

        // Recent activity placeholder
        JLabel recentLabel = new JLabel("System Information");
        recentLabel.setFont(AppTheme.FONT_HEADING);
        recentLabel.setForeground(AppTheme.TEXT_PRIMARY);
        recentLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        recentLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 15, 0));

        ModernComponents.CardPanel infoCard = new ModernComponents.CardPanel();
        infoCard.setLayout(new BoxLayout(infoCard, BoxLayout.Y_AXIS));
        infoCard.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));

        JLabel infoTitle = new JLabel("Welcome to Patient System Application");
        infoTitle.setFont(AppTheme.FONT_BODY_BOLD);
        infoTitle.setForeground(AppTheme.TEXT_PRIMARY);

        JLabel infoDesc = new JLabel("<html><body style='width: 500px'>Use the sidebar navigation to manage patients, " +
            "view statistics, and export data. The dashboard provides a quick overview of your patient records.</body></html>");
        infoDesc.setFont(AppTheme.FONT_BODY);
        infoDesc.setForeground(AppTheme.TEXT_SECONDARY);

        infoCard.add(infoTitle);
        infoCard.add(Box.createVerticalStrut(10));
        infoCard.add(infoDesc);

        dashboardPanel.add(statsRow);
        dashboardPanel.add(quickActionsLabel);
        dashboardPanel.add(quickActionsRow);
        dashboardPanel.add(recentLabel);
        dashboardPanel.add(infoCard);
        dashboardPanel.add(Box.createVerticalGlue());

        mainContentArea.add(dashboardPanel, BorderLayout.CENTER);
        mainContentArea.revalidate();
        mainContentArea.repaint();
    }

    private JPanel createQuickActionCard(String title, String description, String icon, Runnable action) {
        JPanel card = new JPanel() {
            private boolean isHovered = false;

            {
                setBackground(AppTheme.BG_CARD);
                setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(AppTheme.BORDER, 1),
                    BorderFactory.createEmptyBorder(20, 20, 20, 20)
                ));
                setCursor(new Cursor(Cursor.HAND_CURSOR));
                setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        isHovered = true;
                        setBackground(AppTheme.BG_HOVER);
                        repaint();
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        isHovered = false;
                        setBackground(AppTheme.BG_CARD);
                        repaint();
                    }

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        action.run();
                    }
                });
            }
        };

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 24));
        iconLabel.setForeground(AppTheme.PRIMARY);
        iconLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(AppTheme.FONT_BODY_BOLD);
        titleLabel.setForeground(AppTheme.TEXT_PRIMARY);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel descLabel = new JLabel(description);
        descLabel.setFont(AppTheme.FONT_SMALL);
        descLabel.setForeground(AppTheme.TEXT_SECONDARY);
        descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(iconLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(5));
        card.add(descLabel);

        return card;
    }

    private void handleNavigation(int index) {
        setActiveButton(index);

        switch (index) {
            case 0: // Dashboard
                showDashboard();
                break;
            case 1: // View Patients
                openGetPatientList();
                break;
            case 2: // Add Patient
                openAddPatient();
                break;
            case 3: // Transfer Patient
                openTransferPatient();
                break;
            case 4: // Delete Patient
                openDeletePatient();
                break;
            case 5: // Sort Patients
                openSortPatients();
                break;
            case 6: // Statistics
                showStatistics();
                break;
            case 7: // Export Data
                openDownloadCSV();
                break;
        }
    }

    private void setActiveButton(int index) {
        for (int i = 0; i < sidebarButtons.length; i++) {
            sidebarButtons[i].setSelected(i == index);
        }
    }

    private void showStatistics() {
        welcomeLabel.setText("Statistics");
        mainContentArea.removeAll();

        JPanel statsPanel = new JPanel();
        statsPanel.setOpaque(false);
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));

        // Patient count section
        JLabel countLabel = new JLabel("Patient Overview");
        countLabel.setFont(AppTheme.FONT_HEADING);
        countLabel.setForeground(AppTheme.TEXT_PRIMARY);
        countLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        int[] counts = {0, 0, 0};
        double avgAge = 0;
        try {
            counts = DatabaseUtils.getPatientCount();
            avgAge = DatabaseUtils.returnAverageAge();
        } catch (Exception e) {
            // Use defaults
        }

        JPanel countCards = new JPanel(new GridLayout(1, 3, 20, 0));
        countCards.setOpaque(false);
        countCards.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        countCards.setAlignmentX(Component.LEFT_ALIGNMENT);

        countCards.add(new ModernComponents.StatCard("Sick Patients", String.valueOf(counts[0]), AppTheme.ERROR));
        countCards.add(new ModernComponents.StatCard("Recovered Patients", String.valueOf(counts[1]), AppTheme.SUCCESS));
        countCards.add(new ModernComponents.StatCard("Total Patients", String.valueOf(counts[2]), AppTheme.PRIMARY));

        // Average age section
        JLabel ageLabel = new JLabel("Demographics");
        ageLabel.setFont(AppTheme.FONT_HEADING);
        ageLabel.setForeground(AppTheme.TEXT_PRIMARY);
        ageLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        ageLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 15, 0));

        ModernComponents.CardPanel ageCard = new ModernComponents.CardPanel();
        ageCard.setLayout(new BorderLayout());
        ageCard.setMaximumSize(new Dimension(300, 120));
        ageCard.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel avgAgeValue = new JLabel(String.format("%.2f years", avgAge));
        avgAgeValue.setFont(new Font(AppTheme.FONT_FAMILY, Font.BOLD, 36));
        avgAgeValue.setForeground(AppTheme.INFO);

        JLabel avgAgeLabel = new JLabel("Average Patient Age");
        avgAgeLabel.setFont(AppTheme.FONT_BODY);
        avgAgeLabel.setForeground(AppTheme.TEXT_SECONDARY);

        JPanel ageContent = new JPanel();
        ageContent.setOpaque(false);
        ageContent.setLayout(new BoxLayout(ageContent, BoxLayout.Y_AXIS));
        ageContent.add(avgAgeValue);
        ageContent.add(avgAgeLabel);

        ageCard.add(ageContent, BorderLayout.CENTER);

        statsPanel.add(countLabel);
        statsPanel.add(Box.createVerticalStrut(15));
        statsPanel.add(countCards);
        statsPanel.add(ageLabel);
        statsPanel.add(ageCard);
        statsPanel.add(Box.createVerticalGlue());

        mainContentArea.add(statsPanel, BorderLayout.CENTER);
        mainContentArea.revalidate();
        mainContentArea.repaint();
    }

    private void refreshDashboard() {
        showDashboard();
        ToastNotification.showSuccess(administratorViewFrame, "Dashboard refreshed");
    }

    private String getCurrentDate() {
        java.time.LocalDate date = java.time.LocalDate.now();
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy");
        return date.format(formatter);
    }

    // ===== Open screen methods =====

    private void openAddPatient() {
        welcomeLabel.setText("Add Patient");
        if (addPatientScreen == null || !addPatientScreen.addPatientFrame.isShowing()) {
            addPatientScreen = new AddPatient();
        } else {
            addPatientScreen.addPatientFrame.toFront();
            addPatientScreen.addPatientFrame.requestFocus();
        }
    }

    private void openDeletePatient() {
        welcomeLabel.setText("Delete Patient");
        if (deletePatientScreen == null || !deletePatientScreen.deletePatientFrame.isShowing()) {
            deletePatientScreen = new DeletePatient();
        } else {
            deletePatientScreen.deletePatientFrame.toFront();
            deletePatientScreen.deletePatientFrame.requestFocus();
        }
    }

    private void openTransferPatient() {
        welcomeLabel.setText("Transfer Patient");
        if (transferPatientScreen == null || !transferPatientScreen.transferPatientFrame.isShowing()) {
            transferPatientScreen = new TransferPatient();
        } else {
            transferPatientScreen.transferPatientFrame.toFront();
            transferPatientScreen.transferPatientFrame.requestFocus();
        }
    }

    private void openGetPatientList() {
        welcomeLabel.setText("View Patients");
        if (getPatientListScreen == null || !getPatientListScreen.getPatientListFrame.isShowing()) {
            try {
                getPatientListScreen = new GetPatientList();
            } catch (FileNotFoundException | SQLException e) {
                ToastNotification.showError(administratorViewFrame, "Failed to load patient list");
            }
        } else {
            getPatientListScreen.getPatientListFrame.toFront();
            getPatientListScreen.getPatientListFrame.requestFocus();
        }
    }

    private void openSortPatients() {
        welcomeLabel.setText("Sort Patients");
        if (sortPatientsScreen == null || !sortPatientsScreen.sortPatientsFrame.isShowing()) {
            sortPatientsScreen = new SortPatients();
        } else {
            sortPatientsScreen.sortPatientsFrame.toFront();
            sortPatientsScreen.sortPatientsFrame.requestFocus();
        }
    }

    private void openDownloadCSV() {
        welcomeLabel.setText("Export Data");
        if (downloadCSVScreen == null || !downloadCSVScreen.downloadCSVFrame.isShowing()) {
            downloadCSVScreen = new DownloadCSV();
        } else {
            downloadCSVScreen.downloadCSVFrame.toFront();
            downloadCSVScreen.downloadCSVFrame.requestFocus();
        }
    }

    private void showLogoutDialog() {
        if (logoutScreen == null || !logoutScreen.logoutFrame.isShowing()) {
            logoutScreen = new LogoutScreen();
        } else {
            logoutScreen.logoutFrame.toFront();
            logoutScreen.logoutFrame.requestFocus();
        }
    }
}

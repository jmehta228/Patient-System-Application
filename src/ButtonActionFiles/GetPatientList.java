package ButtonActionFiles;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Modern Patient List view with search, sorting, and improved table styling.
 */
public class GetPatientList extends JFrame {
    public static JFrame getPatientListFrame;
    public static String[][] patientInformation;
    private static final String[] COLUMN_NAMES = {"ID", "First Name", "Last Name", "Birthdate", "Status"};

    private ModernComponents.ModernTable table;
    private DefaultTableModel tableModel;
    private ModernComponents.ModernTextField searchField;
    private JLabel resultCountLabel;
    private String[][] originalData;
    private CardLayout tableCardLayout;
    private JPanel tableCardPanel;
    private static final String CARD_TABLE = "table";
    private static final String CARD_EMPTY = "empty";

    public GetPatientList() throws FileNotFoundException, SQLException {
        createFrame();
    }

    private void createFrame() throws SQLException, FileNotFoundException {
        getPatientListFrame = new JFrame("Patient Records");
        getPatientListFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getPatientListFrame.setSize(900, 600);
        getPatientListFrame.setMinimumSize(new Dimension(700, 400));
        getPatientListFrame.setLocationRelativeTo(null);
        getPatientListFrame.setBackground(AppTheme.BG_PRIMARY);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(AppTheme.BG_PRIMARY);

        mainPanel.add(createHeader(), BorderLayout.NORTH);
        mainPanel.add(createTablePanel(), BorderLayout.CENTER);
        mainPanel.add(createFooter(), BorderLayout.SOUTH);

        getPatientListFrame.add(mainPanel);
        getPatientListFrame.setVisible(true);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(AppTheme.BG_SECONDARY);
        header.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, AppTheme.BORDER),
            BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));

        // Title section
        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Patient Records");
        titleLabel.setFont(AppTheme.FONT_SUBTITLE);
        titleLabel.setForeground(AppTheme.TEXT_PRIMARY);

        resultCountLabel = new JLabel("Loading...");
        resultCountLabel.setFont(AppTheme.FONT_SMALL);
        resultCountLabel.setForeground(AppTheme.TEXT_SECONDARY);

        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(4));
        titlePanel.add(resultCountLabel);

        // Search section
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        searchPanel.setOpaque(false);

        searchField = new ModernComponents.ModernTextField("Search patients...");
        searchField.setPreferredSize(new Dimension(250, AppTheme.INPUT_HEIGHT));
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { filterTable(); }
            @Override
            public void removeUpdate(DocumentEvent e) { filterTable(); }
            @Override
            public void changedUpdate(DocumentEvent e) { filterTable(); }
        });

        ModernComponents.SecondaryButton refreshBtn = new ModernComponents.SecondaryButton("Refresh");
        refreshBtn.setPreferredSize(new Dimension(100, AppTheme.INPUT_HEIGHT));
        refreshBtn.addActionListener(e -> refreshData());

        searchPanel.add(searchField);
        searchPanel.add(Box.createHorizontalStrut(10));
        searchPanel.add(refreshBtn);

        header.add(titlePanel, BorderLayout.WEST);
        header.add(searchPanel, BorderLayout.EAST);

        return header;
    }

    private JPanel createTablePanel() throws SQLException, FileNotFoundException {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(AppTheme.BG_PRIMARY);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        // Load data
        loadData();

        // Create table model
        tableModel = new DefaultTableModel(patientInformation, COLUMN_NAMES) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Create modern table
        table = new ModernComponents.ModernTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Set column widths
        table.getColumnModel().getColumn(0).setPreferredWidth(60);  // ID
        table.getColumnModel().getColumn(1).setPreferredWidth(150); // First Name
        table.getColumnModel().getColumn(2).setPreferredWidth(150); // Last Name
        table.getColumnModel().getColumn(3).setPreferredWidth(120); // Birthdate
        table.getColumnModel().getColumn(4).setPreferredWidth(100); // Status

        // Custom renderer for status column
        table.getColumnModel().getColumn(4).setCellRenderer(new StatusCellRenderer());

        // Enable sorting
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        // Scroll pane
        JScrollPane scrollPane = ModernComponents.createModernScrollPane(table);
        scrollPane.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, AppTheme.BORDER));

        // Table/empty state (card layout so refresh can switch views)
        tableCardLayout = new CardLayout();
        tableCardPanel = new JPanel(tableCardLayout);
        tableCardPanel.setBackground(AppTheme.BG_PRIMARY);

        tableCardPanel.add(scrollPane, CARD_TABLE);
        tableCardPanel.add(createEmptyState(), CARD_EMPTY);

        tablePanel.add(tableCardPanel, BorderLayout.CENTER);
        updateTableView();

        updateResultCount();

        return tablePanel;
    }

    private JPanel createEmptyState() {
        JPanel emptyPanel = new JPanel();
        emptyPanel.setBackground(AppTheme.BG_PRIMARY);
        emptyPanel.setLayout(new BoxLayout(emptyPanel, BoxLayout.Y_AXIS));
        emptyPanel.setBorder(BorderFactory.createEmptyBorder(80, 0, 80, 0));

        JLabel iconLabel = new JLabel("\u2639");
        iconLabel.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 48));
        iconLabel.setForeground(AppTheme.TEXT_MUTED);
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel messageLabel = new JLabel("No patients found");
        messageLabel.setFont(AppTheme.FONT_HEADING);
        messageLabel.setForeground(AppTheme.TEXT_SECONDARY);
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel hintLabel = new JLabel("Add a new patient to get started");
        hintLabel.setFont(AppTheme.FONT_BODY);
        hintLabel.setForeground(AppTheme.TEXT_MUTED);
        hintLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        emptyPanel.add(Box.createVerticalGlue());
        emptyPanel.add(iconLabel);
        emptyPanel.add(Box.createVerticalStrut(15));
        emptyPanel.add(messageLabel);
        emptyPanel.add(Box.createVerticalStrut(8));
        emptyPanel.add(hintLabel);
        emptyPanel.add(Box.createVerticalGlue());

        return emptyPanel;
    }

    private JPanel createFooter() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        footer.setBackground(AppTheme.BG_PRIMARY);
        footer.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, AppTheme.BORDER));

        ModernComponents.SecondaryButton closeButton = new ModernComponents.SecondaryButton("Close");
        closeButton.setPreferredSize(new Dimension(AppTheme.BUTTON_WIDTH_MD, AppTheme.BUTTON_HEIGHT));
        closeButton.addActionListener(e -> getPatientListFrame.dispose());

        footer.add(closeButton);

        return footer;
    }

    private void loadData() throws SQLException, FileNotFoundException {
        patientInformation = DatabaseUtils.getPatientListFromDatabase();
        if (patientInformation != null) {
            originalData = patientInformation.clone();
        } else {
            patientInformation = new String[0][5];
            originalData = new String[0][5];
        }
    }

    private void filterTable() {
        String searchText = searchField.getText().toLowerCase().trim();

        if (searchText.isEmpty()) {
            // Reset to original data
            tableModel.setRowCount(0);
            for (String[] row : originalData) {
                tableModel.addRow(row);
            }
        } else {
            // Filter data
            tableModel.setRowCount(0);
            for (String[] row : originalData) {
                boolean matches = false;
                for (String cell : row) {
                    if (cell != null && cell.toLowerCase().contains(searchText)) {
                        matches = true;
                        break;
                    }
                }
                if (matches) {
                    tableModel.addRow(row);
                }
            }
        }

        updateResultCount();
    }

    private void refreshData() {
        try {
            loadData();
            tableModel.setRowCount(0);
            for (String[] row : patientInformation) {
                tableModel.addRow(row);
            }
            searchField.setText("");
            updateTableView();
            updateResultCount();
            ToastNotification.showSuccess(getPatientListFrame, "Patient list refreshed");
        } catch (Exception e) {
            ToastNotification.showError(getPatientListFrame, "Failed to refresh: " + e.getMessage());
        }
    }

    private void updateTableView() {
        if (tableCardLayout == null || tableCardPanel == null) {
            return;
        }
        boolean hasData = originalData != null && originalData.length > 0;
        tableCardLayout.show(tableCardPanel, hasData ? CARD_TABLE : CARD_EMPTY);
    }

    private void updateResultCount() {
        int count = tableModel.getRowCount();
        int total = originalData != null ? originalData.length : 0;

        if (searchField.getText().isEmpty()) {
            resultCountLabel.setText(total + " patient" + (total != 1 ? "s" : "") + " total");
        } else {
            resultCountLabel.setText("Showing " + count + " of " + total + " patients");
        }
    }

    // Custom cell renderer for status column
    private static class StatusCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {

            JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
            panel.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));

            if (isSelected) {
                panel.setBackground(AppTheme.PRIMARY_LIGHT);
            } else {
                panel.setBackground(row % 2 == 0 ? AppTheme.BG_CARD : AppTheme.BG_PRIMARY);
            }

            if (value != null) {
                String status = value.toString();
                JLabel badge = new JLabel(status);
                badge.setFont(AppTheme.FONT_SMALL);
                badge.setOpaque(true);
                badge.setBorder(BorderFactory.createEmptyBorder(3, 10, 3, 10));

                if ("Sick".equalsIgnoreCase(status)) {
                    badge.setBackground(AppTheme.STATUS_SICK_BG);
                    badge.setForeground(AppTheme.ERROR);
                } else if ("Recover".equalsIgnoreCase(status)) {
                    badge.setBackground(AppTheme.STATUS_RECOVER_BG);
                    badge.setForeground(AppTheme.SUCCESS);
                } else {
                    badge.setBackground(AppTheme.BG_PRIMARY);
                    badge.setForeground(AppTheme.TEXT_SECONDARY);
                }

                panel.add(badge);
            }

            return panel;
        }
    }
}

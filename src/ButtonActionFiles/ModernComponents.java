package ButtonActionFiles;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Collection of modern, styled Swing components for the Patient System Application.
 */
public class ModernComponents {

    // ===== MODERN BUTTON =====
    public static class ModernButton extends JButton {
        private Color backgroundColor;
        private Color hoverColor;
        private Color pressedColor;
        private Color foregroundColor;
        private int radius = AppTheme.BUTTON_RADIUS;
        private boolean isHovered = false;
        private boolean isPressed = false;

        public ModernButton(String text) {
            this(text, AppTheme.PRIMARY, AppTheme.TEXT_LIGHT);
        }

        public ModernButton(String text, Color bgColor, Color fgColor) {
            super(text);
            this.backgroundColor = bgColor;
            this.foregroundColor = fgColor;
            this.hoverColor = AppTheme.createHoverColor(bgColor);
            this.pressedColor = AppTheme.createPressedColor(bgColor);
            init();
        }

        private void init() {
            setFont(AppTheme.FONT_BUTTON);
            setForeground(foregroundColor);
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setPreferredSize(new Dimension(120, AppTheme.BUTTON_HEIGHT));

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    isHovered = true;
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    isHovered = false;
                    repaint();
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    isPressed = true;
                    repaint();
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    isPressed = false;
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            Color bgColor = isPressed ? pressedColor : (isHovered ? hoverColor : backgroundColor);

            if (!isEnabled()) {
                bgColor = new Color(200, 200, 200);
                g2.setColor(new Color(150, 150, 150));
            } else {
                g2.setColor(bgColor);
            }

            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), radius * 2, radius * 2));
            g2.dispose();

            super.paintComponent(g);
        }

        public void setButtonColors(Color bg, Color fg) {
            this.backgroundColor = bg;
            this.foregroundColor = fg;
            this.hoverColor = AppTheme.createHoverColor(bg);
            this.pressedColor = AppTheme.createPressedColor(bg);
            setForeground(fg);
            repaint();
        }
    }

    // ===== SECONDARY BUTTON (Outlined) =====
    public static class SecondaryButton extends JButton {
        private Color borderColor;
        private Color hoverBgColor;
        private int radius = AppTheme.BUTTON_RADIUS;
        private boolean isHovered = false;

        public SecondaryButton(String text) {
            this(text, AppTheme.PRIMARY);
        }

        public SecondaryButton(String text, Color color) {
            super(text);
            this.borderColor = color;
            this.hoverBgColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), 20);
            init();
        }

        private void init() {
            setFont(AppTheme.FONT_BUTTON);
            setForeground(borderColor);
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setPreferredSize(new Dimension(120, AppTheme.BUTTON_HEIGHT));

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    isHovered = true;
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    isHovered = false;
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (isHovered) {
                g2.setColor(hoverBgColor);
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), radius * 2, radius * 2));
            }

            g2.setColor(borderColor);
            g2.setStroke(new BasicStroke(1.5f));
            g2.draw(new RoundRectangle2D.Double(1, 1, getWidth() - 2, getHeight() - 2, radius * 2, radius * 2));
            g2.dispose();

            super.paintComponent(g);
        }
    }

    // ===== MODERN TEXT FIELD =====
    public static class ModernTextField extends JTextField {
        private String placeholder;
        private Color borderColor = AppTheme.BORDER;
        private Color focusBorderColor = AppTheme.PRIMARY;
        private boolean isFocused = false;
        private JLabel iconLabel;

        public ModernTextField() {
            this("");
        }

        public ModernTextField(String placeholder) {
            this.placeholder = placeholder;
            init();
        }

        private void init() {
            setFont(AppTheme.FONT_BODY);
            setForeground(AppTheme.TEXT_PRIMARY);
            setCaretColor(AppTheme.PRIMARY);
            setBorder(BorderFactory.createEmptyBorder(
                AppTheme.SPACING_SM, AppTheme.SPACING_MD,
                AppTheme.SPACING_SM, AppTheme.SPACING_MD));
            setPreferredSize(new Dimension(200, AppTheme.INPUT_HEIGHT));

            addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    isFocused = true;
                    repaint();
                }

                @Override
                public void focusLost(FocusEvent e) {
                    isFocused = false;
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Background
            g2.setColor(getBackground());
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(),
                AppTheme.INPUT_RADIUS * 2, AppTheme.INPUT_RADIUS * 2));

            g2.dispose();
            super.paintComponent(g);

            // Placeholder text
            if (getText().isEmpty() && !isFocused && placeholder != null && !placeholder.isEmpty()) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g2d.setColor(AppTheme.TEXT_MUTED);
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                int x = getInsets().left;
                int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
                g2d.drawString(placeholder, x, y);
                g2d.dispose();
            }
        }

        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(isFocused ? focusBorderColor : borderColor);
            g2.setStroke(new BasicStroke(isFocused ? 2f : 1f));
            g2.draw(new RoundRectangle2D.Double(0.5, 0.5, getWidth() - 1, getHeight() - 1,
                AppTheme.INPUT_RADIUS * 2, AppTheme.INPUT_RADIUS * 2));
            g2.dispose();
        }

        public void setPlaceholder(String placeholder) {
            this.placeholder = placeholder;
            repaint();
        }
    }

    // ===== MODERN PASSWORD FIELD =====
    public static class ModernPasswordField extends JPasswordField {
        private String placeholder;
        private Color borderColor = AppTheme.BORDER;
        private Color focusBorderColor = AppTheme.PRIMARY;
        private boolean isFocused = false;

        public ModernPasswordField() {
            this("");
        }

        public ModernPasswordField(String placeholder) {
            this.placeholder = placeholder;
            init();
        }

        private void init() {
            setFont(AppTheme.FONT_BODY);
            setForeground(AppTheme.TEXT_PRIMARY);
            setCaretColor(AppTheme.PRIMARY);
            setBorder(BorderFactory.createEmptyBorder(
                AppTheme.SPACING_SM, AppTheme.SPACING_MD,
                AppTheme.SPACING_SM, AppTheme.SPACING_MD));
            setPreferredSize(new Dimension(200, AppTheme.INPUT_HEIGHT));

            addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    isFocused = true;
                    repaint();
                }

                @Override
                public void focusLost(FocusEvent e) {
                    isFocused = false;
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(),
                AppTheme.INPUT_RADIUS * 2, AppTheme.INPUT_RADIUS * 2));
            g2.dispose();

            super.paintComponent(g);

            // Placeholder text
            if (getPassword().length == 0 && !isFocused && placeholder != null && !placeholder.isEmpty()) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g2d.setColor(AppTheme.TEXT_MUTED);
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                int x = getInsets().left;
                int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
                g2d.drawString(placeholder, x, y);
                g2d.dispose();
            }
        }

        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(isFocused ? focusBorderColor : borderColor);
            g2.setStroke(new BasicStroke(isFocused ? 2f : 1f));
            g2.draw(new RoundRectangle2D.Double(0.5, 0.5, getWidth() - 1, getHeight() - 1,
                AppTheme.INPUT_RADIUS * 2, AppTheme.INPUT_RADIUS * 2));
            g2.dispose();
        }

        public void setPlaceholder(String placeholder) {
            this.placeholder = placeholder;
            repaint();
        }
    }

    // ===== MODERN CARD PANEL =====
    public static class CardPanel extends JPanel {
        private int radius = AppTheme.CARD_RADIUS;
        private boolean hasShadow = true;

        public CardPanel() {
            setOpaque(false);
            setBackground(AppTheme.BG_CARD);
            setBorder(BorderFactory.createEmptyBorder(
                AppTheme.SPACING_LG, AppTheme.SPACING_LG,
                AppTheme.SPACING_LG, AppTheme.SPACING_LG));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Shadow
            if (hasShadow) {
                g2.setColor(AppTheme.SHADOW_COLOR);
                g2.fill(new RoundRectangle2D.Double(2, 2, getWidth() - 2, getHeight() - 2, radius * 2, radius * 2));
            }

            // Card background
            g2.setColor(getBackground());
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth() - 2, getHeight() - 2, radius * 2, radius * 2));

            // Border
            g2.setColor(AppTheme.BORDER);
            g2.draw(new RoundRectangle2D.Double(0, 0, getWidth() - 3, getHeight() - 3, radius * 2, radius * 2));

            g2.dispose();
            super.paintComponent(g);
        }

        public void setShadow(boolean hasShadow) {
            this.hasShadow = hasShadow;
            repaint();
        }
    }

    // ===== SIDEBAR BUTTON =====
    public static class SidebarButton extends JButton {
        private boolean isSelected = false;
        private Color hoverColor = new Color(255, 255, 255, 20);
        private Color selectedColor = new Color(255, 255, 255, 30);
        private String iconChar;
        private boolean isHovered = false;

        public SidebarButton(String text, String iconChar) {
            super(text);
            this.iconChar = iconChar;
            init();
        }

        private void init() {
            setFont(AppTheme.FONT_BODY);
            setForeground(new Color(200, 200, 200));
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setHorizontalAlignment(SwingConstants.LEFT);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setPreferredSize(new Dimension(AppTheme.SIDEBAR_WIDTH - 20, 44));
            setBorder(BorderFactory.createEmptyBorder(0, AppTheme.SPACING_MD, 0, AppTheme.SPACING_MD));

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    isHovered = true;
                    if (!isSelected) {
                        setForeground(Color.WHITE);
                    }
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    isHovered = false;
                    if (!isSelected) {
                        setForeground(new Color(200, 200, 200));
                    }
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (isSelected) {
                g2.setColor(selectedColor);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                // Left accent bar
                g2.setColor(AppTheme.PRIMARY);
                g2.fillRoundRect(0, 4, 3, getHeight() - 8, 2, 2);
            } else if (isHovered) {
                g2.setColor(hoverColor);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
            }

            // Icon
            g2.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 16));
            g2.setColor(getForeground());
            g2.drawString(iconChar, AppTheme.SPACING_MD, getHeight() / 2 + 5);

            g2.dispose();

            // Adjust text position for icon
            setBorder(BorderFactory.createEmptyBorder(0, AppTheme.SPACING_MD + 30, 0, AppTheme.SPACING_MD));
            super.paintComponent(g);
        }

        public void setSelected(boolean selected) {
            this.isSelected = selected;
            setForeground(selected ? Color.WHITE : new Color(200, 200, 200));
            repaint();
        }

        public boolean isSelected() {
            return isSelected;
        }
    }

    // ===== MODERN TABLE =====
    public static class ModernTable extends JTable {
        public ModernTable(TableModel model) {
            super(model);
            init();
        }

        private void init() {
            setFont(AppTheme.FONT_BODY);
            setRowHeight(44);
            setShowGrid(false);
            setIntercellSpacing(new Dimension(0, 0));
            setSelectionBackground(AppTheme.PRIMARY_LIGHT);
            setSelectionForeground(AppTheme.TEXT_PRIMARY);
            setBackground(AppTheme.BG_CARD);
            setFillsViewportHeight(true);

            // Header styling
            JTableHeader header = getTableHeader();
            header.setFont(AppTheme.FONT_BODY_BOLD);
            header.setBackground(AppTheme.BG_PRIMARY);
            header.setForeground(AppTheme.TEXT_PRIMARY);
            header.setPreferredSize(new Dimension(header.getPreferredSize().width, 48));
            header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, AppTheme.BORDER));

            // Row striping
            setDefaultRenderer(Object.class, new ModernTableCellRenderer());
        }
    }

    // ===== MODERN TABLE CELL RENDERER =====
    public static class ModernTableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            setBorder(BorderFactory.createEmptyBorder(0, AppTheme.SPACING_MD, 0, AppTheme.SPACING_MD));

            if (isSelected) {
                c.setBackground(AppTheme.PRIMARY_LIGHT);
                c.setForeground(AppTheme.TEXT_PRIMARY);
            } else {
                c.setBackground(row % 2 == 0 ? AppTheme.BG_CARD : AppTheme.BG_PRIMARY);
                c.setForeground(AppTheme.TEXT_PRIMARY);
            }

            // Status column styling
            if (value != null) {
                String text = value.toString();
                if (text.equals("Sick")) {
                    c.setForeground(AppTheme.ERROR);
                } else if (text.equals("Recover")) {
                    c.setForeground(AppTheme.SUCCESS);
                }
            }

            return c;
        }
    }

    // ===== MODERN SCROLL PANE =====
    public static JScrollPane createModernScrollPane(Component view) {
        JScrollPane scrollPane = new JScrollPane(view);
        scrollPane.setBorder(BorderFactory.createLineBorder(AppTheme.BORDER, 1));
        scrollPane.getViewport().setBackground(AppTheme.BG_CARD);

        // Custom scrollbar
        scrollPane.getVerticalScrollBar().setUI(new ModernScrollBarUI());
        scrollPane.getHorizontalScrollBar().setUI(new ModernScrollBarUI());
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, Integer.MAX_VALUE));
        scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(Integer.MAX_VALUE, 10));

        return scrollPane;
    }

    // ===== MODERN SCROLLBAR UI =====
    public static class ModernScrollBarUI extends BasicScrollBarUI {
        @Override
        protected void configureScrollBarColors() {
            this.thumbColor = new Color(180, 180, 180);
            this.trackColor = AppTheme.BG_PRIMARY;
        }

        @Override
        protected JButton createDecreaseButton(int orientation) {
            return createZeroButton();
        }

        @Override
        protected JButton createIncreaseButton(int orientation) {
            return createZeroButton();
        }

        private JButton createZeroButton() {
            JButton button = new JButton();
            button.setPreferredSize(new Dimension(0, 0));
            return button;
        }

        @Override
        protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(thumbColor);
            g2.fill(new RoundRectangle2D.Double(thumbBounds.x + 2, thumbBounds.y + 2,
                thumbBounds.width - 4, thumbBounds.height - 4, 6, 6));
            g2.dispose();
        }

        @Override
        protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
            g.setColor(trackColor);
            g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
        }
    }

    // ===== FORM FIELD WITH LABEL =====
    public static class FormField extends JPanel {
        private JLabel label;
        private ModernTextField textField;
        private JLabel errorLabel;

        public FormField(String labelText, String placeholder) {
            this(labelText, placeholder, false);
        }

        public FormField(String labelText, String placeholder, boolean required) {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setOpaque(false);
            setAlignmentX(Component.LEFT_ALIGNMENT);

            // Label
            JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
            labelPanel.setOpaque(false);
            label = new JLabel(labelText);
            label.setFont(AppTheme.FONT_BODY_BOLD);
            label.setForeground(AppTheme.TEXT_PRIMARY);
            labelPanel.add(label);

            if (required) {
                JLabel reqLabel = new JLabel(" *");
                reqLabel.setFont(AppTheme.FONT_BODY_BOLD);
                reqLabel.setForeground(AppTheme.ERROR);
                labelPanel.add(reqLabel);
            }
            labelPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            labelPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 24));

            // Text field
            textField = new ModernTextField(placeholder);
            textField.setAlignmentX(Component.LEFT_ALIGNMENT);
            textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, AppTheme.INPUT_HEIGHT));

            // Error label
            errorLabel = new JLabel(" ");
            errorLabel.setFont(AppTheme.FONT_SMALL);
            errorLabel.setForeground(AppTheme.ERROR);
            errorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

            add(labelPanel);
            add(Box.createVerticalStrut(AppTheme.SPACING_XS));
            add(textField);
            add(Box.createVerticalStrut(AppTheme.SPACING_XS));
            add(errorLabel);
        }

        public String getText() {
            return textField.getText();
        }

        public void setText(String text) {
            textField.setText(text);
        }

        public void setError(String error) {
            errorLabel.setText(error != null ? error : " ");
        }

        public void clearError() {
            errorLabel.setText(" ");
        }

        public ModernTextField getTextField() {
            return textField;
        }
    }

    // ===== SEARCH FIELD =====
    public static class SearchField extends JPanel {
        private ModernTextField searchField;
        private Timer debounceTimer;
        private Runnable onSearch;

        public SearchField(String placeholder) {
            setLayout(new BorderLayout());
            setOpaque(false);

            searchField = new ModernTextField(placeholder);
            searchField.setBorder(BorderFactory.createEmptyBorder(
                AppTheme.SPACING_SM, AppTheme.SPACING_XL,
                AppTheme.SPACING_SM, AppTheme.SPACING_MD));

            add(searchField, BorderLayout.CENTER);
        }

        public void setOnSearch(Runnable onSearch) {
            this.onSearch = onSearch;
            searchField.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) { debounceSearch(); }
                @Override
                public void removeUpdate(DocumentEvent e) { debounceSearch(); }
                @Override
                public void changedUpdate(DocumentEvent e) { debounceSearch(); }
            });
        }

        private void debounceSearch() {
            if (debounceTimer != null) {
                debounceTimer.stop();
            }
            debounceTimer = new Timer(300, e -> {
                if (onSearch != null) {
                    onSearch.run();
                }
            });
            debounceTimer.setRepeats(false);
            debounceTimer.start();
        }

        public String getText() {
            return searchField.getText();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Draw search icon
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(AppTheme.TEXT_MUTED);
            g2.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 14));
            g2.drawString("\uD83D\uDD0D", 12, getHeight() / 2 + 5);
            g2.dispose();
        }
    }

    // ===== STAT CARD =====
    public static class StatCard extends CardPanel {
        public StatCard(String title, String value, Color accentColor) {
            setLayout(new BorderLayout(0, AppTheme.SPACING_SM));

            // Value label
            JLabel valueLabel = new JLabel(value);
            valueLabel.setFont(new Font(AppTheme.FONT_FAMILY, Font.BOLD, 36));
            valueLabel.setForeground(accentColor);

            // Title label
            JLabel titleLabel = new JLabel(title);
            titleLabel.setFont(AppTheme.FONT_BODY);
            titleLabel.setForeground(AppTheme.TEXT_SECONDARY);

            // Accent bar
            JPanel accentBar = new JPanel();
            accentBar.setBackground(accentColor);
            accentBar.setPreferredSize(new Dimension(4, 60));

            JPanel contentPanel = new JPanel();
            contentPanel.setOpaque(false);
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
            contentPanel.add(valueLabel);
            contentPanel.add(titleLabel);

            add(accentBar, BorderLayout.WEST);
            add(contentPanel, BorderLayout.CENTER);
        }
    }

    // ===== STATUS BADGE =====
    public static class StatusBadge extends JLabel {
        public StatusBadge(String status) {
            super(status);
            setOpaque(true);
            setFont(AppTheme.FONT_SMALL);
            setBorder(BorderFactory.createEmptyBorder(4, 12, 4, 12));

            if ("Sick".equalsIgnoreCase(status)) {
                setBackground(AppTheme.STATUS_SICK_BG);
                setForeground(AppTheme.ERROR);
            } else if ("Recover".equalsIgnoreCase(status)) {
                setBackground(AppTheme.STATUS_RECOVER_BG);
                setForeground(AppTheme.SUCCESS);
            } else {
                setBackground(AppTheme.BG_PRIMARY);
                setForeground(AppTheme.TEXT_SECONDARY);
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    // ===== ICON BUTTON =====
    public static class IconButton extends JButton {
        private String iconChar;
        private Color hoverColor = new Color(0, 0, 0, 20);
        private boolean isHovered = false;

        public IconButton(String iconChar, String tooltip) {
            this.iconChar = iconChar;
            setToolTipText(tooltip);
            init();
        }

        private void init() {
            setPreferredSize(new Dimension(36, 36));
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setForeground(AppTheme.TEXT_SECONDARY);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    isHovered = true;
                    setForeground(AppTheme.TEXT_PRIMARY);
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    isHovered = false;
                    setForeground(AppTheme.TEXT_SECONDARY);
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (isHovered) {
                g2.setColor(hoverColor);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
            }

            g2.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 18));
            g2.setColor(getForeground());
            FontMetrics fm = g2.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(iconChar)) / 2;
            int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
            g2.drawString(iconChar, x, y);

            g2.dispose();
        }
    }
}

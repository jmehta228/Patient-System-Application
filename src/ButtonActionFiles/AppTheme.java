package ButtonActionFiles;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Centralized theme system for the Patient System Application.
 * Provides consistent colors, fonts, spacing, and styling across all UI components.
 */
public class AppTheme {

    // ===== COLOR PALETTE =====
    // Primary colors - Healthcare-friendly teal/blue
    public static final Color PRIMARY = new Color(0, 150, 136);           // Teal
    public static final Color PRIMARY_DARK = new Color(0, 121, 107);      // Dark Teal
    public static final Color PRIMARY_LIGHT = new Color(128, 203, 196);   // Light Teal

    // Secondary colors
    public static final Color SECONDARY = new Color(38, 50, 56);          // Dark blue-gray
    public static final Color SECONDARY_LIGHT = new Color(69, 90, 100);   // Light blue-gray

    // Background colors
    public static final Color BG_PRIMARY = new Color(250, 250, 252);      // Off-white
    public static final Color BG_SECONDARY = new Color(255, 255, 255);    // Pure white
    public static final Color BG_DARK = new Color(38, 50, 56);            // Dark background
    public static final Color BG_SIDEBAR = new Color(30, 41, 46);         // Sidebar dark
    public static final Color BG_CARD = new Color(255, 255, 255);         // Card background
    public static final Color BG_HOVER = new Color(232, 245, 243);        // Hover state

    // Text colors
    public static final Color TEXT_PRIMARY = new Color(33, 33, 33);       // Dark gray
    public static final Color TEXT_SECONDARY = new Color(117, 117, 117);  // Medium gray
    public static final Color TEXT_LIGHT = new Color(255, 255, 255);      // White
    public static final Color TEXT_MUTED = new Color(158, 158, 158);      // Muted gray

    // Status colors
    public static final Color SUCCESS = new Color(76, 175, 80);           // Green
    public static final Color SUCCESS_LIGHT = new Color(232, 245, 233);   // Light green bg
    public static final Color WARNING = new Color(255, 152, 0);           // Orange
    public static final Color WARNING_LIGHT = new Color(255, 243, 224);   // Light orange bg
    public static final Color ERROR = new Color(244, 67, 54);             // Red
    public static final Color ERROR_LIGHT = new Color(255, 235, 238);     // Light red bg
    public static final Color INFO = new Color(33, 150, 243);             // Blue
    public static final Color INFO_LIGHT = new Color(227, 242, 253);      // Light blue bg

    // Patient status colors
    public static final Color STATUS_SICK = new Color(255, 138, 128);     // Coral red
    public static final Color STATUS_SICK_BG = new Color(255, 235, 238);  // Light red bg
    public static final Color STATUS_RECOVER = new Color(129, 199, 132);  // Light green
    public static final Color STATUS_RECOVER_BG = new Color(232, 245, 233); // Light green bg

    // Border colors
    public static final Color BORDER = new Color(224, 224, 224);          // Light gray
    public static final Color BORDER_FOCUS = PRIMARY;                      // Focus border
    public static final Color DIVIDER = new Color(238, 238, 238);         // Divider line

    // ===== TYPOGRAPHY =====
    public static final String FONT_FAMILY = "Segoe UI";
    public static final String FONT_FAMILY_FALLBACK = "Arial";

    // Font sizes
    public static final int FONT_SIZE_XS = 11;
    public static final int FONT_SIZE_SM = 12;
    public static final int FONT_SIZE_MD = 14;
    public static final int FONT_SIZE_LG = 16;
    public static final int FONT_SIZE_XL = 20;
    public static final int FONT_SIZE_XXL = 24;
    public static final int FONT_SIZE_XXXL = 32;

    // Pre-defined fonts
    public static final Font FONT_TITLE = new Font(FONT_FAMILY, Font.BOLD, FONT_SIZE_XXL);
    public static final Font FONT_SUBTITLE = new Font(FONT_FAMILY, Font.BOLD, FONT_SIZE_XL);
    public static final Font FONT_HEADING = new Font(FONT_FAMILY, Font.BOLD, FONT_SIZE_LG);
    public static final Font FONT_BODY = new Font(FONT_FAMILY, Font.PLAIN, FONT_SIZE_MD);
    public static final Font FONT_BODY_BOLD = new Font(FONT_FAMILY, Font.BOLD, FONT_SIZE_MD);
    public static final Font FONT_SMALL = new Font(FONT_FAMILY, Font.PLAIN, FONT_SIZE_SM);
    public static final Font FONT_CAPTION = new Font(FONT_FAMILY, Font.PLAIN, FONT_SIZE_XS);
    public static final Font FONT_BUTTON = new Font(FONT_FAMILY, Font.BOLD, FONT_SIZE_MD);

    // ===== SPACING =====
    public static final int SPACING_XS = 4;
    public static final int SPACING_SM = 8;
    public static final int SPACING_MD = 16;
    public static final int SPACING_LG = 24;
    public static final int SPACING_XL = 32;
    public static final int SPACING_XXL = 48;

    // ===== SIZING =====
    // Button dimensions
    public static final int BUTTON_HEIGHT = 40;
    public static final int BUTTON_HEIGHT_SM = 32;
    public static final int BUTTON_HEIGHT_LG = 48;
    public static final int BUTTON_WIDTH_SM = 100;
    public static final int BUTTON_WIDTH_MD = 120;
    public static final int BUTTON_WIDTH_LG = 140;
    public static final int BUTTON_WIDTH_XL = 160;

    // Input dimensions
    public static final int INPUT_HEIGHT = 40;
    public static final int INPUT_MIN_WIDTH = 200;
    public static final int FORM_FIELD_HEIGHT = 85;  // Standard height for form field containers

    // Layout dimensions
    public static final int SIDEBAR_WIDTH = 240;
    public static final int DIALOG_WIDTH_SM = 400;
    public static final int DIALOG_WIDTH_MD = 450;
    public static final int DIALOG_WIDTH_LG = 600;
    public static final int DIALOG_MIN_WIDTH = 400;

    // Border radius
    public static final int CARD_RADIUS = 8;
    public static final int BUTTON_RADIUS = 6;
    public static final int INPUT_RADIUS = 6;

    // Border thickness
    public static final int BORDER_WIDTH = 1;

    // Icon sizes
    public static final int ICON_SIZE_SM = 16;
    public static final int ICON_SIZE_MD = 20;
    public static final int ICON_SIZE_LG = 24;

    // ===== SHADOWS =====
    public static final Color SHADOW_COLOR = new Color(0, 0, 0, 20);
    public static final Color SHADOW_COLOR_DARK = new Color(0, 0, 0, 40);

    // ===== BORDERS =====
    public static Border createRoundedBorder(int radius, Color color) {
        return BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 1),
            BorderFactory.createEmptyBorder(SPACING_SM, SPACING_MD, SPACING_SM, SPACING_MD)
        );
    }

    public static Border createCardBorder() {
        return BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER, 1),
            BorderFactory.createEmptyBorder(SPACING_MD, SPACING_MD, SPACING_MD, SPACING_MD)
        );
    }

    public static Border createPaddingBorder(int padding) {
        return BorderFactory.createEmptyBorder(padding, padding, padding, padding);
    }

    public static Border createPaddingBorder(int vertical, int horizontal) {
        return BorderFactory.createEmptyBorder(vertical, horizontal, vertical, horizontal);
    }

    // ===== UTILITY METHODS =====

    /**
     * Apply the application's look and feel
     */
    public static void applyLookAndFeel() {
        try {
            // Set system look and feel with custom modifications
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            // Customize UI defaults
            UIManager.put("Button.font", FONT_BUTTON);
            UIManager.put("Label.font", FONT_BODY);
            UIManager.put("TextField.font", FONT_BODY);
            UIManager.put("TextArea.font", FONT_BODY);
            UIManager.put("ComboBox.font", FONT_BODY);
            UIManager.put("Table.font", FONT_BODY);
            UIManager.put("TableHeader.font", FONT_BODY_BOLD);
            UIManager.put("TabbedPane.font", FONT_BODY);
            UIManager.put("Menu.font", FONT_BODY);
            UIManager.put("MenuItem.font", FONT_BODY);

            // Colors
            UIManager.put("Panel.background", BG_PRIMARY);
            UIManager.put("OptionPane.background", BG_SECONDARY);
            UIManager.put("Button.background", PRIMARY);
            UIManager.put("Button.foreground", TEXT_LIGHT);

            // Table styling
            UIManager.put("Table.gridColor", DIVIDER);
            UIManager.put("Table.selectionBackground", PRIMARY_LIGHT);
            UIManager.put("Table.selectionForeground", TEXT_PRIMARY);
            UIManager.put("TableHeader.background", BG_PRIMARY);
            UIManager.put("TableHeader.foreground", TEXT_PRIMARY);

            // Focus colors
            UIManager.put("TextField.caretForeground", PRIMARY);
            UIManager.put("TextArea.caretForeground", PRIMARY);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create a consistent panel with default styling
     */
    public static JPanel createPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(BG_PRIMARY);
        return panel;
    }

    /**
     * Create a card-style panel with border and padding
     */
    public static JPanel createCard() {
        JPanel panel = new JPanel();
        panel.setBackground(BG_CARD);
        panel.setBorder(createCardBorder());
        return panel;
    }

    /**
     * Style a label as a heading
     */
    public static void styleAsHeading(JLabel label) {
        label.setFont(FONT_HEADING);
        label.setForeground(TEXT_PRIMARY);
    }

    /**
     * Style a label as a title
     */
    public static void styleAsTitle(JLabel label) {
        label.setFont(FONT_TITLE);
        label.setForeground(TEXT_PRIMARY);
    }

    /**
     * Style a label as body text
     */
    public static void styleAsBody(JLabel label) {
        label.setFont(FONT_BODY);
        label.setForeground(TEXT_PRIMARY);
    }

    /**
     * Style a label as secondary/muted text
     */
    public static void styleAsSecondary(JLabel label) {
        label.setFont(FONT_SMALL);
        label.setForeground(TEXT_SECONDARY);
    }

    /**
     * Get a contrasting text color for a given background
     */
    public static Color getContrastingTextColor(Color background) {
        double luminance = (0.299 * background.getRed() + 0.587 * background.getGreen() + 0.114 * background.getBlue()) / 255;
        return luminance > 0.5 ? TEXT_PRIMARY : TEXT_LIGHT;
    }

    /**
     * Create a subtle hover effect color
     */
    public static Color createHoverColor(Color baseColor) {
        return new Color(
            Math.max(0, baseColor.getRed() - 15),
            Math.max(0, baseColor.getGreen() - 15),
            Math.max(0, baseColor.getBlue() - 15)
        );
    }

    /**
     * Create a subtle pressed effect color
     */
    public static Color createPressedColor(Color baseColor) {
        return new Color(
            Math.max(0, baseColor.getRed() - 30),
            Math.max(0, baseColor.getGreen() - 30),
            Math.max(0, baseColor.getBlue() - 30)
        );
    }
}

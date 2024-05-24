package ButtonActionFiles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import javax.swing.table.DefaultTableModel;

public class GetPatientList extends JFrame {
    static JFrame getPatientListFrame;
    static String[][] patientInformation;
    static String[] categories;
    JTable table;
    JButton closeButton;
    JScrollPane scrollPane;
    public GetPatientList() throws FileNotFoundException {
        getPatientListFrame = new JFrame("Patient System Application - Get Patient List");
        getPatientListFrame.setSize(new Dimension(500, 200));
        getPatientListFrame.setLocation(new Point(500, 200));

        patientInformation = Utils.getPatientList(Utils.fileName);
        categories = new String[]{"First Name", "Last Name", "Birthdate", "Status"};

        NonEditableTableModel model = new NonEditableTableModel(patientInformation, categories);
        table = new JTable(model);

        closeButton = new JButton("Close");
        closeButton.addActionListener(new CloseButtonAction());
        closeButton.setPreferredSize(new Dimension(100, 50));
        scrollPane = new JScrollPane(table);
        getPatientListFrame.add(scrollPane);
//        getPatientListFrame.add(closeButton);
        getPatientListFrame.setResizable(true);
        getPatientListFrame.pack();
        getPatientListFrame.setVisible(true);
    }

    private static class CloseButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            getPatientListFrame.dispose();
        }
    }

    public static class NonEditableTableModel extends DefaultTableModel {
        public NonEditableTableModel(Object[][] data, Object[] columnNames) {
            super(patientInformation, categories);
        }
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }
}
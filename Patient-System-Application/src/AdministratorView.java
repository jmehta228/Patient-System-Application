import javax.swing.*;
import java.awt.*;

public class AdministratorView extends JPanel {
    public AdministratorView() {
        JFrame administratorViewFrame = new JFrame();
        administratorViewFrame.setPreferredSize(new Dimension(1440, 805));
        administratorViewFrame.setResizable(false);
        administratorViewFrame.setTitle("Patient System Application");

        JPanel administratorViewPanel = new JPanel();
        JLabel administratorViewLabel = new JLabel("Administrator view");
        administratorViewLabel.setFont(new Font("Calibri", Font.PLAIN, 20));
        administratorViewPanel.add(administratorViewLabel);

        administratorViewFrame.add(administratorViewPanel);
        administratorViewFrame.setResizable(true);
        administratorViewFrame.pack();
        administratorViewFrame.setVisible(true);
    }
}

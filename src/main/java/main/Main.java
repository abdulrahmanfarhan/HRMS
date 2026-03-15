package main;

import com.formdev.flatlaf.intellijthemes.FlatArcIJTheme;
import com.project.hrms.Dashboard;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        try {
            FlatArcIJTheme.setup();
        } catch (Exception ex) {
            System.err.println("فشل تحميل الثيم: " + ex.getMessage());
        }

        SwingUtilities.invokeLater(() -> {
            try {
                Dashboard dashboard = new Dashboard();
                dashboard.setLocationRelativeTo(null);
                dashboard.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}

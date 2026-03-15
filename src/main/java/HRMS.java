
import com.formdev.flatlaf.FlatIntelliJLaf;
import java.awt.CardLayout;

public class HRMS extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(HRMS.class.getName());

    CardLayout card;

    public HRMS() {
        initComponents();
        this.card = (CardLayout) main.getLayout();
        Employee e = new Employee();
        card.addLayoutComponent(e, "em");
        Attendence a = new Attendence();
        card.addLayoutComponent(a, "at");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        main = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        employeeButton = new javax.swing.JMenuItem();
        attendnce = new javax.swing.JMenuItem();
        payroll = new javax.swing.JMenuItem();
        setting = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        main.setMaximumSize(new java.awt.Dimension(1170, 678));
        main.setMinimumSize(new java.awt.Dimension(1170, 678));
        main.setLayout(new java.awt.CardLayout());

        jMenu2.setText("Edit");

        employeeButton.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        employeeButton.setText("Empployee");
        employeeButton.addActionListener(this::employeeButtonActionPerformed);
        jMenu2.add(employeeButton);

        attendnce.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        attendnce.setText("Attendence & Leave");
        attendnce.addActionListener(this::attendnceActionPerformed);
        jMenu2.add(attendnce);

        payroll.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        payroll.setText("PayRoll");
        payroll.addActionListener(this::payrollActionPerformed);
        jMenu2.add(payroll);

        setting.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        setting.setText("Setting");
        setting.addActionListener(this::settingActionPerformed);
        jMenu2.add(setting);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(main, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(main, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void employeeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_employeeButtonActionPerformed
        Employee e = new Employee();
        card.addLayoutComponent(e, "em");
        card.show(main, "em");
    }//GEN-LAST:event_employeeButtonActionPerformed

    private void attendnceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_attendnceActionPerformed
        Attendence a = new Attendence();
        card.addLayoutComponent(a, "at");
        card.show(main, "at");
    }//GEN-LAST:event_attendnceActionPerformed

    private void payrollActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_payrollActionPerformed
        payroll p = new payroll();
        card.addLayoutComponent(p, "p");
        card.show(main, "p");
    }//GEN-LAST:event_payrollActionPerformed

    private void settingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_settingActionPerformed
        setting s = new setting();
        card.addLayoutComponent(s, "s");
        card.show(main, "s");
    }//GEN-LAST:event_settingActionPerformed

    public static void main(String args[]) {
        FlatIntelliJLaf.setup();
        java.awt.EventQueue.invokeLater(() -> new HRMS().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem attendnce;
    private javax.swing.JMenuItem employeeButton;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel main;
    private javax.swing.JMenuItem payroll;
    private javax.swing.JMenuItem setting;
    // End of variables declaration//GEN-END:variables
}

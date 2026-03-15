package com.project.main;

import com.project.screens.Employee;
import com.project.screens.payroll;
import com.project.screens.Attendence;
import com.project.screens.setting;
import com.formdev.flatlaf.FlatIntelliJLaf;

import javax.swing.*;
import java.awt.*;


public class HRMS extends JFrame {

    private static final String PAGE_EMPLOYEE   = "EMPLOYEES";
    private static final String PAGE_ATTENDANCE = "ATTENDANCE";
    private static final String PAGE_PAYROLL    = "PAYROLL";
    private static final String PAGE_SETTING    = "SETTING";

    private final JPanel     mainPanel;
    private final CardLayout cardLayout;

    public HRMS() {
        cardLayout = new CardLayout();
        mainPanel  = new JPanel(cardLayout);

        mainPanel.add(new Employee(),   PAGE_EMPLOYEE);
        mainPanel.add(new Attendence(), PAGE_ATTENDANCE);
        mainPanel.add(new payroll(),    PAGE_PAYROLL);
        mainPanel.add(new setting(),    PAGE_SETTING);

        initUI();

        navigate(PAGE_EMPLOYEE);
    }


    private void initUI() {
        setTitle("HRMS — نظام إدارة الموارد البشرية");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        getContentPane().add(mainPanel);

        setJMenuBar(buildMenuBar());

        pack();
        setLocationRelativeTo(null);
    }

    private JMenuBar buildMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu    menu    = new JMenu("Sections");

        JMenuItem empItem = new JMenuItem("Employee");
        empItem.setAccelerator(
            KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E,
                                   java.awt.event.InputEvent.CTRL_DOWN_MASK));
        empItem.addActionListener(e -> navigate(PAGE_EMPLOYEE));

        JMenuItem attItem = new JMenuItem("Attendance & Leave");
        attItem.setAccelerator(
            KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A,
                                   java.awt.event.InputEvent.CTRL_DOWN_MASK));
        attItem.addActionListener(e -> navigate(PAGE_ATTENDANCE));

        JMenuItem payItem = new JMenuItem("PayRoll");
        payItem.setAccelerator(
            KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q,
                                   java.awt.event.InputEvent.CTRL_DOWN_MASK));
        payItem.addActionListener(e -> navigate(PAGE_PAYROLL));

        JMenuItem setItem = new JMenuItem("Setting");
        setItem.setAccelerator(
            KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S,
                                   java.awt.event.InputEvent.CTRL_DOWN_MASK));
        setItem.addActionListener(e -> navigate(PAGE_SETTING));

        menu.add(empItem);
        menu.add(attItem);
        menu.add(payItem);
        menu.add(setItem);
        menuBar.add(menu);

        return menuBar;
    }

    private void navigate(String pageKey) {
        cardLayout.show(mainPanel, pageKey);
    }

    public static void main(String[] args) {
        FlatIntelliJLaf.setup();
        SwingUtilities.invokeLater(() -> new HRMS().setVisible(true));
    }
}
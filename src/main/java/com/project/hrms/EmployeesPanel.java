package com.project.hrms;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class EmployeesPanel extends JPanel {

    private static final Color BG           = new Color(248, 250, 252);
    private static final Color CARD         = Color.WHITE;
    private static final Color ACCENT       = new Color(59, 130, 246);
    private static final Color ACCENT_LIGHT = new Color(239, 246, 255);
    private static final Color GREEN        = new Color(16, 185, 129);
    private static final Color RED          = new Color(239, 68, 68);
    private static final Color ORANGE       = new Color(245, 158, 11);
    private static final Color TEXT_PRI     = new Color(15, 23, 42);
    private static final Color TEXT_SEC     = new Color(100, 116, 139);
    private static final Color BORDER       = new Color(226, 232, 240);

    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JLabel totalLabel, activeLabel, inactiveLabel;

    private final Object[][] employeesData = {
        {"EMP-001", "أحمد خالد محمود",    "تقنية المعلومات",    "مطور برمجيات",       "2022-03-15", "4,500 ₪",  "نشط"},
        {"EMP-002", "سارة يوسف علي",      "الموارد البشرية",    "أخصائية توظيف",      "2021-07-01", "3,800 ₪",  "نشط"},
        {"EMP-003", "محمد أحمد الزيد",    "المالية",            "محاسب أول",          "2020-01-20", "5,200 ₪",  "نشط"},
        {"EMP-004", "نور حسين إبراهيم",   "المبيعات",           "مدير مبيعات",        "2019-05-10", "6,000 ₪",  "نشط"},
        {"EMP-005", "ليلى عمر النجار",     "التسويق",            "أخصائية تسويق",      "2023-02-28", "3,500 ₪",  "نشط"},
        {"EMP-006", "خالد سامي الحسن",    "تقنية المعلومات",    "مهندس شبكات",        "2021-11-15", "4,800 ₪",  "إجازة"},
        {"EMP-007", "ريم عبدالله فارس",   "المحاسبة",           "محاسبة",             "2022-08-01", "3,600 ₪",  "نشط"},
        {"EMP-008", "عمر صالح المنصور",   "الإدارة",            "مساعد إداري",        "2020-06-14", "3,200 ₪",  "نشط"},
        {"EMP-009", "دينا وليد شاهين",    "خدمة العملاء",       "ممثلة خدمة عملاء",   "2023-09-01", "2,900 ₪",  "نشط"},
        {"EMP-010", "يوسف ناصر قاسم",     "تقنية المعلومات",    "مختبر برمجيات",      "2021-04-20", "4,200 ₪",  "موقوف"},
    };

    public EmployeesPanel() {
        setLayout(new BorderLayout());
        setBackground(BG);
        setBorder(new EmptyBorder(24, 24, 24, 24));

        add(buildHeader(),   BorderLayout.NORTH);
        add(buildContent(),  BorderLayout.CENTER);
    }

    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BG);
        header.setBorder(new EmptyBorder(0, 0, 20, 0));

        JPanel titlePane = new JPanel();
        titlePane.setLayout(new BoxLayout(titlePane, BoxLayout.Y_AXIS));
        titlePane.setBackground(BG);

        JLabel title = new JLabel("إدارة الموظفين");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(TEXT_PRI);

        JLabel subtitle = new JLabel("عرض وإدارة بيانات جميع الموظفين");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setForeground(TEXT_SEC);

        titlePane.add(title);
        titlePane.add(Box.createVerticalStrut(4));
        titlePane.add(subtitle);

        JButton addBtn = createPrimaryButton("+ إضافة موظف جديد");
        addBtn.addActionListener(e -> showAddEmployeeDialog());

        header.add(titlePane, BorderLayout.WEST);
        header.add(addBtn,    BorderLayout.EAST);
        return header;
    }

    private JPanel buildContent() {
        JPanel content = new JPanel(new BorderLayout(0, 16));
        content.setBackground(BG);

        content.add(buildStatsRow(),   BorderLayout.NORTH);
        content.add(buildTableCard(),  BorderLayout.CENTER);

        return content;
    }

    private JPanel buildStatsRow() {
        JPanel row = new JPanel(new GridLayout(1, 3, 16, 0));
        row.setBackground(BG);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));

        long total    = employeesData.length;
        long active   = countByStatus("نشط");
        long inactive = total - active;

        totalLabel    = null;
        activeLabel   = null;
        inactiveLabel = null;

        row.add(buildMiniStat("👥 إجمالي الموظفين",  String.valueOf(total),    ACCENT));
        row.add(buildMiniStat("✅ الموظفون النشطون",  String.valueOf(active),   GREEN));
        row.add(buildMiniStat("⚠️ غير نشطين / إجازة", String.valueOf(inactive), ORANGE));

        return row;
    }

    private long countByStatus(String status) {
        long c = 0;
        for (Object[] row : employeesData)
            if (status.equals(row[6])) c++;
        return c;
    }

    private JPanel buildMiniStat(String label, String value, Color accent) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER, 1, true),
            new EmptyBorder(14, 18, 14, 18)
        ));

        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl.setForeground(TEXT_SEC);

        JLabel val = new JLabel(value);
        val.setFont(new Font("Segoe UI", Font.BOLD, 26));
        val.setForeground(accent);

        card.add(lbl, BorderLayout.NORTH);
        card.add(val, BorderLayout.CENTER);
        return card;
    }

    private JPanel buildTableCard() {
        JPanel card = new JPanel(new BorderLayout(0, 12));
        card.setBackground(CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER, 1, true),
            new EmptyBorder(16, 18, 16, 18)
        ));

        card.add(buildToolbar(),    BorderLayout.NORTH);
        card.add(buildTable(),      BorderLayout.CENTER);
        card.add(buildTableFooter(), BorderLayout.SOUTH);

        return card;
    }

    private JPanel buildToolbar() {
        JPanel bar = new JPanel(new BorderLayout(12, 0));
        bar.setBackground(CARD);
        bar.setBorder(new EmptyBorder(0, 0, 10, 0));

        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(280, 36));
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        searchField.setForeground(TEXT_PRI);
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER, 1, true),
            new EmptyBorder(4, 10, 4, 10)
        ));
        searchField.putClientProperty("JTextField.placeholderText", "🔍  ابحث باسم الموظف أو القسم...");
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e)  { filterTable(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e)  { filterTable(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filterTable(); }
        });

        String[] depts = {"كل الأقسام", "تقنية المعلومات", "الموارد البشرية", "المالية", "المبيعات", "التسويق", "المحاسبة", "الإدارة", "خدمة العملاء"};
        JComboBox<String> deptFilter = new JComboBox<>(depts);
        deptFilter.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        deptFilter.setPreferredSize(new Dimension(160, 36));

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        left.setBackground(CARD);
        left.add(searchField);
        left.add(deptFilter);

        JButton exportBtn = createSecondaryButton("📤 تصدير");
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        right.setBackground(CARD);
        right.add(exportBtn);

        bar.add(left,  BorderLayout.WEST);
        bar.add(right, BorderLayout.EAST);
        return bar;
    }

    private JScrollPane buildTable() {
        String[] cols = {"الرقم الوظيفي", "الاسم الكامل", "القسم", "المسمى الوظيفي", "تاريخ التعيين", "الراتب", "الحالة", "إجراءات"};
        tableModel = new DefaultTableModel(employeesData, cols) {
            @Override public boolean isCellEditable(int r, int c) { return c == 7; }
        };

        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setForeground(TEXT_PRI);
        table.setBackground(CARD);
        table.setRowHeight(42);
        table.setShowHorizontalLines(true);
        table.setGridColor(new Color(241, 245, 249));
        table.setSelectionBackground(ACCENT_LIGHT);
        table.setSelectionForeground(TEXT_PRI);
        table.setIntercellSpacing(new Dimension(0, 0));

        // رأس الجدول
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        header.setForeground(TEXT_SEC);
        header.setBackground(new Color(248, 250, 252));
        header.setPreferredSize(new Dimension(0, 40));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER));

        // عرض الأعمدة
        int[] widths = {90, 160, 130, 150, 110, 90, 80, 90};
        for (int i = 0; i < widths.length; i++)
            table.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);

        // Renderer للحالة
        table.getColumnModel().getColumn(6).setCellRenderer(new StatusRenderer());

        // Renderer/Editor للإجراءات
        table.getColumnModel().getColumn(7).setCellRenderer(new ActionRenderer());
        table.getColumnModel().getColumn(7).setCellEditor(new ActionEditor());

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(CARD);
        scroll.setPreferredSize(new Dimension(0, 380));
        return scroll;
    }

    // ── تذييل الجدول
    private JPanel buildTableFooter() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(CARD);
        footer.setBorder(new EmptyBorder(10, 0, 0, 0));

        JLabel info = new JLabel("عرض " + employeesData.length + " من " + employeesData.length + " موظف");
        info.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        info.setForeground(TEXT_SEC);

        footer.add(info, BorderLayout.WEST);
        return footer;
    }

    // ── فلترة الجدول
    private void filterTable() {
        String query = searchField.getText().toLowerCase().trim();
        tableModel.setRowCount(0);
        for (Object[] row : employeesData) {
            boolean match = query.isEmpty()
                || row[1].toString().toLowerCase().contains(query)
                || row[2].toString().toLowerCase().contains(query)
                || row[3].toString().toLowerCase().contains(query);
            if (match) tableModel.addRow(row);
        }
    }

    // ── نافذة إضافة موظف
    private void showAddEmployeeDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "إضافة موظف جديد", true);
        dialog.setSize(480, 520);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().setBackground(CARD);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(CARD);
        form.setBorder(new EmptyBorder(24, 28, 16, 28));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 4, 6, 4);

        String[] fieldLabels = {"الاسم الكامل *", "القسم *", "المسمى الوظيفي *", "الراتب الأساسي *", "تاريخ التعيين *", "رقم الهاتف", "البريد الإلكتروني"};
        JTextField[] fields  = new JTextField[fieldLabels.length];

        for (int i = 0; i < fieldLabels.length; i++) {
            gbc.gridx = 0; gbc.gridy = i; gbc.weightx = 0.3;
            JLabel lbl = new JLabel(fieldLabels[i]);
            lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            lbl.setForeground(TEXT_SEC);
            form.add(lbl, gbc);

            gbc.gridx = 1; gbc.weightx = 0.7;
            fields[i] = new JTextField();
            fields[i].setFont(new Font("Segoe UI", Font.PLAIN, 13));
            fields[i].setPreferredSize(new Dimension(0, 34));
            fields[i].setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1, true),
                new EmptyBorder(4, 10, 4, 10)
            ));
            form.add(fields[i], gbc);
        }

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 12));
        buttons.setBackground(CARD);
        buttons.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, BORDER));

        JButton cancel = createSecondaryButton("إلغاء");
        cancel.addActionListener(e -> dialog.dispose());

        JButton save = createPrimaryButton("حفظ الموظف");
        save.addActionListener(e -> {
            // هنا يمكن إضافة منطق الحفظ
            JOptionPane.showMessageDialog(dialog, "تم إضافة الموظف بنجاح!", "نجاح", JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
        });

        buttons.add(cancel);
        buttons.add(save);

        dialog.add(form,    BorderLayout.CENTER);
        dialog.add(buttons, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    // ── Renderer للحالة
    private static class StatusRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable t, Object val, boolean sel, boolean foc, int r, int c) {
            JLabel lbl = new JLabel(val != null ? val.toString() : "");
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 11));
            lbl.setOpaque(true);
            lbl.setBorder(new EmptyBorder(4, 10, 4, 10));
            lbl.setHorizontalAlignment(CENTER);

            String s = val != null ? val.toString() : "";
            switch (s) {
                case "نشط":
                    lbl.setForeground(new Color(5, 150, 105));
                    lbl.setBackground(new Color(209, 250, 229));
                    break;
                case "إجازة":
                    lbl.setForeground(new Color(180, 83, 9));
                    lbl.setBackground(new Color(254, 243, 199));
                    break;
                case "موقوف":
                    lbl.setForeground(new Color(185, 28, 28));
                    lbl.setBackground(new Color(254, 226, 226));
                    break;
                default:
                    lbl.setForeground(new Color(100, 116, 139));
                    lbl.setBackground(new Color(241, 245, 249));
            }

            if (sel) lbl.setBackground(new Color(239, 246, 255));
            return lbl;
        }
    }

    // ── Renderer للإجراءات
    private static class ActionRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable t, Object val, boolean sel, boolean foc, int r, int c) {
            JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 6));
            p.setBackground(sel ? new Color(239, 246, 255) : Color.WHITE);

            JButton edit = new JButton("✏️");
            edit.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
            edit.setPreferredSize(new Dimension(30, 28));
            edit.setFocusPainted(false);
            edit.setBackground(new Color(239, 246, 255));
            edit.setBorder(BorderFactory.createLineBorder(new Color(147, 197, 253), 1, true));

            JButton del = new JButton("🗑");
            del.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
            del.setPreferredSize(new Dimension(30, 28));
            del.setFocusPainted(false);
            del.setBackground(new Color(254, 226, 226));
            del.setBorder(BorderFactory.createLineBorder(new Color(252, 165, 165), 1, true));

            p.add(edit);
            p.add(del);
            return p;
        }
    }

    // ── Editor للإجراءات
    private class ActionEditor extends AbstractCellEditor implements TableCellEditor {
        private JPanel panel;

        @Override
        public Component getTableCellEditorComponent(JTable t, Object val, boolean sel, int r, int c) {
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 6));
            panel.setBackground(new Color(239, 246, 255));

            JButton edit = new JButton("✏️");
            edit.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
            edit.setPreferredSize(new Dimension(30, 28));
            edit.setFocusPainted(false);
            edit.setBackground(new Color(239, 246, 255));
            edit.setBorder(BorderFactory.createLineBorder(new Color(147, 197, 253), 1, true));
            edit.addActionListener(e -> {
                stopCellEditing();
                JOptionPane.showMessageDialog(EmployeesPanel.this, "تعديل بيانات الموظف في الصف: " + (r + 1));
            });

            JButton del = new JButton("🗑");
            del.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
            del.setPreferredSize(new Dimension(30, 28));
            del.setFocusPainted(false);
            del.setBackground(new Color(254, 226, 226));
            del.setBorder(BorderFactory.createLineBorder(new Color(252, 165, 165), 1, true));
            del.addActionListener(e -> {
                stopCellEditing();
                int confirm = JOptionPane.showConfirmDialog(EmployeesPanel.this,
                    "هل أنت متأكد من حذف هذا الموظف؟", "تأكيد الحذف",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (confirm == JOptionPane.YES_OPTION)
                    tableModel.removeRow(r);
            });

            panel.add(edit);
            panel.add(del);
            return panel;
        }

        @Override public Object getCellEditorValue() { return ""; }
    }

    // ── أزرار مساعدة
    private JButton createPrimaryButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setForeground(Color.WHITE);
        btn.setBackground(ACCENT);
        btn.setBorder(new EmptyBorder(8, 18, 8, 18));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(new Color(37, 99, 235)); }
            public void mouseExited(MouseEvent e)  { btn.setBackground(ACCENT); }
        });
        return btn;
    }

    private JButton createSecondaryButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setForeground(TEXT_PRI);
        btn.setBackground(CARD);
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER, 1, true),
            new EmptyBorder(7, 16, 7, 16)
        ));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(new Color(248, 250, 252)); }
            public void mouseExited(MouseEvent e)  { btn.setBackground(CARD); }
        });
        return btn;
    }
}

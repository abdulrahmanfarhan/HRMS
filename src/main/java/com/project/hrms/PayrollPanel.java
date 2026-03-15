package com.project.hrms;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class PayrollPanel extends JPanel {

    private static final Color BG           = new Color(248, 250, 252);
    private static final Color CARD         = Color.WHITE;
    private static final Color ACCENT       = new Color(245, 158, 11);
    private static final Color ACCENT_LIGHT = new Color(254, 243, 199);
    private static final Color BLUE         = new Color(59, 130, 246);
    private static final Color GREEN        = new Color(16, 185, 129);
    private static final Color RED          = new Color(239, 68, 68);
    private static final Color TEXT_PRI     = new Color(15, 23, 42);
    private static final Color TEXT_SEC     = new Color(100, 116, 139);
    private static final Color BORDER_CLR   = new Color(226, 232, 240);

    private DefaultTableModel tableModel;

    // بيانات الرواتب الوهمية
    private final Object[][] payrollData = {
        {"EMP-001", "أحمد خالد محمود",   "تقنية المعلومات", "4,500", "500",  "200", "150", "5,050", "محسوب"},
        {"EMP-002", "سارة يوسف علي",     "الموارد البشرية", "3,800", "300",  "0",   "190", "3,910", "محسوب"},
        {"EMP-003", "محمد أحمد الزيد",   "المالية",         "5,200", "700",  "0",   "260", "5,640", "مدفوع"},
        {"EMP-004", "نور حسين إبراهيم",  "المبيعات",        "6,000", "1,200","0",   "360", "6,840", "مدفوع"},
        {"EMP-005", "ليلى عمر النجار",    "التسويق",         "3,500", "400",  "100", "175", "3,825", "محسوب"},
        {"EMP-006", "خالد سامي الحسن",   "تقنية المعلومات", "4,800", "0",    "0",   "240", "4,560", "معلق"},
        {"EMP-007", "ريم عبدالله فارس",  "المحاسبة",        "3,600", "300",  "150", "180", "3,870", "مدفوع"},
        {"EMP-008", "عمر صالح المنصور",  "الإدارة",         "3,200", "200",  "0",   "160", "3,240", "محسوب"},
        {"EMP-009", "دينا وليد شاهين",   "خدمة العملاء",    "2,900", "0",    "0",   "145", "2,755", "معلق"},
        {"EMP-010", "يوسف ناصر قاسم",    "تقنية المعلومات", "4,200", "0",    "0",   "210", "3,990", "محسوب"},
    };

    public PayrollPanel() {
        setLayout(new BorderLayout());
        setBackground(BG);
        setBorder(new EmptyBorder(24, 24, 24, 24));

        add(buildHeader(),  BorderLayout.NORTH);
        add(buildContent(), BorderLayout.CENTER);
    }

    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BG);
        header.setBorder(new EmptyBorder(0, 0, 20, 0));

        JPanel titlePane = new JPanel();
        titlePane.setLayout(new BoxLayout(titlePane, BoxLayout.Y_AXIS));
        titlePane.setBackground(BG);

        JLabel title = new JLabel("إدارة الرواتب");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(TEXT_PRI);

        JLabel subtitle = new JLabel("معالجة وصرف رواتب الموظفين — مارس 2026");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setForeground(TEXT_SEC);

        titlePane.add(title);
        titlePane.add(Box.createVerticalStrut(4));
        titlePane.add(subtitle);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        btnPanel.setBackground(BG);

        JButton processBtn = createColorButton("⚙️ معالجة الرواتب", ACCENT);
        JButton payBtn     = createColorButton("💳 صرف الرواتب",    GREEN);
        JButton exportBtn  = createSecondaryButton("📤 تصدير");

        processBtn.addActionListener(e ->
            JOptionPane.showMessageDialog(this, "جاري معالجة الرواتب...\nتمت المعالجة بنجاح!", "معالجة الرواتب", JOptionPane.INFORMATION_MESSAGE));
        payBtn.addActionListener(e -> showPaymentDialog());

        btnPanel.add(exportBtn);
        btnPanel.add(processBtn);
        btnPanel.add(payBtn);

        header.add(titlePane, BorderLayout.WEST);
        header.add(btnPanel,  BorderLayout.EAST);
        return header;
    }

    private JPanel buildContent() {
        JPanel content = new JPanel(new BorderLayout(0, 16));
        content.setBackground(BG);

        content.add(buildSummaryRow(), BorderLayout.NORTH);

        JPanel middle = new JPanel(new BorderLayout(16, 0));
        middle.setBackground(BG);
        middle.add(buildTableCard(),   BorderLayout.CENTER);
        middle.add(buildSidePanel(),   BorderLayout.EAST);

        content.add(middle, BorderLayout.CENTER);
        return content;
    }

    // ── صف ملخص الرواتب
    private JPanel buildSummaryRow() {
        JPanel row = new JPanel(new GridLayout(1, 4, 14, 0));
        row.setBackground(BG);

        row.add(buildSummaryCard("💰 إجمالي الرواتب",   "45,680 ₪", "هذا الشهر",        ACCENT));
        row.add(buildSummaryCard("✅ مدفوع",             "18,390 ₪", "3 موظفين",          GREEN));
        row.add(buildSummaryCard("🔄 قيد المعالجة",     "22,815 ₪", "5 موظفين",          BLUE));
        row.add(buildSummaryCard("⚠️ معلق",             "7,315 ₪",  "2 موظفين",          RED));

        return row;
    }

    private JPanel buildSummaryCard(String label, String value, String sub, Color accent) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_CLR, 1, true),
            new EmptyBorder(16, 18, 16, 18)
        ));

        JPanel colorBar = new JPanel();
        colorBar.setBackground(accent);
        colorBar.setPreferredSize(new Dimension(0, 3));
        card.add(colorBar, BorderLayout.NORTH);

        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBackground(CARD);
        body.setBorder(new EmptyBorder(10, 0, 0, 0));

        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl.setForeground(TEXT_SEC);

        JLabel val = new JLabel(value);
        val.setFont(new Font("Segoe UI", Font.BOLD, 20));
        val.setForeground(accent);

        JLabel subLbl = new JLabel(sub);
        subLbl.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        subLbl.setForeground(TEXT_SEC);

        body.add(lbl);
        body.add(Box.createVerticalStrut(6));
        body.add(val);
        body.add(Box.createVerticalStrut(3));
        body.add(subLbl);

        card.add(body, BorderLayout.CENTER);
        return card;
    }

    // ── بطاقة الجدول
    private JPanel buildTableCard() {
        JPanel card = new JPanel(new BorderLayout(0, 12));
        card.setBackground(CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_CLR, 1, true),
            new EmptyBorder(16, 18, 16, 18)
        ));

        // شريط أدوات
        JPanel toolbar = new JPanel(new BorderLayout());
        toolbar.setBackground(CARD);
        toolbar.setBorder(new EmptyBorder(0, 0, 10, 0));

        JLabel tableTitle = new JLabel("كشف الرواتب الشهري");
        tableTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tableTitle.setForeground(TEXT_PRI);

        JPanel filters = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        filters.setBackground(CARD);

        JTextField search = new JTextField();
        search.setPreferredSize(new Dimension(200, 34));
        search.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        search.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_CLR, 1, true),
            new EmptyBorder(4, 10, 4, 10)
        ));
        search.putClientProperty("JTextField.placeholderText", "🔍  بحث...");

        String[] months = {"مارس 2026", "فبراير 2026", "يناير 2026"};
        JComboBox<String> monthFilter = new JComboBox<>(months);
        monthFilter.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        monthFilter.setPreferredSize(new Dimension(140, 34));

        filters.add(search);
        filters.add(monthFilter);

        toolbar.add(tableTitle, BorderLayout.WEST);
        toolbar.add(filters,    BorderLayout.EAST);
        card.add(toolbar, BorderLayout.NORTH);

        // الجدول
        String[] cols = {"الرقم", "الاسم", "القسم", "الراتب الأساسي", "البدلات", "الإضافي", "الاستقطاعات", "الصافي", "الحالة"};
        tableModel = new DefaultTableModel(payrollData, cols) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        JTable table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setForeground(TEXT_PRI);
        table.setBackground(CARD);
        table.setRowHeight(42);
        table.setShowHorizontalLines(true);
        table.setGridColor(new Color(241, 245, 249));
        table.setSelectionBackground(ACCENT_LIGHT);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        header.setForeground(TEXT_SEC);
        header.setBackground(new Color(248, 250, 252));
        header.setPreferredSize(new Dimension(0, 40));

        int[] widths = {80, 150, 120, 110, 80, 80, 110, 100, 90};
        for (int i = 0; i < widths.length; i++)
            table.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);

        table.getColumnModel().getColumn(8).setCellRenderer(new PayrollStatusRenderer());

        // تلوين صف المجموع
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val, boolean sel, boolean foc, int r, int c) {
                Component comp = super.getTableCellRendererComponent(t, val, sel, foc, r, c);
                if (!sel) comp.setBackground(r % 2 == 0 ? CARD : new Color(250, 252, 254));
                return comp;
            }
        });
        table.getColumnModel().getColumn(8).setCellRenderer(new PayrollStatusRenderer());

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(CARD);
        scroll.setPreferredSize(new Dimension(0, 340));
        card.add(scroll, BorderLayout.CENTER);

        // صف الإجمالي
        JPanel totals = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 8));
        totals.setBackground(new Color(248, 250, 252));
        totals.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, BORDER_CLR));

        addTotalItem(totals, "إجمالي الأساسي:", "41,700 ₪", TEXT_SEC);
        addTotalItem(totals, "إجمالي البدلات:", "3,400 ₪",  TEXT_SEC);
        addTotalItem(totals, "إجمالي الاستقطاعات:", "1,870 ₪", RED);
        addTotalItem(totals, "الصافي الإجمالي:", "45,680 ₪",  ACCENT);

        card.add(totals, BorderLayout.SOUTH);
        return card;
    }

    private void addTotalItem(JPanel parent, String label, String value, Color color) {
        JPanel item = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        item.setBackground(new Color(248, 250, 252));
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl.setForeground(TEXT_SEC);
        JLabel val = new JLabel(value);
        val.setFont(new Font("Segoe UI", Font.BOLD, 13));
        val.setForeground(color);
        item.add(lbl); item.add(val);
        parent.add(item);
    }

    // ── لوحة جانبية: توزيع الرواتب
    private JPanel buildSidePanel() {
        JPanel side = new JPanel();
        side.setLayout(new BoxLayout(side, BoxLayout.Y_AXIS));
        side.setBackground(BG);
        side.setPreferredSize(new Dimension(220, 0));

        // بطاقة توزيع الأقسام
        JPanel distCard = new JPanel(new BorderLayout());
        distCard.setBackground(CARD);
        distCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_CLR, 1, true),
            new EmptyBorder(16, 16, 16, 16)
        ));

        JLabel distTitle = new JLabel("توزيع حسب القسم");
        distTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        distTitle.setForeground(TEXT_PRI);
        distTitle.setBorder(new EmptyBorder(0, 0, 12, 0));

        JPanel bars = new JPanel();
        bars.setLayout(new BoxLayout(bars, BoxLayout.Y_AXIS));
        bars.setBackground(CARD);

        String[][] depts = {
            {"تقنية المعلومات", "14,490", "32"},
            {"المبيعات",         "6,840",  "15"},
            {"المالية",          "5,640",  "12"},
            {"الموارد البشرية",  "3,910",  "9"},
            {"أخرى",             "14,800", "32"},
        };

        Color[] colors = {BLUE, ACCENT, GREEN, new Color(139, 92, 246), RED};

        for (int i = 0; i < depts.length; i++) {
            bars.add(buildDeptBar(depts[i][0], depts[i][1] + " ₪",
                Integer.parseInt(depts[i][2]), colors[i % colors.length]));
            bars.add(Box.createVerticalStrut(10));
        }

        distCard.add(distTitle, BorderLayout.NORTH);
        distCard.add(bars,      BorderLayout.CENTER);

        // بطاقة الإجراءات السريعة
        JPanel actCard = new JPanel(new BorderLayout());
        actCard.setBackground(CARD);
        actCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_CLR, 1, true),
            new EmptyBorder(16, 16, 16, 16)
        ));

        JLabel actTitle = new JLabel("إجراءات سريعة");
        actTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        actTitle.setForeground(TEXT_PRI);

        JPanel actBtns = new JPanel();
        actBtns.setLayout(new BoxLayout(actBtns, BoxLayout.Y_AXIS));
        actBtns.setBackground(CARD);
        actBtns.setBorder(new EmptyBorder(10, 0, 0, 0));

        String[] actions = {"📋 كشف راتب فردي", "📊 تقرير الشهر", "⚙️ إعدادات الرواتب"};
        for (String action : actions) {
            JButton btn = new JButton(action);
            btn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            btn.setForeground(TEXT_PRI);
            btn.setBackground(CARD);
            btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_CLR, 1, true),
                new EmptyBorder(8, 12, 8, 12)
            ));
            btn.setFocusPainted(false);
            btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            btn.setAlignmentX(LEFT_ALIGNMENT);
            btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
            actBtns.add(btn);
            actBtns.add(Box.createVerticalStrut(6));
        }

        actCard.add(actTitle, BorderLayout.NORTH);
        actCard.add(actBtns,  BorderLayout.CENTER);

        side.add(distCard);
        side.add(Box.createVerticalStrut(14));
        side.add(actCard);
        return side;
    }

    private JPanel buildDeptBar(String name, String amount, int pct, Color color) {
        JPanel item = new JPanel(new BorderLayout(0, 4));
        item.setBackground(CARD);

        JPanel topRow = new JPanel(new BorderLayout());
        topRow.setBackground(CARD);
        JLabel nameLbl = new JLabel(name);
        nameLbl.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        nameLbl.setForeground(TEXT_SEC);
        JLabel amtLbl = new JLabel(pct + "%");
        amtLbl.setFont(new Font("Segoe UI", Font.BOLD, 11));
        amtLbl.setForeground(color);
        topRow.add(nameLbl, BorderLayout.WEST);
        topRow.add(amtLbl,  BorderLayout.EAST);

        JPanel barBg = new JPanel(new BorderLayout());
        barBg.setBackground(new Color(229, 231, 235));
        barBg.setPreferredSize(new Dimension(0, 6));
        JPanel barFill = new JPanel();
        barFill.setBackground(color);
        barFill.setPreferredSize(new Dimension(pct * 2, 6));
        barBg.add(barFill, BorderLayout.WEST);

        item.add(topRow, BorderLayout.NORTH);
        item.add(barBg,  BorderLayout.CENTER);
        return item;
    }

    // ── نافذة صرف الرواتب
    private void showPaymentDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "صرف الرواتب", true);
        dialog.setSize(420, 300);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(CARD);
        dialog.setLayout(new BorderLayout());

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(CARD);
        content.setBorder(new EmptyBorder(24, 28, 16, 28));

        JLabel title = new JLabel("تأكيد صرف الرواتب");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setForeground(TEXT_PRI);
        title.setAlignmentX(CENTER_ALIGNMENT);

        JLabel info = new JLabel("سيتم صرف رواتب الموظفين المحسوبين");
        info.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        info.setForeground(TEXT_SEC);
        info.setAlignmentX(CENTER_ALIGNMENT);

        JLabel amount = new JLabel("الإجمالي: 27,290 ₪");
        amount.setFont(new Font("Segoe UI", Font.BOLD, 20));
        amount.setForeground(GREEN);
        amount.setAlignmentX(CENTER_ALIGNMENT);

        content.add(title);
        content.add(Box.createVerticalStrut(12));
        content.add(info);
        content.add(Box.createVerticalStrut(16));
        content.add(amount);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 12));
        buttons.setBackground(CARD);
        buttons.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, BORDER_CLR));

        JButton cancel = createSecondaryButton("إلغاء");
        cancel.addActionListener(e -> dialog.dispose());

        JButton confirm = createColorButton("✅ تأكيد الصرف", GREEN);
        confirm.addActionListener(e -> {
            JOptionPane.showMessageDialog(dialog, "تم صرف الرواتب بنجاح!", "تم", JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
        });

        buttons.add(cancel);
        buttons.add(confirm);

        dialog.add(content, BorderLayout.CENTER);
        dialog.add(buttons, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    // ── Renderer الحالة
    private static class PayrollStatusRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable t, Object val, boolean sel, boolean foc, int r, int c) {
            JLabel lbl = new JLabel(val != null ? val.toString() : "");
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 11));
            lbl.setOpaque(true);
            lbl.setHorizontalAlignment(CENTER);
            lbl.setBorder(new EmptyBorder(4, 8, 4, 8));
            switch (val != null ? val.toString() : "") {
                case "مدفوع":
                    lbl.setForeground(new Color(5, 150, 105));
                    lbl.setBackground(new Color(209, 250, 229));
                    break;
                case "محسوب":
                    lbl.setForeground(new Color(29, 78, 216));
                    lbl.setBackground(new Color(219, 234, 254));
                    break;
                case "معلق":
                    lbl.setForeground(new Color(180, 83, 9));
                    lbl.setBackground(new Color(254, 243, 199));
                    break;
                default:
                    lbl.setForeground(new Color(100, 116, 139));
                    lbl.setBackground(new Color(241, 245, 249));
            }
            if (sel) lbl.setBackground(new Color(254, 243, 199));
            return lbl;
        }
    }

    private JButton createColorButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setBorder(new EmptyBorder(8, 18, 8, 18));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JButton createSecondaryButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setForeground(TEXT_PRI);
        btn.setBackground(CARD);
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_CLR, 1, true),
            new EmptyBorder(7, 16, 7, 16)
        ));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }
}

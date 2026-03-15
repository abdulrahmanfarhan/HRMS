package com.project.hrms;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AttendancePanel extends JPanel {

    private static final Color BG           = new Color(248, 250, 252);
    private static final Color CARD         = Color.WHITE;
    private static final Color ACCENT       = new Color(16, 185, 129);
    private static final Color ACCENT_LIGHT = new Color(209, 250, 229);
    private static final Color BLUE         = new Color(59, 130, 246);
    private static final Color ORANGE       = new Color(245, 158, 11);
    private static final Color RED          = new Color(239, 68, 68);
    private static final Color TEXT_PRI     = new Color(15, 23, 42);
    private static final Color TEXT_SEC     = new Color(100, 116, 139);
    private static final Color BORDER_CLR   = new Color(226, 232, 240);

    private DefaultTableModel tableModel;
    private String selectedDate;

    private final Object[][] attendanceData = {
        {"EMP-001", "أحمد خالد محمود",   "08:02", "17:05", "8.05", "حاضر",   "—"},
        {"EMP-002", "سارة يوسف علي",     "08:15", "17:00", "7.75", "حاضر",   "—"},
        {"EMP-003", "محمد أحمد الزيد",   "—",     "—",     "0",    "غائب",   "بدون إذن"},
        {"EMP-004", "نور حسين إبراهيم",  "09:30", "17:00", "7.5",  "متأخر",  "30 دقيقة"},
        {"EMP-005", "ليلى عمر النجار",    "08:00", "16:00", "8.0",  "حاضر",   "—"},
        {"EMP-006", "خالد سامي الحسن",   "—",     "—",     "0",    "إجازة",  "إجازة سنوية"},
        {"EMP-007", "ريم عبدالله فارس",  "07:55", "17:10", "8.25", "حاضر",   "—"},
        {"EMP-008", "عمر صالح المنصور",  "08:00", "17:00", "9.0",  "حاضر",   "عمل إضافي"},
        {"EMP-009", "دينا وليد شاهين",   "10:00", "17:00", "7.0",  "متأخر",  "ساعة ونصف"},
        {"EMP-010", "يوسف ناصر قاسم",    "—",     "—",     "0",    "غائب",   "مرض"},
    };

    public AttendancePanel() {
        setLayout(new BorderLayout());
        setBackground(BG);
        setBorder(new EmptyBorder(24, 24, 24, 24));
        selectedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

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

        JLabel title = new JLabel("الحضور والغياب");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(TEXT_PRI);

        JLabel subtitle = new JLabel("متابعة حضور وغياب الموظفين يومياً");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setForeground(TEXT_SEC);

        titlePane.add(title);
        titlePane.add(Box.createVerticalStrut(4));
        titlePane.add(subtitle);

        // أزرار التسجيل
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        btnPanel.setBackground(BG);

        JButton checkInBtn = createColorButton("📥 تسجيل حضور", ACCENT);
        JButton checkOutBtn = createColorButton("📤 تسجيل انصراف", BLUE);
        JButton exportBtn = createSecondaryButton("📊 تصدير التقرير");

        checkInBtn.addActionListener(e ->
            JOptionPane.showMessageDialog(this, "تم تسجيل الحضور بنجاح!", "تسجيل حضور", JOptionPane.INFORMATION_MESSAGE));
        checkOutBtn.addActionListener(e ->
            JOptionPane.showMessageDialog(this, "تم تسجيل الانصراف بنجاح!", "تسجيل انصراف", JOptionPane.INFORMATION_MESSAGE));

        btnPanel.add(exportBtn);
        btnPanel.add(checkOutBtn);
        btnPanel.add(checkInBtn);

        header.add(titlePane, BorderLayout.WEST);
        header.add(btnPanel,  BorderLayout.EAST);
        return header;
    }

    private JPanel buildContent() {
        JPanel content = new JPanel(new BorderLayout(0, 16));
        content.setBackground(BG);

        content.add(buildTopRow(),    BorderLayout.NORTH);
        content.add(buildTableCard(), BorderLayout.CENTER);

        return content;
    }

    // ── صف العلوي: إحصاءات اليوم + فلتر التاريخ
    private JPanel buildTopRow() {
        JPanel row = new JPanel(new BorderLayout(16, 0));
        row.setBackground(BG);

        // إحصاءات
        JPanel stats = new JPanel(new GridLayout(1, 4, 12, 0));
        stats.setBackground(BG);

        long total   = attendanceData.length;
        long present = countStatus("حاضر");
        long late    = countStatus("متأخر");
        long absent  = countStatus("غائب") + countStatus("إجازة");

        stats.add(buildStatCard("✅ حاضرون",     String.valueOf(present), ACCENT));
        stats.add(buildStatCard("⏰ متأخرون",    String.valueOf(late),    ORANGE));
        stats.add(buildStatCard("❌ غائبون",     String.valueOf(absent),  RED));
        stats.add(buildStatCard("📊 نسبة الحضور",
            String.format("%.0f%%", (present * 100.0 / total)), BLUE));

        // منتقي التاريخ
        JPanel datePicker = new JPanel(new BorderLayout());
        datePicker.setBackground(CARD);
        datePicker.setPreferredSize(new Dimension(220, 0));
        datePicker.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_CLR, 1, true),
            new EmptyBorder(14, 16, 14, 16)
        ));

        JLabel dateTitle = new JLabel("تاريخ اليوم");
        dateTitle.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        dateTitle.setForeground(TEXT_SEC);

        JLabel dateVal = new JLabel(selectedDate);
        dateVal.setFont(new Font("Segoe UI", Font.BOLD, 18));
        dateVal.setForeground(TEXT_PRI);

        JLabel dayName = new JLabel("الأحد");
        dayName.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        dayName.setForeground(ACCENT);

        JPanel dateInfo = new JPanel();
        dateInfo.setLayout(new BoxLayout(dateInfo, BoxLayout.Y_AXIS));
        dateInfo.setBackground(CARD);
        dateInfo.add(dateTitle);
        dateInfo.add(Box.createVerticalStrut(4));
        dateInfo.add(dateVal);
        dateInfo.add(Box.createVerticalStrut(2));
        dateInfo.add(dayName);

        datePicker.add(dateInfo, BorderLayout.CENTER);

        row.add(stats,     BorderLayout.CENTER);
        row.add(datePicker, BorderLayout.EAST);
        return row;
    }

    private long countStatus(String s) {
        long c = 0;
        for (Object[] r : attendanceData) if (s.equals(r[5])) c++;
        return c;
    }

    private JPanel buildStatCard(String label, String value, Color accent) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_CLR, 1, true),
            new EmptyBorder(14, 16, 14, 16)
        ));
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl.setForeground(TEXT_SEC);

        JLabel val = new JLabel(value);
        val.setFont(new Font("Segoe UI", Font.BOLD, 24));
        val.setForeground(accent);

        card.add(lbl, BorderLayout.NORTH);
        card.add(val, BorderLayout.CENTER);
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

        // شريط فوق الجدول
        JPanel toolbar = new JPanel(new BorderLayout());
        toolbar.setBackground(CARD);
        toolbar.setBorder(new EmptyBorder(0, 0, 10, 0));

        JLabel title = new JLabel("سجل الحضور — " + selectedDate);
        title.setFont(new Font("Segoe UI", Font.BOLD, 14));
        title.setForeground(TEXT_PRI);

        JTextField search = new JTextField();
        search.setPreferredSize(new Dimension(220, 34));
        search.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        search.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_CLR, 1, true),
            new EmptyBorder(4, 10, 4, 10)
        ));
        search.putClientProperty("JTextField.placeholderText", "🔍  بحث...");

        String[] statuses = {"كل الحالات", "حاضر", "متأخر", "غائب", "إجازة"};
        JComboBox<String> statusFilter = new JComboBox<>(statuses);
        statusFilter.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        statusFilter.setPreferredSize(new Dimension(130, 34));

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        right.setBackground(CARD);
        right.add(search);
        right.add(statusFilter);

        toolbar.add(title, BorderLayout.WEST);
        toolbar.add(right, BorderLayout.EAST);
        card.add(toolbar, BorderLayout.NORTH);

        // الجدول
        String[] cols = {"الرقم الوظيفي", "اسم الموظف", "وقت الحضور", "وقت الانصراف", "ساعات العمل", "الحالة", "ملاحظات"};
        tableModel = new DefaultTableModel(attendanceData, cols) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        JTable table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setForeground(TEXT_PRI);
        table.setBackground(CARD);
        table.setRowHeight(42);
        table.setShowHorizontalLines(true);
        table.setGridColor(new Color(241, 245, 249));
        table.setSelectionBackground(new Color(209, 250, 229));
        table.setSelectionForeground(TEXT_PRI);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        header.setForeground(TEXT_SEC);
        header.setBackground(new Color(248, 250, 252));
        header.setPreferredSize(new Dimension(0, 40));

        int[] widths = {100, 160, 100, 110, 100, 90, 130};
        for (int i = 0; i < widths.length; i++)
            table.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);

        table.getColumnModel().getColumn(5).setCellRenderer(new AttendanceStatusRenderer());

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(CARD);
        card.add(scroll, BorderLayout.CENTER);

        // تذييل
        JPanel footer = buildWeeklyChart();
        card.add(footer, BorderLayout.SOUTH);

        return card;
    }

    // ── مخطط أسبوعي بسيط
    private JPanel buildWeeklyChart() {
        JPanel section = new JPanel(new BorderLayout());
        section.setBackground(CARD);
        section.setBorder(new EmptyBorder(16, 0, 0, 0));

        JLabel title = new JLabel("ملخص الأسبوع الحالي");
        title.setFont(new Font("Segoe UI", Font.BOLD, 13));
        title.setForeground(TEXT_PRI);
        title.setBorder(new EmptyBorder(0, 0, 10, 0));

        String[] days = {"الأحد", "الإثنين", "الثلاثاء", "الأربعاء", "الخميس"};
        int[]  present = {21, 19, 22, 20, 21};
        int    total   = 24;

        JPanel bars = new JPanel(new GridLayout(1, 5, 12, 0));
        bars.setBackground(CARD);

        for (int i = 0; i < days.length; i++) {
            JPanel col = new JPanel();
            col.setLayout(new BoxLayout(col, BoxLayout.Y_AXIS));
            col.setBackground(CARD);

            int pct = (present[i] * 100) / total;

            JLabel pctLbl = new JLabel(pct + "%");
            pctLbl.setFont(new Font("Segoe UI", Font.BOLD, 11));
            pctLbl.setForeground(ACCENT);
            pctLbl.setAlignmentX(CENTER_ALIGNMENT);

            JPanel barWrap = new JPanel(new BorderLayout());
            barWrap.setBackground(new Color(229, 231, 235));
            barWrap.setPreferredSize(new Dimension(30, 60));
            barWrap.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

            JPanel fill = new JPanel();
            fill.setBackground(i == 0 ? ACCENT : new Color(52, 211, 153));
            fill.setPreferredSize(new Dimension(0, (pct * 60) / 100));
            barWrap.add(fill, BorderLayout.SOUTH);

            JLabel dayLbl = new JLabel(days[i]);
            dayLbl.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            dayLbl.setForeground(TEXT_SEC);
            dayLbl.setAlignmentX(CENTER_ALIGNMENT);

            col.add(pctLbl);
            col.add(Box.createVerticalStrut(4));
            col.add(barWrap);
            col.add(Box.createVerticalStrut(4));
            col.add(dayLbl);
            bars.add(col);
        }

        section.add(title, BorderLayout.NORTH);
        section.add(bars,  BorderLayout.CENTER);
        return section;
    }

    // ── Renderer للحالة
    private static class AttendanceStatusRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable t, Object val, boolean sel, boolean foc, int r, int c) {
            JLabel lbl = new JLabel(val != null ? val.toString() : "");
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 11));
            lbl.setOpaque(true);
            lbl.setHorizontalAlignment(CENTER);
            lbl.setBorder(new EmptyBorder(4, 8, 4, 8));

            switch (val != null ? val.toString() : "") {
                case "حاضر":
                    lbl.setForeground(new Color(5, 150, 105));
                    lbl.setBackground(new Color(209, 250, 229));
                    break;
                case "متأخر":
                    lbl.setForeground(new Color(180, 83, 9));
                    lbl.setBackground(new Color(254, 243, 199));
                    break;
                case "غائب":
                    lbl.setForeground(new Color(185, 28, 28));
                    lbl.setBackground(new Color(254, 226, 226));
                    break;
                case "إجازة":
                    lbl.setForeground(new Color(29, 78, 216));
                    lbl.setBackground(new Color(219, 234, 254));
                    break;
                default:
                    lbl.setForeground(TEXT_SEC);
                    lbl.setBackground(new Color(241, 245, 249));
            }
            if (sel) lbl.setBackground(new Color(209, 250, 229));
            return lbl;
        }

        private static final Color TEXT_SEC = new Color(100, 116, 139);
    }

    // ── أزرار مساعدة
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

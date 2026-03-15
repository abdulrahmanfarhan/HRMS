package com.project.hrms;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class ReportsPanel extends JPanel {

    private static final Color BG           = new Color(248, 250, 252);
    private static final Color CARD         = Color.WHITE;
    private static final Color ACCENT       = new Color(139, 92, 246);
    private static final Color ACCENT_LIGHT = new Color(237, 233, 254);
    private static final Color BLUE         = new Color(59, 130, 246);
    private static final Color GREEN        = new Color(16, 185, 129);
    private static final Color ORANGE       = new Color(245, 158, 11);
    private static final Color RED          = new Color(239, 68, 68);
    private static final Color TEXT_PRI     = new Color(15, 23, 42);
    private static final Color TEXT_SEC     = new Color(100, 116, 139);
    private static final Color BORDER_CLR   = new Color(226, 232, 240);

    public ReportsPanel() {
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

        JLabel title = new JLabel("التقارير والإحصاءات");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(TEXT_PRI);

        JLabel subtitle = new JLabel("نظرة شاملة على أداء الموارد البشرية — 2026");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setForeground(TEXT_SEC);

        titlePane.add(title);
        titlePane.add(Box.createVerticalStrut(4));
        titlePane.add(subtitle);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        btnPanel.setBackground(BG);

        String[] periods = {"هذا الشهر", "الربع الأول", "هذا العام"};
        JComboBox<String> periodFilter = new JComboBox<>(periods);
        periodFilter.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        periodFilter.setPreferredSize(new Dimension(140, 36));

        JButton exportBtn = createColorButton("📤 تصدير التقرير", ACCENT);
        exportBtn.addActionListener(e ->
            JOptionPane.showMessageDialog(this, "جاري تصدير التقرير...\nتم التصدير بنجاح!", "تصدير", JOptionPane.INFORMATION_MESSAGE));

        btnPanel.add(periodFilter);
        btnPanel.add(exportBtn);

        header.add(titlePane, BorderLayout.WEST);
        header.add(btnPanel,  BorderLayout.EAST);
        return header;
    }

    private JPanel buildContent() {
        JScrollPane scroll = new JScrollPane(buildScrollContent());
        scroll.setBorder(null);
        scroll.setBackground(BG);
        scroll.getViewport().setBackground(BG);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(BG);
        wrapper.add(scroll, BorderLayout.CENTER);
        return wrapper;
    }

    private JPanel buildScrollContent() {
        JPanel page = new JPanel();
        page.setLayout(new BoxLayout(page, BoxLayout.Y_AXIS));
        page.setBackground(BG);

        // ── صف KPIs
        page.add(buildKPIRow());
        page.add(Box.createVerticalStrut(16));

        // ── صف المخططات (حضور + رواتب)
        JPanel chartsRow = new JPanel(new GridLayout(1, 2, 16, 0));
        chartsRow.setBackground(BG);
        chartsRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 280));
        chartsRow.add(buildAttendanceChart());
        chartsRow.add(buildSalaryChart());
        page.add(chartsRow);
        page.add(Box.createVerticalStrut(16));

        // ── صف: مخطط دائري + جدول التقارير
        JPanel bottomRow = new JPanel(new GridLayout(1, 2, 16, 0));
        bottomRow.setBackground(BG);
        bottomRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 320));
        bottomRow.add(buildDeptPieCard());
        bottomRow.add(buildReportsListCard());
        page.add(bottomRow);
        page.add(Box.createVerticalStrut(16));

        // ── جدول أعلى الموظفين أداءً
        page.add(buildTopEmployeesTable());

        return page;
    }

    // ── صف مؤشرات KPI
    private JPanel buildKPIRow() {
        JPanel row = new JPanel(new GridLayout(1, 4, 14, 0));
        row.setBackground(BG);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        row.add(buildKPICard("📈 معدل الحضور",     "91.2%",  "+2.1%", GREEN,  true));
        row.add(buildKPICard("👥 الموظفون النشطون", "215",    "+12",   BLUE,   true));
        row.add(buildKPICard("💰 متوسط الراتب",     "4,568 ₪","+3.5%", ORANGE, true));
        row.add(buildKPICard("🔄 معدل الدوران",     "4.2%",   "-1.1%", ACCENT, false));

        return row;
    }

    private JPanel buildKPICard(String label, String value, String change, Color accent, boolean positive) {
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
        val.setFont(new Font("Segoe UI", Font.BOLD, 22));
        val.setForeground(accent);

        JLabel chg = new JLabel((positive ? "▲ " : "▼ ") + change + " عن الشهر الماضي");
        chg.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        chg.setForeground(positive ? GREEN : RED);

        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBackground(CARD);
        body.add(lbl);
        body.add(Box.createVerticalStrut(6));
        body.add(val);
        body.add(Box.createVerticalStrut(4));
        body.add(chg);

        card.add(body, BorderLayout.CENTER);
        return card;
    }

    // ── مخطط الحضور الشهري (رسم يدوي بـ Graphics2D)
    private JPanel buildAttendanceChart() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_CLR, 1, true),
            new EmptyBorder(16, 18, 16, 18)
        ));

        JLabel title = new JLabel("معدل الحضور — الأشهر الستة الماضية");
        title.setFont(new Font("Segoe UI", Font.BOLD, 13));
        title.setForeground(TEXT_PRI);
        title.setBorder(new EmptyBorder(0, 0, 10, 0));

        // مخطط خطي مرسوم يدوياً
        int[] attendanceValues = {88, 91, 87, 93, 90, 91};
        String[] months = {"أكتوبر", "نوفمبر", "ديسمبر", "يناير", "فبراير", "مارس"};

        JPanel chart = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int w = getWidth(), h = getHeight();
                int padL = 40, padR = 20, padT = 20, padB = 40;
                int chartW = w - padL - padR;
                int chartH = h - padT - padB;

                // خلفية
                g2.setColor(CARD);
                g2.fillRect(0, 0, w, h);

                // خطوط أفقية
                g2.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{3}, 0));
                int steps = 5;
                for (int i = 0; i <= steps; i++) {
                    int y = padT + (chartH * i / steps);
                    g2.setColor(new Color(226, 232, 240));
                    g2.drawLine(padL, y, padL + chartW, y);
                    g2.setColor(TEXT_SEC);
                    g2.setFont(new Font("Segoe UI", Font.PLAIN, 10));
                    int val = 100 - (i * 20);
                    g2.drawString(val + "%", 2, y + 4);
                }

                // رسم المنطقة المملوءة
                int n = attendanceValues.length;
                int[] xPoints = new int[n + 2];
                int[] yPoints = new int[n + 2];
                for (int i = 0; i < n; i++) {
                    xPoints[i] = padL + (i * chartW / (n - 1));
                    yPoints[i] = padT + chartH - ((attendanceValues[i] - 80) * chartH / 20);
                }
                xPoints[n]   = padL + chartW;
                yPoints[n]   = padT + chartH;
                xPoints[n+1] = padL;
                yPoints[n+1] = padT + chartH;

                GradientPaint gradient = new GradientPaint(0, padT, new Color(139, 92, 246, 60), 0, padT + chartH, new Color(139, 92, 246, 5));
                g2.setPaint(gradient);
                g2.setStroke(new BasicStroke(1));
                g2.fillPolygon(xPoints, yPoints, n + 2);

                // رسم الخط
                g2.setColor(ACCENT);
                g2.setStroke(new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                for (int i = 0; i < n - 1; i++) {
                    g2.drawLine(xPoints[i], yPoints[i], xPoints[i+1], yPoints[i+1]);
                }

                // نقاط البيانات
                for (int i = 0; i < n; i++) {
                    g2.setColor(CARD);
                    g2.fillOval(xPoints[i] - 5, yPoints[i] - 5, 10, 10);
                    g2.setColor(ACCENT);
                    g2.setStroke(new BasicStroke(2));
                    g2.drawOval(xPoints[i] - 5, yPoints[i] - 5, 10, 10);

                    // قيمة فوق النقطة
                    g2.setFont(new Font("Segoe UI", Font.BOLD, 10));
                    g2.setColor(TEXT_PRI);
                    g2.drawString(attendanceValues[i] + "%", xPoints[i] - 12, yPoints[i] - 10);

                    // اسم الشهر
                    g2.setColor(TEXT_SEC);
                    g2.setFont(new Font("Segoe UI", Font.PLAIN, 10));
                    g2.drawString(months[i], xPoints[i] - 18, padT + chartH + 18);
                }

                g2.dispose();
            }
        };
        chart.setBackground(CARD);
        chart.setPreferredSize(new Dimension(0, 200));

        card.add(title, BorderLayout.NORTH);
        card.add(chart, BorderLayout.CENTER);
        return card;
    }

    // ── مخطط الرواتب الشهري (عمودي)
    private JPanel buildSalaryChart() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_CLR, 1, true),
            new EmptyBorder(16, 18, 16, 18)
        ));

        JLabel title = new JLabel("مصروف الرواتب — الأشهر الستة الماضية");
        title.setFont(new Font("Segoe UI", Font.BOLD, 13));
        title.setForeground(TEXT_PRI);
        title.setBorder(new EmptyBorder(0, 0, 10, 0));

        int[] salaries = {42000, 43500, 41800, 44200, 45100, 45680};
        String[] months = {"أكتوبر", "نوفمبر", "ديسمبر", "يناير", "فبراير", "مارس"};

        JPanel chart = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int w = getWidth(), h = getHeight();
                int padL = 50, padR = 10, padT = 20, padB = 40;
                int chartW = w - padL - padR;
                int chartH = h - padT - padB;

                g2.setColor(CARD);
                g2.fillRect(0, 0, w, h);

                int maxVal = 50000, minVal = 38000;
                int n = salaries.length;
                int barW = chartW / (n * 2);

                // خطوط أفقية
                g2.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{3}, 0));
                for (int i = 0; i <= 4; i++) {
                    int y = padT + (chartH * i / 4);
                    g2.setColor(new Color(226, 232, 240));
                    g2.drawLine(padL, y, padL + chartW, y);
                    g2.setColor(TEXT_SEC);
                    g2.setFont(new Font("Segoe UI", Font.PLAIN, 9));
                    int val = (int)(maxVal - (i * (maxVal - minVal) / 4));
                    g2.drawString((val / 1000) + "k", 4, y + 4);
                }

                // الأعمدة
                for (int i = 0; i < n; i++) {
                    int x = padL + (i * chartW / n) + (chartW / n - barW) / 2;
                    int barH = (int)((salaries[i] - minVal) * chartH / (double)(maxVal - minVal));
                    int y = padT + chartH - barH;

                    // ظل العمود
                    g2.setColor(new Color(59, 130, 246, 30));
                    g2.fillRoundRect(x + 3, y + 3, barW, barH, 6, 6);

                    // العمود
                    GradientPaint gp = new GradientPaint(x, y, BLUE, x, y + barH, new Color(99, 179, 237));
                    g2.setPaint(gp);
                    g2.fillRoundRect(x, y, barW, barH, 6, 6);

                    // قيمة
                    g2.setColor(TEXT_PRI);
                    g2.setFont(new Font("Segoe UI", Font.BOLD, 9));
                    String label = (salaries[i] / 1000) + "k";
                    int strW = g2.getFontMetrics().stringWidth(label);
                    g2.drawString(label, x + (barW - strW) / 2, y - 4);

                    // اسم الشهر
                    g2.setColor(TEXT_SEC);
                    g2.setFont(new Font("Segoe UI", Font.PLAIN, 10));
                    int mw = g2.getFontMetrics().stringWidth(months[i]);
                    g2.drawString(months[i], x + (barW - mw) / 2, padT + chartH + 16);
                }

                g2.dispose();
            }
        };
        chart.setBackground(CARD);

        card.add(title, BorderLayout.NORTH);
        card.add(chart, BorderLayout.CENTER);
        return card;
    }

    // ── مخطط دائري للأقسام
    private JPanel buildDeptPieCard() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_CLR, 1, true),
            new EmptyBorder(16, 18, 16, 18)
        ));

        JLabel title = new JLabel("توزيع الموظفين حسب القسم");
        title.setFont(new Font("Segoe UI", Font.BOLD, 13));
        title.setForeground(TEXT_PRI);
        title.setBorder(new EmptyBorder(0, 0, 10, 0));

        String[] depts = {"تقنية المعلومات", "المبيعات", "المالية", "الموارد البشرية", "أخرى"};
        int[] values   = {65, 45, 38, 30, 70};
        Color[] colors = {ACCENT, BLUE, GREEN, ORANGE, RED};

        JPanel piePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int w = getWidth(), h = getHeight();
                int size = Math.min(w, h) - 40;
                int x = (w - size) / 2;
                int y = (h - size) / 2;

                int total = 0;
                for (int v : values) total += v;

                int startAngle = 90;
                for (int i = 0; i < values.length; i++) {
                    int arc = (int)(360.0 * values[i] / total);
                    g2.setColor(colors[i]);
                    g2.fillArc(x, y, size, size, startAngle, arc);
                    g2.setColor(CARD);
                    g2.setStroke(new BasicStroke(2));
                    g2.drawArc(x, y, size, size, startAngle, arc);
                    startAngle += arc;
                }

                // حلقة داخلية
                g2.setColor(CARD);
                int inner = size / 2;
                g2.fillOval(x + size / 4, y + size / 4, inner, inner);

                // نص المنتصف
                g2.setColor(TEXT_PRI);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 18));
                String totalStr = String.valueOf(total);
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(totalStr, x + size / 2 - fm.stringWidth(totalStr) / 2, y + size / 2 + 6);
                g2.setFont(new Font("Segoe UI", Font.PLAIN, 10));
                g2.setColor(TEXT_SEC);
                String lbl = "موظف";
                g2.drawString(lbl, x + size / 2 - fm.stringWidth(lbl) / 2 + 5, y + size / 2 + 20);

                g2.dispose();
            }
        };
        piePanel.setBackground(CARD);
        piePanel.setPreferredSize(new Dimension(0, 160));

        // مفتاح الألوان
        JPanel legend = new JPanel(new GridLayout(0, 1, 0, 4));
        legend.setBackground(CARD);
        legend.setBorder(new EmptyBorder(8, 0, 0, 0));

        int total = 0;
        for (int v : values) total += v;

        for (int i = 0; i < depts.length; i++) {
            JPanel item = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
            item.setBackground(CARD);
            JPanel dot = new JPanel();
            dot.setBackground(colors[i]);
            dot.setPreferredSize(new Dimension(10, 10));
            JLabel name = new JLabel(depts[i]);
            name.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            name.setForeground(TEXT_SEC);
            int pct = (values[i] * 100) / total;
            JLabel pctLbl = new JLabel(pct + "%");
            pctLbl.setFont(new Font("Segoe UI", Font.BOLD, 11));
            pctLbl.setForeground(colors[i]);
            item.add(dot); item.add(name); item.add(pctLbl);
            legend.add(item);
        }

        card.add(title,    BorderLayout.NORTH);
        card.add(piePanel, BorderLayout.CENTER);
        card.add(legend,   BorderLayout.SOUTH);
        return card;
    }

    // ── قائمة التقارير المتاحة
    private JPanel buildReportsListCard() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_CLR, 1, true),
            new EmptyBorder(16, 18, 16, 18)
        ));

        JLabel title = new JLabel("التقارير المتاحة");
        title.setFont(new Font("Segoe UI", Font.BOLD, 13));
        title.setForeground(TEXT_PRI);
        title.setBorder(new EmptyBorder(0, 0, 12, 0));

        Object[][] reports = {
            {"📋 تقرير الحضور الشهري",    "مارس 2026",  "PDF",   "جديد"},
            {"💰 كشف الرواتب",            "مارس 2026",  "Excel", "جاهز"},
            {"👥 تقرير الموارد البشرية",  "Q1 2026",    "PDF",   "جاهز"},
            {"📊 تحليل الأداء الوظيفي",   "2025",       "PDF",   "قديم"},
            {"🔄 تقرير دوران الموظفين",   "فبراير 2026","Excel", "جاهز"},
            {"📈 ملخص تنفيذي",           "Q1 2026",    "PDF",   "قيد الإعداد"},
        };

        String[] cols = {"اسم التقرير", "الفترة", "التنسيق", "الحالة"};
        DefaultTableModel model = new DefaultTableModel(reports, cols) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setForeground(TEXT_PRI);
        table.setBackground(CARD);
        table.setRowHeight(38);
        table.setShowHorizontalLines(true);
        table.setGridColor(new Color(241, 245, 249));
        table.setSelectionBackground(ACCENT_LIGHT);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 11));
        header.setForeground(TEXT_SEC);
        header.setBackground(new Color(248, 250, 252));
        header.setPreferredSize(new Dimension(0, 36));

        int[] widths = {180, 90, 70, 100};
        for (int i = 0; i < widths.length; i++)
            table.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);

        table.getColumnModel().getColumn(3).setCellRenderer(new ReportStatusRenderer());

        // نقر مزدوج لتنزيل التقرير
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = table.getSelectedRow();
                    if (row >= 0) {
                        String name = model.getValueAt(row, 0).toString();
                        JOptionPane.showMessageDialog(ReportsPanel.this,
                            "جاري تنزيل: " + name, "تنزيل التقرير", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(CARD);

        JLabel hint = new JLabel("💡 انقر مرتين على أي تقرير لتنزيله");
        hint.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        hint.setForeground(TEXT_SEC);
        hint.setBorder(new EmptyBorder(8, 0, 0, 0));

        card.add(title,  BorderLayout.NORTH);
        card.add(scroll, BorderLayout.CENTER);
        card.add(hint,   BorderLayout.SOUTH);
        return card;
    }

    // ── جدول أفضل الموظفين أداءً
    private JPanel buildTopEmployeesTable() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 240));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_CLR, 1, true),
            new EmptyBorder(16, 18, 16, 18)
        ));

        JLabel title = new JLabel("🏆 أفضل الموظفين أداءً — هذا الشهر");
        title.setFont(new Font("Segoe UI", Font.BOLD, 14));
        title.setForeground(TEXT_PRI);
        title.setBorder(new EmptyBorder(0, 0, 12, 0));

        Object[][] topData = {
            {"🥇", "نور حسين إبراهيم",   "المبيعات",           "98%",  "5 / 5",  "ممتاز"},
            {"🥈", "محمد أحمد الزيد",    "المالية",            "96%",  "4.8 / 5","ممتاز"},
            {"🥉", "ريم عبدالله فارس",   "المحاسبة",           "95%",  "4.7 / 5","جيد جداً"},
            {"4",   "أحمد خالد محمود",   "تقنية المعلومات",    "93%",  "4.5 / 5","جيد جداً"},
            {"5",   "ليلى عمر النجار",    "التسويق",            "90%",  "4.3 / 5","جيد"},
        };

        String[] cols = {"الترتيب", "اسم الموظف", "القسم", "نسبة الحضور", "تقييم الأداء", "الدرجة"};
        DefaultTableModel model = new DefaultTableModel(topData, cols) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setForeground(TEXT_PRI);
        table.setBackground(CARD);
        table.setRowHeight(40);
        table.setShowHorizontalLines(true);
        table.setGridColor(new Color(241, 245, 249));
        table.setSelectionBackground(ACCENT_LIGHT);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        header.setForeground(TEXT_SEC);
        header.setBackground(new Color(248, 250, 252));
        header.setPreferredSize(new Dimension(0, 38));

        int[] widths = {70, 180, 150, 100, 120, 100};
        for (int i = 0; i < widths.length; i++)
            table.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);

        table.getColumnModel().getColumn(5).setCellRenderer(new GradeRenderer());

        // صف التمييز للأول (لون خاص)
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val, boolean sel, boolean foc, int r, int c) {
                Component comp = super.getTableCellRendererComponent(t, val, sel, foc, r, c);
                if (!sel) {
                    if (r == 0) comp.setBackground(new Color(255, 251, 235));
                    else comp.setBackground(CARD);
                }
                return comp;
            }
        });
        table.getColumnModel().getColumn(5).setCellRenderer(new GradeRenderer());

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(CARD);

        card.add(title,  BorderLayout.NORTH);
        card.add(scroll, BorderLayout.CENTER);
        return card;
    }

    // ── Renderer حالة التقرير
    private static class ReportStatusRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable t, Object val, boolean sel, boolean foc, int r, int c) {
            JLabel lbl = new JLabel(val != null ? val.toString() : "");
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 11));
            lbl.setOpaque(true);
            lbl.setHorizontalAlignment(CENTER);
            lbl.setBorder(new EmptyBorder(4, 8, 4, 8));
            switch (val != null ? val.toString() : "") {
                case "جديد":
                    lbl.setForeground(new Color(29, 78, 216));
                    lbl.setBackground(new Color(219, 234, 254));
                    break;
                case "جاهز":
                    lbl.setForeground(new Color(5, 150, 105));
                    lbl.setBackground(new Color(209, 250, 229));
                    break;
                case "قيد الإعداد":
                    lbl.setForeground(new Color(180, 83, 9));
                    lbl.setBackground(new Color(254, 243, 199));
                    break;
                default:
                    lbl.setForeground(new Color(100, 116, 139));
                    lbl.setBackground(new Color(241, 245, 249));
            }
            if (sel) lbl.setBackground(new Color(237, 233, 254));
            return lbl;
        }
    }

    // ── Renderer درجة الأداء
    private static class GradeRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable t, Object val, boolean sel, boolean foc, int r, int c) {
            JLabel lbl = new JLabel(val != null ? val.toString() : "");
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 11));
            lbl.setOpaque(true);
            lbl.setHorizontalAlignment(CENTER);
            lbl.setBorder(new EmptyBorder(4, 8, 4, 8));
            switch (val != null ? val.toString() : "") {
                case "ممتاز":
                    lbl.setForeground(new Color(5, 150, 105));
                    lbl.setBackground(new Color(209, 250, 229));
                    break;
                case "جيد جداً":
                    lbl.setForeground(new Color(29, 78, 216));
                    lbl.setBackground(new Color(219, 234, 254));
                    break;
                default:
                    lbl.setForeground(new Color(180, 83, 9));
                    lbl.setBackground(new Color(254, 243, 199));
            }
            if (sel) lbl.setBackground(new Color(237, 233, 254));
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
}

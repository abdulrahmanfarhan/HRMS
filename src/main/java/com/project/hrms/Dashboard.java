package com.project.hrms;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

/**
 * ════════════════════════════════════════════════════════════════════
 *  Dashboard — الشاشة الرئيسية لنظام HRMS
 * ════════════════════════════════════════════════════════════════════
 *
 *  هيكل الكلاس:
 *  ┌─ 1. CONSTANTS       : الألوان والثوابت
 *  ├─ 2. FIELDS          : المتغيرات العامة
 *  ├─ 3. CONSTRUCTOR     : نقطة البداية
 *  ├─ 4. FRAME SETUP     : إعداد الـ JFrame
 *  ├─ 5. NAVIGATION      : كل منطق التنقل في مكان واحد ✦
 *  ├─ 6. SIDEBAR BUILD   : بناء القائمة الجانبية
 *  ├─ 7. MAIN AREA BUILD : بناء منطقة المحتوى
 *  └─ 8. HOME CONTENT    : محتوى الصفحة الرئيسية
 *
 * ════════════════════════════════════════════════════════════════════
 */
public class Dashboard extends JFrame {

    private static final Color SIDEBAR_BG      = new Color(22, 28, 45);
    private static final Color SIDEBAR_LOGO_BG = new Color(15, 20, 35);
    private static final Color CONTENT_BG      = new Color(248, 250, 252);
    private static final Color CARD_BG         = Color.WHITE;

    private static final Color NAV_IDLE_BG     = SIDEBAR_BG;
    private static final Color NAV_ACTIVE_BG   = new Color(35, 43, 65);
    private static final Color NAV_IDLE_TEXT   = new Color(148, 163, 184);
    private static final Color NAV_ACTIVE_TEXT = Color.WHITE;
    private static final Color NAV_INDICATOR   = new Color(59, 130, 246);

    private static final Color ACCENT_BLUE   = new Color(59, 130, 246);
    private static final Color ACCENT_GREEN  = new Color(16, 185, 129);
    private static final Color ACCENT_ORANGE = new Color(245, 158, 11);
    private static final Color ACCENT_RED    = new Color(239, 68, 68);
    private static final Color TEXT_PRIMARY  = new Color(15, 23, 42);
    private static final Color TEXT_SECONDARY = new Color(100, 116, 139);
    private static final Color BORDER_COLOR  = new Color(226, 232, 240);

    private JPanel contentArea;

    private NavButton[] navButtons;

    private NavButton activeNavButton;

    public Dashboard() {
        setupFrame();

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(CONTENT_BG);
        root.add(buildSidebar(),  BorderLayout.WEST);
        root.add(buildMainArea(), BorderLayout.CENTER);
        setContentPane(root);

        navigateTo(Page.HOME);
    }

    private void setupFrame() {
        setTitle("HRMS — نظام إدارة الموارد البشرية");
        setSize(1280, 720);
        setMinimumSize(new Dimension(1000, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    enum Page {
        HOME,
        EMPLOYEES,
        ATTENDANCE,
        PAYROLL,
        REPORTS
    }

    private void navigateTo(Page page) {
        updateSidebarSelection(page);
        JPanel panel = buildPagePanel(page);
        swapContent(panel);
    }

    private JPanel buildPagePanel(Page page) {
        switch (page) {
            case HOME:       return buildHomePanel();
            case EMPLOYEES:  return new EmployeesPanel();
            case ATTENDANCE: return new AttendancePanel();
            case PAYROLL:    return new PayrollPanel();
            case REPORTS:    return new ReportsPanel();
            default:         return buildHomePanel();
        }
    }

    private void swapContent(JPanel newPanel) {
        contentArea.removeAll();
        contentArea.add(newPanel, BorderLayout.CENTER);
        contentArea.revalidate();
        contentArea.repaint();
    }

    private void updateSidebarSelection(Page page) {
        if (navButtons == null) return;

        if (activeNavButton != null) {
            activeNavButton.setActiveState(false);
        }

        for (NavButton nb : navButtons) {
            if (nb.getPage() == page) {
                nb.setActiveState(true);
                activeNavButton = nb;
                break;
            }
        }
    }

    private class NavButton extends JPanel {

        private final Page   page;
        private final JPanel indicator;
        private final JLabel iconLabel;
        private final JLabel textLabel;
        private final JPanel innerPanel;

        NavButton(Page page, String icon, String text) {
            this.page = page;

            setLayout(new BorderLayout());
            setBackground(NAV_IDLE_BG);
            setMaximumSize(new Dimension(210, 48));
            setMinimumSize(new Dimension(210, 48));
            setPreferredSize(new Dimension(210, 48));
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            indicator = new JPanel();
            indicator.setPreferredSize(new Dimension(4, 0));
            indicator.setBackground(NAV_IDLE_BG);
            add(indicator, BorderLayout.WEST);

            innerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
            innerPanel.setBackground(NAV_IDLE_BG);
            innerPanel.setBorder(new EmptyBorder(12, 10, 12, 10));

            iconLabel = new JLabel(icon);
            iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
            iconLabel.setForeground(NAV_IDLE_TEXT);

            textLabel = new JLabel(text);
            textLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            textLabel.setForeground(NAV_IDLE_TEXT);

            innerPanel.add(iconLabel);
            innerPanel.add(textLabel);
            add(innerPanel, BorderLayout.CENTER);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    navigateTo(page);
                }
            });
        }

        Page getPage() { return page; }

        void setActiveState(boolean active) {
            Color bg        = active ? NAV_ACTIVE_BG   : NAV_IDLE_BG;
            Color indColor  = active ? NAV_INDICATOR   : NAV_IDLE_BG;
            Color textColor = active ? NAV_ACTIVE_TEXT : NAV_IDLE_TEXT;
            int   fontStyle = active ? Font.BOLD       : Font.PLAIN;

            setBackground(bg);
            innerPanel.setBackground(bg);
            indicator.setBackground(indColor);
            iconLabel.setForeground(textColor);
            textLabel.setForeground(textColor);
            textLabel.setFont(textLabel.getFont().deriveFont(fontStyle));
            repaint();
        }
    }

    private JPanel buildSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(SIDEBAR_BG);
        sidebar.setPreferredSize(new Dimension(210, 0));

        sidebar.add(buildLogo());
        sidebar.add(buildSidebarDivider("القائمة الرئيسية"));

        navButtons = new NavButton[] {
            new NavButton(Page.HOME,       "🏠", "الرئيسية"),
            new NavButton(Page.EMPLOYEES,  "👥", "الموظفون"),
            new NavButton(Page.ATTENDANCE, "📅", "الحضور"),
            new NavButton(Page.PAYROLL,    "💰", "الرواتب"),
            new NavButton(Page.REPORTS,    "📊", "التقارير")
        };

        sidebar.add(navButtons[0]);

        sidebar.add(buildSidebarDivider("الوحدات"));
        for (int i = 1; i < navButtons.length; i++) {
            sidebar.add(navButtons[i]);
        }

        sidebar.add(Box.createVerticalGlue());
        sidebar.add(buildUserCard());

        return sidebar;
    }

    private JPanel buildLogo() {
        JPanel logo = new JPanel(new BorderLayout());
        logo.setBackground(SIDEBAR_LOGO_BG);
        logo.setMaximumSize(new Dimension(210, 70));
        logo.setMinimumSize(new Dimension(210, 70));
        logo.setBorder(new EmptyBorder(16, 20, 16, 20));

        JPanel inner = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        inner.setBackground(SIDEBAR_LOGO_BG);

        JLabel icon = new JLabel("⚡");
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 22));
        icon.setForeground(ACCENT_BLUE);

        JLabel text = new JLabel("HRMS");
        text.setFont(new Font("Segoe UI", Font.BOLD, 18));
        text.setForeground(Color.WHITE);

        inner.add(icon);
        inner.add(text);
        logo.add(inner, BorderLayout.CENTER);
        return logo;
    }

    private JPanel buildSidebarDivider(String label) {
        JPanel divider = new JPanel(new BorderLayout());
        divider.setBackground(SIDEBAR_BG);
        divider.setMaximumSize(new Dimension(210, 36));
        divider.setBorder(new EmptyBorder(10, 20, 4, 20));

        JLabel lbl = new JLabel(label.toUpperCase());
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        lbl.setForeground(new Color(71, 85, 105));
        divider.add(lbl, BorderLayout.CENTER);
        return divider;
    }

    private JPanel buildUserCard() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(SIDEBAR_LOGO_BG);
        card.setMaximumSize(new Dimension(210, 64));
        card.setMinimumSize(new Dimension(210, 64));
        card.setBorder(new EmptyBorder(12, 16, 12, 16));

        JLabel avatar = new JLabel("👤");
        avatar.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 22));

        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBackground(SIDEBAR_LOGO_BG);
        info.setBorder(new EmptyBorder(0, 10, 0, 0));

        JLabel name = new JLabel("مدير النظام");
        name.setFont(new Font("Segoe UI", Font.BOLD, 12));
        name.setForeground(Color.WHITE);

        JLabel role = new JLabel("Administrator");
        role.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        role.setForeground(new Color(100, 116, 139));

        info.add(name);
        info.add(role);
        card.add(avatar, BorderLayout.WEST);
        card.add(info,   BorderLayout.CENTER);
        return card;
    }

    private JPanel buildMainArea() {
        JPanel mainArea = new JPanel(new BorderLayout());
        mainArea.setBackground(CONTENT_BG);
        mainArea.add(buildTopBar(), BorderLayout.NORTH);

        contentArea = new JPanel(new BorderLayout());
        contentArea.setBackground(CONTENT_BG);
        contentArea.setBorder(new EmptyBorder(20, 24, 20, 24));

        JScrollPane scroll = new JScrollPane(contentArea);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setBackground(CONTENT_BG);
        mainArea.add(scroll, BorderLayout.CENTER);

        return mainArea;
    }

    private JPanel buildTopBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(CARD_BG);
        bar.setPreferredSize(new Dimension(0, 56));
        bar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR),
            new EmptyBorder(8, 24, 8, 24)
        ));

        JLabel title = new JLabel("لوحة التحكم");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setForeground(TEXT_PRIMARY);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        actions.setBackground(CARD_BG);
        actions.add(makeIconLabel("🔔"));
        actions.add(makeIconLabel("⚙️"));

        bar.add(title,   BorderLayout.WEST);
        bar.add(actions, BorderLayout.EAST);
        return bar;
    }

    private JLabel makeIconLabel(String emoji) {
        JLabel lbl = new JLabel(emoji);
        lbl.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        lbl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return lbl;
    }

    private JPanel buildHomePanel() {
        JPanel page = new JPanel();
        page.setLayout(new BoxLayout(page, BoxLayout.Y_AXIS));
        page.setBackground(CONTENT_BG);

        page.add(buildHomeHeader());
        page.add(Box.createVerticalStrut(4));
        page.add(buildStatsRow());
        page.add(Box.createVerticalStrut(20));
        page.add(buildSectionLabel("وصول سريع"));
        page.add(Box.createVerticalStrut(10));
        page.add(buildQuickAccessRow());
        page.add(Box.createVerticalStrut(20));
        page.add(buildSectionLabel("آخر النشاطات"));
        page.add(Box.createVerticalStrut(10));
        page.add(buildActivityTable());

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(CONTENT_BG);
        wrapper.add(page, BorderLayout.NORTH);
        return wrapper;
    }

    private JPanel buildHomeHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(CONTENT_BG);
        header.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        header.setBorder(new EmptyBorder(0, 0, 16, 0));

        JPanel text = new JPanel();
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
        text.setBackground(CONTENT_BG);

        JLabel title = new JLabel("مرحباً بك في نظام الموارد البشرية 👋");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(TEXT_PRIMARY);

        JLabel sub = new JLabel("نظرة عامة على بيانات اليوم");
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        sub.setForeground(TEXT_SECONDARY);

        text.add(title);
        text.add(Box.createVerticalStrut(4));
        text.add(sub);

        header.add(text, BorderLayout.CENTER);
        return header;
    }

    private JPanel buildStatsRow() {
        JPanel row = new JPanel(new GridLayout(1, 4, 16, 0));
        row.setBackground(CONTENT_BG);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));

        row.add(buildStatCard("👥", "إجمالي الموظفين", "248",       "+12", ACCENT_BLUE));
        row.add(buildStatCard("✅", "حاضرون اليوم",    "215",       "+5",  ACCENT_GREEN));
        row.add(buildStatCard("💰", "إجمالي الرواتب",  "45,200 ₪", "+3%", ACCENT_ORANGE));
        row.add(buildStatCard("📋", "طلبات معلقة",    "8",         "-2",  ACCENT_RED));

        return row;
    }

    private JPanel buildStatCard(String icon, String label, String value, String change, Color accent) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1, true),
            new EmptyBorder(16, 18, 16, 18)
        ));

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(CARD_BG);

        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl.setForeground(TEXT_SECONDARY);

        JPanel iconBox = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 6));
        iconBox.setBackground(new Color(accent.getRed(), accent.getGreen(), accent.getBlue(), 25));
        iconBox.setPreferredSize(new Dimension(36, 36));
        JLabel ic = new JLabel(icon);
        ic.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        iconBox.add(ic);

        top.add(lbl,    BorderLayout.CENTER);
        top.add(iconBox, BorderLayout.EAST);

        JLabel val = new JLabel(value);
        val.setFont(new Font("Segoe UI", Font.BOLD, 22));
        val.setForeground(TEXT_PRIMARY);

        JLabel chg = new JLabel(change + " هذا الشهر");
        chg.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        chg.setForeground(change.startsWith("+") ? ACCENT_GREEN : ACCENT_RED);

        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBackground(CARD_BG);
        body.add(val);
        body.add(Box.createVerticalStrut(4));
        body.add(chg);

        card.add(top,  BorderLayout.NORTH);
        card.add(body, BorderLayout.CENTER);
        return card;
    }

    private JPanel buildSectionLabel(String text) {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(CONTENT_BG);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lbl.setForeground(TEXT_PRIMARY);
        row.add(lbl, BorderLayout.WEST);
        return row;
    }

    private JPanel buildQuickAccessRow() {
        JPanel row = new JPanel(new GridLayout(1, 4, 16, 0));
        row.setBackground(CONTENT_BG);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));

        row.add(buildQuickCard("👥", "الموظفون", "إدارة بيانات الموظفين",  Page.EMPLOYEES));
        row.add(buildQuickCard("📅", "الحضور",   "متابعة الحضور والغياب",  Page.ATTENDANCE));
        row.add(buildQuickCard("💰", "الرواتب",  "معالجة وصرف الرواتب",    Page.PAYROLL));
        row.add(buildQuickCard("📊", "التقارير", "تقارير وإحصاءات شاملة", Page.REPORTS));

        return row;
    }

    private JPanel buildQuickCard(String icon, String title, String desc, Page target) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_BG);
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1, true),
            new EmptyBorder(14, 16, 14, 16)
        ));

        JLabel ic = new JLabel(icon + "  " + title);
        ic.setFont(new Font("Segoe UI", Font.BOLD, 14));
        ic.setForeground(TEXT_PRIMARY);

        JLabel ds = new JLabel(desc);
        ds.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        ds.setForeground(TEXT_SECONDARY);

        JPanel inner = new JPanel();
        inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));
        inner.setBackground(CARD_BG);
        inner.add(ic);
        inner.add(Box.createVerticalStrut(4));
        inner.add(ds);

        card.add(inner, BorderLayout.CENTER);

        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                navigateTo(target);
            }
        });

        return card;
    }

    private JPanel buildActivityTable() {
        JPanel tableCard = new JPanel(new BorderLayout());
        tableCard.setBackground(CARD_BG);
        tableCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1, true),
            new EmptyBorder(16, 18, 16, 18)
        ));

        String[] cols = {"الموظف", "النشاط", "التاريخ", "الحالة"};
        Object[][] rows = {
            {"أحمد خالد",  "تسجيل حضور",   "2026-03-15", "✅ مكتمل"},
            {"سارة محمود", "طلب إجازة",     "2026-03-15", "⏳ معلق"},
            {"محمد علي",   "صرف راتب",      "2026-03-14", "✅ مكتمل"},
            {"نور الدين",  "تحديث بيانات",  "2026-03-14", "✅ مكتمل"},
            {"ليلى أحمد",  "طلب تقرير",     "2026-03-13", "🔴 مرفوض"},
        };

        JTable table = new JTable(rows, cols) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setForeground(TEXT_PRIMARY);
        table.setBackground(CARD_BG);
        table.setRowHeight(36);
        table.setShowHorizontalLines(true);
        table.setGridColor(BORDER_COLOR);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setForeground(TEXT_SECONDARY);
        table.getTableHeader().setBackground(new Color(248, 250, 252));
        table.setSelectionBackground(new Color(239, 246, 255));

        tableCard.add(table.getTableHeader(), BorderLayout.NORTH);
        tableCard.add(table,                  BorderLayout.CENTER);
        return tableCard;
    }
}
package Views;

import Controllers.CategoryController;
import Controllers.TransactionController;
import Models.Category;
import Models.Transaction;
import Models.User;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ReportPanel extends javax.swing.JPanel {
    private User user;
    private TransactionController transactionController;
    private CategoryController categoryController;
    private DecimalFormat currencyFormat;
    
    public ReportPanel(User user) {
        this.user = user;
        this.transactionController = new TransactionController();
        this.categoryController = new CategoryController();
        this.currencyFormat = new DecimalFormat("#,###");
        initComponents();
        setupPanel();
        loadMonthlyReport();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(240, 248, 255));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header Panel
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // Main Content Panel
        JPanel mainPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        mainPanel.setOpaque(false);

        // Summary Panel (Top)
        JPanel summaryPanel = createSummaryPanel();
        mainPanel.add(summaryPanel);

        // Detail Panel (Bottom)
        JPanel detailPanel = createDetailPanel();
        mainPanel.add(detailPanel);

        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(70, 130, 180));
        panel.setPreferredSize(new Dimension(0, 80));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel titleLabel = new JLabel("BÁO CÁO THU CHI");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Month selector
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        controlPanel.setOpaque(false);
        
        JLabel monthLabel = new JLabel("Tháng: ");
        monthLabel.setForeground(Color.WHITE);
        monthLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        String[] months = new String[12];
        for (int i = 0; i < 12; i++) {
            months[i] = String.format("%02d", i + 1);
        }
        JComboBox<String> monthCombo = new JComboBox<>(months);
        monthCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        
        String[] years = {"2024", "2025", "2026"};
        JComboBox<String> yearCombo = new JComboBox<>(years);
        yearCombo.setSelectedItem("2025");
        yearCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JButton refreshBtn = new JButton("Làm mới");
        refreshBtn.setFont(new Font("Arial", Font.BOLD, 14));
        refreshBtn.setBackground(new Color(46, 139, 87));
        refreshBtn.setForeground(Color.WHITE);
        refreshBtn.setFocusPainted(false);
        refreshBtn.addActionListener(e -> {
            String month = (String) monthCombo.getSelectedItem();
            String year = (String) yearCombo.getSelectedItem();
            loadReportByMonth(year + "-" + month);
        });
        
        controlPanel.add(monthLabel);
        controlPanel.add(monthCombo);
        controlPanel.add(yearCombo);
        controlPanel.add(refreshBtn);

        panel.add(titleLabel, BorderLayout.CENTER);
        panel.add(controlPanel, BorderLayout.EAST);

        return panel;
    }

    private JPanel createSummaryPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 3, 15, 0));
        panel.setOpaque(false);

        // Tổng thu
        JPanel incomePanel = createStatCard("TỔNG THU", "0 VND", new Color(46, 139, 87), "income");
        panel.add(incomePanel);

        // Tổng chi
        JPanel expensePanel = createStatCard("TỔNG CHI", "0 VND", new Color(220, 20, 60), "expense");
        panel.add(expensePanel);

        // Số dư
        JPanel balancePanel = createStatCard("SỐ DƯ", "0 VND", new Color(70, 130, 180), "balance");
        panel.add(balancePanel);

        return panel;
    }

    private JPanel createStatCard(String title, String value, Color color, String name) {
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 3),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        card.setName(name);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(color);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 28));
        valueLabel.setForeground(color);
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        valueLabel.setName(name + "Value");

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }

    private JPanel createDetailPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 0));
        panel.setOpaque(false);

        // Table Panel
        JPanel tablePanel = createTablePanel();
        panel.add(tablePanel);

        // Chart Panel
        JPanel chartPanel = createChartPanel();
        panel.add(chartPanel);

        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JLabel titleLabel = new JLabel("CHI TIẾT THEO DANH MỤC");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(70, 130, 180));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        String[] columns = {"Danh mục", "Loại", "Số tiền", "Tỷ lệ %"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(35);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(70, 130, 180));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionBackground(new Color(173, 216, 230));
        table.setName("reportTable");

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createChartPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JLabel titleLabel = new JLabel("BIỂU ĐỒ PHÂN BỔ CHI TIÊU");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(70, 130, 180));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel chartArea = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawPieChart(g);
            }
        };
        chartArea.setBackground(Color.WHITE);
        chartArea.setName("chartArea");

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(chartArea, BorderLayout.CENTER);

        return panel;
    }

    private Map<String, CategoryData> categoryDataMap = new HashMap<>();

    private void drawPieChart(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (categoryDataMap.isEmpty()) {
            g2d.setFont(new Font("Arial", Font.PLAIN, 16));
            g2d.setColor(Color.GRAY);
            String msg = "Chưa có dữ liệu";
            FontMetrics fm = g2d.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(msg)) / 2;
            int y = getHeight() / 2;
            g2d.drawString(msg, x, y);
            return;
        }

        int width = getWidth();
        int height = getHeight();
        int size = Math.min(width, height) - 100;
        int x = (width - size) / 2;
        int y = 50;

        double total = categoryDataMap.values().stream()
            .mapToDouble(cd -> cd.amount)
            .sum();

        if (total == 0) return;

        Color[] colors = {
            new Color(255, 99, 132),
            new Color(54, 162, 235),
            new Color(255, 206, 86),
            new Color(75, 192, 192),
            new Color(153, 102, 255),
            new Color(255, 159, 64),
            new Color(199, 199, 199),
            new Color(83, 102, 255)
        };

        int startAngle = 0;
        int colorIndex = 0;
        int legendY = y + size + 30;

        for (Map.Entry<String, CategoryData> entry : categoryDataMap.entrySet()) {
            CategoryData data = entry.getValue();
            int arcAngle = (int) Math.round((data.amount / total) * 360);

            g2d.setColor(colors[colorIndex % colors.length]);
            g2d.fillArc(x, y, size, size, startAngle, arcAngle);

            // Draw legend
            g2d.fillRect(50, legendY, 20, 20);
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            String legend = String.format("%s: %.1f%%", 
                data.name, (data.amount / total) * 100);
            g2d.drawString(legend, 80, legendY + 15);

            startAngle += arcAngle;
            colorIndex++;
            legendY += 25;
        }
    }

    private void setupPanel() {
        // Setup initial state
    }

    private void loadMonthlyReport() {
        Calendar cal = Calendar.getInstance();
        String currentMonth = String.format("%d-%02d", 
            cal.get(Calendar.YEAR), 
            cal.get(Calendar.MONTH) + 1);
        loadReportByMonth(currentMonth);
    }

    private void loadReportByMonth(String yearMonth) {
        double totalIncome = 0;
        double totalExpense = 0;
        categoryDataMap.clear();

        ArrayList<Transaction> transactions = transactionController.getAllTransactions(user.getId());
        ArrayList<Category> categories = categoryController.showCategory();
        Map<Integer, Category> categoryMap = new HashMap<>();
        
        for (Category cat : categories) {
            categoryMap.put(cat.getId(), cat);
        }

        // Filter transactions by month
        for (Transaction trans : transactions) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            String transMonth = sdf.format(trans.getDate());
            
            if (transMonth.equals(yearMonth)) {
                Category cat = categoryMap.get(trans.getCategoryId());
                if (cat != null) {
                    String catName = cat.getName();
                    double amount = trans.getAmount();
                    
                    categoryDataMap.putIfAbsent(catName, 
                        new CategoryData(catName, cat.getType()));
                    categoryDataMap.get(catName).amount += amount;
                    
                    if ("Thu Nhập".equals(cat.getType())) {
                        totalIncome += amount;
                    } else {
                        totalExpense += amount;
                    }
                }
            }
        }

        updateSummaryCards(totalIncome, totalExpense);
        updateReportTable(totalIncome + totalExpense);
        repaint();
    }

    private void updateSummaryCards(double income, double expense) {
        Component[] components = ((JPanel) ((JPanel) getComponent(1)).getComponent(0)).getComponents();
        
        for (Component comp : components) {
            if (comp instanceof JPanel) {
                JPanel card = (JPanel) comp;
                String name = card.getName();
                
                for (Component c : card.getComponents()) {
                    if (c instanceof JLabel) {
                        JLabel label = (JLabel) c;
                        if (label.getName() != null) {
                            if (label.getName().equals("incomeValue")) {
                                label.setText(currencyFormat.format(income) + " VND");
                            } else if (label.getName().equals("expenseValue")) {
                                label.setText(currencyFormat.format(expense) + " VND");
                            } else if (label.getName().equals("balanceValue")) {
                                double balance = income - expense;
                                label.setText(currencyFormat.format(balance) + " VND");
                                label.setForeground(balance >= 0 ? 
                                    new Color(46, 139, 87) : new Color(220, 20, 60));
                            }
                        }
                    }
                }
            }
        }
    }

    private void updateReportTable(double total) {
        JPanel detailPanel = (JPanel) ((JPanel) getComponent(1)).getComponent(1);
        JPanel tablePanel = (JPanel) detailPanel.getComponent(0);
        JScrollPane scrollPane = (JScrollPane) tablePanel.getComponent(1);
        JTable table = (JTable) scrollPane.getViewport().getView();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        
        model.setRowCount(0);
        
        for (CategoryData data : categoryDataMap.values()) {
            double percentage = total > 0 ? (data.amount / total) * 100 : 0;
            model.addRow(new Object[]{
                data.name,
                data.type,
                currencyFormat.format(data.amount) + " VND",
                String.format("%.1f%%", percentage)
            });
        }
    }

    private static class CategoryData {
        String name;
        String type;
        double amount;
        
        CategoryData(String name, String type) {
            this.name = name;
            this.type = type;
            this.amount = 0;
        }
    }
}
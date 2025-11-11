package Views;

import Models.User;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Home {
    private JFrame frame;

    public Home(User user) {
        frame = new JFrame("Trang chu");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(screenSize.width, screenSize.height);
        
        CardLayout cardLayout = new CardLayout();
        JPanel center = new JPanel(cardLayout);
        center.setBackground(new Color(220, 220, 220));
        
        //--------------//
        JPanel header = new JPanel();
        JLabel title = new JLabel("QUẢN LÝ CHI TIÊU");
        title.setForeground(Color.WHITE);       
        title.setFont(new Font("Arial", Font.BOLD, 24)); 
        
        header.setBackground(Color.GRAY);
        header.setPreferredSize(new Dimension(screenSize.width, 80));
        header.add(title);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        //------------//
        JPanel sider = new JPanel(new GridLayout(10, 1, 10, 15));
        sider.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        sider.setBackground(Color.LIGHT_GRAY);
        sider.setPreferredSize(new Dimension(150, screenSize.height));
        
        JButton budget = new JButton("Budget");
        JButton transaction = new JButton("Transaction");
        JButton category = new JButton("Category");
        JButton report = new JButton("Report");
        JButton setting = new JButton("Setting");  
        JButton advice = new JButton("Advice");
        JButton logout = new JButton("Logout");
        
        budget.setBackground(new Color(70, 130, 180));
        budget.setForeground(Color.WHITE);
        budget.setFont(new Font("Arial", Font.BOLD, 18));

        transaction.setBackground(new Color(46, 139, 87));
        transaction.setForeground(Color.WHITE);
        transaction.setFont(new Font("Arial", Font.BOLD, 18));

        category.setBackground(new Color(255, 140, 0));
        category.setForeground(Color.WHITE);
        category.setFont(new Font("Arial", Font.BOLD, 18));

        report.setBackground(new Color(220, 20, 60));
        report.setForeground(Color.WHITE);
        report.setFont(new Font("Arial", Font.BOLD, 18));

       
        setting.setBackground(new Color(123, 104, 238)); 
        setting.setForeground(Color.WHITE);
        setting.setFont(new Font("Arial", Font.BOLD, 18));
        
        advice.setBackground(new Color(255, 69, 0)); 
        advice.setForeground(Color.WHITE);
        advice.setFont(new Font("Arial", Font.BOLD, 18));
        
        logout.setBackground(new Color(255, 69, 0)); 
        logout.setForeground(Color.WHITE);
        logout.setFont(new Font("Arial", Font.BOLD, 18));
        
        sider.add(budget);
        sider.add(transaction);
        sider.add(category);
        sider.add(report);
        sider.add(setting);
        sider.add(advice);
        sider.add(logout); 
        //-------------//
        frame.add(header, BorderLayout.NORTH);  
        frame.add(sider, BorderLayout.WEST);
        frame.add(center, BorderLayout.CENTER);
        
        //------------//
        BudgetPanel budgetPanel = new BudgetPanel(user);
        TransactionPanel transactionPanel=new TransactionPanel(user);
        CategoryPanel categoryPanel=new CategoryPanel(user, budgetPanel,transactionPanel);
        ReportPanel reportPanel=new ReportPanel(user);
        SettingPanel settingPanel=new SettingPanel(user);
        AdvicePanel advicePanel=new AdvicePanel(user);
        center.add(budgetPanel, "Budget");
        center.add(transactionPanel, "Transaction");
        center.add(categoryPanel, "Category");
        center.add(reportPanel, "Report");  
        center.add(advicePanel, "Advice");  
        center.add(settingPanel, "Setting");  
        
        //-------------//
        budget.addActionListener(e -> cardLayout.show(center, "Budget"));
        transaction.addActionListener(e -> cardLayout.show(center, "Transaction"));
        category.addActionListener(e -> cardLayout.show(center, "Category"));
        report.addActionListener(e -> cardLayout.show(center, "Report"));
        setting.addActionListener(e -> cardLayout.show(center, "Setting"));
        advice.addActionListener(e -> cardLayout.show(center, "Advice"));
        //--------------//
        logout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(frame, 
                "Bạn có chắc chắn muốn đăng xuất không?", 
                "Xác nhận", 
                JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                frame.dispose(); // đóng cửa sổ hiện tại
                new Login().setVisible(true); // quay lại màn hình đăng nhập
            }
        });
        
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }

    public void showHome() {
        frame.setVisible(true);
    }

    public JFrame getFrame() {
        return frame;
    }
}
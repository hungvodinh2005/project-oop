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
        
        sider.add(budget);
        sider.add(transaction);
        sider.add(category);
        sider.add(report);
        
        //-------------//
        frame.add(header, BorderLayout.NORTH);  
        frame.add(sider, BorderLayout.WEST);
        frame.add(center, BorderLayout.CENTER);
        
        //------------//
        BudgetPanel budgetPanel = new BudgetPanel(user);
        center.add(budgetPanel, "Budget");
        center.add(new TransactionPanel(), "Transaction");
        center.add(new CategoryPanel(user, budgetPanel), "Category");
        center.add(new ReportPanel(), "Report");  
        
        //-------------//
        budget.addActionListener(e -> cardLayout.show(center, "Budget"));
        transaction.addActionListener(e -> cardLayout.show(center, "Transaction"));
        category.addActionListener(e -> cardLayout.show(center, "Category"));
        report.addActionListener(e -> cardLayout.show(center, "Report"));
        
        //--------------//
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
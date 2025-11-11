package Controllers;
import Connect.connectMysql;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class ReportController {

    
    public double getTotalIncome(int userId, String monthYear) {
        double total = 0;
        String sql = """
            SELECT SUM(t.money) AS total
            FROM transactions t
            JOIN category c ON t.categoryId = c.id
            WHERE t.userId = ?
              AND c.type = 'thu nhập'
              AND DATE_FORMAT(t.date, '%Y-%m') = ?
        """;
        try (Connection conn = new connectMysql().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setString(2, monthYear);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) total = rs.getDouble("total");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }

    
    public double getTotalExpense(int userId, String monthYear) {
        double total = 0;
        String sql = """
            SELECT SUM(t.money) AS total
            FROM transactions t
            JOIN category c ON t.categoryId = c.id
            WHERE t.userId = ?
              AND c.type = 'chi tiêu'
              AND DATE_FORMAT(t.date, '%Y-%m') = ?
        """;
        try (Connection conn = new connectMysql().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setString(2, monthYear);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) total = rs.getDouble("total");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }

   
    public Map<String, Double> getMonthlySummary(int userId, String monthYear) {
        Map<String, Double> summary = new HashMap<>();
        double income = getTotalIncome(userId, monthYear);
        double expense = getTotalExpense(userId, monthYear);
        double balance = income - expense;
        summary.put("income", income);
        summary.put("expense", expense);
        summary.put("balance", balance);
        return summary;
    }

    
    public Map<String, Double> getExpenseByCategory(int userId, String monthYear) {
        Map<String, Double> map = new HashMap<>();
        String sql = """
            SELECT c.name, SUM(t.money) AS total,c.id as id
            FROM transactions t
            JOIN category c ON t.categoryId = c.id
            WHERE t.userId = ?
              AND c.type = 'chi tiêu'
              AND DATE_FORMAT(t.date, '%Y-%m') = ?
            GROUP BY c.name,c.id
        """;
        try (Connection conn = new connectMysql().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setString(2, monthYear);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                map.put(rs.getString("id"), rs.getDouble("total"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

   
    public Map<String, Boolean> checkBudgetExceeded(int userId, String monthYear) {
        Map<String, Boolean> result = new HashMap<>();
        String sql = """
            SELECT c.name, b.limitAmount, IFNULL(SUM(t.money), 0) AS spent
            FROM budget b
            JOIN category c ON b.categoryId = c.id
            LEFT JOIN transactions t
                ON t.categoryId = b.categoryId
                AND DATE_FORMAT(t.date, '%Y-%m') = b.month
                AND t.userId = b.userId
            WHERE b.userId = ? AND b.month = ?
            GROUP BY c.name, b.limitAmount
        """;
        try (Connection conn = new connectMysql().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setString(2, monthYear);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                double spent = rs.getDouble("spent");
                double limit = rs.getDouble("limitAmount");
                boolean exceeded = spent > limit;
                result.put(rs.getString("name"), exceeded);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    
    public int getExceededCount(int userId, String monthYear) {
        Map<String, Boolean> map = checkBudgetExceeded(userId, monthYear);
        int count = 0;
        for (Boolean b : map.values()) {
            if (b) count++;
        }
        return count;
    }
}

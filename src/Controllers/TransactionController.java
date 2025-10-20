package Controllers;

import java.sql.Connection;
import Connect.connectMysql;
import Models.Transaction;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class TransactionController {
    
    public void createTransaction(Transaction transaction) {
        String sql = "INSERT INTO transactions(userId, categoryId, amount, date, description) VALUES(?,?,?,?,?)";
        try {
            Connection conn = new connectMysql().getConnection();
            PreparedStatement create = conn.prepareStatement(sql);
            create.setInt(1, transaction.getUserId());
            create.setInt(2, transaction.getCategoryId());
            create.setDouble(3, transaction.getAmount());
            create.setDate(4, new java.sql.Date(transaction.getDate().getTime()));
            create.setString(5, transaction.getDescription());
            create.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public ArrayList<Transaction> getAllTransactions(int userId) {
        ArrayList<Transaction> list = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE userId = ? ORDER BY date DESC";
        try {
            Connection conn = new connectMysql().getConnection();
            PreparedStatement show = conn.prepareStatement(sql);
            show.setInt(1, userId);
            ResultSet result = show.executeQuery();
            while (result.next()) {
                Transaction trans = new Transaction();
                trans.setId(result.getInt("id"));
                trans.setUserId(result.getInt("userId"));
                trans.setCategoryId(result.getInt("categoryId"));
                trans.setAmount(result.getDouble("amount"));
                trans.setDate(result.getDate("date"));
                trans.setDescription(result.getString("description"));
                list.add(trans);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public ArrayList<Transaction> getTransactionsByMonth(int userId, String yearMonth) {
        ArrayList<Transaction> list = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE userId = ? AND DATE_FORMAT(date, '%Y-%m') = ? ORDER BY date DESC";
        try {
            Connection conn = new connectMysql().getConnection();
            PreparedStatement show = conn.prepareStatement(sql);
            show.setInt(1, userId);
            show.setString(2, yearMonth);
            ResultSet result = show.executeQuery();
            while (result.next()) {
                Transaction trans = new Transaction();
                trans.setId(result.getInt("id"));
                trans.setUserId(result.getInt("userId"));
                trans.setCategoryId(result.getInt("categoryId"));
                trans.setAmount(result.getDouble("amount"));
                trans.setDate(result.getDate("date"));
                trans.setDescription(result.getString("description"));
                list.add(trans);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public void updateTransaction(Transaction transaction) {
        String sql = "UPDATE transactions SET categoryId=?, amount=?, date=?, description=? WHERE id=? AND userId=?";
        try {
            Connection conn = new connectMysql().getConnection();
            PreparedStatement edit = conn.prepareStatement(sql);
            edit.setInt(1, transaction.getCategoryId());
            edit.setDouble(2, transaction.getAmount());
            edit.setDate(3, new java.sql.Date(transaction.getDate().getTime()));
            edit.setString(4, transaction.getDescription());
            edit.setInt(5, transaction.getId());
            edit.setInt(6, transaction.getUserId());
            edit.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void deleteTransaction(Transaction transaction) {
        String sql = "DELETE FROM transactions WHERE id=? AND userId=?";
        try {
            Connection conn = new connectMysql().getConnection();
            PreparedStatement delete = conn.prepareStatement(sql);
            delete.setInt(1, transaction.getId());
            delete.setInt(2, transaction.getUserId());
            delete.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public double getTotalIncomeByMonth(int userId, String yearMonth) {
        double total = 0;
        String sql = "SELECT SUM(t.amount) as total FROM transactions t " +
                     "JOIN category c ON t.categoryId = c.id " +
                     "WHERE t.userId = ? AND c.type = 'Thu Nhập' " +
                     "AND DATE_FORMAT(t.date, '%Y-%m') = ?";
        try {
            Connection conn = new connectMysql().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setString(2, yearMonth);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getDouble("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }
    
    public double getTotalExpenseByMonth(int userId, String yearMonth) {
        double total = 0;
        String sql = "SELECT SUM(t.amount) as total FROM transactions t " +
                     "JOIN category c ON t.categoryId = c.id " +
                     "WHERE t.userId = ? AND c.type = 'Chi Tiêu' " +
                     "AND DATE_FORMAT(t.date, '%Y-%m') = ?";
        try {
            Connection conn = new connectMysql().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setString(2, yearMonth);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getDouble("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }
}
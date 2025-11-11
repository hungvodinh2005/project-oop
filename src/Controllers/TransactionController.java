package Controllers;

import java.sql.Connection;
import Connect.connectMysql;
import Models.Transaction;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class TransactionController {
    
    
    public String generateTransactionId() {
        String prefix = "TRANS";
        String sql = "SELECT id FROM transactions ORDER BY id DESC LIMIT 1";
        String newId = "";

        try {
            Connection conn = new connectMysql().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String lastId = rs.getString("id");
                int number = Integer.parseInt(lastId.substring(prefix.length()));
                number++;
                newId = String.format("%s%04d", prefix, number);
            } else {
                newId = "TRANS0001";
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            newId = "TRANS0001";
        }
        return newId;
    }

    
    public void createTransaction(Transaction transaction) {
        String sql = "INSERT INTO transactions (id, userId, categoryId, money, date, description) VALUES (?,?,?,?,?,?)";
        try {
            Connection conn = new connectMysql().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, transaction.getId());
            ps.setInt(2, transaction.getUserId());
            ps.setString(3, transaction.getCategoryId());
            ps.setDouble(4, transaction.getMoney());
            ps.setDate(5, new java.sql.Date(transaction.getDate().getTime()));
            ps.setString(6, transaction.getDescription());
            ps.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   
    public ArrayList<Transaction> getAllTransactions(int userId) {
        ArrayList<Transaction> list = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE userId = ? ORDER BY date DESC";
        try {
            Connection conn = new connectMysql().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Transaction t = new Transaction();
                t.setId(rs.getString("id"));
                t.setUserId(rs.getInt("userId"));
                t.setCategoryId(rs.getString("categoryId"));
                t.setMoney(rs.getDouble("money"));
                t.setDate(rs.getDate("date"));
                t.setDescription(rs.getString("description"));
                list.add(t);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

   
    public ArrayList<Transaction> getTransactionsByMonth(int userId) {
        ArrayList<Transaction> list = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE userId = ? ORDER BY date DESC";
        try {
            Connection conn = new connectMysql().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Transaction t = new Transaction();
                t.setId(rs.getString("id"));
                t.setUserId(rs.getInt("userId"));
                t.setCategoryId(rs.getString("categoryId"));
                t.setMoney(rs.getDouble("money"));
                t.setDate(rs.getDate("date"));
                t.setDescription(rs.getString("description"));
                list.add(t);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

   
    public void updateTransaction(Transaction transaction) {
        String sql = "UPDATE transactions SET categoryId=?, money=?, date=?, description=? WHERE id=? AND userId=?";
        try {
            Connection conn = new connectMysql().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, transaction.getCategoryId());
            ps.setDouble(2, transaction.getMoney());
            ps.setDate(3, new java.sql.Date(transaction.getDate().getTime()));
            ps.setString(4, transaction.getDescription());
            ps.setString(5, transaction.getId());
            ps.setInt(6, transaction.getUserId());
            ps.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   
    public void deleteTransaction(Transaction transaction) {
        String sql = "DELETE FROM transactions WHERE id=? AND userId=?";
        try {
            Connection conn = new connectMysql().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, transaction.getId());
            ps.setInt(2, transaction.getUserId());
            ps.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   
    public double getTotalIncomeByMonth(int userId) {
        double total = 0;
        String sql = """
            SELECT SUM(t.money) AS total
            FROM transactions t
            JOIN category c ON t.categoryId = c.id
            WHERE t.userId = ? AND c.type = 'thu nhập' 
           
        """;
        try {
            Connection conn = new connectMysql().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
           
            ResultSet rs = ps.executeQuery();
            if (rs.next()) total = rs.getDouble("total");
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }

    
    public double getTotalExpenseByMonth(int userId) {
        double total = 0;
        String sql = """
            SELECT SUM(t.money) AS total
            FROM transactions t
            JOIN category c ON t.categoryId = c.id
            WHERE t.userId = ? AND c.type = 'chi tiêu' 
            
        """;
        try {
            Connection conn = new connectMysql().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
           
            ResultSet rs = ps.executeQuery();
            if (rs.next()) total = rs.getDouble("total");
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }
     public Transaction getTransactions(String id) {
        Transaction t = new Transaction();
        String sql = "SELECT * FROM transactions WHERE id =? ";
        try {
            Connection conn = new connectMysql().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                
                t.setId(rs.getString("id"));
                t.setUserId(rs.getInt("userId"));
                t.setCategoryId(rs.getString("categoryId"));
                t.setMoney(rs.getDouble("money"));
                t.setDate(rs.getDate("date"));
                t.setDescription(rs.getString("description"));
                
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }
}

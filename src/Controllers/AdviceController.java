package Controllers;

import Connect.connectMysql;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdviceController {

   
    public String getBudgetsByMonth(int userId, String month) {
        StringBuilder result = new StringBuilder();
        String sql = "SELECT * FROM budget WHERE userId = ? AND month = ?";

        try (Connection conn = new connectMysql().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setString(2, month);

            ResultSet rs = ps.executeQuery();
            result.append("Danh sách ngân sách của User ")
                  .append(userId).append(" tháng ").append(month).append(":\n");

            while (rs.next()) {
                result.append("- ID: ").append(rs.getString("id"))
                      .append(" | Danh mục: ").append(rs.getString("categoryId"))
                      .append(" | Hạn mức: ")
                      .append(String.format("%.0f", rs.getDouble("limitAmount")))
                      .append("đ\n");
            }

            rs.close();

            if (result.toString().endsWith(":\n")) {
                return "Không có dữ liệu ngân sách cho User " + userId + " trong tháng " + month;
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return "Lỗi khi truy vấn dữ liệu ngân sách: " + e.getMessage();
        }

        
        return new String(result.toString().getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
    }

    
    public String getTransactionsByMonth(int userId, String monthYear) {
        StringBuilder result = new StringBuilder();
        String sql = "SELECT * FROM transactions WHERE userId = ? AND DATE_FORMAT(date, '%m/%Y') = ?";

        try (Connection conn = new connectMysql().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setString(2, monthYear);

            ResultSet rs = ps.executeQuery();
            result.append("Danh sách giao dịch của User ")
                  .append(userId).append(" tháng ").append(monthYear).append(":\n");

            while (rs.next()) {
                result.append("- ID: ").append(rs.getString("id"))
                      .append(" | Danh mục: ").append(rs.getString("categoryId"))
                      .append(" | Số tiền: ")
                      .append(String.format("%.0f", rs.getDouble("money"))).append("đ")
                      .append(" | Ngày: ").append(rs.getDate("date"))
                      .append(" | Mô tả: ").append(rs.getString("description"))
                      .append("\n");
            }

            rs.close();

            if (result.toString().endsWith(":\n")) {
                return "Không có giao dịch nào cho User " + userId + " trong tháng " + monthYear;
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return "Lỗi khi truy vấn dữ liệu giao dịch: " + e.getMessage();
        }

        
        return new String(result.toString().getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
    }
}

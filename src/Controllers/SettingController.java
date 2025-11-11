package Controllers;

import Connect.connectMysql;
import Models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SettingController {

    
    public User getUserInfo(int userId) {
        User user = null;
        String sql = """
            SELECT id, username, password, fullname, email, phone, address, gender, birthday, created_at, updated_at
            FROM users WHERE id = ?
        """;

        try{
            Connection conn = new connectMysql().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setFullname(rs.getString("fullname"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setGender(rs.getString("gender"));
                user.setBirthday(rs.getDate("birthday"));
                user.setCreatedAt(rs.getTimestamp("created_at"));
                user.setUpdatedAt(rs.getTimestamp("updated_at"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            System.getLogger(SettingController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return user;
    }

   
    public boolean updateUserInfo(User user)  {
        String sql = """
            UPDATE users 
            SET fullname = ?, email = ?, phone = ?, address = ?, gender = ?, 
                password = ?, updated_at = NOW()
            WHERE id = ?
        """;

        try{
            Connection conn = new connectMysql().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getFullname());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            ps.setString(4, user.getAddress());
            ps.setString(5, user.getGender());
           
            ps.setString(6, user.getPassword());
            ps.setInt(7, user.getId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}

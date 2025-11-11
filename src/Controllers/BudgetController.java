
package Controllers;
import java.sql.Connection;
import Connect.connectMysql;
import Models.Budget;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BudgetController {
    public String generateNextBudgetId() {
        String nextId = "BUD0001"; 
        String sql = "SELECT id FROM budget ORDER BY id DESC LIMIT 1";

        try {
            Connection conn = new connectMysql().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String lastId = rs.getString("id"); 
                int lastNumber = Integer.parseInt(lastId.substring(3));
                nextId = String.format("BUD%04d", lastNumber + 1);
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return nextId;
    }
    public void createBudget(Budget budget){
    String sql="INSERT INTO budget(id,userId,categoryId,month,limitAmount) values(?,?,?,?,?)";
    try{
    Connection conn= new connectMysql().getConnection();
    PreparedStatement create=conn.prepareStatement(sql);
    create.setString(1, String.valueOf(budget.getId()));
    create.setString(2, String.valueOf(budget.getUserId()));
    create.setString(3,  String.valueOf(budget.getCategoryId()));
    create.setString(4,  String.valueOf(budget.getMonth()));
    create.setString(5,  String.valueOf(budget.getLimitAmount()));
    create.executeUpdate();
    }catch(Exception e){
        e.printStackTrace();
    }
}
    public ArrayList<Budget> showCategory(int userId){
        ArrayList<Budget> list = new ArrayList<>();
        String sql="select * from budget where userId=?";
        try{
            Connection conn=new connectMysql().getConnection();
            PreparedStatement show=conn.prepareStatement(sql);
            show.setInt(1, userId);
            ResultSet result=show.executeQuery();
            while(result.next()){
            Budget budget = new Budget();
            budget.setId(result.getString("id"));
            budget.setUserId(result.getInt("userId"));
            budget.setCategoryId(result.getString("categoryId"));
            budget.setMonth(result.getString("month"));
            budget.setLimitAmount(result.getDouble("limitAmount"));
            list.add(budget);
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }
    public void editBudget(Budget budget){
        String sql="update budget set categoryId=?,month=?,limitAmount=? where id=? and userId=?";
        try{
            Connection conn=new connectMysql().getConnection();
            PreparedStatement edit=conn.prepareStatement(sql);
            edit.setString(1, String.valueOf(budget.getCategoryId()));
            edit.setString(2,budget.getMonth());
            edit.setString(3, String.valueOf(budget.getLimitAmount()));
            edit.setString(4,String.valueOf(budget.getId()));
            edit.setString(5,String.valueOf(budget.getUserId()));
            edit.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }   
    public void deleteBudget(Budget budget){
        String sql="delete from Budget where id=? and userId=?";
        try{
            Connection conn=new connectMysql().getConnection();
            PreparedStatement delete=conn.prepareStatement(sql);
            delete.setString(1, budget.getId());
            delete.setInt(2, budget.getUserId());
            delete.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public Budget getBudgetById(String id) {
    Budget budget = null;
    String sql = "SELECT * FROM budget WHERE id = ?";
    
    try (Connection conn = new connectMysql().getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            budget = new Budget();
            budget.setId(rs.getString("id"));
            budget.setUserId(rs.getInt("userId"));
            budget.setCategoryId(rs.getString("categoryId"));
            budget.setMonth(rs.getString("month"));
            budget.setLimitAmount(rs.getDouble("limitAmount"));
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return budget; 
}
    public double getBudgetLimit(int userId, String categoryId, String monthYear) {
    double limitAmount = 0.0;
    String sql = """
        SELECT limitAmount
        FROM budget
        WHERE userId = ?
          AND categoryId = ?
          AND month = ?
        LIMIT 1
    """;

    try (Connection conn = new connectMysql().getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, userId);
        ps.setString(2, categoryId);
        ps.setString(3, monthYear);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            limitAmount = rs.getDouble("limitAmount");
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return limitAmount;
}
}


package Controllers;
import java.sql.Connection;
import Connect.connectMysql;
import Models.Budget;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BudgetController {
    public void createBudget(Budget budget){
    String sql="INSERT INTO budget(userId,categoryId,month,limitAmount) values(?,?,?,?)";
    try{
    Connection conn= new connectMysql().getConnection();
    PreparedStatement create=conn.prepareStatement(sql);
    create.setString(1, String.valueOf(budget.getUserId()));
    create.setString(2,  String.valueOf(budget.getCategoryId()));
    create.setString(3,  String.valueOf(budget.getMonth()));
    create.setString(4,  String.valueOf(budget.getLimitAmount()));
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
            budget.setId(result.getInt("id"));
            budget.setUserId(result.getInt("userId"));
            budget.setCategoryId(result.getInt("categoryId"));
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
            delete.setInt(1, budget.getId());
            delete.setInt(2, budget.getUserId());
            delete.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

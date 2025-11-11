package Controllers;

import java.sql.Connection;
import Connect.connectMysql;
import Models.Category;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
/**
 *
 * @author DINHHUNG
 */
public class CategoryController {
    public String generateNextCategoryId() {
    String nextId = "CATE0001"; 
    String sql = "SELECT id FROM category ORDER BY id DESC LIMIT 1";
    try {
        Connection conn = new connectMysql().getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            String lastId = rs.getString("id"); 
            int lastNumber = Integer.parseInt(lastId.substring(4)); 
            nextId = String.format("CATE%04d", lastNumber + 1);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return nextId;
}
    public void createCategory(Category cate){
    String sql="INSERT INTO Category(id,userId,name,type) values(?,?,?,?)";
    try{
    Connection conn= new connectMysql().getConnection();
    PreparedStatement create=conn.prepareStatement(sql);
    create.setString(1, cate.getId());
    create.setString(2, String.valueOf(cate.getUserId()));
    create.setString(3, cate.getName());
    create.setString(4, cate.getType());
    create.executeUpdate();
    }catch(Exception e){
        e.printStackTrace();
    }
}
    public ArrayList<Category> showCategory(int userId){
        ArrayList<Category> list = new ArrayList<>();
        String sql="select * from Category where userId=?";
        try{
            Connection conn=new connectMysql().getConnection();
            PreparedStatement show=conn.prepareStatement(sql);
            show.setInt(1, userId);
            ResultSet result=show.executeQuery();
            while(result.next()){
            Category cate = new Category();
            cate.setId(result.getString("id"));
            cate.setName(result.getString("name"));
            cate.setType(result.getString("type"));
            list.add(cate);
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }
    public void editCategory(Category cate){
        String sql="update Category set name=?,type=? where id=? and userId=?";
        try{
            Connection conn=new connectMysql().getConnection();
            PreparedStatement edit=conn.prepareStatement(sql);
            edit.setString(1, cate.getName());
            edit.setString(2, cate.getType());
            edit.setString(3, String.valueOf(cate.getId()));
            edit.setInt(2,cate.getUserId());
            edit.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }   
    public void deleteCategory(Category cate){
        String sql="delete from Category where id=? and userId=?";
        try{
            Connection conn=new connectMysql().getConnection();
            PreparedStatement delete=conn.prepareStatement(sql);
            delete.setString(1, cate.getId());
            delete.setInt(2,cate.getUserId());
            delete.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }   
    public Category getCategoryById(String categoryId) {
        Category category = null;
        String sql = "SELECT * FROM category WHERE id = ?";

        try (Connection conn = new connectMysql().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, categoryId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                category = new Category();
                category.setId(rs.getString("id"));
                category.setUserId(rs.getInt("userId"));
                category.setName(rs.getString("name"));
                category.setType(rs.getString("type"));
               
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return category;
    }

}

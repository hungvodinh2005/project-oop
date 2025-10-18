/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
    public void createCategory(Category cate){
    String sql="INSERT INTO Category(name,type) values(?,?)";
    try{
    Connection conn= new connectMysql().getConnection();
    PreparedStatement create=conn.prepareStatement(sql);
    create.setString(1, cate.getName());
    create.setString(2, cate.getType());
    create.executeUpdate();
    }catch(Exception e){
        e.printStackTrace();
    }
}
    public ArrayList<Category> showCategory(){
        ArrayList<Category> list = new ArrayList<>();
        String sql="select * from Category ";
        try{
            Connection conn=new connectMysql().getConnection();
            PreparedStatement show=conn.prepareStatement(sql);
            ResultSet result=show.executeQuery();
            while(result.next()){
            Category cate = new Category();
            cate.setId(result.getInt("id"));
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
        String sql="update Category set name=?,type=? where id=?";
        try{
            Connection conn=new connectMysql().getConnection();
            PreparedStatement edit=conn.prepareStatement(sql);
            edit.setString(1, cate.getName());
            edit.setString(2, cate.getType());
            edit.setString(3, String.valueOf(cate.getId()));
            edit.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }   
    public void deleteCategory(Category cate){
        String sql="delete from Category where id=?";
        try{
            Connection conn=new connectMysql().getConnection();
            PreparedStatement delete=conn.prepareStatement(sql);
            delete.setInt(1, cate.getId());
            delete.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }   
    
}

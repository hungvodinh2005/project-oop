/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import java.sql.Connection;
import Connect.connectMysql;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 *
 * @author DINHHUNG
 */
public class LoginController {
    public boolean check(String user,String password){
        String sql="SELECT * FROM users WHERE username = ? AND password = ?";
        try{
            Connection conn=new connectMysql().getConnection();
            PreparedStatement test=conn.prepareStatement(sql);
            test.setString(1, user);
            test.setString(2, password);
            ResultSet rs = test.executeQuery();
            
            return rs.next();
            
        }catch(Exception e){
            System.out.println("error");
        }
        return true;
    }
    
}

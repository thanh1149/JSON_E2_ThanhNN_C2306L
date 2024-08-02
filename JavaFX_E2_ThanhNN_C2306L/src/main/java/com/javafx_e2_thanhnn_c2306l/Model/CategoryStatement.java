package com.javafx_e2_thanhnn_c2306l.Model;

import com.javafx_e2_thanhnn_c2306l.Entity.Category;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryStatement {
    private static final Connection conn = MySQLConnection.getConnection();

    public static void insert(Category category){
        try {
            String sql = "INSERT INTO `tblcategory`(cat_name,cat_description) VALUES(?,?)";
            PreparedStatement pst = conn.prepareStatement(sql);
//            pst.setInt(1,category.getId());
            pst.setString(1,category.getName());
            pst.setString(2,category.getDescription());
            pst.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void update(Category category){
        try {
            String sql = "UPDATE tblcategory SET cat_name = ?, cat_description = ? WHERE id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(2, category.getName());
            pst.setString(3, category.getDescription());
            pst.setInt(1,category.getId());
            pst.execute();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void delete(Category category){
        try {
            String sql = "DELETE FROM tblcategory WHERE id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,category.getId());
            pst.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public static ObservableList<Category> getAll(){
        ObservableList<Category> categoryList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM tblcategory";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (((ResultSet) rs).next()){
                Category category = new Category();
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("cat_name"));
                category.setDescription(rs.getString("cat_description"));
                categoryList.add(category);
            }
            rs.close();
            pst.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return categoryList;
    }

    public static Category getById(int id){
        Category category = new Category();
        try {
            String sql = "SELECT * FROM tblcategory WHERE id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,id);
            pst.executeQuery();
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("cat_name"));
                category.setDescription(rs.getString("cat_description"));
            }
        }catch (SQLException e ){
            e.printStackTrace();
        }
        return category;
    }

}

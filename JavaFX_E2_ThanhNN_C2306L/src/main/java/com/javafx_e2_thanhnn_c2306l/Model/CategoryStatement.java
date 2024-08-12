package com.javafx_e2_thanhnn_c2306l.Model;

import com.javafx_e2_thanhnn_c2306l.Entity.Category;
import com.javafx_e2_thanhnn_c2306l.IGeneric.IService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class CategoryStatement implements IService<Category,Integer> {
    private static final Connection conn = MySQLConnection.getConnection();
    private static final ObservableList<Category> categoryList = FXCollections.observableArrayList();


    @Override
    public void add(Category category) {
        try {
            String sql = "INSERT INTO `tblcategory`(cat_name,cat_description) VALUES(?,?)";
            PreparedStatement pst = conn.prepareStatement(sql);
//            pst.setInt(1,category.getId());
            pst.setString(1,category.getName());
            pst.setString(2,category.getDescription());
            pst.executeUpdate();

            categoryList.add(0,category);
//            categoryList.stream()
//                    .sorted(Comparator.comparing(Category::getId).reversed())
//                    .collect(Collectors.toList());

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Category category) {
        try {
            String sql = "DELETE FROM tblcategory WHERE id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,category.getId());
            pst.executeUpdate();

            categoryList.removeIf(c -> c.getId() == category.getId());
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Category> findByID(Integer id) {
        Category category = null;
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
        return Optional.ofNullable(category);
    }

    @Override
    public ObservableList<Category> findAll() {
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

    @Override
    public void edit(Category category) {
        try {
            String sql = "UPDATE tblcategory SET cat_name = ?, cat_description = ? WHERE id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, category.getName());
            pst.setString(2, category.getDescription());
            pst.setInt(3,category.getId());
            pst.execute();

            categoryList.stream()
                    .filter(c -> c.getId() == category.getId()).findFirst()
                    .ifPresent(c ->{
                        c.setName(category.getName());
                        c.setDescription(category.getDescription());
                    });

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    //method search by name
    public static ObservableList<Category> findByName(String name){
        ObservableList<Category> searchResult = FXCollections.observableArrayList();
        try {
            String sql = "SELECT id,cat_name,cat_description FROM tblcategory WHERE cat_name LIKE ? ";
            PreparedStatement pst =  conn.prepareStatement(sql);
            pst.setString(1,"%" + name + "%");
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
               int id = rs.getInt("id");
               String cat_name = rs.getString("cat_name");
               String cat_des = rs.getString("cat_description");
               Category category = new Category(id,cat_name,cat_des);
                searchResult.add(category);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return searchResult;
    }

}

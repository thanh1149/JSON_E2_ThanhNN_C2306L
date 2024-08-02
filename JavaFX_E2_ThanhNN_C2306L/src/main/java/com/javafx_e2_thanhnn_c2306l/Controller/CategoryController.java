package com.javafx_e2_thanhnn_c2306l.Controller;

import com.javafx_e2_thanhnn_c2306l.Entity.Category;
import com.javafx_e2_thanhnn_c2306l.Model.CategoryStatement;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class CategoryController implements Initializable {
    @FXML
    private TableColumn<Category, Integer> cid;
    @FXML
    private TableColumn<Category, String> cname;
    @FXML
    private TableColumn<Category, String> cdescription;
    @FXML
    private TextField txtName;
    @FXML
    private TextArea txtDescription;
    @FXML
    private Button btnNewCategory;
    @FXML
    private Button btnEditCategory;
    @FXML
    private Button btnDeleteCategory;
    @FXML
    private TableView<Category> tableCategory = new TableView<Category>();
    private ObservableList<Category> categories;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //list category
        categories = CategoryStatement.getAll();
        tableCategory.setEditable(true);
        tableCategory.getColumns().get(0).setVisible(true);
//        cid.setText("ID");
        cid.setCellValueFactory(new PropertyValueFactory<Category, Integer>("id"));
        cname.setCellValueFactory(new PropertyValueFactory<Category, String>("name"));
        cdescription.setCellValueFactory(new PropertyValueFactory<Category, String>("description"));
        tableCategory.setItems(categories);
        tableCategory.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showCategoryDetails(newValue));

        //add new category
        btnNewCategory.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Category category = new Category();
                category.setName(txtName.getText());
                category.setDescription(txtDescription.getText());

                CategoryStatement.insert(category);
                tableCategory.getItems().clear();
                categories = CategoryStatement.getAll();
                tableCategory.setItems(categories);
                tableCategory.refresh();
            }
        });

        //update category
        btnEditCategory.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Category getCategory = tableCategory.getSelectionModel().getSelectedItem();
                if (getCategory != null) {
                    getCategory.setName(txtName.getText());
                    getCategory.setDescription(txtDescription.getText());
                    try {
                        CategoryStatement.update(getCategory);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        //delete category
        btnDeleteCategory.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
    }

    private void showCategoryDetails(Category category) {
        if (category != null) {
            // Hiển thị chi tiết category trong các TextField
            txtName.setText(category.getName());
            txtDescription.setText(category.getDescription());
        } else {
            txtName.clear();
            txtDescription.clear();
        }


    }
}

package lab2.ui;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import java.sql.ResultSet;
import static java.lang.System.out;

public class AddCategoryDialog {
    public Sql sql;
    @FXML TextField tf_CategoryName;

    public void init(Sql sql) {
        this.sql = sql;
    }

    @FXML private void btn_Add_Click(Event e) {
        // if <category_name> exists: msbox, don't add, don't close
        // else: add category, close
        try {
            String categoryName = tf_CategoryName.getText();
            ResultSet rs = sql.getResultSet("SELECT 1 FROM Category WHERE Name='"+categoryName+"'");
            if (rs.next()) {
                //out.println("record "+categoryName+" is already exists");
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Добавление категории товаров");
                alert.setHeaderText("Ошибка добавления категории");
                alert.setContentText("Категория \""+categoryName+"\" уже существует.");
                alert.showAndWait();
            }
            else {
                //out.println("category "+categoryName+" was added");
                sql.query("INSERT INTO Category (Name) VALUES ('"+categoryName+"');");
            }
        }
        catch (Exception ex) {
            out.println(ex.getMessage());
        }


        boolean exists;
    }
}

/*
 * variant 7
 * */

package lab2_old.ui;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.util.Callback;

public class Lab2UI extends Application {
    private static Sql sql;
    @FXML TableView tv_Table;

    public static void main(String[] args) throws Exception {
        sql = new Sql();
        Application.launch(args);
    }

    @FXML private void menuItem_TABLE_View_Click(ActionEvent e) throws Exception {
        //TableView_Table.getItems().clear();
        tv_Table.getColumns().clear();

        String id = ((MenuItem)e.getSource()).getId();
        String tableName = "";
        if (id.equals("MenuItem_Categories_View"))
            tableName="Category";
        else if (id.equals("MenuItem_Products_View"))
            tableName="Product";

        String[] columnNames = sql.getColumnNames(tableName);
        ObservableList data = sql.getTableData(tableName);

        // add columns
        for(int i=0 ; i<columnNames.length; i++) {
            final int j = i;
            TableColumn col = new TableColumn(columnNames[i]);
            col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param ->
                    new SimpleStringProperty(param.getValue().get(j).toString())
            );
            tv_Table.getColumns().addAll(col);
        }

        // add data to TableView
        tv_Table.setItems(data);
    }

    @FXML private void menuItem_Categories_Add_Click(Event e) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddCategoryDialog.fxml"));
        Parent parent = fxmlLoader.load();
        AddCategoryDialog dialog = fxmlLoader.<AddCategoryDialog>getController();
        dialog.init(sql);

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML private void menuItem_TABLE_Add_Click(Event e) throws Exception {
        /*FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddCategoryDialog.fxml"));
        Parent parent = fxmlLoader.load();
        AddRecordDialog dialog = fxmlLoader.<AddRecordDialog>getController();

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();*/
    }

    /*private void messageBox() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message Here...");
        alert.setHeaderText("Look, an Information Dialog");
        alert.setContentText("I have a great message for you!");
        alert.showAndWait();
    }*/

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Lab2UI.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Lab 2");
        stage.setScene(scene);

        stage.show();
        stage.setAlwaysOnTop(true);
        stage.setAlwaysOnTop(false);
    }
}
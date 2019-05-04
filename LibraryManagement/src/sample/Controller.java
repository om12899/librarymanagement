package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private ComboBox<String> type;

    @FXML
    private Button btnabtDev;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnClear;

    @FXML
    private TextField txtSearch;

    @FXML
    private Button btnAdminLogin;

    @FXML
    private Button btnIssue;

    @FXML
    private Button btnReturn;

    @FXML
    private RadioButton btnTitle;

    @FXML
    private RadioButton btnAuthor;

    @FXML
    public void issuePage(ActionEvent e) {
        try {
            Stage secondaryStage = new Stage();
            Parent root1 = FXMLLoader.load(getClass().getResource("issueBook.fxml"));
            secondaryStage.setTitle("Issue Book");
            secondaryStage.setScene(new Scene(root1, 600, 400));
            secondaryStage.show();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

    }

    @FXML
    public void returnPage(ActionEvent e) {
        try {
            Stage secondaryStage = new Stage();
            Parent root1 = FXMLLoader.load(getClass().getResource("returnBook.fxml"));
            secondaryStage.setTitle("Return Book");
            secondaryStage.setScene(new Scene(root1, 600, 400));
            secondaryStage.show();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

    }

    @FXML
    public void loginPage(ActionEvent e) {
        try {
            Stage secondaryStage = new Stage();
            Parent root1 = FXMLLoader.load(getClass().getResource("adminLogin.fxml"));
            secondaryStage.setTitle("Admin Login");
            secondaryStage.setScene(new Scene(root1, 600, 400));
            secondaryStage.show();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

    }

    @FXML
    public void searchPage(ActionEvent e) throws SQLException {
        String searchQuery = txtSearch.getText();
        ResultSet rs;
        if (!searchQuery.isEmpty()) {
            String dept = type.getValue();
            if(!dept.equals("All")) {
                if (btnTitle.isSelected()) {
                    String sqlSearch = "SELECT * FROM books WHERE name LIKE '%" + searchQuery + "%' and department='" + dept + "'";
                    rs = Datasource.dbExecute(sqlSearch);
                } else {
                    String sqlSearch = "SELECT * FROM books where author LIKE '%" + searchQuery + "%' and department='" + dept + "'";
                    rs = Datasource.dbExecute(sqlSearch);
                }
            }
            else {
                if (btnTitle.isSelected()) {
                    String sqlSearch = "SELECT * FROM books WHERE name LIKE '%" + searchQuery + "%'";
                    rs = Datasource.dbExecute(sqlSearch);
                } else {
                    String sqlSearch = "SELECT * FROM books where author LIKE '%" + searchQuery + "%'";
                    rs = Datasource.dbExecute(sqlSearch);
                }
            }
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("search.fxml"));
                Stage secondaryStage = new Stage();
                Parent root1 = loader.load();
                searchController controller = loader.getController();
                controller.populateTable(rs);
                secondaryStage.setTitle("Result of search");
                secondaryStage.setScene(new Scene(root1, 600, 400));
                secondaryStage.show();

            } catch (Exception exception) {}
        }
        else {
            Stage secondaryStage = new Stage();
            Parent root1 = null;
            try {
                root1 = FXMLLoader.load(getClass().getResource("search_error.fxml"));
            } catch (IOException f) {
            }
            secondaryStage.setTitle("Error");
            secondaryStage.setScene(new Scene(root1, 316, 66));
            secondaryStage.show();
        }
    }

    @FXML
    public void clearInput(ActionEvent e) {
        txtSearch.clear();
    }

    @FXML
    public void abtDev(ActionEvent e){
        try {
            Stage secondaryStage=new Stage();
            Parent root1 = FXMLLoader.load(getClass().getResource("about.fxml"));
            secondaryStage.setTitle("About Developers");
            secondaryStage.setScene(new Scene(root1, 600, 400));
            secondaryStage.show();
        } catch (Exception exception){}
    }


    ObservableList<String> types = FXCollections.observableArrayList("All","Computer","Mechanical","Civil","Electronics","Electrical","Production");
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        type.setItems(types);
        type.getSelectionModel().selectFirst();
    }

}
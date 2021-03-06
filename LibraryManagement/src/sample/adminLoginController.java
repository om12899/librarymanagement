package sample;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class adminLoginController {

    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button btnLogin;

    @FXML
    public void backScene() {
        Stage stage = (Stage) btnLogin.getScene().getWindow();
        stage.close();
    }
    public void setBtnLogin(ActionEvent event) {
        String username  = txtUsername.getText();
        String password = txtPassword.getText();
        if(username.equals("admin") && password.equals("admin")) {
            try {
                Parent root1 = FXMLLoader.load(getClass().getResource("adminPanel.fxml"));
                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                window.setTitle("Admin Panel");
                window.setScene(new Scene(root1, 730, 400));
                window.show();
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        }
        else{
            Stage secondaryStage = new Stage();
            Parent root1 = null;
            try {
                root1 = FXMLLoader.load(getClass().getResource("loginError.fxml"));
            } catch (IOException f) {
            }
            secondaryStage.setTitle("Error");
            secondaryStage.setScene(new Scene(root1, 316, 66));
            secondaryStage.show();
        }
    }
}

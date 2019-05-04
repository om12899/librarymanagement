package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class IssueDoneController {

    public Button ok;

    public void okfun(ActionEvent actionEvent) {
        Stage stage=(Stage) ok.getScene().getWindow();
        stage.close();
    }
}

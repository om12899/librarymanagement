package sample;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ReturnBookController {
    @FXML private Button btnReset;
    @FXML private Button btnScan;
    @FXML private Button btnReturn;
    @FXML private TextField txtBookId;
    @FXML private TextField txtBookName;
    @FXML private TextField txtStudentId;
    @FXML private TextField txtFine;

    public void backScene() {
        Stage stage = (Stage) btnReset.getScene().getWindow();
        stage.close();
    }

    public void setBtnReset(ActionEvent e ) {
        txtBookId.setText("");
        txtBookName.setText("");
        txtStudentId.setText("");
        txtFine.setText("");
    }

    public void setBtnReturn(ActionEvent e) throws SQLException {

        String id = txtBookId.getText();
        if(!id.isEmpty()) {
            String sql = "DELETE FROM issued WHERE bookId='" + id + "'";
            Datasource.executeQuery(sql);
            String sql1 = "UPDATE books SET status='On Shelf' where bookId='" + id + "'";
            Datasource.executeQuery(sql1);
            Stage secondaryStage = new Stage();
            Parent root1 = null;
            try {
                root1 = FXMLLoader.load(getClass().getResource("bookReturnDone.fxml"));
            } catch (IOException f) {
            }
            secondaryStage.setTitle("Error");
            secondaryStage.setScene(new Scene(root1, 316, 66));
            secondaryStage.show();
            btnReset.fire();
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

    public void setBtnScan() throws SQLException, ParseException {
        String issueDate;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        String today=dtf.format(now);
        Webcam web = Webcam.getDefault();
        web.setViewSize(new Dimension(320,240));
        WebcamPanel webcamPanel = new WebcamPanel(web);
        webcamPanel.setMirrored(false);
        JFrame jFrame = new JFrame();
        jFrame.add(webcamPanel);
        jFrame.pack();
        jFrame.setVisible(true);
        jFrame.setLocationRelativeTo(null);
        jFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                web.close();
                jFrame.dispose();


            }
        });
        do {
            try {
                BufferedImage image = web.getImage();
                LuminanceSource source = new BufferedImageLuminanceSource(image);
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                Result result = new MultiFormatReader().decode(bitmap);
                if(result.getText() != null) {
                    txtBookId.setText(result.getText());
                    jFrame.setVisible(false);
                    jFrame.dispose();
                    web.close();
                    break;
                }

            }catch (NotFoundException z ) {}

        } while(true);
        String id = txtBookId.getText();
        ResultSet resultSet= Datasource.dbExecute("SELECT * FROM issued WHERE bookId='"+id+"'");
        if (!resultSet.isClosed()) {
            txtBookName.setText(resultSet.getString("name"));
            txtStudentId.setText(resultSet.getString("studentId"));
            issueDate = resultSet.getString("dateofissue");
            java.util.Date d1= new SimpleDateFormat("dd/MM/yyyy").parse(issueDate);
            Date d2 = (new SimpleDateFormat("dd/MM/yyyy").parse(today));
            System.out.println(d1);
            System.out.println(d2);
            long days = (d2.getTime()-d1.getTime())/(1000*60*60*24);
            int fine = (int)days - 21;
            if(fine>0) {
                txtFine.setText(Integer.toString(fine));
            }
            else txtFine.setText("0");
        }
        else {
            Stage secondaryStage=new Stage();
            Parent root1 = null;
            try {
                root1 = FXMLLoader.load(getClass().getResource("bookReturnError.fxml"));
            } catch (IOException e) {}
            secondaryStage.setTitle("Error");
            secondaryStage.setScene(new Scene(root1, 316, 66));
            secondaryStage.show();
            btnReset.fire();
        }
    }
}

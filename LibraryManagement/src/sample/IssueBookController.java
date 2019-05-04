package sample;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ResourceBundle;

public class IssueBookController extends JFrame implements Initializable{

    @FXML private Button btnIssue;
    @FXML private Button btnReset;
    @FXML private Button btnScanBook;
    @FXML private Button btnScanStudent;
    @FXML private TextField txtBookId;
    @FXML private TextField txtBookName;
    @FXML private TextField txtStudentId;
    @FXML private TextField txtStudentName;
    @FXML private TextField txtDate;

    @FXML
    public void setBtnIssue() throws SQLException{
        String name = txtBookName.getText();
        String id = txtBookId.getText();
        String studentId = txtStudentId.getText();
        String doi = txtDate.getText();
        if(!name.isEmpty() && !id.isEmpty() && !studentId.isEmpty()) {
            String sql = "INSERT INTO issued values('" + id + "','" + name + "','" + studentId + "','" + doi + "')";
            Datasource.executeQuery(sql);
            String sql1 = "UPDATE books SET status='Issued' where bookId='" + id + "'";
            Datasource.executeQuery(sql1);
            Stage secondaryStage=new Stage();
            Parent root1 = null;
            try {
                root1 = FXMLLoader.load(getClass().getResource("issue_done.fxml"));
            } catch (IOException e) {}
            secondaryStage.setTitle("Error");
            secondaryStage.setScene(new Scene(root1, 316, 66));
            secondaryStage.show();
            btnReset.fire();
        }
        else{
            Stage secondaryStage=new Stage();
            Parent root1 = null;
            try {
                root1 = FXMLLoader.load(getClass().getResource("search_error.fxml"));
            } catch (IOException e) {}
            secondaryStage.setTitle("Error");
            secondaryStage.setScene(new Scene(root1, 316, 66));
            secondaryStage.show();
            btnReset.fire();
        }
    }

    public void setBtnReset() {
        txtBookId.setText("");
        txtBookName.setText("");
        txtStudentId.setText("");
        txtStudentName.setText("");
    }

    public void backScene() {
        Stage stage = (Stage) btnReset.getScene().getWindow();
        stage.close();
    }

    public void setBtnScanBook() throws SQLException {
        Webcam web = Webcam.getDefault();
        web.setViewSize(new Dimension(320, 240));
        WebcamPanel webcamPanel = new WebcamPanel(web);
        webcamPanel.setMirrored(false);
        IssueBookController jFrame = new IssueBookController();
        jFrame.add(webcamPanel);
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);

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
                if (result.getText() != null) {
                    txtBookId.setText(result.getText());
                    jFrame.setVisible(false);
                    jFrame.dispose();
                    web.close();
                    break;
                }

            } catch (NotFoundException z) {
            }

        } while (true);
        String txt = txtBookId.getText();
        ResultSet resultSet = Datasource.dbExecute("SELECT name FROM books WHERE bookId='" + txt + "'");
        if (!resultSet.isClosed()) {
            txtBookName.setText(resultSet.getString("name"));
        }
        else {
            Stage secondaryStage=new Stage();
            Parent root1 = null;
            try {
                root1 = FXMLLoader.load(getClass().getResource("bookIdScanError.fxml"));
            } catch (IOException e) {}
            secondaryStage.setTitle("Error");
            secondaryStage.setScene(new Scene(root1, 316, 66));
            secondaryStage.show();
            btnReset.fire();
        }
    }
    public void setBtnScanStudent() throws SQLException{
        Webcam web = Webcam.getDefault();
        web.setViewSize(new Dimension(320,240));
        WebcamPanel webcamPanel = new WebcamPanel(web);
        webcamPanel.setMirrored(false);
        JFrame jFrame = new JFrame();
        jFrame.add(webcamPanel);
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
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
                    txtStudentId.setText(result.getText());
                    jFrame.setVisible(false);
                    jFrame.dispose();
                    web.close();
                    break;
                }

            }catch (NotFoundException z ){}

        } while(true);
        String txt1=txtStudentId.getText();
        ResultSet resultSet= Datasource.dbExecute("SELECT name FROM students WHERE id='"+txt1+"'");
        if (!resultSet.isClosed()) {
            txtStudentName.setText(resultSet.getString("name"));
        }
        else {
            Stage secondaryStage=new Stage();
            Parent root1 = null;
            try {
                root1 = FXMLLoader.load(getClass().getResource("studentIdScanError.fxml"));
            } catch (IOException e) {}
            secondaryStage.setTitle("Error");
            secondaryStage.setScene(new Scene(root1, 316, 66));
            secondaryStage.show();
            btnReset.fire();
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        txtDate.setText(dtf.format(now));
    }
}

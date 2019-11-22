package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Controller {

    @FXML
    private Label label1;
    @FXML
    private Button runeAnalyze;

    public File runeFile;

    @FXML
    public void chooseRuneFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File loc = new File(System.getProperty("user.home") + System.getProperty("file.separator") + "Desktop" + System.getProperty("file.separator") + "Summoners War Exporter Files");
        if (loc.exists()) {
            fileChooser.setInitialDirectory(loc);
        }
        fileChooser.setTitle("Open Resource File");
        runeFile = fileChooser.showOpenDialog(this.runeAnalyze.getScene().getWindow());

        if (runeFile != null) {
            label1.setText("File selected: " + runeFile.getName());
        }
        else {
            label1.setText("No file selected!!!");
        }
    }

    @FXML
    public void showAnalyzedRuneWindow() {
        try {
            if (runeFile != null) {
                Stage stage = (Stage) this.runeAnalyze.getScene().getWindow();
                stage.close();

                Stage userStage = new Stage();
                FXMLLoader FXMLLoader = new FXMLLoader(getClass().getResource("analyze.fxml"));
                //Pane root = userLoader.load(getClass().getResource("analyze.fxml").openStream());
                Parent root = FXMLLoader.load();

                AnalyzeController AnalyzeController = FXMLLoader.getController();
                AnalyzeController.runeFile = runeFile;

                Scene scene = new Scene(root);
                userStage.setScene(scene);
                userStage.setTitle("Rune Seller: Analyzing Runes");
                //userStage.setResizable(false);
                userStage.show();
                AnalyzeController.jsonTest();
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}

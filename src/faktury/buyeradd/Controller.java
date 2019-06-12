package faktury.buyeradd;

import faktury.Database;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private TextField sName, sMail, sAddress, sNip, sTel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void create() throws SQLException {
        Database.addSubjectEntry(Database.getMaxId() + 1, sName.getText(), sMail.getText(), sAddress.getText(), sNip.getText(), sTel.getText());
        Stage stage = (Stage) sName.getScene().getWindow();
        stage.close();
    }
}
package faktury.selleredit;

import faktury.Database;
import javafx.fxml.FXML;
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

    ArrayList<String> list = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            list = Database.getDefaultSeller();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (list.size() > 0) {
            sName.setText(list.get(0));
            sMail.setText(list.get(1));
            sAddress.setText(list.get(2));
            sNip.setText(list.get(3));
            sTel.setText(list.get(4));
        }
    }

    public void update() throws SQLException {
        if(list.size() > 0) {
            Database.updateSubjectEntry(1, sName.getText(), sMail.getText(), sAddress.getText(), sNip.getText(), sTel.getText());
        } else {
            Database.addSubjectEntry(1, sName.getText(), sMail.getText(), sAddress.getText(), sNip.getText(), sTel.getText());
        }
        Stage stage = (Stage) sName.getScene().getWindow();
        stage.close();
    }
}
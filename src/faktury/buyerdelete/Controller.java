package faktury.buyerdelete;

import faktury.Database;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class Controller implements Initializable {

    @FXML
    ListView listView;

    String selected;
    ArrayList<String> list = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            list = Database.getSubjectNames();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for(int i = 1; i < list.size(); i++){
            listView.getItems().add(list.get(i));
        }

    }

    public void delete() throws SQLException {
        selected = listView.getSelectionModel().getSelectedItem().toString();
        Database.deleteByName(selected);
        listView.getItems().remove(selected);
    }
}
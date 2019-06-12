package faktury;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    static GridPane root;
    static List<GridPane> grid = new ArrayList<GridPane>();
    private static int idx_cur = 0;

    @Override
    public void start(Stage primaryStage){
        try {

            Database.createTable();
            Database.addSubjectEntry(2, "Hymel Jadwiga","styrta@gmail.com","Laczna 43, Lipinki Luzyckie", "7402938102", "820492091");
            Database.addSubjectEntry(4, "Hobogoblin","halo@policja.com.pl","ul. Wiejska, Nowy Jork, USA", "1234567890", "987654321");
            Database.addSubjectEntry(1, "Spock","spock@enterprise.com.pl","Kosmos", "9486389201", "800989223");
            Database.addSubjectEntry(3, "Ryszard Lipton","ryszard@lipot.co.uk","Wiktoriańska Anglia", "1526387423", "949212562");

            root = (GridPane)FXMLLoader.load(getClass().getResource("invcreator/sample.fxml"));

            primaryStage.setTitle("jFaktury");

            primaryStage.setScene(new Scene(root, 1280, 800));
            primaryStage.show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static GridPane get_pane(int idx){
       return grid.get(idx);
    }

    public static void set_pane(int idx){
        root.getChildren().remove(grid.get(idx_cur));
        root.getChildren().add(grid.get(idx));
        idx_cur = idx;
    }


    public static void main(String[] args) {
        launch(args);
    }

}

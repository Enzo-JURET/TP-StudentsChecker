import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.List;

public class Main extends Application {

    Stage window;
    BorderPane layout;
    Group root = new Group();
    String result = "rien..";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        window = stage;
        window.setTitle("Demo");

        // To Creating a Observable List
        ObservableList<Eleve> eleves = FXCollections.observableArrayList();

        // READ ALL ELEVES
        EleveDao elevesDao = new EleveDao();
        List<Eleve> ls = elevesDao.getEleves();
        for (Eleve allele : ls) {
            System.out.println(allele);
            eleves.add(allele);
        }

        Label label = new Label();

        // Create a ListView
        ListView<Eleve> listView = new ListView<Eleve>(eleves);
        listView.setStyle("-fx-font-size: 1.5em ;");

        // Only allowed to select single row in the ListView.
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        listView.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                //label.setText("OLD Index: " + oldValue + ",  NEW Index: " + newValue);
                label.setText("Index: " + newValue);
                Number coucou = newValue;
                System.out.println(coucou);
            }
        });

        Button button = new Button();
        button.setText("Regarder la classe");


        button.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                Label secondLabel = new Label("I'm a Label on new Window");

                StackPane secondaryLayout = new StackPane();
                secondaryLayout.getChildren().add(secondLabel);

                Scene secondScene = new Scene(secondaryLayout, 230, 100);

                // New window (Stage)
                Stage newWindow = new Stage();
                newWindow.setTitle("Second Stage");
                newWindow.setScene(secondScene);

                // Set position of second window, related to primary window.
                newWindow.setX(stage.getX() + 200);
                newWindow.setY(stage.getY() + 100);

                newWindow.show();
            }
        });

        stage.setTitle("ListView (o7planning.org)");

        //---Mise en place de grille de placement
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10,10,10,10));
        gridPane.setVgap(8);
        gridPane.setHgap(10);
        gridPane.add(listView, 0,0);
        gridPane.add(label, 0,1);
        gridPane.add(button, 1,0);

        root.getChildren().addAll(gridPane);

        Scene scene = new Scene(root, 470, 780);
        window.setScene(scene);
        window.show();
    }
}

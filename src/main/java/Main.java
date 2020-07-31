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

import java.sql.SQLException;
import java.util.List;

public class Main extends Application {

    Stage window;
    BorderPane layout;
    Group root = new Group();
    Number idClasse = -1;

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
                idClasse = listView.getSelectionModel().getSelectedItem().getIdEleve();
            }
        });

        Button button = new Button();
        button.setText("Voir la page de l'élève");


        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Number comparateur = -1;

                if(idClasse != comparateur) {
                    Eleve ele = null;
                    try {
                        ele = elevesDao.getEleve((Integer) idClasse);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    Label idLabel = new Label("ID : "+ele.getIdEleve());
                    Label prenomLabel = new Label("Prénom : "+ele.getPrenomEleve());
                    Label nomLabel = new Label("Nom : "+ele.getNomEleve());
                    Label dateNaissanceLabel = new Label("Date de naissance : "+ele.getDateNaissance());
                    Label idClasseLabel = new Label("ID Classe : "+ele.getIdClasse());
                    StackPane secondaryLayout = new StackPane();

                    GridPane gridPane = new GridPane();
                    gridPane.setPadding(new Insets(10,10,10,10));
                    gridPane.setVgap(8);
                    gridPane.setHgap(10);
                    gridPane.add(idLabel, 0,0);
                    gridPane.add(prenomLabel, 0,1);
                    gridPane.add(nomLabel, 0,2);
                    gridPane.add(dateNaissanceLabel, 0,3);
                    gridPane.add(idClasseLabel, 0,4);

                    secondaryLayout.getChildren().add(gridPane);

                    Scene secondScene = new Scene(secondaryLayout, 540, 500);
                    // New window (Stage)
                    Stage newWindow = new Stage();
                    newWindow.setTitle("Second Stage");
                    newWindow.setScene(secondScene);
                    // Set position of second window, related to primary window.
                    newWindow.setX(stage.getX() + 200);
                    newWindow.setY(stage.getY() + 100);
                    newWindow.show();
                }
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
        gridPane.add(button, 0,2);

        root.getChildren().addAll(gridPane);

        Scene scene = new Scene(root, 270, 500);
        window.setScene(scene);
        window.show();
    }
}

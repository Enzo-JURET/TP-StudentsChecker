import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class Main extends Application {

    Stage windowConnexion;
    BorderPane layout;
    Group root = new Group();
    Number idEleve = -1;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        windowConnexion = stage;
        windowConnexion.setTitle("Connecter vous !");

        //---Mise en place de grille de placement
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Bienvenue");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
        gridPane.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("E-mail :");
        gridPane.add(userName, 0, 1);

        TextField userTextField = new TextField();
        gridPane.add(userTextField, 1, 1);

        Label pw = new Label("Password:");
        gridPane.add(pw, 0, 2);

        PasswordField pwBox = new PasswordField();
        gridPane.add(pwBox, 1, 2);

        Button buttonLogin = new Button("Sign in");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(buttonLogin);
        gridPane.add(hbBtn, 1, 4);

        root.getChildren().addAll(gridPane);

        Scene sceneConnexion = new Scene(root, 280, 230);
        windowConnexion.setScene(sceneConnexion);
        windowConnexion.show();

        buttonLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Condition qui  est connecté ?
                Stage windowClasses = new Stage();
                windowClasses.setTitle("Liste des classes");

                Button buttonDeconnexion = new Button();
                buttonDeconnexion.setText("se déconnecter");

                Button buttonEleves = new Button();
                buttonEleves.setText("Consulter les élèves de la classe");

                // To Creating a Observable List
                ObservableList<Classe> classes = FXCollections.observableArrayList();

                // READ ALL Classes
                ClasseDao classeDao = new ClasseDao();
                List<Classe> lc = null;
                try {
                    lc = classeDao.getClasses();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                for (Classe clc : lc) {
                    System.out.println(clc.getLibelleClasse());
                    classes.add(clc);
                }

                ListView<Classe> listViewClasse = new ListView(classes);
                listViewClasse.setStyle("-fx-font-size: 1.5em ;");

                // Only allowed to select single row in the ListView.
                listViewClasse.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

                listViewClasse.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        idEleve = listViewClasse.getSelectionModel().getSelectedItem().getIdClasse();
                        System.out.println(idEleve);
                    }
                });

                StackPane layoutClasses = new StackPane();

                //---Mise en place de grille de placement
                GridPane gridPane = new GridPane();
                gridPane.setPadding(new Insets(10, 10, 10, 10));
                gridPane.setVgap(8);
                gridPane.setHgap(10);
                gridPane.add(buttonDeconnexion,0,0);
                gridPane.add(listViewClasse, 0,1);
                gridPane.add(buttonEleves,0,2);

                layoutClasses.getChildren().addAll(gridPane);

                Scene scene = new Scene(layoutClasses, 540, 500);
                windowClasses.setScene(scene);
                windowClasses.show();
                windowConnexion.close();

                buttonDeconnexion.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        windowClasses.close();
                        windowConnexion.show();
                    }
                });

                buttonEleves.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Stage windowEleves = new Stage();
                        windowEleves.setTitle("Liste des élèves de la classe : ?");

                        // To Creating a Observable List
                        ObservableList<Eleve> eleves = FXCollections.observableArrayList();

                        // READ ALL ELEVES
                        EleveDao elevesDao = new EleveDao();
                        List<Eleve> ls = null;
                        try {
                            ls = elevesDao.getEleves();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        for (Eleve allele : ls) {
                            System.out.println(allele);
                            eleves.add(allele);
                        }

                        // Create a ListView
                        ListView<Eleve> listViewEleve = new ListView<Eleve>(eleves);
                        listViewEleve.setStyle("-fx-font-size: 1.5em ;");

                        // Only allowed to select single row in the ListView.
                        listViewEleve.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

                        listViewEleve.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                            @Override
                            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                                idEleve = listViewEleve.getSelectionModel().getSelectedItem().getIdEleve();
                            }
                        });

                        Button buttonRead = new Button();
                        buttonRead.setText("Consulter l'élève");

                        Button buttonUpdate = new Button();
                        buttonUpdate.setText("Modifier l'élève");

                        Button buttonCreate = new Button();
                        buttonCreate.setText("Ajouter un élève");

                        Button buttonDelete = new Button();
                        buttonDelete.setText("Supprimer l'élève");

                        Button buttonRetour = new Button();
                        buttonRetour.setText("Retour à la liste des classe..");

                        windowEleves.setTitle("ListView (o7planning.org)");

                        StackPane layoutAccueil = new StackPane();

                        //---Mise en place de grille de placement
                        GridPane gridPane = new GridPane();
                        gridPane.setPadding(new Insets(10, 10, 10, 10));
                        gridPane.setVgap(8);
                        gridPane.setHgap(10);
                        gridPane.add(buttonRetour, 0, 0);
                        gridPane.add(listViewEleve, 0, 1);
                        gridPane.add(buttonRead, 0, 2);
                        gridPane.add(buttonUpdate, 0, 3);
                        gridPane.add(buttonCreate, 0, 4);
                        gridPane.add(buttonDelete, 0, 5);

                        layoutAccueil.getChildren().addAll(gridPane);

                        Scene scene = new Scene(layoutAccueil, 270, 550);
                        windowEleves.setScene(scene);
                        windowEleves.show();
                        windowClasses.close();

                        buttonRetour.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                windowEleves.close();
                                windowClasses.show();
                            }
                        });

                        buttonRead.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                Number comparateur = -1;

                                if (idEleve != comparateur) {
                                    Eleve ele = null;
                                    try {
                                        ele = elevesDao.getEleve((Integer) idEleve);
                                    } catch (SQLException throwables) {
                                        throwables.printStackTrace();
                                    }
                                    ClasseDao classeDao = new ClasseDao();

                                    Label idLabel = new Label("ID : " + ele.getIdEleve());
                                    Label prenomLabel = new Label("Prénom : " + ele.getPrenomEleve());
                                    Label nomLabel = new Label("Nom : " + ele.getNomEleve());
                                    Label dateNaissanceLabel = new Label("Date de naissance : " + ele.getDateNaissance());
                                    Label idClasseLabel = null;
                                    try {
                                        idClasseLabel = new Label("Classe : " + classeDao.getClasse(ele.getIdClasse()).getLibelleClasse());
                                    } catch (SQLException throwables) {
                                        throwables.printStackTrace();
                                    }
                                    StackPane secondaryLayout = new StackPane();

                                    GridPane gridPane = new GridPane();
                                    gridPane.setPadding(new Insets(10, 10, 10, 10));
                                    gridPane.setVgap(8);
                                    gridPane.setHgap(10);
                                    gridPane.add(idLabel, 0, 0);
                                    gridPane.add(prenomLabel, 0, 1);
                                    gridPane.add(nomLabel, 0, 2);
                                    gridPane.add(dateNaissanceLabel, 0, 3);
                                    gridPane.add(idClasseLabel, 0, 4);

                                    secondaryLayout.getChildren().add(gridPane);

                                    Scene secondScene = new Scene(secondaryLayout, 540, 500);
                                    // New window (Stage)
                                    Stage newWindow = new Stage();
                                    newWindow.setTitle("Consultation du compte de " + listViewEleve.getSelectionModel().getSelectedItem().getPrenomEleve() + " " + listViewEleve.getSelectionModel().getSelectedItem().getNomEleve());
                                    newWindow.setScene(secondScene);
                                    // Set position of second window, related to primary window.
                                    newWindow.setX(stage.getX() + 200);
                                    newWindow.setY(stage.getY() + 100);
                                    newWindow.show();
                                }
                            }
                        });

                        buttonUpdate.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                Number comparateur = -1;
                                if (idEleve != comparateur) {
                                    Label label = new Label("Modification du compte : " + listViewEleve.getSelectionModel().getSelectedItem().getPrenomEleve() + " " + listViewEleve.getSelectionModel().getSelectedItem().getNomEleve());

                                    Button buttonAnnuler = new Button();
                                    buttonAnnuler.setText("Annuler");

                                    Label labelNom = new Label("Nom de l'élève : ");
                                    Label labelPrenom = new Label("Prénom de l'élève : ");
                                    Label labelDateNaissance = new Label("Date de naissance de l'élève : ");
                                    Label labelMail = new Label("E-mail de l'élève : ");
                                    Label labelMdp = new Label("Mot de passe de l'élève : ");

                                    TextArea nomTextArea = new TextArea();
                                    TextArea prenomTextArea = new TextArea();
                                    TextArea dateNaissanceTextArea = new TextArea();
                                    TextArea mailTextArea = new TextArea();
                                    TextArea mdpTextArea = new TextArea();

                                    nomTextArea.setText(listViewEleve.getSelectionModel().getSelectedItem().getNomEleve());
                                    prenomTextArea.setText(listViewEleve.getSelectionModel().getSelectedItem().getPrenomEleve());
                                    dateNaissanceTextArea.setText(listViewEleve.getSelectionModel().getSelectedItem().getDateNaissance());
                                    mailTextArea.setText(listViewEleve.getSelectionModel().getSelectedItem().getMail());
                                    mdpTextArea.setText(listViewEleve.getSelectionModel().getSelectedItem().getMdp());

                                    Button buttonModifier = new Button();
                                    buttonModifier.setText("Modifier");

                                    GridPane gridPane = new GridPane();
                                    gridPane.setPadding(new Insets(10, 10, 10, 10));
                                    gridPane.setVgap(8);
                                    gridPane.setHgap(10);
                                    gridPane.add(label, 0, 0);
                                    gridPane.add(labelNom, 0, 1);
                                    gridPane.add(nomTextArea, 1, 1);
                                    gridPane.add(labelPrenom, 0, 2);
                                    gridPane.add(prenomTextArea, 1, 2);
                                    gridPane.add(labelDateNaissance, 0, 3);
                                    gridPane.add(dateNaissanceTextArea, 1, 3);
                                    gridPane.add(labelMail, 0, 4);
                                    gridPane.add(mailTextArea, 1, 4);
                                    gridPane.add(labelMdp, 0, 5);
                                    gridPane.add(mdpTextArea, 1, 5);
                                    gridPane.add(buttonModifier, 0, 6);
                                    gridPane.add(buttonAnnuler, 1, 6);

                                    StackPane secondaryLayout = new StackPane();
                                    secondaryLayout.getChildren().add(gridPane);


                                    Scene secondScene = new Scene(secondaryLayout, 700, 290);

                                    // New window (Stage)
                                    Stage newWindow = new Stage();
                                    newWindow.setTitle("Modification du compte de " + listViewEleve.getSelectionModel().getSelectedItem().getPrenomEleve() + " " + listViewEleve.getSelectionModel().getSelectedItem().getNomEleve());
                                    newWindow.setScene(secondScene);

                                    // Set position of second window, related to primary window.
                                    newWindow.setX(stage.getX() + 200);
                                    newWindow.setY(stage.getY() + 100);

                                    windowEleves.close();
                                    newWindow.show();

                                    buttonModifier.setOnAction(new EventHandler<ActionEvent>() {
                                        @Override
                                        public void handle(ActionEvent event) {
                                            listViewEleve.getSelectionModel().getSelectedItem().setNomEleve(nomTextArea.getText());
                                            listViewEleve.getSelectionModel().getSelectedItem().setPrenomEleve(prenomTextArea.getText());
                                            listViewEleve.getSelectionModel().getSelectedItem().setDateNaissance(dateNaissanceTextArea.getText());
                                            listViewEleve.getSelectionModel().getSelectedItem().setMail(mailTextArea.getText());
                                            listViewEleve.getSelectionModel().getSelectedItem().setMdp(mdpTextArea.getText());

                                            try {
                                                elevesDao.updateEleve(listViewEleve.getSelectionModel().getSelectedItem());
                                            } catch (SQLException throwables) {
                                                throwables.printStackTrace();
                                            }

                                            listViewEleve.refresh();
                                            newWindow.close();
                                            windowEleves.show();
                                        }
                                    });

                                    buttonAnnuler.setOnAction(new EventHandler<ActionEvent>() {
                                        @Override
                                        public void handle(ActionEvent event) {
                                            listViewEleve.refresh();
                                            newWindow.close();
                                            windowEleves.show();
                                        }
                                    });
                                }
                            }
                        });

                        buttonCreate.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                Label Label = new Label("Creation d'un nouvel élève");

                                Button buttonAnnulerCreation = new Button();
                                buttonAnnulerCreation.setText("Annuler");

                                Button buttonCreerEleve = new Button();
                                buttonCreerEleve.setText("Créer");

                                Label labelNom = new Label("Nom de l'élève : ");
                                Label labelPrenom = new Label("Prénom de l'élève : ");
                                Label labelDateNaissance = new Label("Date de naissance de l'élève : ");
                                Label labelMail = new Label("E-mail de l'élève : ");
                                Label labelMdp = new Label("Mot de passe de l'élève : ");
                                Label labelConfirmerMdp = new Label("Confirmer le mot de passe : ");

                                TextArea nomTextArea = new TextArea();
                                TextArea prenomTextArea = new TextArea();
                                TextArea dateNaissanceTextArea = new TextArea();
                                TextArea mailTextArea = new TextArea();
                                TextArea mdpTextArea = new TextArea();
                                TextArea confirmerMdpTextArea = new TextArea();

                                GridPane gridPane = new GridPane();
                                gridPane.setPadding(new Insets(10, 10, 10, 10));
                                gridPane.setVgap(8);
                                gridPane.setHgap(10);
                                gridPane.add(Label, 0, 0);
                                gridPane.add(labelNom, 0, 1);
                                gridPane.add(nomTextArea, 1, 1);
                                gridPane.add(labelPrenom, 0, 2);
                                gridPane.add(prenomTextArea, 1, 2);
                                gridPane.add(labelDateNaissance, 0, 3);
                                gridPane.add(dateNaissanceTextArea, 1, 3);
                                gridPane.add(labelMail, 0, 4);
                                gridPane.add(mailTextArea, 1, 4);
                                gridPane.add(labelMdp, 0, 5);
                                gridPane.add(mdpTextArea, 1, 5);
                                gridPane.add(labelConfirmerMdp, 0, 6);
                                gridPane.add(confirmerMdpTextArea, 1, 6);
                                gridPane.add(buttonCreerEleve, 0, 7);
                                gridPane.add(buttonAnnulerCreation, 1, 7);

                                StackPane secondaryLayout = new StackPane();
                                secondaryLayout.getChildren().add(gridPane);
                                Scene secondScene = new Scene(secondaryLayout, 700, 350);

                                // New window (Stage)
                                Stage newWindow = new Stage();
                                newWindow.setTitle("Création d'un eleve");
                                newWindow.setScene(secondScene);

                                // Set position of second window, related to primary window.
                                newWindow.setX(stage.getX() + 200);
                                newWindow.setY(stage.getY() + 100);

                                windowEleves.close();
                                newWindow.show();

                                buttonCreerEleve.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent event) {
                                        listViewEleve.refresh();
                                        newWindow.close();
                                        windowEleves.show();
                                    }
                                });

                                buttonAnnulerCreation.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent event) {
                                        listViewEleve.refresh();
                                        newWindow.close();
                                        windowEleves.show();
                                    }
                                });
                            }
                        });

                        buttonDelete.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                Number comparateur = -1;
                                if (idEleve != comparateur) {
                                    Label Label = new Label("Confirmer la suppression du compte de l'élève " + listViewEleve.getSelectionModel().getSelectedItem().getPrenomEleve() + " " + listViewEleve.getSelectionModel().getSelectedItem().getNomEleve() + "...");

                                    Button buttonValider = new Button();
                                    buttonValider.setText("Supprimer");

                                    Button buttonAnnuler = new Button();
                                    buttonAnnuler.setText("Annuler");

                                    GridPane gridPaneSupprimer = new GridPane();
                                    gridPaneSupprimer.setPadding(new Insets(10, 10, 10, 10));
                                    gridPaneSupprimer.setVgap(8);
                                    gridPaneSupprimer.setHgap(10);
                                    gridPaneSupprimer.add(Label, 0, 0);
                                    gridPaneSupprimer.add(buttonValider, 0, 1);
                                    gridPaneSupprimer.add(buttonAnnuler, 1, 1);

                                    StackPane secondaryLayout = new StackPane();
                                    secondaryLayout.getChildren().add(gridPaneSupprimer);
                                    Scene secondScene = new Scene(secondaryLayout, 410, 100);

                                    // New window (Stage)
                                    Stage newWindow = new Stage();
                                    newWindow.setTitle("Suppression du compte de l'élève " + listViewEleve.getSelectionModel().getSelectedItem().getPrenomEleve() + " " + listViewEleve.getSelectionModel().getSelectedItem().getNomEleve());
                                    newWindow.setScene(secondScene);

                                    // Set position of second window, related to primary window.
                                    newWindow.setX(stage.getX() + 200);
                                    newWindow.setY(stage.getY() + 100);

                                    windowEleves.close();
                                    newWindow.show();

                                    buttonValider.setOnAction(new EventHandler<ActionEvent>() {
                                        @Override
                                        public void handle(ActionEvent event) {
                                            listViewEleve.refresh();
                                            newWindow.close();
                                            windowEleves.show();
                                        }
                                    });

                                    buttonAnnuler.setOnAction(new EventHandler<ActionEvent>() {
                                        @Override
                                        public void handle(ActionEvent event) {
                                            listViewEleve.refresh();
                                            newWindow.close();
                                            windowEleves.show();
                                        }
                                    });
                                }
                            }
                        });
                    }
                });
            }
        });
    }
}

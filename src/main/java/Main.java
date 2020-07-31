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
import javafx.scene.paint.Color;
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
    Number idClasse = -1;
    Eleve e;

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

        Label labelmsg = new Label("");
        gridPane.add(labelmsg, 1, 5);

        root.getChildren().addAll(gridPane);

        Scene sceneConnexion = new Scene(root, 280, 230);
        windowConnexion.setScene(sceneConnexion);
        windowConnexion.show();

        buttonLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Condition qui  est connecté ?
                Login log = new Login();

                log.setId(userTextField.getText());
                log.setMdp(pwBox.getText());
                try {
                    log.seConnecter();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                System.out.println(log.getId() + " " + log.getMdp());
                System.out.println(log.isConnecte());
                System.out.println(log.isAdmin());

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
                    classes.add(clc);
                }

                ListView<Classe> listViewClasse = new ListView(classes);
                listViewClasse.setStyle("-fx-font-size: 1.5em ;");

                // Only allowed to select single row in the ListView.
                listViewClasse.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

                listViewClasse.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        idClasse = listViewClasse.getSelectionModel().getSelectedItem().getIdClasse();
                    }
                });

                Button buttonReadClasse = new Button();
                buttonReadClasse.setText("Consulter la classe");

                Button buttonUpdateClasse = new Button();
                buttonUpdateClasse.setText("Modifier la classe");

                Button buttonCreateClasse = new Button();
                buttonCreateClasse.setText("Ajouter une classe");

                Button buttonDeleteClasse = new Button();
                buttonDeleteClasse.setText("Supprimer la classe");

                StackPane layoutClasses = new StackPane();

                //---Mise en place de grille de placement
                GridPane gridPane = new GridPane();
                gridPane.setPadding(new Insets(10, 10, 10, 10));
                gridPane.setVgap(8);
                gridPane.setHgap(10);
                gridPane.add(buttonDeconnexion, 0, 0);
                gridPane.add(listViewClasse, 0, 1);
                gridPane.add(buttonEleves, 0, 2);
                gridPane.add(buttonReadClasse, 0, 3);
                gridPane.add(buttonUpdateClasse, 0, 4);
                gridPane.add(buttonCreateClasse, 0, 5);
                gridPane.add(buttonDeleteClasse, 0, 6);

                layoutClasses.getChildren().addAll(gridPane);

                Scene scene = new Scene(layoutClasses, 290, 550);
                windowClasses.setScene(scene);

                if (log.isConnecte() && log.isAdmin()) {
                    windowClasses.show();
                    windowConnexion.close();
                    buttonReadClasse.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            Number comparateur = -1;

                            if (idClasse != comparateur) {
                                Classe cla = null;
                                try {
                                    cla = classeDao.getClasse((Integer) idClasse);
                                } catch (SQLException throwables) {
                                    throwables.printStackTrace();
                                }
                                ClasseDao classeDao = new ClasseDao();

                                Label idLabelClasse = new Label("ID : " + cla.getIdClasse());
                                Label libelleLabelClasse = new Label("Libelle : " + cla.getLibelleClasse());
                                Label periodeLabelClasse = new Label("Période : " + cla.getPeriodeClasse());

                                StackPane secondaryLayout = new StackPane();

                                GridPane gridPane = new GridPane();
                                gridPane.setPadding(new Insets(10, 10, 10, 10));
                                gridPane.setVgap(8);
                                gridPane.setVgap(8);
                                gridPane.setHgap(10);
                                gridPane.add(idLabelClasse, 0, 0);
                                gridPane.add(libelleLabelClasse, 0, 1);
                                gridPane.add(periodeLabelClasse, 0, 2);

                                secondaryLayout.getChildren().add(gridPane);

                                Scene secondScene = new Scene(secondaryLayout, 540, 500);
                                // New window (Stage)
                                Stage newWindow = new Stage();
                                newWindow.setTitle("Consultation de la classe " + listViewClasse.getSelectionModel().getSelectedItem().getLibelleClasse());
                                newWindow.setScene(secondScene);
                                // Set position of second window, related to primary window.
                                newWindow.setX(stage.getX() + 200);
                                newWindow.setY(stage.getY() + 100);
                                newWindow.show();
                            }
                        }
                    });

                    buttonUpdateClasse.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            Number comparateur = -1;
                            if (idClasse != comparateur) {
                                Label label = new Label("Modification de la classe : " + listViewClasse.getSelectionModel().getSelectedItem().getLibelleClasse());

                                Button buttonAnnulerModificationClasse = new Button();
                                buttonAnnulerModificationClasse.setText("Annuler");

                                Label labelLibelle = new Label("Libelle de la classe : ");
                                Label labelPeriode = new Label("Période de la classe : ");

                                TextArea libelleTextArea = new TextArea();
                                TextArea periodeTextArea = new TextArea();

                                libelleTextArea.setText(listViewClasse.getSelectionModel().getSelectedItem().getLibelleClasse());
                                periodeTextArea.setText(listViewClasse.getSelectionModel().getSelectedItem().getPeriodeClasse());

                                Button buttonModifier = new Button();
                                buttonModifier.setText("Modifier");

                                GridPane gridPane = new GridPane();
                                gridPane.setPadding(new Insets(10, 10, 10, 10));
                                gridPane.setVgap(8);
                                gridPane.setHgap(10);
                                gridPane.add(label, 0, 0);
                                gridPane.add(labelLibelle, 0, 1);
                                gridPane.add(libelleTextArea, 1, 1);
                                gridPane.add(labelPeriode, 0, 2);
                                gridPane.add(periodeTextArea, 1, 2);
                                gridPane.add(buttonModifier, 0, 6);
                                gridPane.add(buttonAnnulerModificationClasse, 1, 6);

                                StackPane secondaryLayout = new StackPane();
                                secondaryLayout.getChildren().add(gridPane);


                                Scene secondScene = new Scene(secondaryLayout, 700, 290);

                                // New window (Stage)
                                Stage newWindow = new Stage();
                                newWindow.setTitle("Modification de la classe : " + listViewClasse.getSelectionModel().getSelectedItem().getLibelleClasse());
                                newWindow.setScene(secondScene);

                                // Set position of second window, related to primary window.
                                newWindow.setX(stage.getX() + 200);
                                newWindow.setY(stage.getY() + 100);

                                windowClasses.close();
                                newWindow.show();

                                buttonModifier.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent event) {
                                        listViewClasse.getSelectionModel().getSelectedItem().setLibelleClasse(libelleTextArea.getText());
                                        listViewClasse.getSelectionModel().getSelectedItem().setPeriodeClasse(periodeTextArea.getText());

                                        try {
                                            classeDao.updateClasse(listViewClasse.getSelectionModel().getSelectedItem());
                                        } catch (SQLException throwables) {
                                            throwables.printStackTrace();
                                        }

                                        listViewClasse.refresh();
                                        newWindow.close();
                                        windowClasses.show();
                                    }
                                });

                                buttonAnnulerModificationClasse.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent event) {
                                        listViewClasse.refresh();
                                        newWindow.close();
                                        windowClasses.show();
                                    }
                                });
                            }
                        }
                    });

                    buttonCreateClasse.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            Label Label = new Label("Creation d'un nouvel élève");

                            Button buttonAnnulerCreationClasse = new Button();
                            buttonAnnulerCreationClasse.setText("Annuler");

                            Button buttonCreerClasse = new Button();
                            buttonCreerClasse.setText("Créer");

                            Label labelInfo = new Label("");
                            Label labelLibelle = new Label("Libelle de la classe : ");
                            Label labelPeriode = new Label("Période (ex: 02 février 2000 -> 2000-02-02) : ");

                            TextArea libelleTextArea = new TextArea();
                            TextArea periodeTextArea = new TextArea();

                            GridPane gridPane = new GridPane();
                            gridPane.setPadding(new Insets(10, 10, 10, 10));
                            gridPane.setVgap(8);
                            gridPane.setHgap(10);
                            gridPane.add(Label, 0, 0);
                            gridPane.add(labelLibelle, 0, 1);
                            gridPane.add(libelleTextArea, 1, 1);
                            gridPane.add(labelPeriode, 0, 2);
                            gridPane.add(periodeTextArea, 1, 2);
                            gridPane.add(buttonCreerClasse, 0, 3);
                            gridPane.add(buttonAnnulerCreationClasse, 1, 3);
                            gridPane.add(labelInfo, 0, 4);

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

                            windowClasses.close();
                            newWindow.show();

                            buttonCreerClasse.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    Classe c = new Classe();
                                    c.setLibelleClasse(libelleTextArea.getText());
                                    c.setPeriodeClasse(periodeTextArea.getText());
                                    try {
                                        classeDao.addClasse(c);
                                    } catch (SQLException throwables) {
                                        throwables.printStackTrace();
                                    }
                                    listViewClasse.refresh();
                                    newWindow.close();
                                    windowClasses.show();
                                }
                            });

                            buttonAnnulerCreationClasse.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    listViewClasse.refresh();
                                    newWindow.close();
                                    windowClasses.show();
                                }
                            });
                        }
                    });

                    buttonDeleteClasse.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            Number comparateur = -1;
                            if (idClasse != comparateur) {
                                Label Label = new Label("Confirmer la suppression de la classe " + listViewClasse.getSelectionModel().getSelectedItem().getLibelleClasse());

                                Button buttonValiderClasse = new Button();
                                buttonValiderClasse.setText("Supprimer");

                                Button buttonAnnulerClasse = new Button();
                                buttonAnnulerClasse.setText("Annuler");

                                GridPane gridPaneSupprimer = new GridPane();
                                gridPaneSupprimer.setPadding(new Insets(10, 10, 10, 10));
                                gridPaneSupprimer.setVgap(8);
                                gridPaneSupprimer.setHgap(10);
                                gridPaneSupprimer.add(Label, 0, 0);
                                gridPaneSupprimer.add(buttonValiderClasse, 0, 1);
                                gridPaneSupprimer.add(buttonAnnulerClasse, 1, 1);

                                StackPane secondaryLayout = new StackPane();
                                secondaryLayout.getChildren().add(gridPaneSupprimer);
                                Scene secondScene = new Scene(secondaryLayout, 410, 100);

                                // New window (Stage)
                                Stage newWindow = new Stage();
                                newWindow.setTitle("Suppression du compte de l'élève " + listViewClasse.getSelectionModel().getSelectedItem().getLibelleClasse());
                                newWindow.setScene(secondScene);

                                // Set position of second window, related to primary window.
                                newWindow.setX(stage.getX() + 200);
                                newWindow.setY(stage.getY() + 100);

                                windowClasses.close();
                                newWindow.show();

                                buttonValiderClasse.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent event) {
                                        try {
                                            classeDao.deleteClasse(listViewClasse.getSelectionModel().getSelectedItem().getIdClasse());
                                        } catch (SQLException throwables) {
                                            throwables.printStackTrace();
                                        }
                                        listViewClasse.refresh();
                                        newWindow.close();
                                        windowConnexion.show();
                                    }
                                });

                                buttonAnnulerClasse.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent event) {
                                        listViewClasse.refresh();
                                        newWindow.close();
                                        windowClasses.show();
                                    }
                                });
                            }
                        }
                    });

                    buttonDeconnexion.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            windowClasses.close();
                            windowConnexion.show();
                            log.deconnection();
                            labelmsg.setText("Vous avez été deconnecté !");
                            userTextField.setText("");
                            pwBox.setText("");
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
                                ls = elevesDao.getElevesByClasse((Integer) idClasse);
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                            for (Eleve allele : ls) {
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

                            Button buttonReadEleve = new Button();
                            buttonReadEleve.setText("Consulter l'élève");

                            Button buttonUpdateEleve = new Button();
                            buttonUpdateEleve.setText("Modifier l'élève");

                            Button buttonCreateEleve = new Button();
                            buttonCreateEleve.setText("Ajouter un élève");

                            Button buttonDeleteEleve = new Button();
                            buttonDeleteEleve.setText("Supprimer l'élève");

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
                            gridPane.add(buttonReadEleve, 0, 2);
                            gridPane.add(buttonUpdateEleve, 0, 3);
                            gridPane.add(buttonCreateEleve, 0, 4);
                            gridPane.add(buttonDeleteEleve, 0, 5);

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

                            buttonReadEleve.setOnAction(new EventHandler<ActionEvent>() {
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

                            buttonUpdateEleve.setOnAction(new EventHandler<ActionEvent>() {
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

                            buttonCreateEleve.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    Label Label = new Label("Creation d'un nouvel élève");

                                    Button buttonAnnulerCreation = new Button();
                                    buttonAnnulerCreation.setText("Annuler");

                                    Button buttonCreerEleve = new Button();
                                    buttonCreerEleve.setText("Créer");

                                    Label labelInfo = new Label("");
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
                                    gridPane.add(labelInfo, 0, 8);

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
                                            System.out.println(mdpTextArea.getText() + " " + confirmerMdpTextArea.getText());
                                            if (mdpTextArea.getText().equals(confirmerMdpTextArea.getText())) {
                                                Eleve e = new Eleve();
                                                e.setNomEleve(nomTextArea.getText());
                                                e.setPrenomEleve(prenomTextArea.getText());
                                                e.setDateNaissance(dateNaissanceTextArea.getText());
                                                e.setMail(mailTextArea.getText());
                                                e.setMdp(mdpTextArea.getText());
                                                e.setIdClasse((int) idClasse);
                                                try {
                                                    elevesDao.addEleve(e);
                                                } catch (SQLException throwables) {
                                                    throwables.printStackTrace();
                                                }
                                                listViewEleve.refresh();
                                                newWindow.close();
                                                windowEleves.show();
                                            } else {
                                                labelInfo.setTextFill(Color.FIREBRICK);
                                                labelInfo.setText("Mot de passe non identique");
                                            }
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

                            buttonDeleteEleve.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    Number comparateur = -1;
                                    if (idClasse != comparateur) {
                                        Label Label = new Label("Confirmer la suppression de l'élève " + listViewEleve.getSelectionModel().getSelectedItem().getPrenomEleve() + " " + listViewEleve.getSelectionModel().getSelectedItem().getNomEleve());

                                        Button buttonValiderClasse = new Button();
                                        buttonValiderClasse.setText("Supprimer");

                                        Button buttonAnnulerClasse = new Button();
                                        buttonAnnulerClasse.setText("Annuler");

                                        GridPane gridPaneSupprimer = new GridPane();
                                        gridPaneSupprimer.setPadding(new Insets(10, 10, 10, 10));
                                        gridPaneSupprimer.setVgap(8);
                                        gridPaneSupprimer.setHgap(10);
                                        gridPaneSupprimer.add(Label, 0, 0);
                                        gridPaneSupprimer.add(buttonValiderClasse, 0, 1);
                                        gridPaneSupprimer.add(buttonAnnulerClasse, 1, 1);

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

                                        buttonValiderClasse.setOnAction(new EventHandler<ActionEvent>() {
                                            @Override
                                            public void handle(ActionEvent event) {
                                                try {
                                                    elevesDao.deleteEleve(listViewEleve.getSelectionModel().getSelectedItem().getIdEleve());
                                                } catch (SQLException throwables) {
                                                    throwables.printStackTrace();
                                                }
                                                listViewEleve.refresh();
                                                newWindow.close();
                                                windowClasses.show();
                                            }
                                        });

                                        buttonAnnulerClasse.setOnAction(new EventHandler<ActionEvent>() {
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
                }else if(log.isConnecte()){
                    EleveDao eleveDao = new EleveDao();
                    try {
                        e = eleveDao.getEleve(userTextField.getText(), pwBox.getText());
                        System.out.println(pwBox.getText());
                    }catch (SQLException throwables) {
                    throwables.printStackTrace();
                    }

                    Label labelQuestion = new Label("Que souhaitez vous faire ?..");

                    Button buttonConsulterCompte = new Button();
                    buttonConsulterCompte.setText("Consulter votre compte");

                    Button buttonModifierCompte = new Button();
                    buttonModifierCompte.setText("Modifier votre compte");

                    GridPane gridPaneSupprimer = new GridPane();
                    gridPaneSupprimer.setPadding(new Insets(10, 10, 10, 10));
                    gridPaneSupprimer.setVgap(8);
                    gridPaneSupprimer.setHgap(10);
                    gridPaneSupprimer.add(labelQuestion, 0, 0);
                    gridPaneSupprimer.add(buttonConsulterCompte, 0, 1);
                    gridPaneSupprimer.add(buttonModifierCompte, 1, 1);

                    StackPane secondaryLayout = new StackPane();
                    secondaryLayout.getChildren().add(gridPaneSupprimer);
                    Scene secondScene = new Scene(secondaryLayout, 410, 100);

                    Stage windowCompteEleve = new Stage();
                    windowCompteEleve.setTitle("Bienvenue  " + e.getPrenomEleve()+" "+e.getNomEleve());
                    windowCompteEleve.setScene(secondScene);

                    // Set position of second window, related to primary window.
                    windowCompteEleve.setX(stage.getX() + 200);
                    windowCompteEleve.setY(stage.getY() + 100);

                    windowConnexion.setScene(sceneConnexion);
                    windowConnexion.close();
                    windowCompteEleve.show();

                    buttonConsulterCompte.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            Eleve ele = null;

                            Label prenomLabel = new Label("Prénom : " + e.getPrenomEleve());
                            Label nomLabel = new Label("Nom : " + e.getNomEleve());
                            Label dateNaissanceLabel = new Label("Date de naissance : " + e.getDateNaissance());
                            Label classeLabel = new Label("");

                            try {
                                classeLabel = new Label("Classe : " + classeDao.getClasse(e.getIdClasse()).getLibelleClasse());
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                            StackPane secondaryLayout = new StackPane();

                            GridPane gridPane = new GridPane();
                            gridPane.setPadding(new Insets(10, 10, 10, 10));
                            gridPane.setVgap(8);
                            gridPane.setHgap(10);
                            gridPane.add(prenomLabel, 0, 1);
                            gridPane.add(nomLabel, 0, 2);
                            gridPane.add(dateNaissanceLabel, 0, 3);
                            gridPane.add(classeLabel,0,4);

                            secondaryLayout.getChildren().add(gridPane);

                            Scene secondScene = new Scene(secondaryLayout, 540, 500);
                            // New window (Stage)
                            Stage newWindow = new Stage();
                            newWindow.setTitle("Consultation du compte de "+e.getPrenomEleve()+" "+e.getNomEleve());
                            newWindow.setScene(secondScene);
                            // Set position of second window, related to primary window.
                            newWindow.setX(stage.getX() + 200);
                            newWindow.setY(stage.getY() + 100);
                            newWindow.show();
                        }
                    });

                    buttonModifierCompte.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            Label label = new Label("Modification du compte : " + e.getPrenomEleve()+" "+e.getNomEleve());

                            Button buttonAnnuler = new Button();
                            buttonAnnuler.setText("Annuler");

                            Label labelMail = new Label("E-mail de l'élève : ");
                            Label labelMdp = new Label("Mot de passe de l'élève : ");

                            TextArea mailTextArea = new TextArea();
                            TextArea mdpTextArea = new TextArea();

                            mailTextArea.setText(e.getMail());
                            mdpTextArea.setText(e.getMdp());

                            Button buttonModifier = new Button();
                            buttonModifier.setText("Modifier");

                            GridPane gridPane = new GridPane();
                            gridPane.setPadding(new Insets(10, 10, 10, 10));
                            gridPane.setVgap(8);
                            gridPane.setHgap(10);
                            gridPane.add(label, 0, 0);
                            gridPane.add(labelMail, 0, 1);
                            gridPane.add(mailTextArea, 1, 1);
                            gridPane.add(labelMdp, 0, 2);
                            gridPane.add(mdpTextArea, 1, 2);
                            gridPane.add(buttonModifier, 0, 3);
                            gridPane.add(buttonAnnuler, 1, 3);

                            StackPane secondaryLayout = new StackPane();
                            secondaryLayout.getChildren().add(gridPane);


                            Scene secondScene = new Scene(secondaryLayout, 700, 290);

                            // New window (Stage)
                            Stage newWindow = new Stage();
                            newWindow.setTitle("Vous pouvez modifier les accès à votre compte..");
                            newWindow.setScene(secondScene);

                            // Set position of second window, related to primary window.
                            newWindow.setX(stage.getX() + 200);
                            newWindow.setY(stage.getY() + 100);

                            windowCompteEleve.close();
                            newWindow.show();

                            buttonModifier.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    e.setMdp(mdpTextArea.getText());
                                    e.setMail(mailTextArea.getText());

                                    try {
                                        eleveDao.updateEleve(e);
                                    } catch (SQLException throwables) {
                                        throwables.printStackTrace();
                                    }
                                    newWindow.close();
                                    windowCompteEleve.show();
                                }
                            });

                            buttonAnnuler.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    newWindow.close();
                                    windowCompteEleve.show();
                                }
                            });
                        }
                    });
                }else{
                labelmsg.setTextFill(Color.FIREBRICK);
                labelmsg.setText("mail ou mot de passe incorrect");
            }
            }
        });

    }
}

package com.example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * Contrôleur JavaFX de la page principale (Page1) de l'application.
 * Cette classe permet de charger, filtrer, apparier et gérer les adolescents depuis un fichier CSV.
 * Elle gère également les interactions utilisateur sur la table des adolescents français et italiens.
 * 
 * @version 1.0
 * @since 2025
 */
public class Page1Controller {

    /** Données de la plateforme (liste complète des adolescents) */
    public PlateformeSejour plateformeSejour = null;

    /** Gestionnaire des appariements entre adolescents */
    public Affectation affectation = null;

    /** Liste des adolescents extraits du fichier CSV */
    public Adolescent[] listAdolescents = null;

    // Filtres
    @FXML
    private CheckBox allergieOui, allergieNon, animalOui, animalNon, pairGenreF, pairGenreM, pairGenreOther, genreF, genreM, genreOther;

    @FXML
    private TextField cheminCSV;

    // Tables et colonnes pour afficher les adolescents
    @FXML
    private TableView<ObservableList<String>> fichierCSVFrance, fichierCSVItalie;
    @FXML
    private TableColumn<ObservableList<String>, String> colFORENAME1, colNAME1, colBIRTH_DATE1;
    @FXML
    private TableColumn<ObservableList<String>, String> colFORENAME2, colNAME2, colBIRTH_DATE2;


    /**
     * Redirige l'utilisateur vers la page des préférences (Page3).
     * @throws IOException si la navigation échoue.
     */
    @FXML
    private void onPreferencesClicked() throws IOException {
        FXMLLoader loader = App.setRoot("page3");
        Page3Controller page3Controller = loader.getController();
        page3Controller.setMyObject(this.affectation, this.listAdolescents, this.plateformeSejour);
    }

    /**
     * Méthode appelée lors de l'initialisation du contrôleur.
     * Initialise les colonnes et les modes de sélection des tables.
     */
    @FXML
    public void initialize() {
        fichierCSVFrance.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        colFORENAME1.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().get(0)));
        colNAME1.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().get(1)));
        colBIRTH_DATE1.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().get(3)));
        
        fichierCSVItalie.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        colFORENAME2.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().get(0)));
        colNAME2.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().get(1)));
        colBIRTH_DATE2.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().get(3)));
    }

    /**
     * Ouvre le fichier CSV indiqué dans le champ cheminCSV et remplit les tables.
     * Initialise les objets Adolescent à partir du contenu.
     * @throws IOException en cas de lecture échouée du fichier.
     */
    @FXML
    private void ouvertureCSV() throws IOException {
        this.plateformeSejour = new PlateformeSejour("IHM/res/" + cheminCSV.getText() + ".csv");
        String[][] contenufichierCSVFrance = this.plateformeSejour.getAdolescents();
        ObservableList<ObservableList<String>> lignesFrance = FXCollections.observableArrayList();
        ObservableList<ObservableList<String>> lignesItalie = FXCollections.observableArrayList();
        fichierCSVFrance.getItems().clear();
        for (String[] row : contenufichierCSVFrance) {
            ObservableList<String> ligne = FXCollections.observableArrayList(row);
            if(ligne.get(2).equals("FRANCE")) {
                lignesFrance.add(ligne);
            } else if(ligne.get(2).equals("ITALIE")) {
                lignesItalie.add(ligne);
            }
        }
        fichierCSVFrance.setItems(lignesFrance);
        fichierCSVItalie.setItems(lignesItalie);

        if(this.listAdolescents == null) {
            String forename, name, country, gender;
            LocalDate date;
            this.listAdolescents = new Adolescent[fichierCSVFrance.getItems().size() + fichierCSVItalie.getItems().size()];
            Map<Critere, String> map;
            for(int i=0; i<fichierCSVFrance.getItems().size(); i++) {
                forename = fichierCSVFrance.getItems().get(i).get(0);
                name = fichierCSVFrance.getItems().get(i).get(1);
                country = fichierCSVFrance.getItems().get(i).get(2);
                gender = fichierCSVFrance.getItems().get(i).get(9);
                date = LocalDate.of(Integer.valueOf(fichierCSVFrance.getItems().get(i).get(3).substring(0, 4)),
                                    Integer.valueOf(fichierCSVFrance.getItems().get(i).get(3).substring(5, 7)),
                                    Integer.valueOf(fichierCSVFrance.getItems().get(i).get(3).substring(8, 10)));
                map = new HashMap<Critere, String>();
                map.put(Critere.BIRTH_DATE, fichierCSVFrance.getItems().get(i).get(3));
                map.put(Critere.GUEST_ANIMAL_ALLERGY, fichierCSVFrance.getItems().get(i).get(4));
                map.put(Critere.HOST_HAS_ANIMAL, fichierCSVFrance.getItems().get(i).get(5));
                map.put(Critere.GUEST_FOOD_CONSTRAINT, fichierCSVFrance.getItems().get(i).get(6));
                map.put(Critere.HOST_FOOD, fichierCSVFrance.getItems().get(i).get(7));
                map.put(Critere.HOBBIES, fichierCSVFrance.getItems().get(i).get(8));
                map.put(Critere.GENDER, fichierCSVFrance.getItems().get(i).get(9));
                map.put(Critere.PAIR_GENDER, fichierCSVFrance.getItems().get(i).get(10));
                map.put(Critere.HISTORY, fichierCSVFrance.getItems().get(i).get(11));
                this.listAdolescents[i] = new Adolescent(forename, name, date, gender, country, map);
            }
            for(int i=0; i<fichierCSVItalie.getItems().size(); i++) {
                forename = fichierCSVItalie.getItems().get(i).get(0);
                name = fichierCSVItalie.getItems().get(i).get(1);
                country = fichierCSVItalie.getItems().get(i).get(2);
                gender = fichierCSVItalie.getItems().get(i).get(9);
                date = LocalDate.of(Integer.valueOf(fichierCSVItalie.getItems().get(i).get(3).substring(0, 4)),
                                    Integer.valueOf(fichierCSVItalie.getItems().get(i).get(3).substring(5, 7)),
                                    Integer.valueOf(fichierCSVItalie.getItems().get(i).get(3).substring(8, 10)));
                map = new HashMap<Critere, String>();
                map.put(Critere.BIRTH_DATE, fichierCSVItalie.getItems().get(i).get(3));
                map.put(Critere.GUEST_ANIMAL_ALLERGY, fichierCSVItalie.getItems().get(i).get(4));
                map.put(Critere.HOST_HAS_ANIMAL, fichierCSVItalie.getItems().get(i).get(5));
                map.put(Critere.GUEST_FOOD_CONSTRAINT, fichierCSVItalie.getItems().get(i).get(6));
                map.put(Critere.HOST_FOOD, fichierCSVItalie.getItems().get(i).get(7));
                map.put(Critere.HOBBIES, fichierCSVItalie.getItems().get(i).get(8));
                map.put(Critere.GENDER, fichierCSVItalie.getItems().get(i).get(9));
                map.put(Critere.PAIR_GENDER, fichierCSVItalie.getItems().get(i).get(10));
                map.put(Critere.HISTORY, fichierCSVItalie.getItems().get(i).get(11));
                this.listAdolescents[i+fichierCSVFrance.getItems().size()] = new Adolescent(forename, name, date, gender, country, map);
            }
        }
    }

    /**
     * Lance l’appariement automatique entre adolescents.
     * @throws IOException en cas d’erreur durant le processus.
     */
    @FXML
    private void appariementAutomatique() throws IOException {
        if(this.plateformeSejour != null) {
            this.affectation = new Affectation();
            this.affectation.affectationAutomatique(this.listAdolescents);
        }
    }

    /**
     * Recherche les appariements possibles pour un adolescent sélectionné.
     * Redirige vers la page de suggestion d’appariement (Page2).
     * @throws IOException si la navigation échoue.
     */
    @FXML
    private void rechercheAppariement() throws IOException {
        ObservableList<ObservableList<String>> selectedObjectFrance = fichierCSVFrance.getSelectionModel().getSelectedItems();
        ObservableList<ObservableList<String>> selectedObjectItalie = fichierCSVItalie.getSelectionModel().getSelectedItems();

        if (selectedObjectFrance.size() + selectedObjectItalie.size() == 1) {
            FXMLLoader loader = App.setRoot("page2");
            Page2Controller page2Controller = loader.getController();
            if(selectedObjectFrance.size() == 0) {
                page2Controller.setMyObject(selectedObjectItalie.get(0), this.affectation, this.listAdolescents, this.plateformeSejour);
            }
            else if(selectedObjectItalie.size() == 0) {
                page2Controller.setMyObject(selectedObjectFrance.get(0), this.affectation, this.listAdolescents, this.plateformeSejour);
            }
        }
    }
    
    /**
     * Supprime un appariement existant pour l’adolescent sélectionné.
     * @throws IOException en cas d’erreur.
     */
    @FXML
    private void suppressionAppariement() throws IOException {
        ObservableList<ObservableList<String>> selectedObjectFrance = fichierCSVFrance.getSelectionModel().getSelectedItems();
        ObservableList<ObservableList<String>> selectedObjectItalie = fichierCSVItalie.getSelectionModel().getSelectedItems();

        if(selectedObjectFrance.size() + selectedObjectItalie.size() == 1 && this.affectation != null) {
            ObservableList<ObservableList<String>> selectedObject = null;
            if(selectedObjectFrance.size() == 0) {
                selectedObject = selectedObjectItalie;
            }
            else if(selectedObjectItalie.size() == 0) {
                selectedObject = selectedObjectFrance;
            }
            int i = 0;
            while(i<this.listAdolescents.length && !this.listAdolescents[i].equals(selectedObject.get(0).get(0), selectedObject.get(0).get(1))) {
                i++;
            }
            Adolescent a1 = this.listAdolescents[i];
            Adolescent a2 = this.affectation.getAppariement(a1);
            if(a2 != null) {
                this.affectation.plateforme_Affectation.remove(a1);
                this.affectation.plateforme_Affectation.remove(a2);
            }
        }
    }

    /**
     * Forçage manuel d’un appariement entre deux adolescents sélectionnés.
     * @throws IOException en cas d’erreur.
     */
    @FXML
    private void forcageAppariement() throws IOException {
        ObservableList<ObservableList<String>> selectedObjectFrance = fichierCSVFrance.getSelectionModel().getSelectedItems();
        ObservableList<ObservableList<String>> selectedObjectItalie = fichierCSVItalie.getSelectionModel().getSelectedItems();
        if(selectedObjectFrance.size() + selectedObjectItalie.size() == 2) {
            ObservableList<ObservableList<String>> selectedObject = FXCollections.observableArrayList();
            for(ObservableList<String> ol : selectedObjectFrance) {
                selectedObject.add(ol);
            }
            for(ObservableList<String> ol : selectedObjectItalie) {
                selectedObject.add(ol);
            }
            int i = 0;
            while(i<this.listAdolescents.length && !this.listAdolescents[i].equals(selectedObject.get(0).get(0), selectedObject.get(0).get(1))) {
                i++;
            }
            Adolescent a1 = this.listAdolescents[i];
            i = 0;
            while(i<this.listAdolescents.length && !this.listAdolescents[i].equals(selectedObject.get(1).get(0), selectedObject.get(1).get(1))) {
                i++;
            }
            Adolescent a2 = this.listAdolescents[i];
            if(this.affectation != null) {
                Adolescent a1Appariement = this.affectation.getAppariement(a1);
                Adolescent a2Appariement = this.affectation.getAppariement(a2);
                this.affectation.plateforme_Affectation.remove(a1);
                this.affectation.plateforme_Affectation.remove(a2);
                if(a1Appariement != null) {
                    this.affectation.plateforme_Affectation.remove(a1Appariement);
                }
                if(a2Appariement != null) {
                    this.affectation.plateforme_Affectation.remove(a2Appariement);
                }
            } else {
                this.affectation = new Affectation();
            }
            this.affectation.plateforme_Affectation.put(a1, a2);
            this.affectation.plateforme_Affectation.put(a2, a1);
        }
    }

    /**
     * Supprime l’adolescent sélectionné et met à jour le fichier CSV.
     * @throws IOException en cas d’échec de suppression ou d’écriture du fichier.
     */
    @FXML
    private void suppressionAdolescent() throws IOException {
        ObservableList<ObservableList<String>> selectedObjectFrance = fichierCSVFrance.getSelectionModel().getSelectedItems();
        ObservableList<ObservableList<String>> selectedObjectItalie = fichierCSVItalie.getSelectionModel().getSelectedItems();

        if(selectedObjectFrance.size() + selectedObjectItalie.size() == 1) {
            ObservableList<ObservableList<String>> selectedObject = null;
            if(selectedObjectFrance.size() == 0) {
                selectedObject = selectedObjectItalie;
            }
            else if(selectedObjectItalie.size() == 0) {
                selectedObject = selectedObjectFrance;
            }

            int n = 0;
            while(n<this.listAdolescents.length && !this.listAdolescents[n].equals(selectedObject.get(0).get(0), selectedObject.get(0).get(1))) {
                n++;
            }
            Adolescent a1 = listAdolescents[n];
            this.listAdolescents = Adolescent.remove(this.listAdolescents, n);
            if(this.affectation != null) {
                Adolescent a2 = this.affectation.getAppariement(a1);
                if(a2 != null) {
                    this.affectation.plateforme_Affectation.remove(a1);
                    this.affectation.plateforme_Affectation.remove(a2);
                }
            }
            if(selectedObject.get(0).get(2).equals("FRANCE")) {
                fichierCSVFrance.getItems().remove(selectedObject.get(0));
            } else if(selectedObject.get(0).get(2).equals("ITALIE")) {
                fichierCSVItalie.getItems().remove(selectedObject.get(0));
            }
            File f = new File(PlateformeSejour.chemin);
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
                bw.write("FORENAME/NAME/COUNTRY/BIRTH_DATE/GUEST_ANIMAL_ALLERGY/HOST_HAS_ANIMAL/GUEST_FOOD_CONSTRAINT/HOST_FOOD/HOBBIES/GENDER/PAIR_GENDER/HISTORY\n");
                String chaine;
                for(int i=0; i<fichierCSVFrance.getItems().size(); i++) {
                    chaine = fichierCSVFrance.getItems().get(i).get(0);
                    for(int j=1; j<fichierCSVFrance.getItems().get(i).size(); j++) {
                        chaine += '/' + fichierCSVFrance.getItems().get(i).get(j);
                    }
                    bw.write(chaine + "\n");
                    f.createNewFile();
                }
                for(int i=0; i<fichierCSVItalie.getItems().size(); i++) {
                    chaine = fichierCSVItalie.getItems().get(i).get(0);
                    for(int j=1; j<fichierCSVItalie.getItems().get(i).size(); j++) {
                        chaine += '/' + fichierCSVItalie.getItems().get(i).get(j);
                    }
                    bw.write(chaine + "\n");
                }
            } catch (IOException e) {
                System.err.println("IO failure");
                e.printStackTrace();
            }
        }
    }

    /**
     * Applique les filtres sélectionnés (genre, allergies, animaux) et met à jour les tables.
     * @throws IOException si une erreur survient.
     */
    @FXML
    private void appliquerFiltre() throws IOException {
        String[][] contenufichierCSV = this.plateformeSejour.getAdolescents();
        ObservableList<ObservableList<String>> lignes = FXCollections.observableArrayList();
        for (String[] row : contenufichierCSV) {
            ObservableList<String> ligne = FXCollections.observableArrayList(row);
            lignes.add(ligne);
        }

        ObservableList<ObservableList<String>> lignesFiltre = FXCollections.observableArrayList();
        boolean aAjouter;

        for (ObservableList<String> ligne : lignes) {
            aAjouter = true;

            if (allergieOui.isSelected() || allergieNon.isSelected()) {
                String valeur = ligne.get(4);
                boolean allergieOK = false;

                if (allergieOui.isSelected() && valeur.equals("oui")) allergieOK = true;
                if (allergieNon.isSelected() && valeur.equals("non")) allergieOK = true;

                if (!allergieOK) aAjouter = false;
            }

            if (animalOui.isSelected() || animalNon.isSelected()) {
                String valeur = ligne.get(5);
                boolean animalOK = false;

                if (animalOui.isSelected() && valeur.equals("oui")) animalOK = true;
                if (animalNon.isSelected() && valeur.equals("non")) animalOK = true;

                if (!animalOK) aAjouter = false;
            }

            if (genreF.isSelected() || genreM.isSelected() || genreOther.isSelected()) {
                String valeur = ligne.get(9);
                boolean genreOK = false;

                if (genreF.isSelected() && valeur.equals("femme")) genreOK = true;
                if (genreM.isSelected() && valeur.equals("homme")) genreOK = true;
                if (genreOther.isSelected() && valeur.equals("other")) genreOK = true;

                if (!genreOK) aAjouter = false;
            }

            if (pairGenreF.isSelected() || pairGenreM.isSelected() || pairGenreOther.isSelected()) {
                String valeur = ligne.get(10);
                boolean pairGenreOK = false;

                if (pairGenreF.isSelected() && valeur.equals("femme")) pairGenreOK = true;
                if (pairGenreM.isSelected() && valeur.equals("homme")) pairGenreOK = true;
                if (pairGenreOther.isSelected() && valeur.equals("other")) pairGenreOK = true;

                if (!pairGenreOK) aAjouter = false;
            }

            if (aAjouter) {
                lignesFiltre.add(ligne);
            }
        }
        
        fichierCSVFrance.getItems().clear();
        fichierCSVItalie.getItems().clear();
        for(ObservableList<String> ol : lignesFiltre) {
            if(ol.get(2).equals("FRANCE")) {
                fichierCSVFrance.getItems().add(ol);
            } else if(ol.get(2).equals("ITALIE")) {
                fichierCSVItalie.getItems().add(ol);
            }
        }
    }

    /**
     * Définit les objets partagés pour la page et recharge les données si nécessaires.
     * @param affectation l’objet d’appariement à partager
     * @param listAdolescents la liste actuelle des adolescents
     */
    public void setMyObject(Affectation affectation, Adolescent[] listAdolescents) {
        this.affectation = affectation;
        this.listAdolescents = listAdolescents;
        if(PlateformeSejour.chemin != null) {
            cheminCSV.setText(PlateformeSejour.chemin.substring(8, PlateformeSejour.chemin.length()-4));
            try {
                ouvertureCSV();
            } catch(IOException e) {
                System.out.println("IOException " + e);
            }
        }
    }

    /** Champ lié au champ de texte de l'interface utilisateur pour saisir la recherche d'un adolescent. */
    @FXML
    private TextField rechercheAdo;

    /**
     * Recherche un adolescent par son prénom ou nom, et met à jour les tables en conséquence.
     */
    @FXML
    private void rechercheAdolescent() {
        String recherche = rechercheAdo.getText().toLowerCase().trim();
        if (plateformeSejour == null) return;

        String[][] contenufichierCSVFrance = plateformeSejour.getAdolescents();
        ObservableList<ObservableList<String>> lignesFiltre = FXCollections.observableArrayList();

        for (String[] row : contenufichierCSVFrance) {
            String prenom = row[0].toLowerCase().trim();
            String nom = row[1].toLowerCase().trim();

            String nomPrenom = nom + " " + prenom;
            String prenomNom = prenom + " " + nom;

            if (
                prenom.contains(recherche) ||
                nom.contains(recherche) ||
                nomPrenom.contains(recherche) ||
                prenomNom.contains(recherche)
            ) {
                lignesFiltre.add(FXCollections.observableArrayList(row));
            }
        }

        fichierCSVFrance.getItems().clear();
        fichierCSVItalie.getItems().clear();

        for (ObservableList<String> ol : lignesFiltre) {
            if (ol.get(2).equalsIgnoreCase("FRANCE")) {
                fichierCSVFrance.getItems().add(ol);
            } else if (ol.get(2).equalsIgnoreCase("ITALIE")) {
                fichierCSVItalie.getItems().add(ol);
            }
        }
    }
}

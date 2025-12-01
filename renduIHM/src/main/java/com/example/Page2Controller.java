package com.example;

import java.io.IOException;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class Page2Controller {

    @FXML
    private Label nomPrenom1, gender1, country1, hobbies1, birth_date1, pair_gender1, guest_animal_allergy1, host_has_animal1, guest_food_constraint1, host_food1, history1,
                  nomPrenom2, gender2, country2, hobbies2, birth_date2, pair_gender2, guest_animal_allergy2, host_has_animal2, guest_food_constraint2, host_food2, history2;

    @FXML
    private Slider birthDateSlider, guestAnimalAllergySlider, guestFoodConstraintSlider, hobbiesSlider, genderSlider;

    @FXML
    private TableView tabAdolescents;

    @FXML
    private TableColumn<ObservableList<String>, String> colFORENAME, colNAME, colBIRTH_DATE, colCOUNTRY;
    
    public PlateformeSejour plateformeSejour = null;
    public Affectation affectation = null;
    public Adolescent[] listAdolescents = null;

    @FXML
    public void initialize() {
        tabAdolescents.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        colFORENAME.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().get(0)));
        colNAME.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().get(1)));
        colCOUNTRY.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().get(2)));
        colBIRTH_DATE.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().get(3)));

        tabAdolescents.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<ObservableList<String>>() {
                @Override
                public void changed(ObservableValue<? extends ObservableList<String>> observable, ObservableList<String> oldValue, ObservableList<String> newValue) {
                    if (newValue != null) {
                        afficherAdolescentAffectation(newValue);
                    }
                }
            }
        );
    }
    
    public void setMyObject(ObservableList<String> ligne, Affectation affectation, Adolescent[] listAdolescents, PlateformeSejour plateformeSejour) {
        this.affectation = affectation;
        this.listAdolescents = listAdolescents;
        this.plateformeSejour = plateformeSejour;
        String[][] contenuTabAdolescents = this.plateformeSejour.getAdolescents();
        ObservableList<ObservableList<String>> lignesTab = FXCollections.observableArrayList();
        tabAdolescents.getItems().clear();
        for (String[] row : contenuTabAdolescents) {
            ObservableList<String> ligneTab = FXCollections.observableArrayList(row);
            lignesTab.add(ligneTab);
        }
        tabAdolescents.setItems(lignesTab);

        afficherAdolescentAffectation(ligne);
    }

    private void afficherAdolescentAffectation(ObservableList<String> ligne) {
        nomPrenom1.setText(ligne.get(1) + " " + ligne.get(0));
        gender1.setText("Gender: " + ligne.get(9));
        country1.setText("Country: " + ligne.get(2));
        hobbies1.setText("Hobbies: " + ligne.get(8));
        birth_date1.setText("Birth date: " + ligne.get(3));
        pair_gender1.setText("Pair gender: " + ligne.get(10));
        guest_animal_allergy1.setText("Guest animal allergy: " + ligne.get(4));
        host_has_animal1.setText("Host has animal: " + ligne.get(5));
        guest_food_constraint1.setText("Guest food constraint: " + ligne.get(6));
        host_food1.setText("Host food: " + ligne.get(7));
        history1.setText("History: " + ligne.get(11));
        int i = 0;
        while(i<listAdolescents.length && !listAdolescents[i].equals(ligne.get(0), ligne.get(1))) {
            i++;
        }
        Adolescent a1 = listAdolescents[i], a2 = null;
        if(affectation != null) {
            //FORENAME/NAME/COUNTRY/BIRTH_DATE/GUEST_ANIMAL_ALLERGY/HOST_HAS_ANIMAL/GUEST_FOOD_CONSTRAINT/HOST_FOOD/HOBBIES/GENDER/PAIR_GENDER/HISTORY
            a2 = affectation.getAppariement(a1);
            if(a2 != null) {
                nomPrenom2.setText(a2.getNom() + " " + a2.getPrenom());
                gender2.setText("Gender: " + a2.getGenre());
                country2.setText("Country: " + a2.getPays());
                hobbies2.setText("Hobbies: " + a2.getCritere().get(Critere.HOBBIES));
                birth_date2.setText("Birth date: " + a2.getDateNaissance());
                pair_gender2.setText("Pair gender: " + a2.getCritere().get(Critere.PAIR_GENDER));
                guest_animal_allergy2.setText("Guest animal allergy: " + a2.getCritere().get(Critere.GUEST_ANIMAL_ALLERGY));
                host_has_animal2.setText("Host has animal: " + a2.getCritere().get(Critere.HOST_HAS_ANIMAL));
                guest_food_constraint2.setText("Guest food constraint: " + a2.getCritere().get(Critere.GUEST_FOOD_CONSTRAINT));
                host_food2.setText("Host food: " + a2.getCritere().get(Critere.HOST_FOOD));
                history2.setText("History: " + a2.getCritere().get(Critere.HISTORY));
            } else {
                nomPrenom2.setText("Pas d'appariement");
                gender2.setText("Gender: ");
                country2.setText("Country: ");
                hobbies2.setText("Hobbies: ");
                birth_date2.setText("Birth date: ");
                pair_gender2.setText("Pair gender: ");
                guest_animal_allergy2.setText("Guest animal allergy: ");
                host_has_animal2.setText("Host has animal: ");
                guest_food_constraint2.setText("Guest food constraint: ");
                host_food2.setText("Host food: ");
                history2.setText("History: ");
            }
        }
        if(a2 != null) {
            int[] importanceCritere = new int[]{Critere.BIRTH_DATE.getPoids(), Critere.GUEST_ANIMAL_ALLERGY.getPoids(), Critere.GUEST_FOOD_CONSTRAINT.getPoids(), Critere.HOBBIES.getPoids(), Critere.GENDER.getPoids(), Critere.PAIR_GENDER.getPoids()};
            if(a1.affiniteAge(a2, importanceCritere) == 0) {
                birthDateSlider.setValue(1);
            }
            if(a1.affiniteAllergie(a2, importanceCritere) == 0) {
                guestAnimalAllergySlider.setValue(1);
            }
            if(a1.affinitePreferenceAlimentaire(a2, importanceCritere) == 0) {
                guestFoodConstraintSlider.setValue(1);
            }
            if(Adolescent.creationListeHobbies(a1).size() > Adolescent.creationListeHobbies(a2).size()) {
                hobbiesSlider.setMax(Adolescent.creationListeHobbies(a1).size());
            } else {
                hobbiesSlider.setMax(Adolescent.creationListeHobbies(a2).size());
            }
            hobbiesSlider.setValue(a1.calculHobbiesCommun(Adolescent.creationListeHobbies(a1), Adolescent.creationListeHobbies(a2)));
            if(a1.affiniteGenre(a2, importanceCritere) == 0) {
                genderSlider.setValue(1);
            }
        } else {
            birthDateSlider.setValue(0);
            guestAnimalAllergySlider.setValue(0);
            guestFoodConstraintSlider.setValue(0);
            hobbiesSlider.setMax(1);
            hobbiesSlider.setValue(0);
            genderSlider.setValue(0);
        }
    }

    @FXML
    private void onAccueilClicked() throws IOException {
        FXMLLoader loader = App.setRoot("page1");
        Page1Controller page1Controller = loader.getController();
        page1Controller.setMyObject(this.affectation, this.listAdolescents);
    }

    @FXML
    private void onPreferencesClicked() throws IOException {
        FXMLLoader loader = App.setRoot("page3");
        Page3Controller page3Controller = loader.getController();
        page3Controller.setMyObject(this.affectation, this.listAdolescents, this.plateformeSejour);
    }
}
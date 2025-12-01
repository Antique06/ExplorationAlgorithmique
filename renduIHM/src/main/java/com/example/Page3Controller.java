package com.example;

import java.io.IOException;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Slider;

public class Page3Controller {
    @FXML private Slider birthDateSlider;
    @FXML private Slider guestAnimalAllergySlider;
    @FXML private Slider hostHasAnimalSlider;
    @FXML private Slider guestFoodConstraintSlider;
    @FXML private Slider hostFoodSlider;
    @FXML private Slider hobbiesSlider;
    @FXML private Slider genderSlider;
    @FXML private Slider pairGenderSlider;

    public PlateformeSejour plateformeSejour = null;
    public Affectation affectation = null;
    public ObservableList<String> ligne = null;
    public Adolescent[] listAdolescents = null;

    @FXML
    private void initialize() {
        // Valeur par defaut
        birthDateSlider.setValue(Critere.BIRTH_DATE.getPoids());
        guestAnimalAllergySlider.setValue(Critere.GUEST_ANIMAL_ALLERGY.getPoids());
        hostHasAnimalSlider.setValue(Critere.HOST_HAS_ANIMAL.getPoids());
        guestFoodConstraintSlider.setValue(Critere.GUEST_FOOD_CONSTRAINT.getPoids());
        hostFoodSlider.setValue(Critere.HOST_FOOD.getPoids());
        hobbiesSlider.setValue(Critere.HOBBIES.getPoids());
        genderSlider.setValue(Critere.GENDER.getPoids());
        pairGenderSlider.setValue(Critere.PAIR_GENDER.getPoids());

        // Associer les sliders aux critères, avec écoute des changements
        birthDateSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            Critere.BIRTH_DATE.setPoids(newVal.intValue());
        });

        guestAnimalAllergySlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            Critere.GUEST_ANIMAL_ALLERGY.setPoids(newVal.intValue());
        });

        hostHasAnimalSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            Critere.HOST_HAS_ANIMAL.setPoids(newVal.intValue());
        });

        guestFoodConstraintSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            Critere.GUEST_FOOD_CONSTRAINT.setPoids(newVal.intValue());
        });

        hostFoodSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            Critere.HOST_FOOD.setPoids(newVal.intValue());
        });

        hobbiesSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            Critere.HOBBIES.setPoids(newVal.intValue());
        });

        genderSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            Critere.GENDER.setPoids(newVal.intValue());
        });

        pairGenderSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            Critere.PAIR_GENDER.setPoids(newVal.intValue());
        });
    }

    public void setMyObject(Affectation affectation, Adolescent[] listAdolescents, PlateformeSejour plateformeSejour) {
        this.affectation = affectation;
        this.listAdolescents = listAdolescents;
        this.plateformeSejour = plateformeSejour;
    }

    @FXML
    private void onAccueilClicked() throws IOException {
        FXMLLoader loader = App.setRoot("page1");
        Page1Controller page1Controller = loader.getController();
        page1Controller.setMyObject(this.affectation, this.listAdolescents);
    }
}


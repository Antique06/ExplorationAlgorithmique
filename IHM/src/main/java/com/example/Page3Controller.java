package com.example;

import java.io.IOException;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Slider;

/**
 * Contrôleur de la page 3 de l'application, permettant à l'utilisateur
 * de définir l'importance (poids) des différents critères d'appariement
 * via des sliders.
 */
public class Page3Controller {
    /** Slider pour le critère de date de naissance. */
    @FXML private Slider birthDateSlider;
    /** Slider pour le critère d'allergie aux animaux (invité). */
    @FXML private Slider guestAnimalAllergySlider;
    /** Slider pour le critère de présence d’animaux (hôte). */
    @FXML private Slider hostHasAnimalSlider;
    /** Slider pour le critère de contraintes alimentaires (invité). */
    @FXML private Slider guestFoodConstraintSlider;
    /** Slider pour le critère de régime alimentaire (hôte). */
    @FXML private Slider hostFoodSlider;
    /** Slider pour le critère des hobbies. */
    @FXML private Slider hobbiesSlider;
    /** Slider pour le critère du genre. */
    @FXML private Slider genderSlider;
    /** Slider pour le critère du genre préféré pour l’appariement. */
    @FXML private Slider pairGenderSlider;

    /** Référence à la plateforme contenant les adolescents du séjour. */
    public PlateformeSejour plateformeSejour = null;

    /** Objet gérant les appariements entre adolescents. */
    public Affectation affectation = null;

    /** Ligne actuellement sélectionnée dans la table des adolescents. */
    public ObservableList<String> ligne = null;

    /** Tableau d’adolescents utilisé dans la plateforme. */
    public Adolescent[] listAdolescents = null;

    /**
     * Initialise les sliders avec les poids actuels des critères et ajoute
     * des écouteurs pour mettre à jour les poids dynamiquement.
     */
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

    /**
     * Définit les objets nécessaires pour cette page : l'affectation, les adolescents,
     * et la plateforme du séjour.
     *
     * @param affectation L’objet de gestion des appariements
     * @param listAdolescents Le tableau d’adolescents disponibles
     * @param plateformeSejour La plateforme de séjour active
     */
    public void setMyObject(Affectation affectation, Adolescent[] listAdolescents, PlateformeSejour plateformeSejour) {
        this.affectation = affectation;
        this.listAdolescents = listAdolescents;
        this.plateformeSejour = plateformeSejour;
    }

    /**
     * Retourne à la page d’accueil (Page1) en passant les objets nécessaires.
     *
     * @throws IOException Si le chargement du fichier FXML échoue
     */
    @FXML
    private void onAccueilClicked() throws IOException {
        FXMLLoader loader = App.setRoot("page1");
        Page1Controller page1Controller = loader.getController();
        page1Controller.setMyObject(this.affectation, this.listAdolescents);
    }
}


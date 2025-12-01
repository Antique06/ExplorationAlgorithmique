package com.example;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Classe principale de l'application JavaFX.
 * Elle initialise la scène principale et permet de naviguer entre différentes vues (FXML).
 * 
 * @author 
 * @version 1.0
 */
public class App extends Application {

    /** La scène principale de l'application, utilisée pour changer de vues dynamiquement. */
    private static Scene scene;

    /**
     * Point d'entrée graphique de l'application.
     * Cette méthode est appelée automatiquement au lancement de l'application.
     *
     * @param stage Le stage principal (fenêtre) fourni par JavaFX.
     * @throws IOException Si le fichier FXML ne peut pas être chargé.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("page1.fxml"));
        scene = new Scene(fxmlLoader.load(), 640, 480);
        stage.setResizable(true);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Change dynamiquement la vue courante de l'application en chargeant un nouveau fichier FXML.
     *
     * @param fxml Le nom du fichier FXML (sans l'extension) à charger comme nouvelle racine de la scène.
     * @return Le {@code FXMLLoader} utilisé pour charger le fichier, permettant d'accéder à son contrôleur.
     * @throws IOException Si le fichier FXML ne peut pas être chargé.
     */
    static FXMLLoader setRoot(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        scene.setRoot(loader.load());
        return loader;
    }

    /**
     * Méthode principale de l'application.
     * Elle lance l'application JavaFX en appelant la méthode {@code start(Stage)}.
     *
     * @param args Les arguments de la ligne de commande (non utilisés).
     */
    public static void main(String[] args) {
        launch();
    }
}
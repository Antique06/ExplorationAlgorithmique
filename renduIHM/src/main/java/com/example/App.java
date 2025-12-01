package com.example;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("page1.fxml"));
        scene = new Scene(fxmlLoader.load(), 640, 480);
        stage.setResizable(true);
        stage.setScene(scene);
        stage.show();
    }

    static FXMLLoader setRoot(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        scene.setRoot(loader.load());
        return loader;
    }

    public static void main(String[] args) {
        launch();
    }
}
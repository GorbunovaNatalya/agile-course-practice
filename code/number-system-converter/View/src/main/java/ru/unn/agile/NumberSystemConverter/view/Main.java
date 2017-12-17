package ru.unn.agile.NumberSystemConverter.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Converter.fxml"));
            primaryStage.setTitle("Converter");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}

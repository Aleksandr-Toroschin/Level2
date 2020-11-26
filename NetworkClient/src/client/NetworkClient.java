package client;

import client.controllers.AuthDialogController;
import client.controllers.Controller;
import client.models.Network;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class NetworkClient extends Application {
    private static final String mainWindow = "views/mainview.fxml";
    private static final String loginWindow = "views/auth-dialog.fxml";
    public static final List<Contact> TEST_USERS = List.of(new Contact("Иван"), new Contact("David"));

    public Stage primaryStage;
    private Network network;
    private Stage loginStage;
    private Controller controller;


    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        FXMLLoader loginLoader = new FXMLLoader();
        loginLoader.setLocation(NetworkClient.class.getResource(loginWindow));
        Parent page = loginLoader.load();
        loginStage = new Stage();

        loginStage.setTitle("Авторизация пользователя");
        loginStage.initModality(Modality.WINDOW_MODAL);
        loginStage.initOwner(primaryStage);
        Scene scene = new Scene(page);
        loginStage.setScene(scene);
        loginStage.show();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(NetworkClient.class.getResource(mainWindow));

        Parent root = loader.load();

        primaryStage.setTitle("Chat 2020");
        primaryStage.setScene(new Scene(root));
//        primaryStage.show();

        network = new Network();

        AuthDialogController authDialogController = loginLoader.getController();
        authDialogController.setNetwork(network);
        authDialogController.setNetworkClient(this);

        if (!network.connect()) {
            showAlert("Connect", "Не удалось подключиться к серверу");
        }

        controller = loader.getController();
        controller.setNetwork(network);
        controller.setClient(this);

        //network.waitMessageFromServer(controller);

        primaryStage.setOnCloseRequest(windowEvent -> network.close());
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void showAlert(String errorMessage, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Проблемы с соединением");
        alert.setHeaderText(errorMessage);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void openChat() {
        loginStage.close();
        primaryStage.show();
        primaryStage.setTitle(network.getUserName());
        network.waitMessageFromServer(controller);
    }
}

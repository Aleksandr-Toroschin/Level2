package client;

import client.controllers.Controller;
import client.models.Network;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.util.List;

public class Main extends Application {
    private static final String mainWindow = "views/mainview.fxml";
    public static final List<Contact> TEST_USERS = List.of(new Contact("Иван"), new Contact("David"));

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource(mainWindow));

        Parent root = loader.load();

        primaryStage.setTitle("Chat 2020");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        Network network = new Network();
        if (!network.connect()) {
            showAlert("Connect", "Не удалось подключиться к серверу");
        }

        Controller controller = loader.getController();
        controller.setNetwork(network);
        controller.setClient(this);

        network.waitMessageFromServer(controller);

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
}

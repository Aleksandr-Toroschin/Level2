package client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Controller {
    private final String aboutWindow = "about.fxml";

    @FXML
    private TextField inputField;

    @FXML
    private ListView<String> messagesField;

    @FXML
    private Button btnSend;

    @FXML
    private TableView<Contact> tableUsers;

    @FXML
    private TableColumn<Contact, String> nameUser;

    @FXML
    private final List<String> users = new LinkedList<>();

    @FXML
    private final ObservableList<Contact> contacts = FXCollections.observableArrayList();

    private Network network;

    Main echoClient;

    @FXML
    public void initialize() {
        nameUser.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableUsers.setItems(contacts);
        contacts.addAll(Main.TEST_USERS);
    }

    @FXML
    private void sendMessage() {
        String text = inputField.getText();
        addTextToList(text);
        inputField.clear();

        try {
            network.getDataOutputStream().writeUTF(text);
        } catch (IOException e) {
            System.out.println("Не удалось отправить сообщение");
            e.printStackTrace();
        }
    }

    @FXML
    public void addTextToList(String text) {
        if (!text.isBlank()) {
            messagesField.getItems().add(text);
        }
    }

    @FXML
    public void reset() {
        messagesField.getItems().clear();
        inputField.setText("");
    }

    @FXML
    public void showAbout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource(aboutWindow));
            AnchorPane page = loader.load();

            Stage aboutStage = new Stage();
            aboutStage.setTitle("О программе");
            aboutStage.initModality(Modality.WINDOW_MODAL);
            //aboutStage.initOwner();
            Scene scene = new Scene(page);
            aboutStage.setScene(scene);

            AboutController controller = loader.getController();
            controller.setAboutStage(aboutStage);

            aboutStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void exit() {
        System.exit(0);
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    public void setClient(Main client) {
        this.echoClient = client;
    }
}

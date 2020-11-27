package client.controllers;

import client.Contact;
import client.NetworkClient;
import client.models.Network;
import javafx.beans.Observable;
import javafx.beans.value.ObservableStringValue;
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
    private final String aboutWindow = "views/about.fxml";

    @FXML
    private TextField inputField;

    @FXML
    private TextArea messagesField;
    //private ListView<String> messagesField;
    private final List<String> listMessages = FXCollections.observableArrayList();

    @FXML
    private TableView<Contact> tableUsers;

    @FXML
    private TableColumn<Contact, String> nameUser;

    @FXML
    private final List<String> users = new LinkedList<>();

    @FXML
    private final ObservableList<Contact> contacts = FXCollections.observableArrayList();

    private Network network;

    NetworkClient echoClient;

    @FXML
    public void initialize() {
        nameUser.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableUsers.setItems(contacts);
        //contacts.addAll(NetworkClient.TEST_USERS);
        //messagesField.setItems(FXCollections.observableArrayList(listMessages));
    }

    @FXML
    private void sendMessage() {
        String text = inputField.getText();
        if (!text.isBlank()) {
            addTextToList("Я: " + text);
            inputField.clear();

            try {
                network.getDataOutputStream().writeUTF(text);
            } catch (IOException e) {
                System.out.println("Не удалось отправить сообщение");
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void addTextToList(String text) {
        if (!text.isBlank()) {
            //messagesField.getItems().add(text);
            //listMessages.add(text);
            messagesField.appendText(text);
            messagesField.appendText(System.lineSeparator());
        }
    }

    @FXML
    public void reset() {
        //messagesField.getItems().clear();
        messagesField.clear();
        inputField.setText("");
    }

    @FXML
    public void showAbout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(NetworkClient.class.getResource(aboutWindow));
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

    public void setClient(NetworkClient client) {
        this.echoClient = client;
    }

    public void addUserInList(String mes) {
        String[] usersList = mes.split("\\s+");
        for (String s : usersList) {
            contacts.add(new Contact(s));
        }
    }
}

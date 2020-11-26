package client.models;

import client.controllers.Controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Network {
    private final String AUTH_CMD_PREFIX = "/auth";
    private final String AUTHOK_CMD_PREFIX = "/authOk";
    private final String AUTHERR_CMD_PREFIX = "/autherr";

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8189;

    private final String serverHost;
    private final int serverPort;

    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    private Socket socket;

    private String userName;

    public Network() {
        this(SERVER_HOST, SERVER_PORT);
    }

    public Network(String serverHost, int serverPort) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    public DataInputStream getDataInputStream() {
        return dataInputStream;
    }

    public DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }

    public boolean connect() {
        try {
            socket = new Socket(serverHost, serverPort);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            return true;
        } catch (IOException e) {
            System.out.println("Не удалось установить соединение");
            e.printStackTrace();
            return false;
        }
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void waitMessageFromServer(Controller controller) {
        Thread thread = new Thread(() -> {
            try {
                while (true) {
                    String message = dataInputStream.readUTF();
                    controller.addTextToList(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } );
        thread.setDaemon(true);
        thread.start();
    }

    public String sendAuthCommand(String login, String pw) {
        try {
            dataOutputStream.writeUTF(AUTH_CMD_PREFIX+" "+login+" "+pw);
            String response = dataInputStream.readUTF();
            if (response.startsWith(AUTHOK_CMD_PREFIX)) {
                this.userName = response.split("\\s+",2)[1];
                return null;
            } else {
                return response.split("\\s+",2)[0];
            }
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public String getUserName() {
        return userName;
    }
}

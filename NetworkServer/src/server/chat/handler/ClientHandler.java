package server.chat.handler;

import server.chat.MyServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    private final String AUTH_CMD_PREFIX = "/auth";
    private final String AUTHOK_CMD_PREFIX = "/authOk";
    private final String AUTHERR_CMD_PREFIX = "/autherr";

    private final MyServer myServer;
    private final Socket clientSocket;
    private DataInputStream in;
    private DataOutputStream out;
    private String userName;

    public ClientHandler(MyServer myServer, Socket clientSocket) {
        this.myServer = myServer;
        this.clientSocket = clientSocket;

    }

    public void hadle() throws IOException {
        in = new DataInputStream(clientSocket.getInputStream());
        out = new DataOutputStream(clientSocket.getOutputStream());

        new Thread(() -> {
            try {
                authentication();
                readMessage();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                closeConnection();
            }
        }).start();

    }

    private void authentication() throws IOException {
        while (true) {
            String message = in.readUTF();
            System.out.println("попытка аутентификации");
            if (message.startsWith(AUTH_CMD_PREFIX)) {

                String[] parts = message.split("\\s+", 3);
                String login = parts[1];
                String password = parts[2];
                this.userName = myServer.getAuthService().getUserName(login, password);

                if (userName != null) {
                    if (myServer.isUserBusy(userName)) {
                        out.writeUTF(AUTHERR_CMD_PREFIX + " Пользователь уже подключен");
                    } else {
                        out.writeUTF(AUTHOK_CMD_PREFIX + " " + userName);
                        myServer.addUser(this);
                        myServer.broadcastMessage(userName + " присоединился", this);
                        System.out.println("Пользователь "+userName+" успешно подключен");
                        break;
                    }
                } else {
                    out.writeUTF(AUTHERR_CMD_PREFIX + " Неверный логин или пароль");
                }
            }
        }
    }

    public String getUserName() {
        return userName;
    }

    public void sendMessage(String message) throws IOException {
        out.writeUTF(message);
    }

    public void readMessage() throws IOException {
        while (true) {
            String message = in.readUTF();
            System.out.println(message + " | " + userName + " : " + message);
            if (message.startsWith("/exit")) {
                return;
            }
            myServer.broadcastMessage(userName+": "+ message, this);
        }
    }

    public void closeConnection() {
        myServer.deleteUser( this );
        myServer.broadcastMessage(userName + " вышел из чата", this);
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

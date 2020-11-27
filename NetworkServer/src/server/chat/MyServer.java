package server.chat;

import server.chat.auth.BaseAuthService;
import server.chat.handler.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MyServer {
    private final ServerSocket serverSocket;
    private final BaseAuthService authService;
    private final List<ClientHandler> clientHandlers = new ArrayList<>();


    public MyServer(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.authService = new BaseAuthService();
    }

    public void start() throws IOException {
        System.out.println("Сервер запущен");
        consoleRead();

        try {
            while (true) {
                waitAndProcessNewClientConnection();
            }
        } catch (IOException e) {
            System.out.println("Не удалось создать новое подключение");
            e.printStackTrace();
        } finally {
            serverSocket.close();
        }
    }

    private void consoleRead() {
        Thread threadSout = new Thread(() -> {
            while (true) {
                Scanner scanner = new Scanner(System.in);
                if (scanner.hasNext()) {
                    String message = scanner.nextLine();
                    if (message.equals("/exit")) {
                        break;
                    }
                    try {
                        clientHandlers.get(0).sendMessage(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//                out.writeUTF(message);
                }
            }
        });
        threadSout.setDaemon(true);
        threadSout.start();
    }

    private void waitAndProcessNewClientConnection() throws IOException {
        System.out.println("Ожидаем подключения...");
        Socket clientSocket = serverSocket.accept();
        System.out.println("Соединение установлено.");
        processClientConnection(clientSocket);
    }

    private void processClientConnection(Socket clientSocket) throws IOException {
        ClientHandler clientHandler = new ClientHandler(this, clientSocket);
        clientHandler.hadle();
    }

    public BaseAuthService getAuthService() {
        return authService;
    }

    public boolean isUserBusy(String userName) {
        for (ClientHandler clientHandler : clientHandlers) {
            if (clientHandler.getUserName().equals(userName)) {
                return true;
            }
        }
        return false;
    }

    public void addUser(ClientHandler clientHandler) {
        clientHandlers.add(clientHandler);
    }

    public void deleteUser(ClientHandler clientHandler) {
        clientHandlers.remove(clientHandler);

    }

    public void broadcastMessage(String message, ClientHandler sender) {
        for (ClientHandler clientHandler : clientHandlers) {
            if (!clientHandler.equals(sender)) {
                try {
                    clientHandler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void personalMessage(String message, String nickName) {
        for (ClientHandler clientHandler : clientHandlers) {
            if (clientHandler.getNickName().equals(nickName)) {
                try {
                    clientHandler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

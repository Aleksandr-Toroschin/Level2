package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class EchoServer {

    private static final int SERVER_PORT = 8189;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            System.out.println("Ожидаем подключения...");

            Socket clientSocket = serverSocket.accept();
            System.out.println("Соединение установлено.");

            DataInputStream in = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

            Thread threadIn = new Thread(() -> {
                while (true) {
                    String message = null;
                    try {
                        message = in.readUTF();
                    } catch (IOException e) {
                        System.out.println("Ошибка получения сообщения.");
                        e.printStackTrace();
                    }
                    System.out.println("Клиент: " + message);

                    if (message.equals("/exit")) {
                        break;
                    }
                }
            });
            threadIn.setDaemon(true);
            threadIn.start();

            while (true) {
                Scanner scanner = new Scanner(System.in);
                if (scanner.hasNext()) {
                    String message = scanner.nextLine();
                    if (message.equals("/exit")) {
                        break;
                    }
                    out.writeUTF(message);
                }
            }

            System.out.println("Соединение разорвано.");

        } catch (IOException e) {
            System.out.println("Порт уже занят");
            e.printStackTrace();
        }

    }
}

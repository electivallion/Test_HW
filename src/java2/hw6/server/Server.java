package java2.hw6.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    private static ServerSocket serverSocket;
    private static Socket socket;
    private static Scanner in;
    private static Scanner scanner = new Scanner(System.in);
    private static PrintWriter out;
    private static final int PORT = 9000;
    private static boolean help = true;

    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Сервер запущен!");
            socket = serverSocket.accept();
            System.out.printf("Клиент %s подключился!\n", socket.getLocalSocketAddress());
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);
            new Thread(() -> {
                while (help) {
                    String str2 = scanner.nextLine();
                    if (str2.equals("/end")) {
                        out.println(str2);
                        help = false;
                        System.out.println("вылезли из OutputStream");
                        break;
                    }
                    out.println(str2);
                    System.out.println("Вы (сервер): " + str2);
                }
            }).start();

            while (in.hasNextLine()) {
                String str1 = in.nextLine();
                if (str1.equals("/end")) {
                    help = false;
                    System.out.println("Вылезли из InputStream");
                    break;
                }
                System.out.println("Клиент: " + str1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
package java2.hw6.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static Socket socket;
    private static Scanner in;
    private static Scanner scanner = new Scanner(System.in);
    private static PrintWriter out;
    private static final String IP_ADDRESS = "127.0.0.1";
    private static final int PORT = 9000;
    private static boolean help = true;

    public static void main(String[] args) {
        try {
            socket = new Socket(IP_ADDRESS, PORT);
            System.out.println("Добро пожаловать на сервер!");
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (help) {
                        String str2 = scanner.nextLine();
                        if (str2.equals("/end")) {
                            System.out.println("OOOOOOOOOOO:" + str2);
                            out.println(str2);

                            help = false;
                            System.out.println("Вылезли из OutputStream");
                            break;
                        }
                        out.println(str2);
                        System.out.println("Вы (клиент): " + str2);
                    }
                }
            });
            thread.start();




            while (in.hasNextLine()) {
                String str1 = in.nextLine();
                if (str1.equals("/end")) {
                    help = false;
                    System.out.println("вылезли из инпута");
                    thread.interrupt();
                    out.close();
                    thread.stop();
                    break;
                }
                System.out.println("Сервер: " + str1);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
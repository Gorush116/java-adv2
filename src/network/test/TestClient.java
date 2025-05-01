package network.test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import static util.MyLogger.log;

public class TestClient {

    private static final int PORT = 12345;

    public static void main(String[] args) {

        try(Socket socket = new Socket("localhost", PORT);
            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {
            log("클라이언트 시작");
            log("소켓 연결 : " + socket);

            Scanner scanner = new Scanner(System.in);

            // 서버로부터 메시지 수신(ReadHandler)
            Thread receiveThread = new Thread(() -> {
                try {
                    while (true) {
                        String received = input.readUTF();
                        System.out.println("[서버 메시지] " + received);
                    }
                } catch (IOException e) {
                    System.out.println("서버와의 연결이 종료되었습니다.");
                }
            });
            receiveThread.start();

            // 사용자 입력 및 송신(WriterHandler)
            while (true) {
                System.out.print("보낼 메시지: ");
                String toSend = scanner.nextLine();
                output.writeUTF(toSend);

                if (toSend.equals("exit")) {
                    break;
                }
            }
        } catch (IOException e) {
            log(e);
        }
    }
}

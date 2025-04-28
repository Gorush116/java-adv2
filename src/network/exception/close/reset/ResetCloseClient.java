package network.exception.close.reset;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import static util.MyLogger.log;

public class ResetCloseClient {

    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = new Socket("localhost", 12345);
        log("소켓 연결 : " + socket);
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();

        // client <- server : FIN
        Thread.sleep(1000); // 서버가 close() 호출할 때 까지 잠시 대기

        // client -> server : PUSH[1]
        outputStream.write(1);

        // client <- server : RST
        Thread.sleep(1000); // RST 메시지 전송 대기

        try {
            int read = inputStream.read();
            System.out.println("read = " + read);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            outputStream.write(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

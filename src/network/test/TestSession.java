package network.test;

import network.tcp.SocketCloseUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Objects;

import static util.MyLogger.log;

public class TestSession implements Runnable {

    private final Socket socket;
    private final DataInputStream input;
    private final DataOutputStream output;
    private final TestSessionManager sessionManager;
    private boolean closed = false;

    public TestSession(Socket socket, TestSessionManager sessionManager) throws IOException {
        this.socket = socket;
        this.input = new DataInputStream(socket.getInputStream());
        this.output = new DataOutputStream(socket.getOutputStream());
        this.sessionManager = sessionManager;
        this.sessionManager.add(this);
    }

    @Override
    public void run() {
        try {
            while (true) {
                // 클라이언트로부터 문자 받기
                String received = input.readUTF();
                log("client -> server : " + received);

                // 입장
                if (received.contains("/join")) {
                    System.out.println("join 입력됨 : " + socket);
                    sessionManager.message("이 몸 등장");
                    continue;
                }

                // 메시지
                if (received.contains("/message")) {
                    System.out.println("message 입력됨");

                    // 클라이언트에게 문자 보내기
                    output.writeUTF(received);
                    log("client <- server : " + received);
                    continue;
                }

                // 이름 변경
                if (received.contains("/change")) {
                    System.out.println("change 입력됨");
                    continue;
                }

                // 전체 사용자
                if (Objects.equals(received, "/users")) {
                    System.out.println("users 입력됨");
                    output.writeUTF(sessionManager.getUserList());
                    continue;
                }

                // 종료
                if (received.equals("/exit")) {
                    log("연결 종료 : " + socket);
                    break;
                }
            }
        } catch (IOException e) {
            log(e);
        } finally {
            sessionManager.remove(this);
            close();
        }
    }

    public synchronized void close() {
        if (closed) {
            return;
        }

        SocketCloseUtil.closeAll(socket, input, output);
        closed = true;
        log("연결 종료 : " + socket);
    }

    public Socket getSocket() {
        return socket;
    }

    public DataInputStream getInput() {
        return input;
    }

    public DataOutputStream getOutput() {
        return output;
    }
}

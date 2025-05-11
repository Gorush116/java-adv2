package chat.server;

import java.io.IOException;

public class CommandManagerV2 implements CommandManager {

    private final SessionManager sessionManager;
    private static final String DELIMITER = "\\|";

    public CommandManagerV2(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public void execute(String totalMessage, Session session) throws IOException {

        if (totalMessage.startsWith("/join")) {
            String[] split = totalMessage.split(DELIMITER);
            String username = split[1];
            session.setUsername(username);
            sessionManager.sendAll(username + " 님이 입장했습니다.");
        } else if (totalMessage.startsWith("/message")) {
            // 클라이언트 전체에게 문자 보내기
            String[] split = totalMessage.split(DELIMITER);
            String message = split[1];
            sessionManager.sendAll("[" + session.getUsername() + "] " + message);
        }



        if (totalMessage.startsWith("/exit")) {
            throw new IOException("exit");
        }

    }
}

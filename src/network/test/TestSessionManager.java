package network.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestSessionManager {

    private List<TestSession> sessions = new ArrayList<>();

    public synchronized void add(TestSession session) {
        sessions.add(session);
    }

    public synchronized void remove(TestSession session) {
        sessions.remove(session);
    }

    public synchronized void closeAll() {
        for (TestSession session : sessions) {
            session.close();
        }
        sessions.clear();
    }

    public void message(String message) throws IOException {
        for (TestSession session : sessions) {
            System.out.println("session = " + session.getSocket());
            session.getOutput().writeUTF(message);
        }

    }

    public String getUserList() throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("\n[User List]\n");
        for (TestSession session : sessions) {
            sb.append(session.getSocket()).append("\n");
        }
        return sb.toString();
    }
}

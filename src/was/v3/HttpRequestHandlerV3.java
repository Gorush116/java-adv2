package was.v3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import static java.nio.charset.StandardCharsets.UTF_8;
import static util.MyLogger.log;

public class HttpRequestHandlerV3 implements Runnable{

    private final Socket socket;

    public HttpRequestHandlerV3(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            process();
        } catch (Exception e) {
            log(e);
        }
    }

    private void process() throws IOException {
        try (socket;
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), UTF_8));
             PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), false, UTF_8);
        ) {

            String requestString = requestToString(reader);
            if(requestString.contains("/favicon.ico")) {
                log("favicon 요청");
                return;
            }

            log("HTTP 요청 정보 출력");
            System.out.println(requestString);

            log("HTTP 응답 생성중...");
            if (requestString.startsWith("GET /site1")) {
                site1(printWriter);
            } else if (requestString.startsWith("GET /site2")) {
                site2(printWriter);
            } else if (requestString.startsWith("GET /search")) {
                search(printWriter, requestString);
            } else if (requestString.startsWith("GET / ")) {
                home(printWriter);
            } else {
                notFound(printWriter);
            }
            log("HTTP 응답 전달 완료");
        }
    }

    private static String requestToString(BufferedReader reader) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.isEmpty()) {
                break;
            }
            sb.append(line).append("\n");
        }
        return sb.toString();
    }

    private void responseToClient(PrintWriter writer) {
        // 웹 브라우저에 전달하는 내용

        String body = "<h1>Hello World!</h1>";
        int length = body.getBytes(UTF_8).length;

        StringBuilder sb = new StringBuilder();
        sb.append("HTTP/1.1 200 OK\r\n");
        sb.append("Content-Type: text/html\r\n");
        sb.append("Content-Length: ").append(length).append("\r\n");
        sb.append("\r\n"); // Header, Body 구분 라인
        sb.append(body);

        log("HTTP 응답 정보 출력");
        System.out.println(sb);

        writer.println(sb);
        writer.flush();


    }

    private static void sleep(int millis) {
        try {
            Thread.sleep(millis); // 서버 처리 시간
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void home(PrintWriter printWriter) {
        printWriter.println("HTTP/1.1 200 OK");
        printWriter.println("Content-Type: text/html; charset=UTF-8");
        printWriter.println();
        printWriter.println("<h1>home</h1>");
        printWriter.println("<ul>");
        printWriter.println("<li><a href='/site1'>site1</a></li>");
        printWriter.println("<li><a href='/site2'>site2</a></li>");
        printWriter.println("<li><a href='/search?q=hello'>검색</a></li>");
        printWriter.println("</ul>");
        printWriter.flush();
    }

    private void site1(PrintWriter printWriter) {
        printWriter.println("HTTP/1.1 200 OK");
        printWriter.println("Content-Type: text/html; charset=UTF-8");
        printWriter.println();
        printWriter.println("<h1>site1</h1>");
        printWriter.flush();
    }

    private void site2(PrintWriter printWriter) {
        printWriter.println("HTTP/1.1 200 OK");
        printWriter.println("Content-Type: text/html; charset=UTF-8");
        printWriter.println();
        printWriter.println("<h1>site2</h1>");
        printWriter.flush();
    }

    // "/search?q=hello"
    // GET /search?q=hello HTTP/1.1
    private void search(PrintWriter printWriter, String requestString) {
        int startIndex = requestString.indexOf("q=");
        int endIndex = requestString.indexOf(" ", startIndex + 2);
        String query = requestString.substring(startIndex + 2, endIndex);
        String decode = URLDecoder.decode(query, UTF_8);

        printWriter.println("HTTP/1.1 404 Not Found");
        printWriter.println("Content-Type: text/html; charset=UTF-8");
        printWriter.println();
        printWriter.println("<h1>Search</h1>");
        printWriter.println("<ul>");
        printWriter.println("<li>query : " + query + "</li>");
        printWriter.println("<li>decode : " + decode + "</li>");
        printWriter.println("</ul>");
        printWriter.flush();
    }

    private void notFound(PrintWriter printWriter) {
        printWriter.println("HTTP/1.1 404 Not Found");
        printWriter.println("Content-Type: text/html; charset=UTF-8");
        printWriter.println();
        printWriter.println("<h1>404 페이지를 찾을 수 없습니다.</h1>");
        printWriter.flush();
    }
}

package charset;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class EncodingMain2 {

    private static final Charset EUC_KR = Charset.forName("EUC-KR");
    private static final Charset MS_949 = Charset.forName("MS949");

    public static void main(String[] args) {
        System.out.println("== 영문 ASCII 인코딩 ==");
        test("A", StandardCharsets.US_ASCII, StandardCharsets.US_ASCII); // ASCII 확장(LATIN-1)
        test("A", StandardCharsets.US_ASCII, StandardCharsets.ISO_8859_1); // ASCII 확장(LATIN-1)
        test("A", StandardCharsets.US_ASCII, EUC_KR); // ASCII 포함
        test("A", StandardCharsets.US_ASCII, MS_949); // ASCII 포함
        test("A", StandardCharsets.US_ASCII, StandardCharsets.UTF_8); // ASCII 포함
        test("A", StandardCharsets.US_ASCII, StandardCharsets.UTF_16BE); // 디코딩 실패

        System.out.println("== 한글 인코딩 - 기본");
        test("가", StandardCharsets.US_ASCII, StandardCharsets.US_ASCII); // X
        test("가", StandardCharsets.ISO_8859_1, StandardCharsets.ISO_8859_1); // X
        test("가", EUC_KR, EUC_KR); // O
        test("가", MS_949, MS_949); // O
        test("가", StandardCharsets.UTF_8, StandardCharsets.UTF_8); // O
        test("가", StandardCharsets.UTF_16BE, StandardCharsets.UTF_16BE); // O
    }

    private static void test(String text, Charset encodingCharset, Charset decodingCharset) {
        byte[] encoded = text.getBytes(encodingCharset);
        String decoded = new String(encoded, decodingCharset);
        System.out.printf("%s -> [%s] 인코딩 -> %s %sbyte -> [%s] 디코딩 -> %s\n",
                text, encodingCharset, Arrays.toString(encoded), encoded.length,
                encodingCharset, decoded);
    }

}

package lod.app.util;

public class Literal {
    private Literal() {
    }

    public static String notQuoteJaToJaLiteral(String str) {
        return "\"" + str.substring(0, str.length() - 3) + "\"@ja";
    }

    public static String toJaLiteral(String str) {
        return "\"" + str + "\"@ja";
    }

    public static String toLiteral(String str) {
        return "\"" + str + "\"";
    }
}

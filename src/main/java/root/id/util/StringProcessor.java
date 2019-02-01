package root.id.util;

public class StringProcessor {
    private StringProcessor() {
        throw new IllegalStateException("Это вспомогательный класс. Создание экземпляра не требуется.");
    }



    public static String stringDeleteChars(String str, String chars) {
        return str.replaceAll(chars, "");
    }

    public static String toStdFormat(String str) {
        return stringDeleteChars(str, ",|!|\\.|\\?|\"")
                .toLowerCase()
                .trim()
                .replaceAll("\\s+", " ");
    }
}

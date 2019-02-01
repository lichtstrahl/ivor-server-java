package root.id.util;

import root.id.KeyWord;
import root.id.db.DBContentLoader;

import java.util.LinkedList;
import java.util.List;

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

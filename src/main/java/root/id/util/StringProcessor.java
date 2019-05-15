package root.id.util;

import lombok.Data;

@Data
public class StringProcessor {
    private String value;

    private StringProcessor(String v) {
        value = v;
    }

    public static StringProcessor createFromString(String str) {
        return new StringProcessor(str);
    }

    public static StringProcessor createEmpty() {
        return new StringProcessor("");
    }

    private StringProcessor stringDeleteChars(String chars) {
        value = value.replaceAll(chars, "");
        return this;
    }

    public StringProcessor toStdFormat() {
        stringDeleteChars(",|!|\\.|\\?|\"");
        value = value
                .toLowerCase()
                .trim()
                .replaceAll("\\s+", " ");
        return this;
    }
}

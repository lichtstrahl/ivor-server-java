package root.id.util;

import lombok.Data;
import root.id.service.PythonServer;

import java.io.IOException;

@Data
public class StringProcessor {
    private static final PythonServer morphologyServer = PythonServer.createDefaultPythonServer();
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

    public StringProcessor toStdFormat() throws IOException {
        stringDeleteChars(",|!|\\.|\\?|\"");
        value = value
                .toLowerCase()
                .trim()
                .replaceAll("\\s+", " ");

        value = morphologyServer.parseToNormalAsString(value).trim();
        return this;
    }

    public StringProcessor setNewValue(String str) {
        this.value = str;
        return this;
    }

    private StringProcessor stringDeleteChars(String chars) {
        value = value.replaceAll(chars, "");
        return this;
    }
}

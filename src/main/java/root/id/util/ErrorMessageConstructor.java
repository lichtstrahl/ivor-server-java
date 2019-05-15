package root.id.util;

import java.util.Locale;

public class ErrorMessageConstructor {
    public static String dontLoadList(Class cls) {
        return String.format(Locale.ENGLISH, "Don't load list of \"%s\" from DB", cls.getName());
    }

    public static String dontLoad(Class cls) {
        return String.format(Locale.ENGLISH, "Don't load \"%s\" from DB", cls.getName());
    }
}

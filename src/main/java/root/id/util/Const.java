package root.id.util;

public class Const {
    public static final String IVOR_NO_ANSWER = "Давай поговорим о чем-нибудь другом";
    public static final String COMMAND_NOT_SUPPORTED_IN_WEV_VERSION = "Функционал с использованием команд не доступен в Web-версии. Используйте мобильную версию приложения для полного функционала";
    public static final String IVOR_NO_ANSWER_FOR_QUESTION = "Знакомая фраза. Но не знаю даже, что на это сказать.";
    public static final String IVOR_NO_ANSWER_FOR_KEYWORDS = "Знакомые слова. Но не знаю даже, что на это сказать.";
    public static final String INVALID_PASSWORD_FOR_LOGIN = "Логин найден. Неверный пароль.";
    public static final String INVALID_LOGIN = "Указанный логин не найден.";
    public static final String NULL_POINTER_EXCEPTION = "Ошибка с нулевым указателем";
    public static final String OK = "OK";

    public static class Database {
        public static final String CONNECTION_PARAMETERS = "useSSL=false&autoReconnect=true&useUnicode=yes&characterEncoding=UTF-8";
        public static final String CONNECTION_URL = "jdbc:mysql://us-cdbr-iron-east-03.cleardb.net/heroku_edd0ec02de1466d";
        public static final String USER = "b19511ab5ea6bd";
        public static final String PASSWORD = "19ee449d";
        public static final String ERROR_INSERT_USER = "Ошибка при добавлении нового пользователя";
        public static final String USER_EXISTS = "Такой пользователь уже существует";
        public static final String LOGIN_BUSY = "Такой логин уже занят";
    }
}


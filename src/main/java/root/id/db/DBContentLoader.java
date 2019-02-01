package root.id.db;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Загрузчик для сузностей из БД
 */
public class DBContentLoader<T extends DBInstance> {
    private static Connection CONNECTION;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String unicode = "useSSL=false&autoReconnect=true&useUnicode=yes&characterEncoding=UTF-8";
            CONNECTION = DriverManager.getConnection("jdbc:mysql://us-cdbr-iron-east-03.cleardb.net:3306/heroku_ec366bbe5402271?"+unicode, "bf2f73eeeafeec", "7eeac762");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public List<T> loadAll(Class<? extends DBInstance> cls) {
        return loadFromDB("SELECT * FROM " + cls.getSimpleName(), cls);
    }

    public List<T> loadAnswerForQuestion(long qID) {
        return loadFromDB("SELECT * \n" +
                "FROM answer\n" +
                "WHERE id IN (\n" +
                "  SELECT answerID FROM communication WHERE questionID = " + qID +
                ")", Answer.class);
    }

    public List<T> loadAnswerForKeyWord(long kID) {
        return loadFromDB("SELECT *\n" +
                "FROM answer\n" +
                "WHERE id IN (\n" +
                "  SELECT answerID FROM communicationkey WHERE keyID = " + kID +
                ")", Answer.class);
    }

    private List<T> loadFromDB(String query, Class<? extends DBInstance> cls) {
        try {
            PreparedStatement ps = CONNECTION.prepareStatement(query);
            ResultSet set = ps.executeQuery();

            return parseResultSet(set, cls);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private static <T> List<T> parseResultSet(ResultSet set, Class<? extends DBInstance> type) {
        List<T> result = new LinkedList<>();
        try {
            while (set.next()) {
                result.add((T)(type.getConstructor(ResultSet.class).newInstance(set)));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
}

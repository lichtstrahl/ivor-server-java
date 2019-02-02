package root.id.db;

import root.id.util.Const;

import javax.annotation.Nullable;
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
            CONNECTION = DriverManager.getConnection(
                    Const.Database.CONNECTION_URL+"?"+Const.Database.CONNECTION_PARAMETERS,
                    Const.Database.USER,
                    Const.Database.PASSWORD);
            System.out.println("Соединение с БД успешно создано");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Nullable
    public List<T> loadAll(Class<? extends DBInstance> cls) {
        return loadFromDB("SELECT * FROM " + cls.getSimpleName(), cls);
    }

    @Nullable
    public List<T> loadAnswerForQuestion(long qID) {
        return loadFromDB("SELECT * \n" +
                "FROM answer\n" +
                "WHERE id IN (\n" +
                "  SELECT answerID FROM communication WHERE questionID = " + qID +
                ")", Answer.class);
    }

    @Nullable
    public List<T> loadAnswerForKeyWord(long kID) {
        return loadFromDB("SELECT *\n" +
                "FROM answer\n" +
                "WHERE id IN (\n" +
                "  SELECT answerID FROM communicationkey WHERE keyID = " + kID +
                ")", Answer.class);
    }

    @Nullable
    private List<T> loadFromDB(String query, Class<? extends DBInstance> cls) {
        try {
            PreparedStatement ps = CONNECTION.prepareStatement(query);
            ResultSet set = ps.executeQuery();
            System.out.println("Запрос успешно выполнен");
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

package root.id.db;

import java.lang.reflect.Type;
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
            CONNECTION = DriverManager.getConnection("jdbc:mysql://us-cdbr-iron-east-03.cleardb.net:3306/heroku_ec366bbe5402271", "bf2f73eeeafeec", "7eeac762");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public List<T> loadAll(Class<? extends DBInstance> cls) {

        try {
            PreparedStatement ps = CONNECTION.prepareStatement("SELECT * FROM " + cls.getSimpleName());
            ResultSet set = ps.executeQuery();

            return loadData(set, cls);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    public static <T> List<T> loadData(ResultSet set, Class<? extends DBInstance> type) {
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

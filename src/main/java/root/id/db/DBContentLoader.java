package root.id.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import root.id.dto.UserDTO;
import root.id.util.Const;

import javax.annotation.Nullable;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DBContentLoader {
    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            config.setJdbcUrl(Const.Database.CONNECTION_URL + "?" + Const.Database.CONNECTION_PARAMETERS);
            config.setUsername(Const.Database.USER);
            config.setPassword(Const.Database.PASSWORD);
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            config.addDataSourceProperty("tcpKeepAlive", true);
            config.setIdleTimeout(3000);
            config.setMinimumIdle(0);
            config.setMaximumPoolSize(10);
            ds = new HikariDataSource(config);
        } catch (ClassNotFoundException e) {
            System.out.println("Не удалось найти драйвер");
            System.out.println(e.getMessage());
            System.out.println();
        }
    }


    private static DBContentLoader instance;

    private DBContentLoader() {
//        openConnection();
    }

    synchronized public static DBContentLoader getInstance() {
        if (instance == null)
            instance = new DBContentLoader();
        return instance;
    }

    public boolean insertNewUser(UserDTO user) {
        String query =
                "insert into client " +
                        "(realName, login, pass, age, city, email, lastEntry, admin)\n" +
                "values  ('"+user.realName+ "', '" + user.login + "', '" + user.pass + "', " + user.age + ", '" + user.city + "', '" + user.email + "', '" + user.lastEntry + "', " + user.admin +");";
        return modifyDataInDB(query);
    }

    public List<Client> getUser(UserDTO user) {
        String getUsers =
                "SELECT *\n" +
                        "FROM client\n" +
                        "WHERE (" +
                        "realName = '"+user.realName+"' AND " +
                        "login = '"+user.login+"' AND " +
                        "pass = '"+user.pass+"' AND " +
                        "age = "+user.age+" AND " +
                        "city = '"+user.city+"' AND " +
                        "email = '"+user.email+"' AND " +
                        "lastEntry = '"+user.lastEntry+"'" +
                        ")";
        return loadFromDB(getUsers, Client.class);
    }

    public List<Client> getUser(String login) {
        String query =
                "SELECT * " +
                "FROM client " +
                "WHERE login = '"+login+"'";
        return loadFromDB(query, Client.class);
    }

    @Nullable
    public <T extends DBInstance> List<T> loadAll(Class<? extends DBInstance> cls) {
        return loadFromDB("SELECT * FROM " + cls.getSimpleName(), cls);
    }


    @Nullable
    public <T extends DBInstance> List<T> loadAnswerForQuestion(long qID) {
        return loadFromDB("SELECT * \n" +
                "FROM answer\n" +
                "WHERE id IN (\n" +
                "  SELECT answerID FROM communication WHERE questionID = " + qID +
                ")", Answer.class);
    }

    @Nullable
    public <T extends DBInstance> List<T> loadAnswerForKeyWord(long kID) {
        return loadFromDB("SELECT *\n" +
                "FROM answer\n" +
                "WHERE id IN (\n" +
                "  SELECT answerID FROM communicationkey WHERE keyID = " + kID +
                ")", Answer.class);
    }

    public CommunicationKey getCommunicationKey(long answerID, long keyID) {
        List<CommunicationKey> com =  loadFromDB("SELECT *\n" +
                "FROM communicationkey\n" +
                "WHERE (answerID = "+answerID+" AND keyID = "+keyID+")", CommunicationKey.class);
        return com.get(0);
    }

    public CommunicationKey getCommunicationKey(long id) {
        List<CommunicationKey> com = loadFromDB("SELECT *\n" +
                "FROM communicationkey\n" +
                "WHERE id = " + id, CommunicationKey.class);
        return com.get(0);
    }

    public boolean evaluationCommunicationKey(long id, long eval) {
        CommunicationKey c = getCommunicationKey(id);
        int curEval = c.getCorrect();
        int curPower = c.getPower();

        if (eval > 0) curEval++;
        if (eval < 0) curEval--;

        String query = "update communicationkey\n" +
                "set correct = "+curEval+", power = "+ ++curPower +"\n" +
                "where id = "+id+"";
        return modifyDataInDB(query);
    }

    public Communication getCommunication(long answerID, long qID) {
        List<Communication> com =  loadFromDB("SELECT *\n" +
                "FROM communication\n" +
                "WHERE (answerID = "+answerID+" AND questionID = "+qID+")", Communication.class);
        return com.get(0);
    }

    public Communication getCommunication(long id) {
        List<Communication> com = loadFromDB("SELECT *\n" +
                "FROM communication\n" +
                "WHERE id = " + id, Communication.class);
        return com.get(0);
    }

    public boolean evaluationCommunication(long id, int eval) {
        Communication c = getCommunication(id);
        int curEval = c.getCorrect();
        int curPower = c.getPower();

        if (eval > 0) curEval++;
        if (eval < 0) curEval--;

        String query = "update communication\n" +
                "set correct = "+curEval+", power = "+ ++curPower +"\n" +
                "where id = "+id+"";

        return modifyDataInDB(query);
    }

    @Nullable
    private <T extends DBInstance> List<T> loadFromDB(String query, Class<? extends DBInstance> cls) {
        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet set = ps.executeQuery()) {
            List<T> result =  parseResultSet(set, cls);
            connection.close();
            return result;
        } catch (SQLException e1) {
            System.out.println("Исключение loadFrom DB");
            System.out.println(e1.getMessage());
        }
        return null;
    }

    @Nullable
    private boolean modifyDataInDB(String query) {
        try (   Connection connection = ds.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)){
                ps.executeUpdate(query);
                connection.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Исключение modifyDataInDB");
            System.out.println(e.getMessage());
            return false;
        }
    }

    private <T> List<T> parseResultSet(ResultSet set, Class<? extends DBInstance> type) {
        List<T> result = new LinkedList<>();
        try {
            while (set.next()) {
                result.add((T)(type.getConstructor(ResultSet.class).newInstance(set)));
            }
        } catch (Exception e) {
            System.out.println("Исключение parseResultSet");
            System.out.println(e.getMessage());
        }
        return result;
    }
}

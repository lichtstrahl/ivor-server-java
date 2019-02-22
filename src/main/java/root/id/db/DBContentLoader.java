package root.id.db;

import root.id.dto.UserDTO;
import root.id.util.Const;

import javax.annotation.Nullable;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Загрузчик для сузностей из БД
 */
public class DBContentLoader {
    private static DBContentLoader instance;
    private Connection CONNECTION;

    private DBContentLoader() {
        openConnection();
    }

    public void openConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            CONNECTION = DriverManager.getConnection(
                    Const.Database.CONNECTION_URL+"?"+Const.Database.CONNECTION_PARAMETERS,
                    Const.Database.USER,
                    Const.Database.PASSWORD);
            System.out.println("Соединение с БД успешно создано");
        } catch (Exception e) {
            System.out.println("Исключение конструктор DBContentLoader");
            System.out.println(e.getMessage());
        }
    }

    public void refreshConnection() {
        try {
            System.out.println("Подключение: " + CONNECTION.isClosed());
            CONNECTION.close();
            openConnection();
            System.out.println("Подключение: " + CONNECTION.isClosed());
            System.out.println("Подключение обновлено");
        } catch (SQLException e) {
            System.out.println("Исключение при обновлении подключения");
            System.out.println(e.getMessage());
        }
    }

    synchronized public static DBContentLoader getInstance() {
        if (instance == null)
            instance = new DBContentLoader();
        instance.refreshConnection();
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
        try (PreparedStatement ps = CONNECTION.prepareStatement(query);
             ResultSet set = ps.executeQuery()) {

            return parseResultSet(set, cls);
        } catch (SQLException e1) {
            System.out.println("Исключение loadFrom DB");
            System.out.println(e1.getMessage());
        }
        return null;
    }

    @Nullable
    private boolean modifyDataInDB(String query) {
        try (PreparedStatement ps = CONNECTION.prepareStatement(query)){
            ps.executeUpdate(query);
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

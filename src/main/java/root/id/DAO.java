package root.id;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DAO {
    public static Connection getConnection() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://us-cdbr-iron-east-03.cleardb.net:3306/heroku_ec366bbe5402271", "bf2f73eeeafeec", "7eeac762");
    }


    public static List<Command> getCommand() {
        List<Command> result = new LinkedList<>();

        try {
            Connection c = getConnection();
            PreparedStatement ps = c.prepareStatement("SELECT * FROM command");
            ResultSet set = ps.executeQuery();

            while (set.next()) {
                long id = set.getInt("id");
                String s = set.getString("cmd");
                result.add(new Command(id, s));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return result;
    }
}

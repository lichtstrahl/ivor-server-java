package root.id.db;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Command extends DBInstance {
    private long id;
    private String cmd;

    public Command(ResultSet set)  throws SQLException {
        super(set);
        this.cmd = set.getString("cmd");
    }

    public long getId() {
        return id;
    }

    public String getCmd() {
        return cmd;
    }

}

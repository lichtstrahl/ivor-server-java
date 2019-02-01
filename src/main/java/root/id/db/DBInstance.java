package root.id.db;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DBInstance {
    protected long id;

    public DBInstance(ResultSet set) throws SQLException{
        this.id = set.getLong("id");
    }

    public long getId() {
        return id;
    }
}

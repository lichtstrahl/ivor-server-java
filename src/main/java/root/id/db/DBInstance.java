package root.id.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class DBInstance {
    protected long id;

    public DBInstance(ResultSet set) throws SQLException{
        set.getLong("id");
    }

    public long getId() {
        return id;
    }
}

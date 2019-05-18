package root.id.db;

import com.google.gson.annotations.SerializedName;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DBInstance {
    @SerializedName("id")
    protected long id;

    public DBInstance(ResultSet set) throws SQLException{
        this.id = set.getLong("id");
    }

    public DBInstance() {}

    public long getId() {
        return id;
    }
}

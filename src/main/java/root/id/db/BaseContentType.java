package root.id.db;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BaseContentType extends DBInstance {
    protected String content;

    public BaseContentType(ResultSet set) throws SQLException {
        super(set);
        content = set.getString("content");
    }

    public String getContent() {
        return content;
    }
}

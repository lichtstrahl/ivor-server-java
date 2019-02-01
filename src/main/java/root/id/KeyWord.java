package root.id;

import root.id.db.BaseContentType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class KeyWord extends BaseContentType {
    public KeyWord(ResultSet set) throws SQLException {
        super(set);
    }
}

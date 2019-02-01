package root.id.db;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Answer extends BaseContentType {
    public Answer(ResultSet set) throws SQLException {
        super(set);
    }
}

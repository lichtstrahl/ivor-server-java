package root.id.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class Question extends BaseContentType {
    public Question(ResultSet set) throws SQLException {
        super(set);
    }
}

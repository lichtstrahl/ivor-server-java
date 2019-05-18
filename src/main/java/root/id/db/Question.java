package root.id.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class Question extends BaseContentType implements DBEntity {
    public static final String TABLE_NAME = "question";
    public Question() {}

    public Question(ResultSet set) throws SQLException {
        super(set);
    }

    @Override
    public String buildInsertQuery() {
        return "insert into question " +
                "(content)" +
                "values ('" + content + "')";
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static Question fromContent(String content) {
        Question q = new Question();
        q.content = content;
        return q;
    }
}

package root.id.db;

import java.sql.ResultSet;
import java.sql.SQLException;


public class Answer extends BaseContentType implements DBEntity {
    public static final String TABLE_NAME = "answer";

    public Answer() {}

    public Answer(ResultSet set) throws SQLException {
        super(set);
    }

    @Override
    public String buildInsertQuery() {
        return "insert into answer" +
                "(content)" +
                "values ('"+content+"')";
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static Answer fromContent(String content) {
        Answer a = new Answer();
        a.content = content;
        return a;
    }

}

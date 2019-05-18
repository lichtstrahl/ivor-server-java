package root.id.db;

import com.google.gson.annotations.SerializedName;

import java.sql.ResultSet;
import java.sql.SQLException;

abstract public class BaseContentType extends DBInstance {
    @SerializedName("content")
    protected String content;

    public BaseContentType() {}

    public BaseContentType(ResultSet set) throws SQLException {
        super(set);
        content = set.getString("content");
    }

    public String getContent() {
        return content;
    }
}

package root.id.db;

public interface DBEntity {
    String buildInsertQuery();
    String getTableName();
}

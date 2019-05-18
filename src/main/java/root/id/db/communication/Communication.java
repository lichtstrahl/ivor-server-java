package root.id.db.communication;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import root.id.db.DBEntity;
import root.id.db.DBInstance;

import javax.websocket.server.ServerEndpoint;
import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class Communication extends DBInstance implements DBEntityCorrected, DBEntity {
    public static final String TABLE_NAME = "communication";
    @SerializedName("questionID")
    private long questionID;
    @SerializedName("answerID")
    private long answerID;
    @SerializedName("power")
    private int power;
    @SerializedName("correct")
    private int correct;

    private Communication() {}

    public Communication(ResultSet set) throws SQLException {
        super(set);
        this.questionID = set.getLong("questionID");
        this.answerID = set.getLong("answerID");
        this.power = set.getInt("power");
        this.correct = set.getInt("correct");
    }

    public static Communication create(long aID, long qID) {
        Communication c = new Communication();
        c.answerID = aID;
        c.questionID = qID;
        c.power = 0;
        c.correct = 0;
        return c;
    }

    public long getQuestionID() {
        return questionID;
    }

    public long getAnswerID() {
        return answerID;
    }

    public int getPower() {
        return power;
    }

    @Override
    public int getCorrect() {
        return correct;
    }

    @Override
    public long getID() {
        return id;
    }

    @Override
    public String buildInsertQuery() {
        return "insert into communication " +
                "(questionID, answerID, power, correct) " +
                "values (" + questionID + ", " + answerID + ", " + power + ", " + correct + ")";
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
}

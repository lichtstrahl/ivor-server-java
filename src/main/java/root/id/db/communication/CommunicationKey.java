package root.id.db.communication;

import root.id.db.DBInstance;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommunicationKey extends DBInstance implements DBEntityCorrected {
    private long answerID;
    private long keyID;
    private int correct;
    private int power;

    public CommunicationKey(ResultSet set) throws SQLException {
        super(set);
        this.answerID = set.getLong("answerID");
        this.keyID = set.getLong("keyID");
        this.correct = set.getInt("correct");
        this.power = set.getInt("power");
    }

    public long getAnswerID() {
        return answerID;
    }

    public long getKeyID() {
        return keyID;
    }

    @Override
    public int getCorrect() {
        return correct;
    }

    public int getPower() {
        return power;
    }

    @Override
    public long getID() {
        return id;
    }
}

package root.id.db;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Communication extends DBInstance{
    protected long id;
    private long questionID;
    private long answerID;
    private int power;
    private int correct;

    public Communication(ResultSet set) throws SQLException {
        super(set);
        this.questionID = set.getLong("questionID");
        this.answerID = set.getLong("answerID");
        this.power = set.getInt("power");
        this.correct = set.getInt("correct");
    }

    @Override
    public long getId() {
        return id;
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

    public int getCorrect() {
        return correct;
    }
}

package root.id.service;

import root.id.Const;
import root.id.db.Answer;
import root.id.db.DBContentLoader;
import root.id.db.communication.Communication;
import root.id.dto.AnswerDTO;
import root.id.dto.ServerAnswer;

public class AnswerService {

    public static ServerAnswer insertNewAnswer(AnswerDTO answer) {
        boolean append = DBContentLoader.getInstance().insertNewEntity(Answer.fromContent(answer.getAnswer()));
        if (!append) return ServerAnswer.answerFail(Const.Database.ERROR_INSERT_ANSWER);

        Answer newAnswer = DBContentLoader.getInstance().searchAnswer(answer.getAnswer());
        Communication communication = Communication.create(newAnswer.getId(), answer.getQuestionID());
        append = DBContentLoader.getInstance().insertNewEntity(communication);
        if (!append) return ServerAnswer.answerFail(Const.Database.ERROR_INSERT_COMMUNICATION);

        return ServerAnswer.answerOK(communication);
    }
}

package root.id.service;

import root.id.Const;
import root.id.db.Answer;
import root.id.db.DBContentLoader;
import root.id.db.communication.Communication;
import root.id.dto.AnswerDTO;
import root.id.dto.ServerAnswer;
import root.id.util.StringProcessor;

public class AnswerService {
    private static final StringProcessor STRING_PROCESSOR = StringProcessor.createEmpty();

    public static ServerAnswer insertNewAnswer(AnswerDTO answer) {
        String content = STRING_PROCESSOR
                .setNewValue(answer.getAnswer())
                .deleteSpace()
                .getValue();
        boolean append = DBContentLoader.getInstance().insertNewEntity(Answer.fromContent(content));
        if (!append) return ServerAnswer.answerFail(Const.Database.ERROR_INSERT_ANSWER);

        Answer newAnswer = DBContentLoader.getInstance().searchAnswer(content);
        Communication communication = Communication.create(newAnswer.getId(), answer.getQuestionID());
        append = DBContentLoader.getInstance().insertNewEntity(communication);
        if (!append) return ServerAnswer.answerFail(Const.Database.ERROR_INSERT_COMMUNICATION);

        return ServerAnswer.answerOK(communication);
    }
}

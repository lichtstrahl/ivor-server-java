package root.id.service;

import org.springframework.util.Assert;
import root.id.Const;
import root.id.db.DBContentLoader;
import root.id.db.Question;
import root.id.dto.ServerAnswer;
import root.id.util.ErrorMessageConstructor;
import root.id.util.StringProcessor;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class QuestionService {
    private static final StringProcessor STRING_PROCESSOR = StringProcessor.createEmpty();
    private static final Random random = new Random();
    /**
     * Вставка нового вопроса. Прежде чем добавить, необходимо удостовериться, что в БД не существует такого же запроса.
     * При успешном добавлении возвращаем новый вопрос.
     */
    public static ServerAnswer insertQuestion(String content) throws IOException {
        String question = STRING_PROCESSOR
                .setNewValue(content)
                .toStdFormat()
                .getValue();
        Question q = RequestService.getInstance().isQuestion(question);
        if (q == null && DBContentLoader.getInstance().insertNewEntity(Question.fromContent(question))) {
            Question newQuestion = DBContentLoader.getInstance().searchQuestion(question).get(0);
            return ServerAnswer.answerOK(newQuestion);
        } else {
            return ServerAnswer.answerFail(q != null ? Const.Database.NOT_UNIQUE_QUESTION : Const.Database.ERROR_INSERT_QUESTION);
        }
    }

    /**
     * Получаем случайный вопрос
     */
    public static ServerAnswer getRandomQuestion() {
        List<Question> questions = DBContentLoader.getInstance().loadAll(Question.class);
        Assert.notNull(questions, ErrorMessageConstructor.dontLoad(Question.class));

        random.setSeed(System.currentTimeMillis());
        if (questions.size() != 0) {
            int r = random.nextInt(questions.size());
            return ServerAnswer.answerOK(questions.get(r));
        } else
            return ServerAnswer.answerFail(Const.NEED_ADD_QUESTION_TO_DB);
    }
}

package root.id.service;

import org.springframework.util.Assert;
import root.id.db.DBContentLoader;
import root.id.db.KeyWord;
import root.id.db.Question;
import root.id.dto.ServerAnswer;
import root.id.util.ErrorMessageConstructor;

import java.util.List;

public class DBService {
    private DBService() {
        throw new IllegalStateException("Это вспомогательный класс. Создание экземпляра не требуется.");
    }

    /**
     * Находим в БД все ключевые слова, у которых есть связи
     */
    public static ServerAnswer<List<KeyWord>> findConnectedKW() {
        List<KeyWord> list = DBContentLoader.getInstance().getConnectedKeyWord();
        Assert.notNull(list, ErrorMessageConstructor.dontLoad(KeyWord.class));

        return ServerAnswer.answerOK(list);
    }

    /**
     * Находим в БД все вопросы, у которых есть связи
     */
    public static ServerAnswer<List<Question>> findConnectedQ() {
        List<Question> list = DBContentLoader.getInstance().getConnectedQuestion();
        Assert.notNull(list, ErrorMessageConstructor.dontLoad(Question.class));

        return ServerAnswer.answerOK(list);
    }
}

package root.id.util;

import root.id.KeyWord;
import root.id.db.*;
import root.id.dto.RequestDTO;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class RequestProcessor {
    private static DBContentLoader<Command> commandLoader = new DBContentLoader<>();
    private static DBContentLoader<Answer> answerLoader = new DBContentLoader<>();
    private static DBContentLoader<Question> questionLoader = new DBContentLoader<>();
    private static DBContentLoader<Communication> communicationLoader = new DBContentLoader<>();
    private static DBContentLoader<KeyWord> keywordLoader = new DBContentLoader<>();


    private RequestProcessor() {
        throw new IllegalStateException("Это вспомогательный класс. Создание экземпляра не требуется.");
    }

    public static String processingRequest(RequestDTO request) {
        String liteString = StringProcessor.toStdFormat(request.getRequest());

        Command c = isCommand(liteString);
        if (c != null) {
            return "Это команда: " + c.getCmd();
        }

        Question q = isQuestion(liteString);
        if (q != null) {
            return "Это вопрос: " + q.getContent();
        }

        List<KeyWord> keyWords = findKeyWords(liteString);

        if (!keyWords.isEmpty()) {
            return "Найдены ключевые слова: n=" + keyWords.size();
        }

        return Const.IVOR_NO_ANSWER;
    }

    public static List<KeyWord> findKeyWords(String string) {
        List<KeyWord> list = new LinkedList<>();

        for (KeyWord word : keywordLoader.loadAll(KeyWord.class)) {
            if (string.contains(word.getContent()))
                list.add(word);
        }

        return list;
    }

    public static Command isCommand(String str) {
        HashSet<String> set = new HashSet<>(Arrays.asList(str.split(" ")));
        List<Command> commands = commandLoader.loadAll(Command.class);
        for (Command cmd : commands) {
            HashSet<String> cmdSet = new HashSet<>(Arrays.asList(cmd.getCmd().split(" ")));
            if (set.containsAll(cmdSet)) {
                return cmd;
            }
        }
        return null;
    }

    public static Question isQuestion(String str) {
        String[] words = str.split(" ");
        HashSet<String> set = new HashSet<>(Arrays.asList(words));
        // Обработка Question
        List<Question> questions = questionLoader.loadAll(Question.class);

        for (Question q : questions) {
            String[] qwords = q.getContent().split(" ");
            HashSet<String> qSet = new HashSet<>(Arrays.asList(qwords));
            if (qSet.equals(set)) {
                return q;
            }
        }
        return null;
    }
}

package root.id.util;

import root.id.KeyWord;
import root.id.db.*;
import root.id.dto.RequestDTO;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class RequestProcessor {
    private static DBContentLoader<Command> commandLoader = new DBContentLoader<>();
    private static DBContentLoader<Answer> answerLoader = new DBContentLoader<>();
    private static DBContentLoader<Question> questionLoader = new DBContentLoader<>();
    private static DBContentLoader<Communication> communicationLoader = new DBContentLoader<>();
    private static DBContentLoader<CommunicationKey> communicationKeyLoader = new DBContentLoader<>();
    private static DBContentLoader<KeyWord> keywordLoader = new DBContentLoader<>();
    private static Random random = new Random();


    private RequestProcessor() {
        throw new IllegalStateException("Это вспомогательный класс. Создание экземпляра не требуется.");
    }

    public static String processingRequest(RequestDTO request) {
        String liteString = StringProcessor.toStdFormat(request.getRequest());
        random.setSeed(Calendar.getInstance().getTimeInMillis());
        Command c = isCommand(liteString);
        if (c != null) {
            return processingCommand(c);
        }

        Question q = isQuestion(liteString);
        if (q != null) {
            return processingQuestion(q);
        }

        List<KeyWord> keyWords = findKeyWords(liteString);
        if (!keyWords.isEmpty()) {
            return processingKeyWord(keyWords);
        }

        return Const.IVOR_NO_ANSWER;
    }

    private static List<KeyWord> findKeyWords(String string) {
        List<KeyWord> list = new LinkedList<>();

        for (KeyWord word : keywordLoader.loadAll(KeyWord.class)) {
            if (string.contains(word.getContent()))
                list.add(word);
        }

        return list;
    }

    @Nullable
    private static Command isCommand(String str) {
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

    @Nullable
    private static Question isQuestion(String str) {
        String[] words = str.split(" ");
        HashSet<String> set = new HashSet<>(Arrays.asList(words));
        // Обработка Question
        List<Question> questions = questionLoader.loadAll(Question.class);

        for (Question q : questions) {
            String[] qWords = q.getContent().split(" ");
            HashSet<String> qSet = new HashSet<>(Arrays.asList(qWords));
            if (qSet.equals(set)) {
                return q;
            }
        }
        return null;
    }

    private static String processingCommand(Command c) {
        return Const.COMMAND_NOT_SUPPORTED_IN_WEV_VERSION;
    }

    // TODO Здесь также необходимо возвращать и коммуникацию (Communication)
    private static String processingQuestion(@Nonnull Question q) {
        List<Answer> answers = answerLoader.loadAnswerForQuestion(q.getId());
        if (!answers.isEmpty()) {
            int r = random.nextInt(answers.size());
            Answer answer = answers.get(r);
            return answer.getContent();
        }

        return Const.IVOR_NO_ANSWER_FOR_QUESTION;
    }

    // TODO Здесь также необходимо возвращать и коммуникацию (CommunicationKey)
    private static String processingKeyWord(List<KeyWord> words) {
        for (KeyWord word : words) {
            List<Answer> answers = answerLoader.loadAnswerForKeyWord(word.getId());
            if (answers.isEmpty())
                continue;

            int r = random.nextInt(answers.size());
            Answer answer =  answers.get(r);
            return answer.getContent();
        }

        return Const.IVOR_NO_ANSWER_FOR_KEYWORDS;
    }
}

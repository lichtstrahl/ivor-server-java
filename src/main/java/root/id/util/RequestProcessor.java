package root.id.util;

import root.id.KeyWord;
import root.id.db.*;
import root.id.dto.RequestDTO;
import root.id.dto.ServerAnswer;
import root.id.dto.UserDTO;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class RequestProcessor {
    private static DBContentLoader<Command> commandLoader = new DBContentLoader<>();
    private static DBContentLoader<Answer> answerLoader = new DBContentLoader<>();
    private static DBContentLoader<Question> questionLoader = new DBContentLoader<>();
    private static DBContentLoader<Communication> communicationLoader = new DBContentLoader<>();
    private static DBContentLoader<CommunicationKey> communicationKeyLoader = new DBContentLoader<>();
    private static DBContentLoader<KeyWord> keywordLoader = new DBContentLoader<>();
    private static DBContentLoader<Client> userLoader = new DBContentLoader<>();
    private static Random random = new Random();

    private RequestProcessor() {
        throw new IllegalStateException("Это вспомогательный класс. Создание экземпляра не требуется.");
    }

    public static ServerAnswer processingRequest(RequestDTO request) {
        String liteString = StringProcessor.toStdFormat(request.getRequest());
        random.setSeed(Calendar.getInstance().getTimeInMillis());

        String answer = Const.IVOR_NO_ANSWER;
        Command c = isCommand(liteString);
        if (c != null) {
            answer = processingCommand(c);
        } else {
            Question q = isQuestion(liteString);
            if (q != null) {
                answer =  processingQuestion(q);
            } else {
                List<KeyWord> keyWords = findKeyWords(liteString);
                if (!keyWords.isEmpty()) {
                    answer =  processingKeyWord(keyWords);
                }
            }
        }
        return ServerAnswer.answerOK(answer);
    }

    public static String getJSONFromBody(HttpServletRequest request) throws IOException {
        StringBuilder jb = new StringBuilder();

        String line;
        BufferedReader reader = request.getReader();
        while ((line = reader.readLine()) != null)
            jb.append(line);

        return jb.toString();
    }

    /**
        Если возвраается пустая строка, значит пользователь найден.
        В противном случае возвращается описание проблемы.
     */
    public static ServerAnswer checkUsersLoginPass(UserDTO user) {
        List<Client> clients = userLoader.loadAll(Client.class);
        for (Client client : clients) {
            if (client.getLogin().equals(user.login)) {
                if (client.getPass().equals(user.pass)) {
                    return ServerAnswer.answerOK(user.loadFromDBClient(client));
                }
                return ServerAnswer.answerFail(Const.INVALID_PASSWORD_FOR_LOGIN);
            }
        }
        return ServerAnswer.answerFail(Const.INVALID_LOGIN);
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

package root.id.util;

import root.id.KeyWord;
import root.id.db.*;
import root.id.dto.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class RequestProcessor {
    private static Random random = new Random();

    private RequestProcessor() {
        throw new IllegalStateException("Это вспомогательный класс. Создание экземпляра не требуется.");
    }

    public static ServerAnswer processingRequest(RequestDTO request) {
        String liteString = StringProcessor.toStdFormat(request.getRequest());
        random.setSeed(Calendar.getInstance().getTimeInMillis());

        Command c = isCommand(liteString);
        if (c != null) {
            return ServerAnswer.answerOK(processingCommand(c));
        } else {
            Question q = isQuestion(liteString);
            if (q != null) {
                return processingQuestion(q);
            } else {
                List<KeyWord> keyWords = findKeyWords(liteString);
                if (!keyWords.isEmpty()) {
                    return  processingKeyWord(keyWords);
                }
            }
        }

        return ServerAnswer.answerOK(AnswerForQuestion.valueOf(Const.IVOR_NO_ANSWER, null));
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
        List<Client> clients = DBContentLoader.getInstance().loadAll(Client.class);
        if (clients == null) {
            return ServerAnswer.answerFail(Const.NULL_POINTER_EXCEPTION + "RequestProcessor:checkUsersLoginPass");
        }

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

    public static ServerAnswer insertNewUser(UserDTO user) {
        if (DBContentLoader.getInstance().insertNewUser(user)) {
            return ServerAnswer.answerOK(null);
        } else {
            return ServerAnswer.answerFail(Const.Database.ERROR_INSERT_USER);
        }
    }

    private static List<KeyWord> findKeyWords(String string) {
        List<KeyWord> list = new LinkedList<>();
        List<KeyWord> words = DBContentLoader.getInstance().loadAll(KeyWord.class);

        for (KeyWord word : words) {
            if (string.contains(word.getContent()))
                list.add(word);
        }

        return list;
    }

    @Nullable
    private static Command isCommand(String str) {
        HashSet<String> set = new HashSet<>(Arrays.asList(str.split(" ")));
        List<Command> commands = DBContentLoader.getInstance().loadAll(Command.class);

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
        List<Question> questions = DBContentLoader.getInstance().loadAll(Question.class);

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
    private static ServerAnswer<AnswerForQuestion> processingQuestion(@Nonnull Question q) {
        List<Answer> answers = DBContentLoader.getInstance().loadAnswerForQuestion(q.getId());
        if (!answers.isEmpty()) {
            int r = random.nextInt(answers.size());
            Answer answer = answers.get(r);
            Communication com = DBContentLoader.getInstance().getCommunication(answer.getId(), q.getId());
            return ServerAnswer.answerOK(AnswerForQuestion.valueOf(answer.getContent(), com.getId()));
        }

        return ServerAnswer.answerOK(AnswerForQuestion.valueOf(Const.IVOR_NO_ANSWER_FOR_QUESTION, null));
    }

    // TODO Здесь также необходимо возвращать и коммуникацию (CommunicationKey)
    private static ServerAnswer<AnswerForKeyWord> processingKeyWord(List<KeyWord> words) {
        for (KeyWord word : words) {
            List<Answer> answers = DBContentLoader.getInstance().loadAnswerForKeyWord(word.getId());
            if (answers.isEmpty())
                continue;

            int r = random.nextInt(answers.size());
            Answer answer =  answers.get(r);
            CommunicationKey comKey = DBContentLoader.getInstance().getCommunicationKey(answer.getId(), word.getId());
            return ServerAnswer.answerOK(AnswerForKeyWord.valueOf(answer.getContent(), comKey.getId()));
        }

        return ServerAnswer.answerOK(AnswerForKeyWord.valueOf(Const.IVOR_NO_ANSWER_FOR_KEYWORDS, null));
    }

    public static String init() {
        String msg = "RequestProcessor is init";
        System.out.println(msg);
        return msg;
    }
}

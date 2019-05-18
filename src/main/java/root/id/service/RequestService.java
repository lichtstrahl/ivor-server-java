package root.id.service;

import org.springframework.util.Assert;
import root.id.Const;
import root.id.db.*;
import root.id.dto.*;
import root.id.util.ErrorMessageConstructor;
import root.id.util.StringProcessor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.util.*;

public class RequestService {
    private static Random random = new Random();
    private final StringProcessor stringProcessor = StringProcessor.createEmpty();

    private RequestService() {
    }

    public static RequestService getInstance() {
        return new RequestService();
    }

    public ServerAnswer processingRequest(RequestDTO request) throws IOException {
        String liteString = stringProcessor
                                        .setNewValue(request.request)
                                        .createFromString(request.getRequest())
                                        .toStdFormat()
                                        .getValue();
        random.setSeed(Calendar.getInstance().getTimeInMillis());
        return ServerAnswer.answerOK(liteString);
        /*
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
        */
    }

    private List<KeyWord> findKeyWords(String string) {
        List<KeyWord> list = new LinkedList<>();
        List<KeyWord> words = DBContentLoader.getInstance().loadAll(KeyWord.class);
        Assert.notNull(words, ErrorMessageConstructor.dontLoadList(KeyWord.class));

        for (KeyWord word : words) {
            if (string.contains(word.getContent()))
                list.add(word);
        }

        return list;
    }

    @Nullable
    private Command isCommand(String str) {
        HashSet<String> set = new HashSet<>(Arrays.asList(str.split(" ")));
        List<Command> commands = DBContentLoader.getInstance().loadAll(Command.class);
        Assert.notNull(commands, ErrorMessageConstructor.dontLoadList(Command.class));

        for (Command cmd : commands) {
            HashSet<String> cmdSet = new HashSet<>(Arrays.asList(cmd.getCmd().split(" ")));
            if (set.containsAll(cmdSet)) {
                return cmd;
            }
        }
        return null;
    }

    @Nullable
    private Question isQuestion(String str) {
        List<String> words = Arrays.asList(str.split(" "));
        HashSet<String> set = new HashSet<>(words);
        // Обработка Question
        List<Question> questions = DBContentLoader.getInstance().loadAll(Question.class);
        Assert.notNull(questions, "Don't load Questions from DB");

        for (Question q : questions) {
            String[] qWords = q.getContent().split(" ");
            HashSet<String> qSet = new HashSet<>(Arrays.asList(qWords));
            if (qSet.containsAll(set)) {
                return q;
            }
        }
        return null;
    }

    private String processingCommand(Command c) {
        return Const.COMMAND_NOT_SUPPORTED_IN_WEV_VERSION;
    }

    private ServerAnswer<AnswerForQuestion> processingQuestion(@Nonnull Question q) {
        List<Answer> answers = DBContentLoader.getInstance().loadAnswerForQuestion(q.getId());
        Assert.notNull(answers, ErrorMessageConstructor.dontLoadList(Answer.class));
        if (!answers.isEmpty()) {
            int r = random.nextInt(answers.size());
            Answer answer = answers.get(r);
            Communication com = DBContentLoader.getInstance().getCommunication(answer.getId(), q.getId());
            return ServerAnswer.answerOK(AnswerForQuestion.valueOf(answer.getContent(), com.getId()));
        }

        return ServerAnswer.answerOK(AnswerForQuestion.valueOf(Const.IVOR_NO_ANSWER_FOR_QUESTION, null));
    }

    private ServerAnswer<AnswerForKeyWord> processingKeyWord(List<KeyWord> words) {
        for (KeyWord word : words) {
            List<Answer> answers = DBContentLoader.getInstance().loadAnswerForKeyWord(word.getId());
            Assert.notNull(answers, ErrorMessageConstructor.dontLoadList(Answer.class));
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
        String msg = "RequestService is init";
        System.out.println(msg);
        return msg;
    }
}

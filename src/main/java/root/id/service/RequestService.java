package root.id.service;

import lombok.Data;
import org.springframework.util.Assert;
import root.id.Const;
import root.id.db.*;
import root.id.db.communication.Communication;
import root.id.db.communication.CommunicationKey;
import root.id.db.communication.DBEntityCorrected;
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

    /**
     * Поступивший запрос делится на предложения. Каждое предложение обрабатывается отдельно.
     * Собирается список ответов, подобранных к каждому выражению.
     * После чего вычисляется наиболее корректный из подобранных ответов.
     * Он и выдается. В слуаче их отсутствия - шаблонная фраза.
     * Для каждого предложения сперва проверяется соответствие вопросу, потом уже ключевому слову.
     * Предполагается, что неважен порядок предложений. Важно, насколько хорошо можно подобрать на них ответ.
     */
    public ServerAnswer processingRequest(RequestDTO request) throws IOException {
        String[] array = StringProcessor.split(request.getRequest());
        List<HolderAnswerAndCommunication> list = new LinkedList<>();
        random.setSeed(Calendar.getInstance().getTimeInMillis());

        for (String str : array) {
            String liteString = stringProcessor
                    .setNewValue(str)
                    .toStdFormat()
                    .getValue();

            Command c = isCommand(liteString);
            if (c != null) {
                return ServerAnswer.answerOK(processingCommand(c));
            } else {
                Question q = isQuestion(liteString);
                HolderAnswerAndCommunication answer = (q != null) ? processingQuestion(q) : null;


                List<KeyWord> keyWords = findKeyWords(liteString);
                HolderAnswerAndCommunication keyAnswer = (!keyWords.isEmpty()) ?  processingKeyWord(keyWords) : null;

                if (answer != null)
                    list.add(answer);
                else if (keyAnswer != null)
                    list.add(keyAnswer);
            }
        }

        if (list.size() != 0) {
            int indexBest = 0;
            for (int h = 0; h < list.size(); h++) {
                if (list.get(h).getCommunication().getCorrect() > list.get(indexBest).getCommunication().getCorrect()) {
                    indexBest = h;
                }
            }

            HolderAnswerAndCommunication holder = list.get(indexBest);
            AnswerContainer container = (holder.getCommunication() instanceof Communication)
                ? AnswerForQuestion.valueOf(holder.getAnswer().getContent(), holder.getCommunication().getID())// AnswerForQuestion
                : AnswerForKeyWord.valueOf(holder.getAnswer().getContent(), holder.getCommunication().getID());// AnswerForKeyWord
            return ServerAnswer.answerOK(container);
        } else
            return ServerAnswer.answerOK(AnswerForQuestion.valueOf(Const.IVOR_NO_ANSWER, null));
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
    public Question isQuestion(String str) {
        List<String> words = Arrays.asList(str.split(" "));
        HashSet<String> set = new HashSet<>(words);
        // Обработка Question
        List<Question> questions = DBContentLoader.getInstance().loadAll(Question.class);
        Assert.notNull(questions, "Don't load Questions from DB");

        for (Question q : questions) {
            String[] qWords = q.getContent().split(" ");
            HashSet<String> qSet = new HashSet<>(Arrays.asList(qWords));
            if (qSet.containsAll(set) || set.containsAll(qSet)) {
                return q;
            }
        }
        return null;
    }

    private String processingCommand(Command c) {
        return Const.COMMAND_NOT_SUPPORTED_IN_WEB_VERSION;
    }

    @Nullable
    private HolderAnswerAndCommunication processingQuestion(@Nonnull Question q) {
        List<Answer> answers = DBContentLoader.getInstance().loadAnswerForQuestion(q.getId());
        Assert.notNull(answers, ErrorMessageConstructor.dontLoadList(Answer.class));
        if (!answers.isEmpty()) {
            int r = random.nextInt(answers.size());
            Answer answer = answers.get(r);
            Communication com = DBContentLoader.getInstance().getCommunication(answer.getId(), q.getId());
            return HolderAnswerAndCommunication.buildHolder(answer, com);
        }
        return null;
    }

    @Nullable
    private HolderAnswerAndCommunication processingKeyWord(List<KeyWord> words) {
        for (KeyWord word : words) {
            List<Answer> answers = DBContentLoader.getInstance().loadAnswerForKeyWord(word.getId());
            Assert.notNull(answers, ErrorMessageConstructor.dontLoadList(Answer.class));
            if (answers.isEmpty())
                continue;

            int r = random.nextInt(answers.size());
            Answer answer =  answers.get(r);
            CommunicationKey comKey = DBContentLoader.getInstance().getCommunicationKey(answer.getId(), word.getId());
            return HolderAnswerAndCommunication.buildHolder(answer, comKey);
        }

        return null;
    }

    public static String init() {
        String msg = "RequestService is init";
        System.out.println(msg);
        return msg;
    }

    @Data
    private static class HolderAnswerAndCommunication {
        private Answer answer;
        private DBEntityCorrected communication;

        public static HolderAnswerAndCommunication buildHolder(Answer answer, DBEntityCorrected com) {
            HolderAnswerAndCommunication holder = new HolderAnswerAndCommunication();
            holder.setAnswer(answer);
            holder.setCommunication(com);
            return holder;
        }
    }


}

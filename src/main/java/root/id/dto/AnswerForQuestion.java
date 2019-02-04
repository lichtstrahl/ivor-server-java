package root.id.dto;

import com.google.gson.annotations.SerializedName;

public class AnswerForQuestion extends AnswerContainer {
    @SerializedName("communication")
    public Long comID;

    public long getCommunication() {
        return comID;
    }

    public static AnswerForQuestion valueOf(String answer, Long comID) {
        AnswerForQuestion a = new AnswerForQuestion();
        a.comID = comID;
        a.answer = answer;
        return a;
    }
}

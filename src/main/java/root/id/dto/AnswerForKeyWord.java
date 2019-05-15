package root.id.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AnswerForKeyWord extends AnswerContainer {
    @SerializedName("communication_key")
    private Long comID;

    public static AnswerForKeyWord valueOf(String answer, Long comID) {
        AnswerForKeyWord a = new AnswerForKeyWord();
        a.comID = comID;
        a.answer = answer;
        return a;
    }
}

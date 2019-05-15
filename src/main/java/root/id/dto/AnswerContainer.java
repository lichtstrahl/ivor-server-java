package root.id.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public abstract class AnswerContainer {
    @SerializedName("answer")
    protected String answer;
}

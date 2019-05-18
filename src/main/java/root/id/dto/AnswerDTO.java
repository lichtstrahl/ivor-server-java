package root.id.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class AnswerDTO {
    @SerializedName("answer")
    private String answer;
    @SerializedName("question_id")
    private Long questionID;
    @SerializedName("keyword_id")
    private Long keywordID;


    private AnswerDTO() {}

}

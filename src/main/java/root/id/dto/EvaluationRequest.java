package root.id.dto;

import com.google.gson.annotations.SerializedName;

public class EvaluationRequest {
    @SerializedName("eval")
    public int eval;
    @SerializedName("id")
    public long id;
    @SerializedName("type")
    public String type;
}

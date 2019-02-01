package root.id.dto;

import com.google.gson.annotations.SerializedName;

public class ServerAnswer {
    @SerializedName("answer")
    public String answer;

    public static ServerAnswer fromString(String answer) {
        ServerAnswer a = new ServerAnswer();
        a.answer = answer;
        return a;
    }
}

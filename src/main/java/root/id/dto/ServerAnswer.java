package root.id.dto;

import com.google.gson.annotations.SerializedName;
import root.id.Const;

public class ServerAnswer<T> {
    @SerializedName("error")
    private int error;
    @SerializedName("msg")
    private String message;
    @SerializedName("data")
    private T data;

    private ServerAnswer() { }

    public int getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }


    public static <T> ServerAnswer<T> answerOK(T data) {
        ServerAnswer<T> answer = new ServerAnswer<>();
        answer.error = 0;
        answer.message = Const.OK;
        answer.data = data;
        return answer;
    }

    public static <T> ServerAnswer<T> answerFail(String msg) {
        ServerAnswer<T> answer = new ServerAnswer<>();
        answer.error = -1;
        answer.message = msg;
        answer.data = null;
        return answer;
    }
}

package root.id.dto;

import com.google.gson.annotations.SerializedName;

public class RequestDTO {
    @SerializedName("request")
    public String request;

    public String getRequest() {
        return request;
    }

    public static RequestDTO fromString(String content) {
        RequestDTO r = new RequestDTO();
        r.request = content;
        return r;
    }
}

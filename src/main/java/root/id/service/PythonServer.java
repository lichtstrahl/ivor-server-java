package root.id.service;

import com.google.gson.stream.JsonReader;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import static cn.hutool.core.lang.Console.log;


public class PythonServer {
    private static final String HTTPS = "https";
    private static final String HTTP = "http";
    private static final String MORPHOLOGY_PYTHON_SERVER = "morphology-python-server.herokuapp.com";
    private static final String METHOD_NORMAL = "normal";
    private static final String PARAM_REQUEST = "request";

    public static PythonServer createDefaultPythonServer() {
        return new PythonServer();
    }

    /**
     * @param message Предложение в стандартном виде
     */
    public List<String> parseToNormal(String message) throws IOException {
        String request = buildHttpRequest(METHOD_NORMAL, PARAM_REQUEST, message);
        HttpGet getRequest = new HttpGet(request);
        log(getRequest.getMethod());
        log(getRequest.getURI().toString());

        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = client.execute(getRequest);

        List<String> list = parseArray(response.getEntity());
        response.close();
        client.close();
        return list;
    }


    public String parseToNormalAsString(String message) throws IOException {
        List<String> normals = parseToNormal(message);
        StringBuilder result = new StringBuilder();

        for (String n : normals)
            result.append(n).append(" ");

        return result.toString();
    }

    private static String buildHttpRequest(String method, String ... args) {
        String request = HTTPS + "://" + MORPHOLOGY_PYTHON_SERVER + "/" + method;

        if (args.length != 0) {
            request += "?";
            StringBuilder param = new StringBuilder();
            for (int i = 0; i < args.length; i += 2) {
                param.append(args[i]).append("=").append(args[i+1].replaceAll(" ", "+")).append("&");
            }
            // Удаляем лишний '&' в конце
            param.deleteCharAt(param.length()-1);
            request += param.toString();
        }


        return request;
    }

    private static List<String> parseArray(HttpEntity entity) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(entity.getContent()));
        reader.beginArray();
        List<String> list = new LinkedList<>();
        while (reader.hasNext()) {
            list.add(reader.nextString());
        }
        reader.endArray();

        reader.close();
        return list;
    }

}

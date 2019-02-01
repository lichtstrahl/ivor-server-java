package root.id.servlet;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import root.id.dto.ServerAnswer;
import root.id.dto.RequestDTO;
import root.id.util.RequestProcessor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;

@WebServlet(name = "RequestServlet", urlPatterns = "/api/request")
public class RequestServlet extends HttpServlet {
    private static final String REQUEST = "request";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder jb = new StringBuilder();

        String line;
        BufferedReader reader = request.getReader();
        while ((line = reader.readLine()) != null)
            jb.append(line);




        RequestDTO r = gson.fromJson(jb.toString(), RequestDTO.class);
        String a = RequestProcessor.processingRequest(r);
        send(response, ServerAnswer.fromString(a));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDTO r = RequestDTO.fromString(request.getParameter(REQUEST));
        String a = RequestProcessor.processingRequest(r);
        send(response, ServerAnswer.fromString(a));
    }

    private void send(HttpServletResponse res, ServerAnswer serverAnswer) throws IOException{
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        res.getWriter().write(gson.toJson(serverAnswer, ServerAnswer.class));
    }
}
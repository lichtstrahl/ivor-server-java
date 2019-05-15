package root.id.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import root.id.dto.ServerAnswer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

abstract public class BaseServlet extends HttpServlet {
    protected static final Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    protected String getJSONFromBody(HttpServletRequest request) throws IOException {
        StringBuilder jb = new StringBuilder();

        String line;
        BufferedReader reader = request.getReader();
        while ((line = reader.readLine()) != null)
            jb.append(line);

        return jb.toString();
    }

    protected void send(HttpServletResponse res, ServerAnswer serverAnswer) throws IOException{
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        res.getWriter().write(gson.toJson(serverAnswer, ServerAnswer.class));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        Throwable t;
    }

}

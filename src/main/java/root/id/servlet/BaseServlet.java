package root.id.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import root.id.dto.ServerAnswer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

abstract public class BaseServlet extends HttpServlet {
    protected static final Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    protected void send(HttpServletResponse res, ServerAnswer serverAnswer) throws IOException{
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        res.getWriter().write(gson.toJson(serverAnswer, ServerAnswer.class));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        enableCORS(resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
        enableCORS(resp);
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        enableCORS(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    private void enableCORS(HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:9000");
        resp.setHeader("Access-Control-Allow-Methods", "GET");
    }
}

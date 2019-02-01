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
}

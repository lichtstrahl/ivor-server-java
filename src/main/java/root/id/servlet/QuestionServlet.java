package root.id.servlet;

import root.id.dto.RequestDTO;
import root.id.dto.ServerAnswer;
import root.id.service.QuestionService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "QuestionServlet", value = "/api/questions")
public class QuestionServlet extends BaseServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDTO question = gson.fromJson(getJSONFromBody(req), RequestDTO.class);
        send(resp, QuestionService.insertQuestion(question.request));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        send(resp, QuestionService.getRandomQuestion());
    }
}

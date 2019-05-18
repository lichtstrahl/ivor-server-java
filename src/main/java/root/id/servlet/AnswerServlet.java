package root.id.servlet;

import root.id.dto.AnswerDTO;
import root.id.dto.RequestDTO;
import root.id.service.AnswerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AnswerServlet", value = "/api/answers")
public class AnswerServlet extends BaseServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AnswerDTO body = gson.fromJson(getJSONFromBody(req), AnswerDTO.class);
        send(resp, AnswerService.insertNewAnswer(body));
    }
}

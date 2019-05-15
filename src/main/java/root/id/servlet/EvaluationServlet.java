package root.id.servlet;

import root.id.dto.EvaluationRequest;
import root.id.dto.ServerAnswer;
import root.id.util.RequestProcessor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "EvaluationServlet", urlPatterns = "/api/evaluation")
public class EvaluationServlet extends BaseServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);
        EvaluationRequest evR = gson.fromJson(getJSONFromBody(request), EvaluationRequest.class);
        if (RequestProcessor.evaluation(evR.type, evR.id, evR.eval)) {
            send(response, ServerAnswer.answerOK(null));
        } else {
            send(response, ServerAnswer.answerFail("Ошибка при оценке"));
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        throw new UnsupportedOperationException("Данный сервлет поддерживает только POST-запросы");
    }
}

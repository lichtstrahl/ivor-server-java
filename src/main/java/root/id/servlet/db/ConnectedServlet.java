package root.id.servlet.db;

import root.id.dto.ServerAnswer;
import root.id.servlet.BaseServlet;
import root.id.service.DBService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ConnectedServlet", value = "/api/db/connected")
public class ConnectedServlet extends BaseServlet {
    private static final String PARAM_TYPE = "type";
    private static final String TYPE_KW = "kw";
    private static final String TYPE_Q = "q";
    private static final String TYPE_TEST = "t";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        String type = req.getParameter(PARAM_TYPE);
        switch (type) {
            case TYPE_KW:
                send(resp, DBService.findConnectedKW());
                break;
            case TYPE_Q:
                send(resp, DBService.findConnectedQ());
                break;
            case TYPE_TEST:
                break;
            default:
                send(resp, ServerAnswer.answerFail(INCORRECT_PARAM));
        }
    }
}

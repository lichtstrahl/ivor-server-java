package root.id.servlet;

import root.id.dto.RequestDTO;
import root.id.service.RequestService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RequestServlet", urlPatterns = "/api/request")
public class RequestServlet extends BaseServlet {
    private static final String REQUEST = "request";
    private static final RequestService processor = RequestService.getInstance();


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);

        RequestDTO r = gson.fromJson(getJSONFromBody(request), RequestDTO.class);
        send(response, processor.processingRequest(r));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
        RequestDTO r = RequestDTO.fromString(request.getParameter(REQUEST));
        send(response, processor.processingRequest(r));
    }
}
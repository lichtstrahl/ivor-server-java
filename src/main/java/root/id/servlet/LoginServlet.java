package root.id.servlet;

import root.id.dto.UserDTO;
import root.id.util.RequestProcessor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = "/api/login")
public class LoginServlet extends BaseServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDTO user = gson.fromJson(RequestProcessor.getJSONFromBody(request), UserDTO.class);
        send(response, RequestProcessor.checkUsersLoginPass(user));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new UnsupportedOperationException("Данный сервлет не принимает GET-запросы. Только POST-запрос.");
    }
}

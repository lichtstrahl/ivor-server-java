package root.id.servlet;

import root.id.dto.UserDTO;
import root.id.service.LoginService;
import root.id.service.RequestService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = "/api/login")
public class LoginServlet extends BaseServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);
        UserDTO user = gson.fromJson(getJSONFromBody(request), UserDTO.class);
        send(response, LoginService.checkUsersLoginPass(user));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
//        throw new UnsupportedOperationException("Данный сервлет не принимает GET-запросы. Только POST-запрос.");
    }
}

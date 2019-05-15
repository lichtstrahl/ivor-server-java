package root.id.servlet;

import root.id.dto.UserDTO;
import root.id.util.RequestProcessor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RegisterServlet", urlPatterns = "/api/register")
public class RegisterServlet extends BaseServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);
        UserDTO newUser = gson.fromJson(getJSONFromBody(request), UserDTO.class);
        send(response, RequestProcessor.insertNewUser(newUser));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

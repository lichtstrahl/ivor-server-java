package root.id.service;

import root.id.Const;
import root.id.db.Client;
import root.id.db.DBContentLoader;
import root.id.dto.ServerAnswer;
import root.id.dto.UserDTO;

import java.util.List;

public class LoginService {
    /**
     Если возвраается пустая строка, значит пользователь найден.
     В противном случае возвращается описание проблемы.
     */
    public static ServerAnswer checkUsersLoginPass(UserDTO user) {
        List<Client> clients = DBContentLoader.getInstance().loadAll(Client.class);
        if (clients == null) {
            return ServerAnswer.answerFail(Const.NULL_POINTER_EXCEPTION + "RequestService:checkUsersLoginPass");
        }

        for (Client client : clients) {
            if (client.getLogin().equals(user.login)) {
                if (client.getPass().equals(user.pass)) {
                    return ServerAnswer.answerOK(user.loadFromDBClient(client));
                }
                return ServerAnswer.answerFail(Const.INVALID_PASSWORD_FOR_LOGIN);
            }
        }
        return ServerAnswer.answerFail(Const.INVALID_LOGIN);
    }
}

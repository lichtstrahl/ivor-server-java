package root.id.service;

import root.id.Const;
import root.id.db.DBContentLoader;
import root.id.dto.ServerAnswer;
import root.id.dto.UserDTO;

public class RegisterService {
    public static ServerAnswer registerUser(UserDTO user) {
        if (!DBContentLoader.getInstance().getUser(user).isEmpty()) {
            return ServerAnswer.answerFail(Const.Database.USER_EXISTS);
        }

        if (!DBContentLoader.getInstance().getUser(user.login).isEmpty()) {
            return ServerAnswer.answerFail(Const.Database.LOGIN_BUSY);
        }

        if (DBContentLoader.getInstance().insertNewEntity(user)) {
            return ServerAnswer.answerOK(null);
        } else {
            return ServerAnswer.answerFail(Const.Database.ERROR_INSERT_USER);
        }
    }

}

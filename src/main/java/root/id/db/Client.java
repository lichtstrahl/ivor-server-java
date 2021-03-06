package root.id.db;

import root.id.dto.UserDTO;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Client extends DBInstance {
    private String realName;
    private String login;
    private String pass;
    private int age;
    private String city;
    private String email;
    private String lastEntry;
    private Integer admin;

    public Client(ResultSet set) throws SQLException {
        super(set);
        this.realName = set.getString("realName");
        this.login = set.getString("login");
        this.pass = set.getString("pass");
        this.age = set.getInt("age");
        this.city = set.getString("city");
        this.email = set.getString("email");
        this.lastEntry = set.getString("lastEntry");
        this.admin = set.getInt("admin");
    }

    public Client() {}

    public String getRealName() {
        return realName;
    }

    public String getLogin() {
        return login;
    }

    public String getPass() {
        return pass;
    }

    public int getAge() {
        return age;
    }

    public String getCity() {
        return city;
    }

    public String getEmail() {
        return email;
    }

    public String getLastEntry() {
        return lastEntry;
    }

    public Integer getAdmin() {
        return admin;
    }

    public Client loadFromUserDTO(UserDTO user) {
        this.realName = user.realName;
        this.login = user.login;
        this.pass = user.pass;
        this.age = user.age;
        this.city = user.city;
        this.email = user.email;
        this.lastEntry = user.lastEntry;
        this.admin = user.admin;
        return this;
    }
}

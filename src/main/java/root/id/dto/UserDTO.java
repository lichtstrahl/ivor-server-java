package root.id.dto;

import com.google.gson.annotations.SerializedName;
import com.mysql.cj.jdbc.result.ResultSetImpl;
import root.id.db.Client;
import root.id.db.DBEntity;

public class UserDTO implements DBEntity {
    @SerializedName("id")
    public long id;
    @SerializedName("realName")
    public String realName;
    @SerializedName("login")
    public String login;
    @SerializedName("pass")
    public String pass;
    @SerializedName("age")
    public int age;
    @SerializedName("city")
    public String city;
    @SerializedName("email")
    public String email;
    @SerializedName("lastEntry")
    public String lastEntry;
    @SerializedName("admin")
    public Integer admin;

    public UserDTO loadFromDBClient(Client user) {
        this.id = user.getId();
        this.realName = user.getRealName();
        this.login = user.getLogin();
        this.pass = user.getPass();
        this.age = user.getAge();
        this.city = user.getCity();
        this.email = user.getEmail();
        this.lastEntry = user.getLastEntry();
        this.admin = user.getAdmin();
        return this;
    }

    @Override
    public String buildInsertQuery() {
        return "insert into client " +
                        "(realName, login, pass, age, city, email, lastEntry, admin)\n" +
                        "values  ('"+realName+"', '" +login+"', '" +pass+"', "+age+", '"+city+"', '"+email+"', '"+lastEntry+"', "+admin+");";
    }

    @Override
    public String getTableName() {
        return "client";
    }
}

package entities;

public class User {
    private int id;
    private String name;
    private String surname;
    private String login;
    private String password;
    private int roleId;

    public User(int id, String name, String surname, String login, String password, int roleId) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.roleId = roleId;
    }

    public User(String name, String surname, String login, String password) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
    }

    public User(String name, String surname, String login, String password, int roleId) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.roleId = roleId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setLogin(String login) {
        this.login = login;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }

    public String getSurname() {
        return surname;
    }

    public int getRoleId() {
        return roleId;
    }
}

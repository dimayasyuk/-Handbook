package commands.github;

import commands.Command;
import constants.Blanks;
import constants.Constants;
import constants.URL;
import dao.UserDao;
import entities.Role;
import entities.User;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

public class GithubAccessTokenCommand implements Command {
    private UserDao userDao;

    public GithubAccessTokenCommand(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String code = request.getParameter("code");

        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("github.properties");
        Properties property = new Properties();
        property.load(inputStream);
        JSONObject jsonObject = new JSONObject(getAccessToken(property, code));
        String accessToken = String.valueOf(jsonObject.get("access_token"));
        JSONObject userInfo = new JSONObject(getUserInfo(property, accessToken));
        validateUser(userInfo, request, response);
    }

    private void validateUser(JSONObject userInfo, HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String login = String.valueOf(userInfo.get("login"));
        String password = String.valueOf(userInfo.get("id"));
        String fullname = String.valueOf(userInfo.get("name"));
        String[] names = fullname.split(" ");
        HttpSession session = request.getSession(true);
        User user = userDao.getUserByLogin(login);

        if (Objects.isNull(user)) {
            Role role = userDao.getRoleByName(Constants.USER_ROLE);
            userDao.insertUser(new User(names[0], names[1], login, password, role.getId()));
            session.setAttribute("user", userDao.getLastUser());
            session.setAttribute("role", role.getName());
            response.sendRedirect(Blanks.BASE_URL + URL.LIST_SECTION);
        } else {
            Role role = userDao.getRoleById(user.getRoleId());
            session.setAttribute("user", user);
            session.setAttribute("role", role.getName());
            String page = role.getName().equals(Constants.ADMIN_ROLE) ? URL.LIST_USER : URL.LIST_SECTION;
            response.sendRedirect(Blanks.BASE_URL + page);
        }
    }

    private String getAccessToken(Properties property, String code) throws IOException {
        HttpClient httpClient = new HttpClient();
        PostMethod method = new PostMethod(property.getProperty("githubAuth.accessUri"));
        method.addRequestHeader("Accept", "application/json");

        method.addParameter("client_id", property.getProperty("githubAuth.clientId"));
        method.addParameter("client_secret", property.getProperty("githubAuth.clientSecret"));
        method.addParameter("code", code);

        httpClient.executeMethod(method);

        return method.getResponseBodyAsString();
    }

    private String getUserInfo(Properties property, String accessToken) throws IOException {
        HttpClient httpClient = new HttpClient();
        GetMethod method = new GetMethod(property.getProperty("githubAuth.users"));

        method.setRequestHeader("Authorization", "token " + accessToken);
        httpClient.executeMethod(method);

        return method.getResponseBodyAsString();
    }
}

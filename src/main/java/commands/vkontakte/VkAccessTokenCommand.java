package commands.vkontakte;

import commands.Command;
import constants.Blanks;
import constants.Constants;
import constants.URL;
import dao.UserDao;
import entities.Role;
import entities.User;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

public class VkAccessTokenCommand implements Command {
    private UserDao userDao;

    public VkAccessTokenCommand(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String code = request.getParameter("code");

        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("vk.properties");
        Properties property = new Properties();
        property.load(inputStream);

        JSONObject jsonObject = new JSONObject(getAccessToken(property, code));
        String accessToken = String.valueOf(jsonObject.get("access_token"));
        String userId = String.valueOf(jsonObject.get("user_id"));

        JSONObject userInfo = new JSONObject(getUserInfo(property, userId, accessToken));
        JSONArray array = userInfo.getJSONArray("response");
        validateUser(array.getJSONObject(0), request, response);

    }

    private void validateUser(JSONObject jsonObject, HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String name = String.valueOf(jsonObject.get("first_name"));
        String surname = String.valueOf(jsonObject.get("last_name"));
        String login = String.valueOf(jsonObject.get("id"));
        HttpSession session = request.getSession(true);
        User user = userDao.getUserByLogin(login);

        if (Objects.isNull(user)) {
            Role role = userDao.getRoleByName(Constants.USER_ROLE);
            userDao.insertUser(new User(name, surname, login, login, role.getId()));
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
        PostMethod method = new PostMethod(property.getProperty("vkAuth.accessUri"));

        method.addParameter("client_id", property.getProperty("vkAuth.clientId"));
        method.addParameter("redirect_uri", property.getProperty("vkAuth.redirectUri"));
        method.addParameter("client_secret", property.getProperty("vkAuth.clientSecret"));
        method.addParameter("code", code);

        httpClient.executeMethod(method);

        return method.getResponseBodyAsString();
    }

    private String getUserInfo(Properties property, String userId, String accessToken) throws IOException {
        HttpClient httpClient = new HttpClient();
        PostMethod method = new PostMethod(property.getProperty("vkAuth.users"));

        method.addParameter("user_ids", userId);
        method.addParameter("access_token", accessToken);
        method.addParameter("fields", "bdate");
        method.addParameter("v", property.getProperty("vkAuth.version"));

        httpClient.executeMethod(method);

        return method.getResponseBodyAsString();
    }
}

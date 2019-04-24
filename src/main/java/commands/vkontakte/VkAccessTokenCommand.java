package commands.vkontakte;

import commands.Command;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

public class VkAccessTokenCommand implements Command {
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
        
    }

    private String getAccessToken(Properties property, String code) throws IOException {
        HttpClient httpClient = new HttpClient();
        PostMethod method = new PostMethod(property.getProperty("vkAuth.accessUri"));

        method.addParameter("client_id", property.getProperty("vkAuth.clientId"));
        method.addParameter("redirect_uri", property.getProperty("vkAuth.redirectUri"));
        method.addParameter("client_secret",property.getProperty("vkAuth.clientSecret"));
        method.addParameter("code",code);

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

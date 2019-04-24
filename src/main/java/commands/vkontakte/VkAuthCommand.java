package commands.vkontakte;

import commands.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

public class VkAuthCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("vk.properties");
        Properties property = new Properties();
        property.load(inputStream);

        String clientId = property.getProperty("vkAuth.clientId");
        String redirectUri = property.getProperty("vkAuth.redirectUri");
        String version = property.getProperty("vkAuth.version");
        String scope = property.getProperty("vkAuth.scope");
        String uri = property.getProperty("vkAuth.authorize");

        String path = uri + "?client_id="
                + clientId
                + "&display=page&redirect_uri=" + redirectUri
                + "&scope=" + scope
                + "&response_type=code&v="
                + version;
        response.sendRedirect(path);
    }
}

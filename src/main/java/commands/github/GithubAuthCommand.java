package commands.github;

import commands.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

public class GithubAuthCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("github.properties");
        Properties property = new Properties();
        property.load(inputStream);

        String clientId = property.getProperty("githubAuth.clientId");
        String redirectUri = property.getProperty("githubAuth.redirectUri");
        String uri = property.getProperty("githubAuth.authorize");

        String path = uri + "?client_id="
                + clientId
                + "&redirect_uri=" + redirectUri;

        response.sendRedirect(path);
    }
}

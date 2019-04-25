package commands.admin;

import commands.Command;
import constants.Blanks;
import dao.UserDao;
import entities.Role;
import entities.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class GetHomeAdminPageCommand implements Command {
    private UserDao userDao;

    public GetHomeAdminPageCommand(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {

        int currentPage = Integer.valueOf(
                request.getParameter("currentPage") == null
                        ? "1"
                        : request.getParameter("currentPage")
        );
        int recordsPerPage = Integer.valueOf(
                request.getParameter("recordsPerPage") == null
                        ? "30"
                        : request.getParameter("recordsPerPage")
        );

        int noOfRecords = userDao.countUsers();

        int numberOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

        List<User> users = userDao.getBulkOfUsers((currentPage - 1) * recordsPerPage, recordsPerPage);
        List<Role> roles = userDao.getRoles();
        request.setAttribute("users", users);
        request.setAttribute("roles", roles);
        request.setAttribute("noOfPages", numberOfPages);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("recordsPerPage", recordsPerPage);
        request.setAttribute("number", noOfRecords);
        RequestDispatcher dispatcher = request.getRequestDispatcher(Blanks.ADMIN_HOME);
        dispatcher.forward(request, response);
    }
}

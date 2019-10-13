package servlets;

import com.google.gson.Gson;
import System.RepositoryManager;
import System.UserManager;
import utils.ServletUtils;
import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet(name = "RepositoriesListServlet", urlPatterns = {"/pages/usersPrivateAccount/repositorieslist"})
public class RepositoriesListServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        String g = request.getParameter("method");
        if (g!=null && g.equals("getRepositoriesList")) {
            try (PrintWriter out = response.getWriter()) {
                Gson gson = new Gson();
                RepositoryManager repositoryManager = ServletUtils.getRepositoryManager(getServletContext());
                Map<String, String> repositoriesList = repositoryManager.getRepository();
                String json = gson.toJson(repositoriesList);
                out.println(json);
                out.flush();
            }
        } else {
            String button = request.getParameter("button");
            switch (button) {
                case "logout": {
                    logout(request, response);
                    break;
                }
            }

        }
    }


   private void logout(HttpServletRequest request, HttpServletResponse response) {
        UserManager userManager = ServletUtils.getUserManager(getServletContext());
        String userName = SessionUtils.getUsername(request);
        try (PrintWriter out = response.getWriter()) {
            userManager.removeUser(userName);
            Gson gson = new Gson();
            String test = "test";
            String json = gson.toJson(test);
            out.print(json);
            out.flush();

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Exception from log out");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}


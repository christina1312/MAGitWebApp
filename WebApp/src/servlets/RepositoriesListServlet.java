package servlets;

import com.google.gson.Gson;
import System.GitManager;
import System.UserManager;
import System.BasicMAGitManager;
import utils.ServletUtils;
import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@WebServlet(name = "RepositoriesListServlet", urlPatterns = {"/pages/usersPrivateAccount/repositorieslist"})
public class RepositoriesListServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        String g = request.getParameter("method");
        if (g!=null && g.equals("getRepositoriesList")) {
            try (PrintWriter out = response.getWriter()) {
                String username = request.getParameter("userName");
                Gson gson = new Gson();
                GitManager gitManager = ServletUtils.getGitManager(getServletContext());
                List<BasicMAGitManager> repositoriesList = gitManager.getRepositoriesByUserName(username);
                Set<String> resList=new HashSet<String>();

                for(BasicMAGitManager manager: repositoriesList) {
                    StringBuilder res = new StringBuilder();
                    res.append("Repository name: "+ manager.getRepositoryName() +"<br/>" );
                    res.append("Active branch name: "+ manager.getRepositoryActiveBranch() +"<br/>");
                    res.append("Branch amount: "+ manager.getRepositoryBranchCount() +"<br/>" );
                    res.append("Last commit time: "+ manager.getRepositoryLastCommitTime() +"<br/>" );
                    res.append("Last commit message: "+ manager.getRepositoryLastCommitMessage() +"<br/>" );
                    res.append("--------------------------------- " + "<br/>" );

                    resList.add(res.toString());
                }

               // String json = gson.toJson(res);
                String json = gson.toJson(resList);
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


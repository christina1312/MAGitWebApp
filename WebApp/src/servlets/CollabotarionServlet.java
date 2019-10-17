package servlets;

import System.UserManager;
import com.google.gson.Gson;
import utils.ServletUtils;
import System.GitManager;
import utils.SessionUtils;
import System.BasicMAGitManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

@WebServlet(name = "CollabotarionServlet", urlPatterns = {"/pages/singleRepositoryInformation/collaboratin"})
public class CollabotarionServlet extends HttpServlet {

   protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
       String usernameFromSession = SessionUtils.getUsername(request);
       response.setContentType("application/json");
       String g = request.getParameter("method");

       switch (g) {
           case "getRemoteRepository": {
               try (PrintWriter out = response.getWriter()) {
                   Gson gson = new Gson();
                   String repoName = request.getParameter("repoName");
                   GitManager currGitManager = ServletUtils.getGitManager(getServletContext());
                   BasicMAGitManager repo = currGitManager.getRepositoryByUserName(usernameFromSession,repoName);
                   String rrName = repo.getRRName();
                   String json = gson.toJson(rrName);
                   out.println(json);
                   out.flush();
               }
               break;
           }
           case "Pull": {

               break;
           }
           case "push": {

               break;
           }
       }
   }

       @Override
       protected void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
           processRequest(request, response);
       }
   }
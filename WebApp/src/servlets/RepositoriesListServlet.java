package servlets;

import com.google.gson.Gson;
import System.GitManager;
import System.UserManager;
import System.BasicMAGitManager;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.codehaus.jackson.map.ObjectMapper;
import utils.ServletUtils;
import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
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
                List<Map<String, String>> resList = new ArrayList<>();

                for(BasicMAGitManager manager: repositoriesList) {
                    Map<String, String> tempMap=new HashMap<>();

                    tempMap.put("RepositoryName", manager.getRepositoryName());
                    tempMap.put("ActiveBranchName", manager.getRepositoryActiveBranch());
                    tempMap.put("BranchAmount", manager.getRepositoryBranchCount());
                    tempMap.put("LastCommitTime", manager.getRepositoryLastCommitTime());
                    tempMap.put("LastCommitMessage", manager.getRepositoryLastCommitMessage());
                    tempMap.put("Path", manager.getRepositoryLocation()); // todo verify path
                    resList.add(tempMap);
                }

                Type resultType = new TypeToken<List<Map<String, Object>>>(){}.getType();
                String jsonRes = gson.toJson(resList,resultType);
                out.println(jsonRes);
                out.flush();
            }catch (Exception ex){
                System.out.println(ex.getMessage());
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


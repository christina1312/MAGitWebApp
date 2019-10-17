package servlets;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import utils.SessionUtils;
import utils.ServletUtils;
import constants.Constants;
import System.GitManager;
import System.Notification;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(name = "NotificationServlet", urlPatterns = {"/pages/usersPrivateAccount/notificationslist"})
public class NotificationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        String username = request.getParameter("username");
        try (PrintWriter out = response.getWriter()) {
            GitManager gitManager = ServletUtils.getGitManager(getServletContext());
            Gson gson = new Gson();
            List<Notification> notificationList = gitManager.getUserNotification(username);
            List<Map<String, String>> resList = new ArrayList<>();

            if (!notificationList.isEmpty()) {
                for (Notification manager : notificationList) {
                    Map<String, String> tempMap = new HashMap<>();
                    tempMap.put("message", manager.getMessage());
                    tempMap.put("time", manager.getTime().toString());
                    resList.add(tempMap);
                }
            }

            Type resultType = new TypeToken<List<Map<String, Object>>>() {
            }.getType();
            String jsonRes = gson.toJson(resList, resultType);
            out.println(jsonRes);
            out.flush();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userName = SessionUtils.getUsername(request);
        if (userName == null) {
            request.setAttribute(Constants.USER_NAME_ERROR, "You must sign in first!");
            getServletContext().getRequestDispatcher("/pages/signup/login_error.jsp").forward(request, response);
        } else {
            GitManager gitManager = ServletUtils.getGitManager(getServletContext());
            String note = request.getReader().lines().collect(Collectors.joining());
            gitManager.addUserNotification(userName, note);
        }
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}

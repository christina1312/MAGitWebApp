package servlets;

import utils.SessionUtils;
import utils.ServletUtils;
import System.UserManager;
import System.GitManager;
import constants.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutServlet extends HttpServlet {


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String usernameFromSession = SessionUtils.getUsername(request);
        UserManager userManager = ServletUtils.getUserManager(getServletContext());
        GitManager currGitManager = ServletUtils.getGitManager(getServletContext());

        if (usernameFromSession != null) {
            userManager.removeUser(usernameFromSession);
            currGitManager.deleteAllUserNotification(usernameFromSession);
            SessionUtils.clearSession(request);
            request.setAttribute(Constants.USER_NAME_LOGOUT, usernameFromSession);
            response.sendRedirect(request.getContextPath() + "/index.html");
        }

        else {
            request.setAttribute(Constants.USER_NAME_ERROR, "You must sign in first!");
            getServletContext().getRequestDispatcher(LoginServlet.LOGIN_ERROR_URL).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}

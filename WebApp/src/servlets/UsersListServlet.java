package servlets;

import com.google.gson.Gson;
import utils.ServletUtils;
import System.UserManager;
import utils.SessionUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static java.lang.System.out;

@WebServlet(name = "UsersListServlet", urlPatterns = {"/pages/usersPrivateAccount/userslist"})
public class UsersListServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        //returning JSON objects, not HTML
        response.setContentType("application/json");
        String g = request.getParameter("method");
        if(g.equals("getUsersList")) {
            try (PrintWriter out = response.getWriter()) {
                Gson gson = new Gson();
                UserManager userManager = ServletUtils.getUserManager(getServletContext());
                Set<String> usersList = userManager.getUsers();
                String json = gson.toJson(usersList);
                out.println(json);
                out.flush();
            }
        }

        else if(g.equals("getCurrentUserName")) {
            try (PrintWriter out = response.getWriter()) {
                Gson gson = new Gson();
                String userName = SessionUtils.getUsername(request);
                String json = gson.toJson(userName);
                out.println(json);
                out.flush();
            }
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
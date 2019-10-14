package servlets;

import System.GitManager;
import utils.ServletUtils;
import utils.SessionUtils;

import java.io.File;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

@WebServlet(name = "UploadFileServlet", urlPatterns = {"/pages/usersPrivateAccount/uploadfile"})
@MultipartConfig
public class UploadFileServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String xmlFile = null;
        String path = null;
        File XmlObj;
        try {
            String ss =request.getParameter("fAddr");
            for (Part part : request.getParts()) {
                String fileName = extractFileName(part);
                if (fileName.endsWith(".xml")) {
                    xmlFile = fileName;

                    XmlObj = new File(fileName);
                    path = XmlObj.getAbsolutePath();
                    System.out.println(path);
                    part.write(path);
                    break;
                }
            }
            GitManager magitDataHolder = ServletUtils.getRepositoryManager(getServletContext());
            if (xmlFile != null) {
                if (magitDataHolder.isRepositoryExists(xmlFile)) {
                    throw new Exception("The name already exists ,please choose different name");
                }
                String userName = SessionUtils.getUsername(request);
                magitDataHolder.addRepository(xmlFile, path, userName);
            }
            else
            {
                throw new Exception("you have to choose  xml file !");
            }

            request.setAttribute("Massage" , "The file loaded successfully");
            getServletContext().getRequestDispatcher("/pages/usersPrivateAccount/usersPrivateAccount.jsp").forward(request, response);


        } catch (Exception ex) {

            request.setAttribute("Massage" , ex.getMessage());
            getServletContext().getRequestDispatcher("/pages/usersPrivateAccount/usersPrivateAccount.jsp").forward(request, response);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);


    }
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length()-1);
            }
        }
        return "";
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}

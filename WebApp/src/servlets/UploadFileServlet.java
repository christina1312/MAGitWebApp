package servlets;

import System.MAGitManager;
import utils.ServletUtils;
import utils.SessionUtils;

import java.io.File;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

@WebServlet(name = "UploadFileServlet", urlPatterns = {"/uploadfile"})
public class UploadFileServlet extends HttpServlet {

    private static final String SAVE_DIR = "uploadFiles";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String xmlFile = null;
        String path = null;
        boolean valid = false;
        String fileName1 =null;
        File XmlObj = null;
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
            MAGitManager magitDataHolder = ServletUtils.getMagitManager(getServletContext());
            if (xmlFile != null) {
                fileName1 = request.getParameter("fileName");
                if(fileName1.isEmpty())
                {
                    throw new Exception("you have to put file name,please choose a name");
                }
                else if (magitDataHolder.isGameExists(fileName1)) {
                    throw new Exception("The name already exists ,please choose different name");
                }
                String userName = SessionUtils.getUsername(request);
                magitDataHolder.addRepository(fileName1, path, userName);
            }
            else
            {
                throw new Exception("you have to choose game");
            }
            if (valid) {
                response.sendRedirect("usersPrivateAccount.jsp");
            }
//            else
//            {
//                response.sendRedirect("usersPrivateAccount.jsp");
//            }

        } catch (Exception ex) {

            request.setAttribute("errorMassage" , ex.getMessage());
            getServletContext().getRequestDispatcher("/MainRoom/MainRoom.jsp").forward(request, response);
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

package servlets;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;
import System.Repository;

public class MyAppServletContextListener
 implements ServletContextListener {
    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        System.out.println("destroy");
        File magitFiles = new File("C:\\magit-ex3");
        Repository.deleteDirectory(magitFiles);
        magitFiles.delete();
    }
    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        //do noting
    }
}

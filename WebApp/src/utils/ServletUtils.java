package utils;

import System.UserManager;
import System.GitManager;
import System.NotificationManager;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class ServletUtils {

    private static final String USER_MANAGER_ATTRIBUTE_NAME = "userManager";
    private static final String BASIC_MAGIT_MANAGER_ATTRIBUTE_NAME = "BasicMAGitManager";
    private static final String NOTIFICATIONS_MANAGER_ATTRIBUTE_NAME = "Notifications";
    public static final int INT_PARAMETER_ERROR = Integer.MIN_VALUE;

    public static UserManager getUserManager(ServletContext servletContext) {
        if (servletContext.getAttribute(USER_MANAGER_ATTRIBUTE_NAME) == null) {
            servletContext.setAttribute(USER_MANAGER_ATTRIBUTE_NAME, new UserManager());
        }
        return (UserManager) servletContext.getAttribute(USER_MANAGER_ATTRIBUTE_NAME);
    }

    public static GitManager getGitManager(ServletContext servletContext) {
        if (servletContext.getAttribute(BASIC_MAGIT_MANAGER_ATTRIBUTE_NAME) == null) {
            servletContext.setAttribute(BASIC_MAGIT_MANAGER_ATTRIBUTE_NAME, new GitManager());
        }
        return (GitManager) servletContext.getAttribute(BASIC_MAGIT_MANAGER_ATTRIBUTE_NAME);
    }

    public static NotificationManager getNotificationManager(ServletContext servletContext) {
        if (servletContext.getAttribute(NOTIFICATIONS_MANAGER_ATTRIBUTE_NAME) == null) {
            servletContext.setAttribute(NOTIFICATIONS_MANAGER_ATTRIBUTE_NAME, new NotificationManager());
        }
        return (NotificationManager) servletContext.getAttribute(NOTIFICATIONS_MANAGER_ATTRIBUTE_NAME);
    }

    public static int getIntParameter(HttpServletRequest request, String name) {
        String value = request.getParameter(name);
        if (value != null) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException numberFormatException) {
            }
        }
        return INT_PARAMETER_ERROR;
    }
}

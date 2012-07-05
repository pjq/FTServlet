import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.pjq.base.BaseHttpServlet;

/**
 * The HomePage.
 * 
 * @author pjq0274
 */
public class HomePage extends BaseHttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = -4397272680358028385L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        // TODO Auto-generated method stub
        super.doGet(req, resp);

        sendHomePage();
    }

    private void sendHomePage() {
        // TODO Auto-generated method stub
        out.println("<html>");
        out.println("<head>");
        out.println("<title>FTClient Web HomePage</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h3>FTClient Web HomePage</h3>");
        out.println("<a href=\"https://216.24.194.197:8443/HelloServlet/account/web/Register\">Register</a>");
        out.println("<br>");
        out.println("<a href=\"https://216.24.194.197:8443/HelloServlet/account/web/Update\">Update Info</a>");
        out.println("<br>");
        out.println("<a href=\"https://216.24.194.197:8443/HelloServlet/s1/GetUserTimeline\">GetTimeline</a>");
        out.println("<br>");
        out.println("<br>");
        out.println("<a href=\"http://pjq.me/wiki/doku.php?id=project:ftclient\">Click here for detail.</a>");
        out.println("<br>");
        out.println("<a href=\"mailto:pengjianqing\">Contact me</a>");
        out.println("<br>");

        out.println("<h4>Source Code</h4>");
        out.println("<a href=\"https://github.com/pjq/FTClient\">Android FTClient</a>");
        out.println("<br>");
        out.println("<a href=\"https://github.com/pjq/HelloServlet\">Servlet Server</a>");
        out.println("<br>");
        out.println("</body>");
        out.println("</html>");

    }
}

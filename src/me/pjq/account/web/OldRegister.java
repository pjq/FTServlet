
package me.pjq.account.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.pjq.base.BaseHttpServlet;
import me.pjq.base.CommonParamString;
import me.pjq.database.SqliteManager;

/**
 * Another register,you can register with your special twitter consumer
 * key/secret,twitter access token/secret.
 * 
 * @see Register
 * @deprecated Please use {@link Register}.
 * @author pjq0274
 */
public class OldRegister extends BaseHttpServlet {
    /**
     * 
     */
    private static final long serialVersionUID = 5407965005777694140L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        // TODO Auto-generated method stub
        super.doGet(req, resp);

        String username = req.getParameter(CommonParamString.PARAM_USERNAME);
        String password = req.getParameter(CommonParamString.PARAM_PASSWORD);
        String twitterConsumerKey = req.getParameter("user_twitter_consumer_key");
        String twitterConsumerSecret = req.getParameter("user_twitter_consumer_secret");
        String twitterAccessToken = req.getParameter("user_twitter_access_token");
        String twitterAccessTokenSecret = req.getParameter("user_twitter_access_token_secret");
        String email = req.getParameter("user_email");

        if (null == username || null == password) {
            sendRegisterHtml();
        } else {
            boolean result = SqliteManager.getInstance().addUser(username, password, twitterAccessToken,
                    twitterAccessTokenSecret, email, twitterConsumerKey, twitterConsumerSecret);

            if (result) {
                out.println("Register success.");
            } else {
                out.println("Register failed,maybe the user already existed,please use another name.");
            }
        }

    }

    /**
     * Send the register html.
     */
    private void sendRegisterHtml() {
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Register</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h3>Register</h3>");

        // if (firstName != null || lastName != null) {
        // out.println("First Name:");
        // out.println(" = " + HTMLFilter.filter(firstName) + "<br>");
        // out.println("Last Name:");
        // out.println(" = " + HTMLFilter.filter(lastName));
        // } else {
        // out.println("No Parameters, Please enter some");
        // }
        out.println("<P>");
        out.print("<form action=\"");
        out.print("Register\" ");
        out.println("method=POST>");
        out.println("User Name:");
        out.println("<input type=text size=20 name="+CommonParamString.PARAM_USERNAME+">");
        // out.println("<br>");
        out.println("Password:");
        out.println("<input type=text size=20 name="+CommonParamString.PARAM_PASSWORD+">");
        // out.println("<br>");
        out.println("Email:");
        out.println("<input type=text size=20 name=user_email>");
        out.println("<br>");
        out.println("Twitter ConsumerKey:");
        out.println("<input type=text size=20 name=user_twitter_consumer_key>");
        // out.println("<br>");
        out.println("Twitter ConsumerKeySecret:");
        out.println("<input type=text size=20 name=user_twitter_consumer_secret>");
        out.println("<br>");
        out.println("Twitter AccessToken:");
        out.println("<input type=text size=20 name=user_twitter_access_token>");
        // out.println("<br>");
        out.println("Twitter AccessTokenSecret:");
        out.println("<input type=text size=20 name=user_twitter_access_token_secret>");
        out.println("<br>");
        out.println("<input type=submit>");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");

    }
}

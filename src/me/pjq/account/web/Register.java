
package me.pjq.account.web;

import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.pjq.account.AccountInfo;
import me.pjq.base.BaseHttpServlet;
import me.pjq.base.CommonParamString;
import me.pjq.database.SqliteManager;
import me.pjq.util.Utils;

/**
 * Register handle.It support xAuth,so the user can register with the twitter
 * username/password,and it will auto get the AccessToken/Secret.Also the user
 * can update their Twitter username/password in {@link Update} <br>
 * It will NOT REMEMBER the Twitter username/password.
 * 
 * @see OldRegister
 * @see Update
 * @author pjq0274
 */
public class Register extends BaseHttpServlet {
    /**
     * 
     */
    private static final long serialVersionUID = 5407965005777694140L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        // TODO Auto-generated method stub
        super.doGet(req, resp);

        String username = getUserName();
        String password = getPassword();
        String twitterUserName = getTwitterUserName();
        String twitterPassword = getTwitterUserPassword();
        String email = getEmail();

        if (Utils.isEmpty(username) || Utils.isEmpty(password)) {
            sendRegisterHtml();
        } else {
            boolean isTwitterAccountAvailable = !Utils.isEmpty(twitterUserName)
                    && !Utils.isEmpty(twitterPassword);

            AccessToken accessToken = null;
            if (isTwitterAccountAvailable) {
                accessToken = getXAuthAccessToken(twitterUserName, twitterPassword);
            }

            String token = "";
            String tokenSecret = "";
            if (null != accessToken) {
                token = accessToken.getToken();
                tokenSecret = accessToken.getTokenSecret();
            } else {
                out.println("Can't get the Twitter Access Token,may be your Twitter UserName/Password is wrong?");
            }

            boolean result = false;

            // Add an new user.
            result = SqliteManager.getInstance().addUser(username, password,
                    token, tokenSecret, email, CONSUMER_KEY, CONSUMER_SECRET);
            if (result) {
                out.println("Register success");

                try {
                    AccountInfo accountInfo = getAccountInfo();
                    accountInfo.setTwitterConsumerKey(CONSUMER_KEY);
                    accountInfo.setTwitterConsumerSecret(CONSUMER_SECRET);
                    accountInfo.setTwitterAccessToken(token);
                    accountInfo.setTwitterAccessTokenSecret(tokenSecret);
                    createTwitterInstance().updateStatus(
                            "哈，我注册了FTClient,一个简单的发推工具,绝对的发推利器. #FTClient cc @pengjianqing");
                } catch (TwitterException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
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
        out.println("<title>Register(It will NOT REMEMBER your Twitter UserName and Password)</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h3>Register(It will NOT REMEMBER your Twitter UserName and Password.)</h3>");

        out.println("<P>");
        out.print("<form action=\"");
        out.print("Register\" ");
        out.println("method=POST>");
        out.println("User Name:");
        out.println("<input type=text size=20 name=" + CommonParamString.PARAM_USERNAME + ">");
        // out.println("<br>");
        out.println("Password:");
        out.println("<input type=text size=20 name=" + CommonParamString.PARAM_PASSWORD + ">");
        // out.println("<br>");
        out.println("Email:");
        out.println("<input type=text size=20 name=" + CommonParamString.PARAM_EMAIL + ">");
        out.println("<br>");
        out.println("Your Twitter User Name:");
        out.println("<input type=text size=20 name=" + CommonParamString.PARAM_TWITTER_USER_NAME
                + ">");
        // out.println("<br>");
        out.println("Your Twitter Password:");
        out.println("<input type=text size=20 name="
                + CommonParamString.PARAM_TWITTER_USER_PASSWORD + ">");
        out.println("<br>");
        out.println("<input type=submit>");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");
    }
}

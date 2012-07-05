
package me.pjq.base;

import me.pjq.account.AccountInfo;
import me.pjq.database.SqliteManager;
import me.pjq.util.Utils;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.BasicAuthorization;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TimeZone;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The base HttpServlet.
 * 
 * @author pjq0274
 */
public class BaseHttpServlet extends HttpServlet {
    public static final String CONSUMER_KEY = "Fa0VtxzeoxV7OuktLMrVLw";
    public static final String CONSUMER_SECRET = "6X8el11OSkrnpeeziXeBNJbf1BBDRmBgFB0LNn7dY";

    /**
     * 
     */
    private static final long serialVersionUID = -7088836496424042711L;
    protected PrintWriter mPrintWriter;
    protected PrintWriter out;
    private HashMap<String, String> mRequestHashMap;
    private String mUserName = "";
    private String mPassword = "";
    private String mEmail = "";
    private String mTwitterUserName = "";
    private String mTwitterUserPassword = "";
    private AccountInfo mAccountInfo;
    /**
     * Store the origin request string read from the input stream.
     */
    protected String mOriginRequestString;

    /**
     * The client identify,it is null,it is from web.
     */
    private String mMachine;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        resp.setContentType("text/html;charset=UTF-8");
        // Init the common vars.
        out = resp.getWriter();
        mPrintWriter = out;

        String request = Utils.readFromInputStream(req.getInputStream());
        
        
        mOriginRequestString=request;
        mRequestHashMap = parserPostParameters(request);
        mMachine = mRequestHashMap.get(CommonParamString.PARAM_MACHINE);

        // Because the UserName and Password are passed in the Http Header,so
        // need parse differently.

        // Use the same parameters,so don't need handle differently.
        if (isFromWeb()) {
            // mUserName =
            // req.getParameter(CommonParamString.PARAM_USERNAME);
            // mPassword =
            // req.getParameter(CommonParamString.PARAM_PASSWORD);
            mUserName =
                    mRequestHashMap.get(CommonParamString.PARAM_USERNAME);
            mPassword =
                    mRequestHashMap.get(CommonParamString.PARAM_PASSWORD);
        } else {

            String authString = req.getHeader(CommonParamString.PARAM_HEADER_USERNAME_PASSWORD);
            parseAuthString(authString);
        }

        // mUserName = removeEnter(mRequestHashMap
        // .get(CommonParamString.PARAM_USERNAME));
        // mPassword = removeEnter(mRequestHashMap
        // .get(CommonParamString.PARAM_PASSWORD));
        mEmail = removeEnter(mRequestHashMap.get(CommonParamString.PARAM_EMAIL));
        mTwitterUserName = removeEnter(mRequestHashMap
                .get(CommonParamString.PARAM_TWITTER_USER_NAME));
        mTwitterUserPassword = removeEnter(mRequestHashMap
                .get(CommonParamString.PARAM_TWITTER_USER_PASSWORD));
        if (isUserNameOrPasswordEmpty()) {
            // mUserName = "pjq";
            // mPassword = "123";
        }

        if (null != mUserName) {
            mAccountInfo = getAccountInfo(mUserName);
        }
    }

    /**
     * Parser the UserName and Password.<br>
     * username=user&password=123
     * 
     * @param authString
     */
    protected void parseAuthString(String authString) {
        if (Utils.isEmpty(authString)) {
            return;
        }

        String[] str = authString.split("&");

        for (String s : str) {
            String[] pair = s.split("=");

            if (2 == pair.length) {
                if (pair[0].equals(CommonParamString.PARAM_USERNAME)) {
                    mUserName = pair[1];
                }
                if (pair[0].equals(CommonParamString.PARAM_PASSWORD)) {
                    mPassword = pair[1];
                }
            }
        }
    }

    /**
     * If the userName or password is null,you can set the default here.If it
     * will used in the situation,such as {@link GetUserTimeline} which will not
     * has the personal/private effect.
     * 
     * @see #getDefaultAccountInfo()
     * @see #userTheDefaultAccount()
     */
    protected void setDefaultUserNamePassword() {
        mUserName = "pjq";
        mPassword = "123";
    }

    /**
     * Print the request if need.
     */
    protected void printRequest() {
        Iterator<Entry<String, String>> iterator = mRequestHashMap.entrySet()
                .iterator();
        out.println("\nYour request:");
        while (iterator.hasNext()) {
            Entry<String, String> entry = iterator.next();
            String key = entry.getKey();
            String value = entry.getValue();

            if (key.contains("password")) {
                value = "****";
            }
            out.println(key + "=" + value);
        }

        out.println("");
    }

    /**
     * Remove the '\n'.
     * 
     * @param string
     * @return
     */
    private String removeEnter(String string) {
        if (null != string && string.contains("\n")) {
            string = string.replace("\n", "");
        }
        return string;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        // super.doPost(req, resp);
        doGet(req, resp);
    }

    protected HashMap<String, String> getRequestHashMap() {
        return mRequestHashMap;
    }

    protected String getUserName() {
        return mUserName;
    }

    protected String getPassword() {
        return mPassword;
    }

    protected String getEmail() {
        return mEmail;
    }

    protected String getTwitterUserName() {
        return mTwitterUserName;
    }

    protected String getTwitterUserPassword() {
        return mTwitterUserPassword;
    }

    protected AccountInfo getAccountInfo() {
        return mAccountInfo;
    }

    protected boolean isPasswordMatched() {
        return mAccountInfo.isPasswordMatched(mPassword);
    }

    protected boolean isUserNameOrPasswordEmpty() {
        return Utils.isEmpty(mUserName) || Utils.isEmpty(mPassword);
    }

    protected boolean isFromWeb() {
        boolean fromWeb = false;

        if (Utils.isEmpty(mMachine)) {
            fromWeb = true;
        } else {
            fromWeb = false;
        }

        return fromWeb;

    }

    /**
     * Create the twitter instance,you can use it to do what you want.
     * 
     * @return Twitter instance.
     */
    protected Twitter createTwitterInstance() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true).setOAuthConsumerKey(
                mAccountInfo.getTwitterConsumerKey()).setOAuthConsumerSecret(
                mAccountInfo.getTwitterConsumerSecret()).setOAuthAccessToken(
                mAccountInfo.getTwitterAccessToken())
                .setOAuthAccessTokenSecret(
                        mAccountInfo.getTwitterAccessTokenSecret());
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        return twitter;
    }

    /**
     * Create the Twitter4j Configutation.
     * 
     * @return
     */
    protected Configuration createTwitter4jConfiguration() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true).setOAuthConsumerKey(
                mAccountInfo.getTwitterConsumerKey()).setOAuthConsumerSecret(
                mAccountInfo.getTwitterConsumerSecret()).setOAuthAccessToken(
                mAccountInfo.getTwitterAccessToken())
                .setOAuthAccessTokenSecret(
                        mAccountInfo.getTwitterAccessTokenSecret());

        return cb.build();
    }

    /**
     * Get the AccessToken use xAuth with the twitter username and password.
     * 
     * @param userName
     * @param password
     */
    public AccessToken getXAuthAccessToken(String userName, String password) {
        out.println("getXAuthAccessToken,userName=" + userName + ",password="
                + password);
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true).setOAuthConsumerKey(CONSUMER_KEY)
                .setOAuthConsumerSecret(CONSUMER_SECRET);

        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance(new BasicAuthorization(userName,
                password));
        AccessToken accessToken = null;
        try {
            accessToken = twitter.getOAuthAccessToken();
            String token = accessToken.getToken();
            String secret = accessToken.getTokenSecret();
            String user = accessToken.getScreenName();
            out.println("Access Token=" + token + ",Access Token Secret="
                    + secret + ",your user name=" + user);
        } catch (TwitterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            out.println(e.getMessage());
        }

        return accessToken;
    }

    protected AccountInfo getAccountInfo(String userName) {
        AccountInfo accountInfo = SqliteManager.getInstance().queryAccountInfo(
                userName);
        mAccountInfo=accountInfo;
        return accountInfo;
    }

    /**
     * Use the default AccountInfo.
     * 
     * @see #setDefaultUserNamePassword()
     * @see #getDefaultAccountInfo()
     */
    protected void userTheDefaultAccount() {
        setDefaultUserNamePassword();
        getDefaultAccountInfo();
    }

    /**
     * Get the default account info after call
     * {@link #setDefaultUserNamePassword()}.
     * 
     * @see #setDefaultUserNamePassword()
     * @see #userTheDefaultAccount()
     * @return
     */
    protected AccountInfo getDefaultAccountInfo() {
        mAccountInfo = SqliteManager.getInstance().queryAccountInfo(mUserName);
        return mAccountInfo;
    }

    private String[] parser(String parameters) {
        String params[] = parameters.split("&");

        return params;
    }

    /**
     * Parser the post parameters.
     * 
     * @param parameters
     * @return
     */
    protected HashMap<String, String> parserPostParameters(String parameters) {
        String[] params = parser(parameters);

        int length = params.length;

        if (length <= 0) {
            return null;
        }

        HashMap<String, String> hashMap = new HashMap<String, String>();
        for (String p : params) {
            String[] map = p.split("=");
            if (map.length != 2) {
                continue;
            }
            hashMap.put(map[0], urlDecode(map[1]));
        }

        return hashMap;
    }

    public static String urlDecode(String str) {
        String result = str;
        try {
            result = URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }

    /**
     * PrintWriter the ResponseList
     * 
     * @param responseList
     */
    protected void printResponseList(ResponseList<Status> responseList,
            String endle) {
        for (Status st : responseList) {
            Date date = st.getCreatedAt();
            DateFormat dateFormat = DateFormat.getInstance();
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
            String dateString = dateFormat.format(date);
            String item = "[" + st.getUser().getName()+"/"+st.getUser().getScreenName() + "]: " + dateString
                    + endle + st.getText();
            out.println(item);
            if (isFromWeb()) {
                out.println("<br>");
            }
            out.println("------------------");
            if (isFromWeb()) {
                out.println("<br>");
            }
        }
    }

    protected void printResponseList(ResponseList<Status> responseList) {
        if (isFromWeb()) {
            printResponseList(responseList, "<br>");
        } else {
            printResponseList(responseList, "\n");
        }

    }

    protected void printHeader() {
        out.println("<html>");
        out.println("<head>");
        out.println("<title>FTClient Web</title>");
        out.println("</head>");
        out.println("<body>");
    }

    protected void printFooter() {
        out.println("</body>");
        out.println("</html>");
    }
}

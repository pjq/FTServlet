
package me.pjq.database;

import me.pjq.account.AccountInfo;
import me.pjq.account.web.Register;
import me.pjq.account.web.Update;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The Sqlite database manager.Use it to insert/update/select the database. <br>
 * NOTE:Very important:YOU MUST CALL {@link #init()} at least for once to init
 * the sqlite database.Otherwise it will occur errors when operation the
 * database.
 * 
 * @see #addUser(String, String, String, String, String, String, String)
 * @see #updateUser(String, String, String, String, String, String, String)
 * @see #queryAccountInfo(String)
 * @author pjq0274
 */
public class SqliteManager implements DataBaseInterface {
    public static final String DATABASE_NAME = "servlet.db";

    public static final String TABLE_ACCOUNT_NAME = "account";

    public static String SQL_CREATE_TABLE = "CREATE TABLE [" +
            TABLE_ACCOUNT_NAME + "] ([" +
            Columns.Account.COLUMNS_USER_ID + "]AUTOINC,[" +
            Columns.Account.ACCOUNT_COLUMNS_USER_NAME + "] TEXT,[" +
            Columns.Account.ACCOUNT_COLUMNS_USER_PASSWORD + "] TEXT,[" +
            Columns.Account.ACCOUNT_COLUMNS_USER_EMAIL + "] TEXT,  [" +
            Columns.Account.ACCOUNT_COLUMNS_USER_CREATE_AT + "] TEXT,[" +
            Columns.Account.ACCOUNT_COLUMNS_USER_TWITTER_CONSUMER_KEY + "] TEXT,[" +
            Columns.Account.ACCOUNT_COLUMNS_USER_TWITTER_CONSUMER_SECRET + "] TEXT,[" +
            Columns.Account.ACCOUNT_COLUMNS_USER_TWITTER_ACCESS_TOKEN + "] TEXT,[" +
            Columns.Account.ACCOUNT_COLUMNS_USER_TWITTER_ACCESS_TOKEN_SECRET + "] TEXT,[" +
            Columns.Account.ACCOUNT_COLUMNS_USER_DISPLAY_NAME + "] TEXT,[" +
            Columns.Account.ACCOUNT_COLUMNS_USER_FACEBOOK_CONSUMER_KEY + "] TEXT,[" +
            Columns.Account.ACCOUNT_COLUMNS_USER_FACEBOOK_CONSUMER_SECRET + "] TEXT,[" +
            Columns.Account.ACCOUNT_COLUMNS_USER_FACEBOOK_ACCESS_TOKEN + "] TEXT,[" +
            Columns.Account.ACCOUNT_COLUMNS_USER_FACEBOOK_ACCESS_TOKEN_SECRET + "] TEXT)";

    public static String SQL_CREATE_TABLE_TMP = "CREATE TABLE [table_tmp]([user_id] AUTOINC,[user_name] TEXT,[user_email] TEXT)";

    private static SqliteManager mInstance;

    /**
     * Get the {@link SqliteManager} static instance.
     * 
     * @return
     */
    public static SqliteManager getInstance() {
        if (null == mInstance) {
            mInstance = new SqliteManager();
        }

        return mInstance;
    }

    @Override
    public boolean init() {
        // TODO Auto-generated method stub
        Connection connection = DataManager.getInstance().getConnection();
        Statement statement;
        boolean result = false;
        try {
            statement = connection.createStatement();
            result = statement.execute(SQL_CREATE_TABLE);
            result = true;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            result = false;
        }

        return result;
    }

    @Override
    public void query() {
        // TODO Auto-generated method stub

    }

    /**
     * Add user,used when register an new user with {@link Register}
     * 
     * @see Register
     * @param username
     * @param password
     * @param twitterAccessToken
     * @param twitterAccessTokenSecret
     * @return true if add success,false if it is already existed.
     */
    public boolean addUser(String username, String password, String twitterAccessToken,
            String twitterAccessTokenSecret, String email, String consumerKey, String consumerSecret) {
        String addUserSQL = "INSERT INTO " + TABLE_ACCOUNT_NAME + " ("
                + Columns.Account.ACCOUNT_COLUMNS_USER_NAME + ","
                + Columns.Account.ACCOUNT_COLUMNS_USER_PASSWORD + ","
                + Columns.Account.ACCOUNT_COLUMNS_USER_TWITTER_ACCESS_TOKEN + ","
                + Columns.Account.ACCOUNT_COLUMNS_USER_TWITTER_ACCESS_TOKEN_SECRET + ","
                + Columns.Account.ACCOUNT_COLUMNS_USER_EMAIL + ","
                + Columns.Account.ACCOUNT_COLUMNS_USER_TWITTER_CONSUMER_KEY + ","
                + Columns.Account.ACCOUNT_COLUMNS_USER_TWITTER_CONSUMER_SECRET + " "
                + ") values('"
                + username + "','"
                + password + "','"
                + twitterAccessToken + "','"
                + twitterAccessTokenSecret + "','"
                + email + "','"
                + consumerKey + "','"
                + consumerSecret + "'"
                + ");";
        boolean result = false;

        if (!isUserExist(username)) {
            result = executeSql(addUserSQL);
        }

        return result;
    }

    /**
     * Update the user info
     * 
     * @param username
     * @param password
     * @param twitterAccessToken
     * @param twitterAccessTokenSecret
     * @param email
     * @param consumerKey
     * @param consumerSecret
     * @see Update
     * @return
     */
    public boolean updateUser(String username, String password, String twitterAccessToken,
            String twitterAccessTokenSecret, String email, String consumerKey, String consumerSecret) {
        String addUserSQL = "UPDATE " + TABLE_ACCOUNT_NAME + " set "
                + Columns.Account.ACCOUNT_COLUMNS_USER_TWITTER_ACCESS_TOKEN + " = '"
                + twitterAccessToken + "',"
                + Columns.Account.ACCOUNT_COLUMNS_USER_TWITTER_ACCESS_TOKEN_SECRET + " = '"
                + twitterAccessTokenSecret + "',"
                + Columns.Account.ACCOUNT_COLUMNS_USER_EMAIL + " = '" + email + "',"
                + Columns.Account.ACCOUNT_COLUMNS_USER_TWITTER_CONSUMER_KEY + " = '" + consumerKey
                + "',"
                + Columns.Account.ACCOUNT_COLUMNS_USER_TWITTER_CONSUMER_SECRET + " = '"
                + consumerSecret + "' "
                + " where (" + Columns.Account.ACCOUNT_COLUMNS_USER_NAME + " = '" + username
                + "' )";

        boolean result = false;

        result = executeSql(addUserSQL);

        return result;
    }

    /**
     * Check if the user already existed.
     * 
     * @param username
     * @return true if the user already existed.
     */
    private boolean isUserExist(String username) {
        String queryUser = "SELECT (" + Columns.Account.ACCOUNT_COLUMNS_USER_NAME + ") from "
                + TABLE_ACCOUNT_NAME + " where ( " + Columns.Account.ACCOUNT_COLUMNS_USER_NAME
                + " = '" + username + "' )";
        boolean existed = false;

        ResultSet resultSet = executeSqlQuery(queryUser);
        try {
            boolean moreRecords = resultSet.next();
            existed = moreRecords;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            existed = false;
        }

        return existed;
    }

    /**
     * Query the user password.
     * 
     * @param username
     * @return password
     */
    public String queryPassword(String username) {
        String queryUser = "SELECT (" + Columns.Account.ACCOUNT_COLUMNS_USER_PASSWORD + ") from "
                + TABLE_ACCOUNT_NAME + " where ( " + Columns.Account.ACCOUNT_COLUMNS_USER_NAME
                + " = '" + username + "' )";
        String password = null;

        ResultSet resultSet = executeSqlQuery(queryUser);
        try {
            password = resultSet.getString(Columns.Account.ACCOUNT_COLUMNS_USER_PASSWORD);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }

        return password;
    }

    /**
     * Check the username and password.
     * 
     * @param username
     * @param password
     * @return true if username and password matched.
     */
    public boolean checkPassword(String username, String password) {
        String sql = "SELECT (" + Columns.Account.ACCOUNT_COLUMNS_USER_NAME + ","
                + Columns.Account.ACCOUNT_COLUMNS_USER_PASSWORD + ") from "
                + TABLE_ACCOUNT_NAME + " where ( " + Columns.Account.ACCOUNT_COLUMNS_USER_NAME
                + " = '" + username + "," + Columns.Account.ACCOUNT_COLUMNS_USER_PASSWORD + "="
                + password + "' )";
        boolean matched = false;

        ResultSet resultSet = executeSqlQuery(sql);
        try {
            boolean moreRecords = resultSet.next();
            matched = moreRecords;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            matched = false;
        }

        return matched;
    }

    /**
     * Query the {@link AccountInfo},it includes all the user info.
     * 
     * @param userName
     * @return
     */
    public AccountInfo queryAccountInfo(String userName) {
        AccountInfo accountInfo = new AccountInfo();
        String sql = "SELECT * from "
                + TABLE_ACCOUNT_NAME + " where ( " + Columns.Account.ACCOUNT_COLUMNS_USER_NAME
                + " = '" + userName + "' )";

        ResultSet resultSet = executeSqlQuery(sql);
        try {
            String userPassword = resultSet
                    .getString(Columns.Account.ACCOUNT_COLUMNS_USER_PASSWORD);
            String userEmail = resultSet
                    .getString(Columns.Account.ACCOUNT_COLUMNS_USER_EMAIL);
            String userTwitterConsumerKey = resultSet
                    .getString(Columns.Account.ACCOUNT_COLUMNS_USER_TWITTER_CONSUMER_KEY);
            String userTwitterConsumerSecret = resultSet
                    .getString(Columns.Account.ACCOUNT_COLUMNS_USER_TWITTER_CONSUMER_SECRET);
            String userTwitterAccessToken = resultSet
                    .getString(Columns.Account.ACCOUNT_COLUMNS_USER_TWITTER_ACCESS_TOKEN);
            String userTwitterAccessTokenSecret = resultSet
                    .getString(Columns.Account.ACCOUNT_COLUMNS_USER_TWITTER_ACCESS_TOKEN_SECRET);
            String userFacebookAccessToken = resultSet
                    .getString(Columns.Account.ACCOUNT_COLUMNS_USER_FACEBOOK_ACCESS_TOKEN);
            String userFacebookAccessTokenSecret = resultSet
                    .getString(Columns.Account.ACCOUNT_COLUMNS_USER_FACEBOOK_ACCESS_TOKEN_SECRET);
            String userDisplayName = resultSet
                    .getString(Columns.Account.ACCOUNT_COLUMNS_USER_DISPLAY_NAME);

            accountInfo.setUserName(userName);
            accountInfo.setPassword(userPassword);
            accountInfo.setEmail(userEmail);
            accountInfo.setDisplayName(userDisplayName);
            accountInfo.setTwitterConsumerKey(userTwitterConsumerKey);
            accountInfo.setTwitterConsumerSecret(userTwitterConsumerSecret);
            accountInfo.setTwitterAccessToken(userTwitterAccessToken);
            accountInfo.setTwitterAccessTokenSecret(userTwitterAccessTokenSecret);
            accountInfo.setFacebookAccessToken(userFacebookAccessToken);
            accountInfo.setFacebookAccessTokenSecret(userFacebookAccessTokenSecret);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return accountInfo;
    }

    /**
     * Execute the sql
     * 
     * @param sql
     * @return true if execute sql success with no SQLException.
     */
    private boolean executeSql(String sql) {
        Connection connection = DataManager.getInstance().getConnection();
        Statement statement;
        boolean result = false;
        
        try {
            statement = connection.createStatement();
            statement.execute(sql);
            result = true;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            result = false;
        }

        return result;
    }

    /**
     * Execute the sql query.
     * 
     * @param sql
     * @return ResultSet
     */
    private ResultSet executeSqlQuery(String sql) {
        Connection connection = DataManager.getInstance().getConnection();
        Statement statement;
        ResultSet result = null;
        
        try {
            statement = connection.createStatement();
            result = statement.executeQuery(sql);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            SqliteManager.getInstance().init();
        }

        return result;
    }
}

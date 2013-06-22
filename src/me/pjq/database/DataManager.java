
package me.pjq.database;

import java.sql.*;

/**
 * The database manager,use it to get the connection. BUT all most the
 * operations are implemented in {@link SqliteManager}
 * 
 * @see SqliteManager
 * @author pjq0274
 */
public class DataManager {
    private static DataManager mInstance;
    private Connection mConnection;

    public static final int DATABASE_TYPE_MYSQL = 1;
    public static final int DATABASE_TYPE_SQLITE = 2;
    public static final int DATABASE_TYPE = DATABASE_TYPE_SQLITE;

    public DataManager() {
        // TODO Auto-generated constructor stub
        init();
    }

    /**
     * Get the instance
     * 
     * @return
     */
    public static DataManager getInstance() {
        if (null == mInstance) {
            mInstance = new DataManager();
        }

        return mInstance;
    }

    private void init() {
        try {
            mConnection = null;
            // Class.forName("org.gjt.mm.mysql.Driver");

            switch (DATABASE_TYPE) {
                case DATABASE_TYPE_SQLITE: {
                    Class.forName("org.sqlite.JDBC");
                    break;
                }

                case DATABASE_TYPE_MYSQL: {
                    Class.forName("com.mysql.jdbc.Driver");
                    break;
                }

                default:
                    break;
            }
        }

        catch (ClassNotFoundException cnfex) {
            System.err.println("Load jdbc driver error");
            cnfex.printStackTrace();
            System.exit(1); // terminate program
        }
    }

    public Connection getConnection() {
        if (null == mConnection) {
            switch (DATABASE_TYPE) {
                case DATABASE_TYPE_SQLITE: {
                    try {
                        mConnection = null;
                        mConnection = DriverManager
                                .getConnection("jdbc:sqlite:" + SqliteManager.DATABASE_NAME);
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        mConnection = null;
                        System.exit(1); // terminate program
                    }

                    break;
                }

                case DATABASE_TYPE_MYSQL: {
                    String url = "jdbc:mysql://localhost:3306/mydb";
                    String username = "root";
                    String password = "mysql";

                    try {
                        mConnection = null;
                        mConnection = DriverManager.getConnection(url, username, password);
                    } catch (SQLException sqlex) {
                        System.err.println("SQLException");
                        sqlex.printStackTrace();
                        mConnection = null;
                        System.exit(1); // terminate program

                    }
                    break;
                }

                default:
                    break;
            }

        }

        return mConnection;
    }

}

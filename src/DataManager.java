import java.sql.*;

public class DataManager {
	private static DataManager mInstance;
	private Connection mConnection;
	public static boolean USE_SQLITE=false;

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
			if (USE_SQLITE) {
				Class.forName("org.sqlite.JDBC");
			}else {
				Class.forName("com.mysql.jdbc.Driver");
			}
			
		}

		catch (ClassNotFoundException cnfex) {
			System.err.println("Load jdbc driver error");
			cnfex.printStackTrace();
			System.exit(1); // terminate program
		}
	}

	public Connection getConnection() {
		String url = "jdbc:mysql://localhost:3306/mydb";
		String urlSqlite="jdbc:sqlite:mydb.db";
		String username = "root";
		String password = "mysql";

		try {
			mConnection = null;
			if (USE_SQLITE) {
				mConnection = DriverManager.getConnection(urlSqlite);
			}else {
				mConnection = DriverManager.getConnection(url, username, password);
			}			
			
		} catch (SQLException sqlex) {
			System.err.println("SQLException");
			sqlex.printStackTrace();
			// System.exit(1); // terminate program
		}

		return mConnection;
	}

	public boolean addAccount(String username, String password, String email,
			String displayname) {
		boolean result = true;

		Connection connection = getConnection();
		String sqlString = "INSERT INTO `mydb`.`account`(`id`,`username`,`password`,`email`,`display_name`) VALUES ( NULL,'"
				+ username
				+ "','"
				+ password
				+ "','"
				+ email
				+ "','"
				+ displayname + "');";

		Statement statement;
		try {
			statement = connection.createStatement();
			//ResultSet resultSet = statement.executeQuery(sqlString);
			statement.execute(sqlString);

			result=true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result=false;
		}

		return result;
	}

}

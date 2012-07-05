
package me.pjq.database;

import me.pjq.base.BaseHttpServlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Just use it query all the database records.
 * 
 * @author pjq0274
 */
public class QueryDatabase extends BaseHttpServlet {
    /**
     * 
     */
    private static final long serialVersionUID = -3469500589287640529L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        super.doGet(req, resp);

        DataManager dataManager = DataManager.getInstance();
        Connection connection = dataManager.getConnection();

        if (null == connection) {
            out.println("Can't get Database Connection,Let's init the database,Please try again.");
            new SqliteManager().init();
            return;
        }

        String sqlString = "SELECT *  from " + SqliteManager.TABLE_ACCOUNT_NAME;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlString);
            boolean moreRecords = resultSet.next();

            if (!moreRecords) {
                out.println("no records in db");
                return;
            }

            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

            int columnCount = resultSetMetaData.getColumnCount();

            out.println("columnCount=" + columnCount + '\n');
            Vector<String> columnHeads = new Vector<String>();
            for (int i = 1; i <= columnCount; i++) {
                columnHeads.addElement(resultSetMetaData.getColumnName(i));
                out.print(resultSetMetaData.getColumnName(i) + " ");
            }

            do {
                out.println('\n');
                for (String v : columnHeads) {
                    String val = resultSet.getString(v);
                    if (v.contains("password")) {
                        val = "******";
                    }
                    out.print(v + "=" + val + " ");
                }
                // out.println('\n');

            } while (resultSet.next());

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            SqliteManager.getInstance().init();
        }
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
        super.destroy();

        try {
            DataManager.getInstance().getConnection().close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

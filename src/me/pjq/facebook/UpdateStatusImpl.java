
package me.pjq.facebook;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.pjq.base.UpdateStatus;

/**
 * Implement update the status for Facebook.
 * 
 * @author pjq0274
 */
public class UpdateStatusImpl extends UpdateStatus {

    /**
	 * 
	 */
    private static final long serialVersionUID = 8259034440405737745L;

    @Override
    public void updateStatus(String userName, String password, String message) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("Facebook Update");
    }

}

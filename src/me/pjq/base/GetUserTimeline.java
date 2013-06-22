
package me.pjq.base;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Get the Timeline interface
 * 
 * @author pjq
 */
public abstract class GetUserTimeline extends BaseHttpServlet {
    /**
	 * 
	 */
    private static final long serialVersionUID = 5510470811990058855L;

    protected abstract void getTimeline();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        // TODO Auto-generated method stub
        super.doGet(req, resp);

        // Use the default user,and reInit the AccountInfo again.
        if (isUserNameOrPasswordEmpty()) {
            userTheDefaultAccount();
        }
    }
}

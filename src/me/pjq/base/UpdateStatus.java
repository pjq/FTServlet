
package me.pjq.base;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Update Status interface
 * 
 * @author pjq
 */
public abstract class UpdateStatus extends BaseHttpServlet {
    /**
	 * 
	 */
    private static final long serialVersionUID = -835511669272745216L;

    protected abstract void updateStatus(String userName, String password,
            String message);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        // TODO Auto-generated method stub
        super.doGet(req, resp);
        
        printRequest();
    }


 

}

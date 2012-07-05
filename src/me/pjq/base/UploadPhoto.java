
package me.pjq.base;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UploadPhoto extends BaseHttpServlet {

    /**
	 * 
	 */
    private static final long serialVersionUID = 8093353980308737148L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        // super.doGet(req, resp);
        // Init the common vars.
        resp.setContentType("text/html;charset=UTF-8");
        out = resp.getWriter();
        mPrintWriter = out;

        String authString =
                req.getHeader(CommonParamString.PARAM_HEADER_USERNAME_PASSWORD);
        //Get the UserName and Password
        parseAuthString(authString);
        getAccountInfo(getUserName());
    }

}


package me.pjq.base;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetUserList extends BaseHttpServlet {
    /**
     * 
     */
    private static final long serialVersionUID = 2612161720724328590L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        // TODO Auto-generated method stub
        super.doGet(req, resp);

        createTwitterInstance();
    }

}

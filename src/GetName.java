import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class GetName extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5061448159802007059L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		    String contentType=req.getContentType();
	       resp.setContentType("text/html");
	       resp.setContentType("css");
	       PrintWriter out = resp.getWriter();
	       out.println("Get name,contentType="+contentType+",the method="+req.getMethod());
	       out.println("\nStart Connect to the Mysql...");
	       //DataManager dataManager=DataManager.getInstance();
	       out.println("\nConnect to the Mysql...done.");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}

}

package me.pjq.openapi;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.pjq.base.BaseHttpServlet;

public class GetPM25 extends BaseHttpServlet {	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);

		resp.setContentType("text/html;charset=UTF-8");
		// Init the common vars.
		//out = resp.getWriter();
		//mPrintWriter = out;

		//String request = Utils.readFromInputStream(req.getInputStream());
		//mRequestHashMap = parserPostParameters(request);

		String city = req.getParameter( "city"); 

		mPrintWriter.println("#Get city pm 2.5:"+ city);
		out.println(city+" pm 2.5 is");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}

	private String[] parser(String parameters) {
		String params[] = parameters.split("&");

		return params;
	}

	/**
	 * Parser the post parameters.
	 * 
	 * @param parameters
	 * @return
	 */
	protected HashMap<String, String> parserPostParameters(String parameters) {
		String[] params = parser(parameters);

		int length = params.length;

		if (length <= 0) {
			return null;
		}

		HashMap<String, String> hashMap = new HashMap<String, String>();
		for (String p : params) {
			String[] map = p.split("=");
			if (map.length != 2) {
				continue;
			}
			hashMap.put(map[0], urlDecode(map[1]));
		}

		return hashMap;
	}

	public static String urlDecode(String str) {
		String result = str;
		try {
			result = URLDecoder.decode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return result;
	}

}

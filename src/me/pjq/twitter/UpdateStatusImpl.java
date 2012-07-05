package me.pjq.twitter;

import sun.reflect.ReflectionFactory.GetReflectionFactoryAction;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.pjq.account.AccountInfo;
import me.pjq.base.CommonParamString;
import me.pjq.base.UpdateStatus;
import me.pjq.util.Utils;

/**
 * Handle update status for Twitter.
 * 
 * @author pjq0274
 */
public class UpdateStatusImpl extends UpdateStatus {

	/**
     * 
     */
	private static final long serialVersionUID = 4979679200836047678L;

	// public static final String CONSUMER_KEY = "Fa0VtxzeoxV7OuktLMrVLw";
	// public static final String CONSUMER_SECRET =
	// "6X8el11OSkrnpeeziXeBNJbf1BBDRmBgFB0LNn7dY";

	// public static final String ACCESS_TOKEN =
	// "52646242-mgwUKKcx9AjEzMmnwHs8aQ6SQeSCa2plg2PU8zeDu";
	// public static final String ACCESS_TOKEN_SECRET =
	// "DlyuSJyvugoMcgLnDV98vxJSjWFXEfmxkAZvMOgCHo";

	/**
	 * Update the status for twitter.
	 */
	private Status updateStatus(PrintWriter out, String message,
			AccountInfo accountInfo) {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey(
				accountInfo.getTwitterConsumerKey()).setOAuthConsumerSecret(
				accountInfo.getTwitterConsumerSecret()).setOAuthAccessToken(
				accountInfo.getTwitterAccessToken()).setOAuthAccessTokenSecret(
				accountInfo.getTwitterAccessTokenSecret());
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();

		Status status = null;
		try {
			status = twitter.updateStatus(message);

		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.println(e.toString());
		}

		return status;
	}

	@Override
	public void updateStatus(String userName, String password, String message) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);

		HashMap<String, String> hashMap = getRequestHashMap();
		// int size = hashMap.size();
		// out.println("parameters size=" + size);

		// Iterator<Entry<String, String>> iterator = hashMap.entrySet()
		// .iterator();
		// out.println("Your request:");
		// while (iterator.hasNext()) {
		// Entry<String, String> entry = iterator.next();
		// String key = entry.getKey();
		// String value = entry.getValue();
		//
		// out.println(key + "=" + value);
		// }

		String userName = getUserName();
		String password = getPassword();
		if (password.contains("\n")) {
			password = password.replace("\n", "");
		}
		String message = hashMap.get(CommonParamString.PARAM_MESSAGE);

		// updateStatus(userName, password, message);
		if (null == message) {
			message = "You message is null,use this default message";
		}

		// out.println("Your UserName/Password=" + userName + "/" + password);

		AccountInfo accountInfo = getAccountInfo();
		// out.print(accountInfo.toString());

		if (isUserNameOrPasswordEmpty()) {
			out
					.println("\nYour input username or password is null.\nIf need help,please contact pengjianqing@gmail.com");
			return;
		}

		if (isPasswordMatched()) {
			out.println("Twitter Update Result:");
			Status status = updateStatus(out, message, accountInfo);
			if (null != status) {
				String st = "Update status success:" + status.getText();
				out.println(st);
			} else {
				out.println("The response status is null,update failed");
			}
		} else {
			out
					.println("Your username or password is wrong.\nIf need help,please contact pengjianqing@gmail.com");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// super.doPost(req, resp);
		doGet(req, resp);
	}
}

package me.pjq.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
	/**
	 * Read data from InputStreamReader
	 * 
	 * @param isr
	 *            InputStreamReader
	 * @return The Data read from The InputStreamReader
	 */
	public static String readFromInputStream(InputStreamReader isr) {
		String result = "";

		BufferedReader rd = new BufferedReader(isr);
		String line;

		try {
			while ((line = rd.readLine()) != null) {
				result += line + '\n';
			}

			isr.close();
			rd.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * Read data from InputStreamReader
	 * 
	 * @param isr
	 *            InputStreamReader
	 * @return The Data read from The InputStreamReader
	 */
	public static String readFromInputStream(InputStream is) {
		String result = null;

		try {
			result = readFromInputStream(new InputStreamReader(is, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * Read data from InputStreamReader
	 * 
	 * @param isr
	 *            InputStreamReader
	 * @return The Data read from The InputStreamReader
	 */
	public static void storeImageFromInputStream(InputStream is, int length,
			String uploadPath, String photoName) {
		try {
			File path = new File(uploadPath);
			if (!path.exists()) {
				path.mkdirs();
			}

			FileOutputStream fileOutputStream = new FileOutputStream(uploadPath
					+ "/" + photoName);

			byte[] cbuf = new byte[8096 * 10];

			byte[] fileBuf = new byte[length * 8];

			int len = -1;

			int total = 0;
			// while ((len = is.read(fileBuf, total+1, 8 * 1024)) != -1) {
			// total += len;
			// fileOutputStream.write(fileBuf, total, len);
			// }

			// is.read(fileBuf, fileBuf.length, 8096 * 10);

			while ((len = is.read(cbuf)) != -1) {
				fileOutputStream.write(cbuf, 0, len);
			}

			fileOutputStream.close();
			is.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String createPhotoName() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
				+ ".png";
	}

	/**
	 * Check the string is empty
	 * 
	 * @param string
	 * @return true if the string is null or "".
	 */
	public static boolean isEmpty(String string) {
		boolean empty = false;

		if (null == string || (null != string && string.equals(""))) {
			empty = true;
		}

		return empty;
	}

	/**
	 * Write file,used to write the log.
	 * 
	 * @param fileName
	 * @param data
	 */
	public static void writeFile(String fileName, String data) {
		BufferedWriter buf = null;
		try {
			buf = new BufferedWriter(new FileWriter(fileName, true));
			buf.write(data, 0, data.length());
			buf.newLine();
		} catch (OutOfMemoryError oom) {

		} catch (Exception e) {
		} finally {
			try {
				if (buf != null)
					buf.close();
			} catch (IOException e) {
			}
		}
	}

	public static String readFileToString(String fullName) {
		StringBuffer sbuffer = new StringBuffer();
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new FileReader(fullName));
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				sbuffer.append(line + "\n");
			}
		} catch (Exception e) {
		} finally {
			try {
				if (bufferedReader != null)
					bufferedReader.close();
			} catch (IOException ex) {
			}
		}
		return sbuffer.toString();
	}

	private static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	private static MessageDigest messagedigest = null;

	static {
		try {
			messagedigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	public static String getFileMD5String(File file) throws IOException {
		FileInputStream in = new FileInputStream(file);
		FileChannel ch = in.getChannel();
		MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0,
				file.length());
		messagedigest.update(byteBuffer);
		return bufferToHex(messagedigest.digest());
	}

	public static String getMD5String(String s) {
		return getMD5String(s.getBytes());
	}

	private static String getMD5String(byte[] bytes) {
		messagedigest.update(bytes);
		return bufferToHex(messagedigest.digest());
	}

	private static String bufferToHex(byte bytes[]) {
		return bufferToHex(bytes, 0, bytes.length);
	}

	private static String bufferToHex(byte bytes[], int m, int n) {
		StringBuffer stringbuffer = new StringBuffer(2 * n);
		int k = m + n;
		for (int l = m; l < k; l++) {
			appendHexPair(bytes[l], stringbuffer);
		}
		return stringbuffer.toString();
	}

	private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
		char c0 = hexDigits[(bt & 0xf0) >> 4];
		char c1 = hexDigits[bt & 0xf];
		stringbuffer.append(c0);
		stringbuffer.append(c1);
	}
}

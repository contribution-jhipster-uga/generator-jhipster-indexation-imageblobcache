//<--! package -->

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

public class SHAUtil {


	public static final String SHA_EXTENSION = "sha1";

	public static String hash(byte[] buf) {
	    MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			// NEVER THROW SINCE SHA-1 EXISTS
			return null;
		}
	    return byteArray2Hex(md.digest(buf));
	}

	private static String byteArray2Hex(final byte[] ba) {
	    Formatter formatter = new Formatter();
	    for (byte b : ba) {
	        formatter.format("%02x", b);
	    }
	    String res = formatter.toString();
	    formatter.close();
	    return res;
	}
}

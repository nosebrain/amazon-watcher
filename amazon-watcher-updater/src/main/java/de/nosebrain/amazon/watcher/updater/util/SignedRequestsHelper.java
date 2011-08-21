package de.nosebrain.amazon.watcher.updater.util;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TimeZone;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * 
 * @author nosebrain
 * @author Amazon
 */
public class SignedRequestsHelper {
	private static final String UTF8_CHARSET = "UTF-8";
	private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";
	private static final String REQUEST_URI = "/onca/xml";
	private static final String REQUEST_METHOD = "GET";
	
	private static final DateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

	static {
		FORMAT.setTimeZone(TimeZone.getTimeZone("GMT"));
	}
	
	private String endpoint = null; // must be lowercase
	private String awsAccessKeyId = null;
	private String awsSecretKey = null;

	private SecretKeySpec secretKeySpec = null;
	private Mac mac = null;
	
	/**
	 * inits the secret key spec and mac
	 */
	public void init() {
		try {
			byte[] secretyKeyBytes = this.awsSecretKey.getBytes(UTF8_CHARSET);
			this.secretKeySpec = new SecretKeySpec(secretyKeyBytes, HMAC_SHA256_ALGORITHM);
			this.mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
			this.mac.init(secretKeySpec);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		} catch (InvalidKeyException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @param params the params to use
	 * @return the signed url 
	 */
	public String sign(final Map<String, String> params) {
		if (this.mac == null) {
			this.init();
		}
		
		/*
		 * add id and timestamp
		 */
		params.put("AWSAccessKeyId", awsAccessKeyId);
		params.put("Timestamp", this.createTimeStamp());

		final SortedMap<String, String> sortedParamMap = new TreeMap<String, String>(params);
		final String canonicalQS = canonicalize(sortedParamMap);
		final String toSign = REQUEST_METHOD + "\n" + this.endpoint + "\n" + REQUEST_URI + "\n" + canonicalQS;
		
		final String sig = this.percentEncodeRfc3986(this.hmac(toSign));
		return "http://" + endpoint + REQUEST_URI + "?" + canonicalQS + "&Signature=" + sig;
	}

	private String hmac(String stringToSign) {
		try {
			final byte[] data = stringToSign.getBytes(UTF8_CHARSET);
			final byte[] rawHmac = mac.doFinal(data);
			final Base64 encoder = new Base64();
			return new String(encoder.encode(rawHmac));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(UTF8_CHARSET + " is unsupported!", e);
		}
	}

	private String createTimeStamp() {
		final Calendar cal = Calendar.getInstance();
		return FORMAT.format(cal.getTime());
	}

	private String canonicalize(final SortedMap<String, String> sortedParamMap) {
		if (sortedParamMap.isEmpty()) {
			return "";
		}

		final StringBuffer builder = new StringBuffer();
		final Iterator<Map.Entry<String, String>> iter = sortedParamMap.entrySet().iterator();
		while (iter.hasNext()) {
			final Map.Entry<String, String> kvpair = iter.next();
			builder.append(this.percentEncodeRfc3986(kvpair.getKey()));
			builder.append("=");
			builder.append(this.percentEncodeRfc3986(kvpair.getValue()));
			if (iter.hasNext()) {
				builder.append("&");
			}
		}
		
		return builder.toString();
	}

	private String percentEncodeRfc3986(String s) {
		try {
			return URLEncoder.encode(s, UTF8_CHARSET).replace("+", "%20").replace("*", "%2A").replace("%7E", "~");
		} catch (UnsupportedEncodingException e) {
			return s;
		}
	}

	/**
	 * @param awsAccessKeyId
	 *            the awsAccessKeyId to set
	 */
	public void setAwsAccessKeyId(String awsAccessKeyId) {
		this.awsAccessKeyId = awsAccessKeyId;
	}

	/**
	 * @param awsSecretKey
	 *            the awsSecretKey to set
	 */
	public void setAwsSecretKey(String awsSecretKey) {
		this.awsSecretKey = awsSecretKey;
	}

	/**
	 * @param endpoint
	 *            the endpoint to set
	 */
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint.toLowerCase();
	}

}
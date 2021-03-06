
/**********************************************************************************************
 * Copyright 2009 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file
 * except in compliance with the License. A copy of the License is located at
 *
 *       http://aws.amazon.com/apache2.0/
 *
 * or in the "LICENSE.txt" file accompanying this file. This file is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under the License.
 *
 * ********************************************************************************************
 *
 *  Amazon Product Advertising API
 *  Signed Requests Sample Code
 *
 *  API Version: 2009-03-31
 *
 */

package de.nosebrain.amazon.watcher.updater.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
 * This class contains all the logic for signing requests
 * to the Amazon Product Advertising API.
 * @author nosebrain
 * @author Amazon
 */
public class SignedRequestsHelper {
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

	static {
		DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT"));
	}

	/**
	 * All strings are handled as UTF-8
	 */
	private static final String UTF8_CHARSET = "UTF-8";

	/**
	 * The HMAC algorithm required by Amazon
	 */
	private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";

	/**
	 * This is the URI for the service, don't change unless you really know
	 * what you're doing.
	 */
	private static final String REQUEST_URI = "/onca/xml";

	/**
	 * The sample uses HTTP GET to fetch the response. If you changed the sample
	 * to use HTTP POST instead, change the value below to POST.
	 */
	private static final String REQUEST_METHOD = "GET";

	/**
	 * Generate a ISO-8601 format timestamp as required by Amazon.
	 * 
	 * @return  ISO-8601 format timestamp.
	 */
	private static String createTimestamp() {
		final Calendar cal = Calendar.getInstance();
		return DATE_FORMAT.format(cal.getTime());
	}

	/**
	 * Percent-encode values according the RFC 3986. The built-in Java
	 * URLEncoder does not encode according to the RFC, so we make the
	 * extra replacements.
	 * 
	 * @param s decoded string
	 * @return  encoded string per RFC 3986
	 */
	private static String percentEncodeRfc3986(final String s) {
		try {
			return URLEncoder.encode(s, UTF8_CHARSET).replace("+", "%20").replace("*", "%2A").replace("%7E", "~");
		} catch (final UnsupportedEncodingException e) {
			return s;
		}
	}

	/**
	 * Canonicalize the query string as required by Amazon.
	 * 
	 * @param sortedParamMap    Parameter name-value pairs in lexicographical order.
	 * @return                  Canonical form of query string.
	 */
	private static String canonicalize(final SortedMap<String, String> sortedParamMap) {
		if (sortedParamMap.isEmpty()) {
			return "";
		}

		final StringBuilder buffer = new StringBuilder();
		final Iterator<Map.Entry<String, String>> iter = sortedParamMap.entrySet().iterator();
		while (iter.hasNext()) {
			final Map.Entry<String, String> kvpair = iter.next();
			buffer.append(percentEncodeRfc3986(kvpair.getKey()));
			buffer.append("=");
			buffer.append(percentEncodeRfc3986(kvpair.getValue()));
			if (iter.hasNext()) {
				buffer.append("&");
			}
		}
		return buffer.toString();
	}

	private String awsAccessKeyId = null;
	private String awsSecretKey = null;
	private String awsAssociateTag = null;

	private Mac mac = null;

	/**
	 * This method signs requests in hashmap form. It returns a URL that should
	 * be used to fetch the response. The URL returned should not be modified in
	 * any way, doing so will invalidate the signature and Amazon will reject
	 * the request.
	 * @param endpoint the end point to use
	 * @param params the params to use
	 * @return the signed request url
	 */
	public String sign(final String endpoint, final Map<String, String> params) {
		if (this.mac == null) {
			this.init();
		}
		// Let's add the AWSAccessKeyId, Timestamp and associate tag parameters to the request.
		params.put("AWSAccessKeyId", this.awsAccessKeyId);
		params.put("AssociateTag", this.awsAssociateTag);
		params.put("Timestamp", createTimestamp());

		// The parameters need to be processed in lexicographical order, so we'll
		// use a TreeMap implementation for that.
		final SortedMap<String, String> sortedParamMap = new TreeMap<String, String>(params);

		// get the canonical form the query string
		final String canonicalQS = canonicalize(sortedParamMap);

		// create the string upon which the signature is calculated
		final String toSign = REQUEST_METHOD + "\n" + endpoint + "\n" + REQUEST_URI + "\n" + canonicalQS;

		// get the signature
		final String sig = percentEncodeRfc3986(this.hmac(toSign));

		// construct the URL
		return "http://" + endpoint + REQUEST_URI + "?" + canonicalQS + "&Signature=" + sig;
	}

	private void init() {
		try {
			final byte[] secretyKeyBytes = this.awsSecretKey.getBytes(UTF8_CHARSET);
			final SecretKeySpec secretKeySpec = new SecretKeySpec(secretyKeyBytes, HMAC_SHA256_ALGORITHM);
			this.mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
			this.mac.init(secretKeySpec);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Compute the HMAC.
	 * 
	 * @param stringToSign  String to compute the HMAC over.
	 * @return              base64-encoded hmac value.
	 */
	private String hmac(final String stringToSign) {
		try {
			final byte[] data = stringToSign.getBytes(UTF8_CHARSET);
			final byte[] rawHmac = this.mac.doFinal(data);
			final Base64 encoder = new Base64(0);
			return new String(encoder.encode(rawHmac));
		} catch (final UnsupportedEncodingException e) {
			throw new RuntimeException(UTF8_CHARSET + " is unsupported!", e);
		}
	}

	/**
	 * @param awsAccessKeyId the awsAccessKeyId to set
	 */
	public void setAwsAccessKeyId(final String awsAccessKeyId) {
		this.awsAccessKeyId = awsAccessKeyId;
	}

	/**
	 * @param awsSecretKey the awsSecretKey to set
	 */
	public void setAwsSecretKey(final String awsSecretKey) {
		this.awsSecretKey = awsSecretKey;
	}

	/**
	 * @param awsAssociateTag the awsAssociateTag to set
	 */
	public void setAwsAssociateTag(final String awsAssociateTag) {
		this.awsAssociateTag = awsAssociateTag;
	}
}
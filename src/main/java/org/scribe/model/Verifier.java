package org.scribe.model;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;

import org.scribe.exceptions.OAuthException;

/**
 * Represents an OAuth verifier code.
 * 
 * @author Pablo Fernandez
 */
public class Verifier
{

	private static final String VERIFIER_REGEX = "(.*)?oauth_verifier=(\\S*)(&(.*))?";

	private final String value;

	/**
	 * Default constructor.
	 * 
	 * @param value verifier value
	 */
	public Verifier(String value)
	{
		this.value = value;
	}

	public String getValue()
	{
		return value;
	}

	/**
	 * @param url Valid url with a verifier
	 * @return Verifier
	 * 
	 * @throws OAuthException if url is invalid or does not contain a verifier
	 */
	public static Verifier createFromUrl(String url)
	{
		try
		{
			String query = new URL(url).getQuery();
			if (query != null) {
			  String[] params = query.split("&");
			  for (String param : params) {
			  	String[] keyValuePair = param.split("=", 2);
					try
					{
				  	if (URLDecoder.decode(keyValuePair[0], "UTF-8").equals("oauth_verifier") && keyValuePair.length == 2) {
				  		return new Verifier(URLDecoder.decode(keyValuePair[1], "UTF-8"));
				  	}
					} catch (UnsupportedEncodingException e)
					{
						// ignore this parameter if it can't be decoded
					}
			  }
			}
			// no match found
			throw new OAuthException("Response body is incorrect. Can't extract a verifier from this: '" + url + "'", null);
		} catch (MalformedURLException e)
		{
			throw new OAuthException("Response body is incorrect. Can't extract a verifier from this: '" + url + "'", e);
		}
	}

}

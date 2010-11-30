package org.scribe.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.scribe.exceptions.OAuthException;


public class VerifierTest
{
	@Test
	public void shouldParse() {
		String url = "http://www.example.com/oauth?oauth_verifier=verifier";
		Verifier verifier = Verifier.createFromUrl(url);
		assertEquals(verifier.getValue(), "verifier");
		
		url = "http://www.example.com/oauth?oauth_verifier=verifier&oauth_token=token";
		verifier = Verifier.createFromUrl(url);
		assertEquals(verifier.getValue(), "verifier");
		
		url = "http://www.example.com/oauth?oauth_token=token&oauth_verifier=verifier";
		verifier = Verifier.createFromUrl(url);
		assertEquals(verifier.getValue(), "verifier");
		
		url = "http://www.example.com/oauth?oauth_verifier=p8k%2BGIjIL9PblXq%2BpH6LmT9l";
		verifier = Verifier.createFromUrl(url);
		assertEquals(verifier.getValue(), "p8k+GIjIL9PblXq+pH6LmT9l");
	}
	
	@Test(expected = OAuthException.class)
	public void shouldNotParseInvalidUrl() {
		String url = "oauth_verifier=verifier";
		Verifier.createFromUrl(url);
	}
	
	@Test(expected = OAuthException.class)
	public void shouldNotParseMissingVerifier() {
		String url = "http://www.example.com/oauth?oauth_token=token";
		Verifier.createFromUrl(url);
	}
}

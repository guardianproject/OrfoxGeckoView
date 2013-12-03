package info.guardianproject.browser;

import java.util.regex.Pattern;

public interface OrwebConstants {

	public final static String DEFAULT_SEARCH_ENGINE = "http://3g2upl4pq6kufc4m.onion/?q=";
	public final static String DEFAULT_SEARCH_ENGINE_NOJS = "https://duckduckgo.com/html?q=";
	
	public final static String DEFAULT_HEADER_ACCEPT = "text/html, */* ISO-8859-1,utf-8;q=0.7,*;q=0.7 gzip,deflate en-us,en;q=0.5";



	static final Pattern ACCEPTED_URI_SCHEMA = Pattern.compile("(?i)"
			+ // switch on case insensitive matching
			"("
			+ // begin group for schema
			"(?:http|https):\\/\\/"
			+ "|(?:data|about|javascript):" + ")" + "(.*)");
	
	public final static String DEFAULT_CHARSET = "utf-8";

}

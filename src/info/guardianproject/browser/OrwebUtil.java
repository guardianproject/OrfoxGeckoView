package info.guardianproject.browser;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrwebUtil implements OrwebConstants {


	public static String smartUrlFilter(String url, boolean doJavascript) {

		String inUrl = url.trim();
		boolean hasSpace = inUrl.indexOf(' ') != -1;

		Matcher matcher = ACCEPTED_URI_SCHEMA.matcher(inUrl);
		if (matcher.matches()) {
			if (hasSpace) {
				inUrl = inUrl.replace(" ", "%20");
			}
			// force scheme to lowercase
			String scheme = matcher.group(1);
			String lcScheme = scheme.toLowerCase();
			if (!lcScheme.equals(scheme)) {
				return lcScheme + matcher.group(2);
			}
			return inUrl;
		}
		else if (url.indexOf(' ') != -1 || url.indexOf('.') == -1)
		{
			try
			{
				if (doJavascript)
					url = DEFAULT_SEARCH_ENGINE + URLEncoder.encode(url, DEFAULT_CHARSET);
				else
					url = DEFAULT_SEARCH_ENGINE_NOJS + URLEncoder.encode(url, DEFAULT_CHARSET);
			}
			catch (UnsupportedEncodingException ue)
			{
				if (doJavascript)
					url = DEFAULT_SEARCH_ENGINE + url.replace(' ','+');
				else
					url = DEFAULT_SEARCH_ENGINE_NOJS + url.replace(' ','+');
				
			}
			
			return url;
		}
		else
			return "http://" + url;
	}

}

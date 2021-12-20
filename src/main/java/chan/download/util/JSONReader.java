package chan.download.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONReader {

	private static String readAll(Reader reader) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		
		while ((cp = reader.read()) != -1) {
			sb.append((char) cp);
		}
		
		return sb.toString();
	}

	public static JSONArray readJsonArrayFromUrl(String url) throws IOException {
		return new JSONArray(readJsonTextFromUrl(url));
	}
	
	public static JSONObject readJsonObjectFromUrl(String url) throws IOException {
		return new JSONObject(readJsonTextFromUrl(url));
	}

	private static String readJsonTextFromUrl(String url) throws IOException, MalformedURLException {
		InputStream is = new URL(url).openStream();
		String jsonText = "";

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			jsonText = readAll(br);
		} finally {
			is.close();
		}

		return jsonText;
	}

}
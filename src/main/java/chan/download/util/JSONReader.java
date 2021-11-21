package chan.download.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONArray;

public class JSONReader {

	private static String readAll(Reader reader) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		
		while ((cp = reader.read()) != -1) {
			sb.append((char) cp);
		}
		
		return sb.toString();
	}

	public static JSONArray readJsonFromUrl(String url) throws IOException {
		InputStream is = new URL(url).openStream();
		JSONArray json = new JSONArray();

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(br);
			json = new JSONArray(jsonText);
		} finally {
			is.close();
		}

		return json;
	}

}
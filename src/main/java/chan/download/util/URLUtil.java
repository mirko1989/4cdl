package chan.download.util;

import java.util.Arrays;
import java.util.List;

public class URLUtil {
	
	public static String makeCatalogURL(String board) {
		return String.format("https://a.4cdn.org/%s/catalog.json", trim(board));
	}
	
	private static String trim(String board) {
		return board.replace("/", "");
	}
	
	public static String makeFileURL(String board, String fileName) {
		return String.format("https://i.4cdn.org/%s/%s", trim(board), fileName);
	}
	
	public static String makeThreadURL(String board, int threadNumber) {
		return "https://a.4cdn.org/" + trim(board) + "/thread/" + threadNumber + ".json";
	}
	
	public static String getFileNameFromURL(String url) {
		List<String> urlParts = Arrays.asList(url.split("/"));
		String fileName = urlParts.get(urlParts.size() - 1);
		
		return fileName;
	}
}

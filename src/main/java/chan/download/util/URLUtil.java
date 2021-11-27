package chan.download.util;

public class URLUtil {
	
	public static String makeCatalogUrl(String board) {
		return String.format("https://a.4cdn.org/%s/catalog.json", trim(board));
	}
	
	private static String trim(String board) {
		return board.replace("/", "");
	}
	
	public static String makeFileURL(String board, String fileName) {
		return String.format("https://i.4cdn.org/%s/%s", trim(board), fileName);
	}
	
}
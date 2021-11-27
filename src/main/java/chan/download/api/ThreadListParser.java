package chan.download.api;

import org.json.JSONArray;

import chan.download.util.JSONReader;
import chan.download.util.URLUtil;

public class ThreadListParser {
	
	public static ThreadList parse(String board) {
		ThreadList threadList = new ThreadList(board);
		
		try {
			JSONArray pages = JSONReader.readJsonFromUrl(URLUtil.makeCatalogUrl(board));
			for(int i = 0; i < pages.length(); i++) {
				JSONArray threads = pages.getJSONObject(i).getJSONArray("threads");
				for(int j = 0; j < threads.length(); j++) {
					Thread thread = ThreadParser.parse(threads.getJSONObject(j));
					threadList.add(thread);
				}
			}
		} catch (Throwable e) {
			System.err.println("Couldn't parse catalog json");
		}
		
		return threadList;
	}
	
}
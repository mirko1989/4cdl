package chan.download.api;

import java.util.List;

import org.json.JSONArray;

import chan.download.util.JSONReader;

public class CatalogContent {
	
	private String board;

	public CatalogContent(String board) {
		this.board = board;
	}
	
	public String getFileBaseUrl() {
		return String.format("https://i.4cdn.org/%s", board.replace("/", ""));
	}
	
	public ThreadList getThreadsFilterBy(List<Filter> filters) {
		ThreadList threadList = new ThreadList();
		threadList.addFilters(filters);
		
		try {
			JSONArray pages = JSONReader.readJsonFromUrl(makeContentUrl());
			for(int i = 0; i < pages.length(); i++) {
				JSONArray threads = pages.getJSONObject(i).getJSONArray("threads");
				for(int j = 0; j < threads.length(); j++) {
					Thread thread = Thread.fromJSON(threads.getJSONObject(j));
					threadList.add(thread);
				}
			}
		} catch (Throwable e) {
			System.err.println("Couldn't parse catalog json");
		}
		
		return threadList;
	}
	
	private String makeContentUrl() {
		return String.format("https://a.4cdn.org/%s/catalog.json", board);
	}
	
}
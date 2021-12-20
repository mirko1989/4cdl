package chan.download.api;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import chan.download.util.JSONReader;
import chan.download.util.URLUtil;

public class ThreadList {

	private List<Thread> threads;
	private List<Filter> filters;
	private String board;
	
	public static ThreadList fromBoard(String board) {
		ThreadList threadList = new ThreadList(board);
		
		try {
			JSONArray pages = JSONReader.readJsonArrayFromUrl(URLUtil.makeCatalogURL(board));
			for(int i = 0; i < pages.length(); i++) {
				JSONArray threads = pages.getJSONObject(i).getJSONArray("threads");
				for(int j = 0; j < threads.length(); j++) {
					ThreadParser.board = board;
					Thread thread = ThreadParser.parse(threads.getJSONObject(j));
					threadList.add(thread);
				}
			}
		} catch (Throwable e) {
			System.err.println("Couldn't parse catalog json");
		}
		
		return threadList;
	}

	public ThreadList(String board) {
		this.board = board;
		threads = new ArrayList<Thread>();
		filters = new ArrayList<Filter>();
	}

	public void add(Thread t) {
		threads.add(t);
	}

	public Thread get(int idx) {
		return getFilteredThreads().get(idx);
	}
	
	public int size() {
		return getFilteredThreads().size();
	}

	private List<Thread> getFilteredThreads() {
		if(isFilterEmpty()) {
			return threads;
		}
		
		List<Thread> filteredThreads = new ArrayList<Thread>();
		for(Thread thread : threads) {
			for(Filter filter : filters) {
				if(match(thread, filter)) {
					filteredThreads.add(thread);
				}
			}
		}
		
		return filteredThreads;
	}

	private boolean match(Thread thread, Filter filter) {
		String field = getField(thread, filter);
		String value = filter.getValue().toLowerCase();
		boolean isMatch;
		
		switch(filter.getOperator()) {
			case CONTAINS:
				isMatch = field.contains(value);
				break;
			default:
				isMatch = false;
		}
		
		return isMatch;
	}

	private String getField(Thread thread, Filter filter) {
		String field;
		
		switch(filter.getField()) {
			case ThreadName:
				field = thread.getName().toLowerCase();
				break;
			default:
				field = "";
		}
		
		return field;
	}

	private boolean isFilterEmpty() {
		return filters.size() == 0;
	}

	public void addFilters(List<Filter> filters) {
		this.filters = filters;
	}

	public List<String> getURLs() {
		List<String>urls = new ArrayList<String>();
		
		for(int i = 0; i < this.size(); i++) {
			for(String fileName : this.get(i).getFiles()) {
				urls.add(URLUtil.makeFileURL(board, fileName));
			}
		}
		
		return urls;
	}
	
}

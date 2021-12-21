package chan.download.api;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import chan.download.util.JSONReader;
import chan.download.util.URLUtil;

public class ThreadList {

	private List<Thread> threads;
	private List<Filter> filters;
	
	public static ThreadList fromBoard(String board) {
		ThreadList threadList = new ThreadList();
		
		try {
			String catalogUrl = URLUtil.makeCatalogURL(board);
			JSONArray pages = JSONReader.readJsonArrayFromUrl(catalogUrl);
			
			for(int i = 0; i < pages.length(); i++) {
				JSONObject page = pages.getJSONObject(i);
				JSONArray threads = getThreadsFromPage(page);
				for(int j = 0; j < threads.length(); j++) {
					Thread thread = Thread.fromJson(threads.getJSONObject(j));
					thread.setBoard(board);
					threadList.add(thread);
				}
			}
		} catch (Throwable e) {
			System.err.println("Couldn't parse catalog json");
		}
		
		return threadList;
	}

	private static JSONArray getThreadsFromPage(JSONObject page) {
		return page.getJSONArray("threads");
	}

	public ThreadList() {
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
		List<String> urls = new ArrayList<String>();
		
		for(int i = 0; i < this.size(); i++) {
			Thread thread = this.get(i); 
			logThread(thread);
			for(String fileName : thread.getFiles()) {
				String url = URLUtil.makeFileURL(thread.getBoard(), fileName);
				urls.add(url);
			}
		}
		
		return urls;
	}

	private void logThread(Thread thread) {
		System.out.println(String.format("%s --> %s", thread.getBoard(), thread.getName()));		
	}
	
}

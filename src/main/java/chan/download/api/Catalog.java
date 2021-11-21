package chan.download.api;

import java.util.ArrayList;
import java.util.List;

public class Catalog {

	private List<Filter> filters;
	private CatalogContent content;
	private ArrayList<String> urls;

	public Catalog(String board) {
		content = new CatalogContent(board);
		filters = new ArrayList<Filter>();
	}

	public void addFilter(Filter filter) {
		filters.add(filter);
	}
	
	public List<String> getURLs() {
		ThreadList threads = content.getThreadsFilterBy(filters);
		urls = new ArrayList<String>();
		
		for(int i = 0; i < threads.size(); i++) {
			addThreadFileURLs(threads.get(i));
		}
		
		return urls;
	}

	private void addThreadFileURLs(Thread thread) {
		List<String> files = thread.getFiles();
		
		for(String fileName : files) {
			urls.add(makeFileURL(fileName));
		}
	}

	private String makeFileURL(String fileName) {
		String baseUrl = content.getFileBaseUrl();
		
		return String.format("%s/%s", baseUrl, fileName);
	}
	
}

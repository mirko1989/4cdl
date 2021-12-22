package chan.download.api;

import java.util.ArrayList;
import java.util.List;

public class Catalog {

	private List<Filter> filters;
	private String board;

	public Catalog(String board) {
		this.board = board;
		filters = new ArrayList<Filter>();
	}

	public void addFilter(Filter filter) {
		filters.add(filter);
	}
	
	public List<String> getURLs() {
		ThreadList threadList = makeThreadList();
		
		return threadList.getURLs();
	}

	private ThreadList makeThreadList() {
		ThreadList threadList = ThreadList.fromBoard(board);
		threadList.addFilters(filters);
		
		return threadList;
	}

	public List<Thread> getThreads() {
		ThreadList threadList = makeThreadList();
		List<Thread> threads = new ArrayList<Thread>();
		
		for(int i = 0; i < threadList.size(); i++) {
			threads.add(threadList.get(i));
		}
		
		return threads;
	}

}

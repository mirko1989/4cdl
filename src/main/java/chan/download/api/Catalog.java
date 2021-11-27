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
		ThreadList threadList = ThreadListParser.parse(board);
		threadList.addFilters(filters);
		
		return threadList.getURLs();
	}

}

package chan.download.api;

import java.util.ArrayList;
import java.util.List;

public class ThreadList {

	private List<Thread> threads;
	private List<Filter> filters;

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
				if(filter.getField() == Field.ThreadName) {
					if(filter.getOperator() == FilterOperator.CONTAINS) {
						if(thread.getName().toLowerCase().contains(filter.getValue().toLowerCase())) {
							filteredThreads.add(thread);
						}
					}
				}
			}
		}
		
		return filteredThreads;
	}

	private boolean isFilterEmpty() {
		return filters.size() == 0;
	}

	public void addFilters(List<Filter> filters) {
		this.filters = filters;
	}
	
}

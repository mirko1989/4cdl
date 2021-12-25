package chan.download.crawler;

import java.util.List;

import chan.download.api.Catalog;
import chan.download.api.Thread;

public class ThreadNameCrawler implements Crawler {

	public void run(List<Catalog> catalogs) {
		for(Catalog catalog : catalogs) {
			for(Thread thread : catalog.getThreads()) {
				printThread(thread);
			}
		}
	}
	
	private void printThread(Thread thread) {
		System.out.println(String.format("%s --> %s", thread.getBoard(), thread.getName()));
	}

}

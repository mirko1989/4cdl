package chan.download.crawler;

import java.util.List;

import chan.download.api.Catalog;
import chan.download.api.Thread;
import chan.download.storage.Repository;
import chan.download.storage.SaveException;

public class ThreadNameCrawler implements Crawler {

	private Repository repository;

	public ThreadNameCrawler(Repository repo) {
		this.repository = repo;
	}
	
	public void run(List<Catalog> catalogs) {
		for(Catalog catalog : catalogs) {
			for(Thread thread : catalog.getThreads()) {
				saveName(thread);
			}
		}
	}
	
	private void saveName(Thread thread) {
		try {
			repository.save(String.format("%s --> %s", thread.getBoard(), thread.getName()));
		} catch (SaveException e) {
			//no handler
		}
	}

}

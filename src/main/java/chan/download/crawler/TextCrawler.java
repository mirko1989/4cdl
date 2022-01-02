package chan.download.crawler;

import java.util.List;

import chan.download.api.Catalog;
import chan.download.api.Thread;
import chan.download.storage.Repository;
import chan.download.storage.SaveException;

public class TextCrawler implements Crawler {

	private Repository repository;

	public TextCrawler(Repository repo) {
		this.repository = repo;
	}

	public void run(List<Catalog> catalogs) {
		for(Catalog catalog : catalogs) {
			for(Thread thread : catalog.getThreads()) {
				try {
					saveComments(thread);
				} catch(SaveException e) {
					//no handler
				}
			}
		}
	}

	private void saveComments(Thread thread) throws SaveException {
		System.out.println(String.format("%s --> No: %s", thread.getBoard(), thread.getNumber()));
		for(String comment : thread.getComments()) {
			repository.save(String.format("    %s", comment));
		}
	}

}

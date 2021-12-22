package chan.download.crawler;

import java.util.ArrayList;
import java.util.List;

import chan.download.api.Catalog;
import chan.download.api.Thread;
import chan.download.storage.DummyRepository;
import chan.download.storage.Repository;
import chan.download.storage.SaveException;

public class WebCrawler {

	private Repository repository;
	private List<Catalog> catalogs;
	private RunMode mode;

	public WebCrawler() {
		repository = new DummyRepository();
		catalogs = new ArrayList<Catalog>();
	}
	
	public void useRepository(Repository repository) {
		this.repository = repository;
	}
	
	public void useMode(RunMode mode) {
		this.mode = mode;
	}
	
	public void addCatalog(Catalog catalog) {
		catalogs.add(catalog);
	}
	
	private void download() {
		for(Catalog catalog : catalogs) {
			downloadCatalog(catalog);
		}
	}

	private void downloadCatalog(Catalog catalog) {
		for(String url : catalog.getURLs()) {
			try {
				repository.save(url);
				System.out.println(url);
			} catch (SaveException e) {
				System.err.println(url);
			}
		}
	}

	private void printThreads() {
		for(Catalog catalog : catalogs) {
			for(Thread thread : catalog.getThreads()) {
				System.out.println(String.format("%s --> %s", thread.getBoard(), thread.getName()));
			}
		}
	}

	public void run() {
		switch(mode) {
			case DOWNLOAD:
				printThreads();
				download();
				break;
			case PRINT:
				printThreads();
				break;
			default:
				//nothing to do
		}
	}
	
}

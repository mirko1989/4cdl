package chan.download.crawler;

import java.util.ArrayList;
import java.util.List;

import chan.download.api.Catalog;
import chan.download.storage.DummyRepository;
import chan.download.storage.Repository;
import chan.download.storage.SaveException;

public class WebCrawler {

	private Repository repository;
	private List<Catalog> catalogs;

	public WebCrawler() {
		repository = new DummyRepository();
		catalogs = new ArrayList<Catalog>();
	}
	
	public void useRepository(Repository repository) {
		this.repository = repository;
	}
	
	public void addCatalog(Catalog catalog) {
		catalogs.add(catalog);
	}
	
	public void download() {
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

	public void listThreads() {
		for(Catalog catalog : catalogs) {
			catalog.getURLs();
		}
	}
	
}

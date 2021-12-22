package chan.download.crawler;

import java.util.List;

import chan.download.api.Catalog;
import chan.download.storage.Repository;
import chan.download.storage.SaveException;

public class ImageCrawler implements CrawlerMode {

	private Repository repository;

	public ImageCrawler(Repository repo) {
		this.repository = repo;
	}

	public void run(List<Catalog> catalogs) {
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
	
}

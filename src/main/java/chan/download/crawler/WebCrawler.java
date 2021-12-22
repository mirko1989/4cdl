package chan.download.crawler;

import java.util.ArrayList;
import java.util.List;

import chan.download.api.Catalog;

public class WebCrawler {

	private List<Catalog> catalogs;
	private CrawlerMode mode;

	public WebCrawler(CrawlerMode mode) {
		this.mode = mode;
		catalogs = new ArrayList<Catalog>();
	}

	public void addCatalog(Catalog catalog) {
		catalogs.add(catalog);
	}
	
	public void run() {
		mode.run(catalogs);
	};
	
}

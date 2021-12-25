package chan.download.crawler;

import java.util.ArrayList;
import java.util.List;

import chan.download.api.Catalog;
import chan.download.api.Field;
import chan.download.api.Filter;
import chan.download.api.FilterOperator;
import chan.download.main.ArgumentMarshaller;

public class CrawlerBuilder {
	
	private ArgumentMarshaller marshaller;
	private Crawler mode;
	private List<Catalog> catalogs;

	public CrawlerBuilder(ArgumentMarshaller marshaller) {
		this.marshaller = marshaller;
		this.mode = CrawlerFactory.create(marshaller);
		this.catalogs = createCatalogs(createFilter());
	}
	
	private Filter createFilter() {
		return new Filter(Field.ThreadName, FilterOperator.CONTAINS, marshaller.getQuery());
	}
	
	private List<Catalog> createCatalogs(Filter filter) {
		List<Catalog> catalogs = new ArrayList<Catalog>();
		
		for(String board : marshaller.getBoards()) {
			Catalog catalog = new Catalog(board);
			catalog.addFilter(filter);
			catalogs.add(catalog);
		}
		
		return catalogs;
	}
	
	public WebCrawler build() {
		WebCrawler crawler = new WebCrawler(mode);
		
		for(Catalog catalog : catalogs) {
			crawler.addCatalog(catalog);
		}
		
		return crawler;
	}
	
}

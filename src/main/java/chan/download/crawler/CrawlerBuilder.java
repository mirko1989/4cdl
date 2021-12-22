package chan.download.crawler;

import java.util.ArrayList;
import java.util.List;

import chan.download.api.Catalog;
import chan.download.api.Field;
import chan.download.api.Filter;
import chan.download.api.FilterOperator;
import chan.download.main.ArgumentMarshaller;
import chan.download.storage.FileRepository;
import chan.download.storage.Repository;

public class CrawlerBuilder {
	
	private ArgumentMarshaller marshaller;
	private Repository repo;
	private CrawlerMode mode;
	private List<Catalog> catalogs;

	public CrawlerBuilder(ArgumentMarshaller marshaller) {
		this.marshaller = marshaller;
		this.repo = createRepository();
		this.mode = getMode();
		this.catalogs = createCatalogs(createFilter());
	}
	
	private Repository createRepository() {
		return new FileRepository(marshaller.getDirectory());
	}
	
	private CrawlerMode getMode() {
		return marshaller.isNameOnly() ? new ThreadNameCrawler(): new ImageCrawler(repo);
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

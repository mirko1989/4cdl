package chan.download.crawler;

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
	private RunMode mode;
	private Filter filter;

	public CrawlerBuilder(ArgumentMarshaller marshaller) {
		this.marshaller = marshaller;
		this.repo = createRepository();
		this.mode = getMode();
		this.filter = createFilter();
	}
	
	private Repository createRepository() {
		return new FileRepository(marshaller.getDirectory());
	}
	
	private RunMode getMode() {
		return marshaller.isNameOnly() ? RunMode.PRINT : RunMode.DOWNLOAD;
	}
	
	private Filter createFilter() {
		return new Filter(Field.ThreadName, FilterOperator.CONTAINS, marshaller.getQuery());
	}
	
	public WebCrawler build() {
		WebCrawler crawler = new WebCrawler();
		crawler.useRepository(repo);
		crawler.useMode(mode);
		
		for(String board : marshaller.getBoards()) {
			Catalog catalog = new Catalog(board);
			catalog.addFilter(filter);
			crawler.addCatalog(catalog);
		}
		
		return crawler;
	}
	
}

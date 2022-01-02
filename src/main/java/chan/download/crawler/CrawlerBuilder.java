package chan.download.crawler;

import java.util.ArrayList;
import java.util.List;

import chan.download.api.Catalog;
import chan.download.api.Field;
import chan.download.api.Filter;
import chan.download.api.FilterOperator;
import chan.download.main.ArgumentMarshaller;
import chan.download.storage.Repository;
import chan.download.storage.RepositoryFactory;

public class CrawlerBuilder {
	
	private ArgumentMarshaller marshaller;

	public CrawlerBuilder(ArgumentMarshaller marshaller) {
		this.marshaller = marshaller;
	}
	
	private List<Catalog> createCatalogs() {
		String query = marshaller.getQuery();
		List<Filter> filters = new ArrayList<Filter>();
		if(!query.isEmpty()) {
			filters.add(new Filter(Field.ThreadName, FilterOperator.CONTAINS, query));
		}
		
		List<Catalog> catalogs = new ArrayList<Catalog>();
		for(String board : marshaller.getBoards()) {
			Catalog catalog = new Catalog(board);
			for(Filter filter : filters) {
				catalog.addFilter(filter);
			}
			catalogs.add(catalog);
		}
		
		return catalogs;
	}
	
	public WebCrawler build() {
		Repository repo = RepositoryFactory.create(marshaller.getDestination());
		Crawler mode = CrawlerFactory.create(marshaller.getCrawlerMode(), repo);
		WebCrawler crawler = new WebCrawler(mode);
		
		for(Catalog catalog : createCatalogs()) {
			crawler.addCatalog(catalog);
		}
		
		return crawler;
	}
	
}

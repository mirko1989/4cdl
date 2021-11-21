package chan.download.main;

import chan.download.api.Catalog;
import chan.download.api.Field;
import chan.download.api.Filter;
import chan.download.api.FilterOperator;
import chan.download.crawler.WebCrawler;
import chan.download.storage.FileRepository;

public class App {

	public static void main(String[] args) {
		if(args.length < 3) {
			System.out.println("Usage: java -jar 4cdl.jar directory board query");
			System.exit(-1);
		}
		
		String directory = args[0];
		String board = args[1];
		String query = args[2];
		
		WebCrawler crawler = new WebCrawler();
		crawler.useRepository(new FileRepository(directory));
		
		Catalog catalog = new Catalog(board);
		catalog.addFilter(new Filter(Field.ThreadName, FilterOperator.CONTAINS, query));
		crawler.addCatalog(catalog);
		
		crawler.download();
	}
	
}

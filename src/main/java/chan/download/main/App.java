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
			System.out.println("Usage: java -jar 4cdl.jar directory board[,board2[,board3]] query [--name-only]");
			System.exit(-1);
		}
		
		String directory = args[0];
		String[] boards = args[1].split(",");
		String query = args[2];
		
		boolean isNamesOnly = false;
		if(args.length == 4) {
			isNamesOnly = args[3].equals("--name-only");
		}
		
		WebCrawler crawler = new WebCrawler();
		crawler.useRepository(new FileRepository(directory));
		Filter filter = new Filter(Field.ThreadName, FilterOperator.CONTAINS, query);
		
		for(String board : boards) {
			Catalog catalog = new Catalog(board);
			catalog.addFilter(filter);
			crawler.addCatalog(catalog);
		}
		
		if(isNamesOnly) {
			crawler.printThreads();
		} else {
			crawler.download();
		}
	}
	
}

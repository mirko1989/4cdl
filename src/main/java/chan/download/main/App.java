package chan.download.main;

import chan.download.crawler.CrawlerBuilder;
import chan.download.crawler.WebCrawler;

public class App {

	public static void main(String[] args) {
		ArgumentMarshaller marshaller = ArgumentMarshaller.fromArgs(args);
		
		if(!marshaller.isValid()) {
			marshaller.printUsage();
			System.exit(-1);
		}
		
		WebCrawler crawler = new CrawlerBuilder(marshaller).build();
		crawler.run();
	}
	
}

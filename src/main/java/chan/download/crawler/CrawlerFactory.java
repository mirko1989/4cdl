package chan.download.crawler;

import chan.download.main.ArgumentMarshaller;
import chan.download.storage.FileRepository;

public class CrawlerFactory {

	public static Crawler create(ArgumentMarshaller marshaller) {
		Crawler crawler;
		
		switch(marshaller.getMode()) {
			case IMAGE:
				crawler = new ImageCrawler(new FileRepository(marshaller.getDirectory()));
				break;
			case TEXT:
				crawler = new TextCrawler();
				break;
			case NAME:
				crawler = new ThreadNameCrawler();
				break;
			default:
				crawler = new DummyCrawler();
		}
		
		return crawler;
	}

}

package chan.download.crawler;

import chan.download.main.ArgumentMarshaller;

public class CrawlerFactory {

	public static Crawler create(ArgumentMarshaller marshaller) {
		Crawler crawler;
		
		switch(marshaller.getCrawlerMode()) {
			case IMAGE:
				crawler = new ImageCrawler(marshaller.getRepository());
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

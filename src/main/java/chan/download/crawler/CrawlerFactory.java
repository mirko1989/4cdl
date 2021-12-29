package chan.download.crawler;

import chan.download.main.ArgumentMarshaller;
import chan.download.storage.Repository;
import chan.download.storage.RepositoryFactory;

public class CrawlerFactory {

	public static Crawler create(ArgumentMarshaller marshaller) {
		Crawler crawler;
		
		switch(marshaller.getCrawlerMode()) {
			case IMAGE:
				Repository repo = RepositoryFactory.create(marshaller);
				crawler = new ImageCrawler(repo);
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

package chan.download.crawler;

import chan.download.storage.Repository;

public class CrawlerFactory {

	public static Crawler create(CrawlerMode mode, Repository repo) {
		Crawler crawler;
		
		switch(mode) {
			case IMAGE:
				crawler = new ImageCrawler(repo);
				break;
			case TEXT:
				crawler = new TextCrawler(repo);
				break;
			case NAME:
				crawler = new ThreadNameCrawler(repo);
				break;
			default:
				crawler = new DummyCrawler();
		}
		
		return crawler;
	}

}

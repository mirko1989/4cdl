package chan.download.crawler;

import chan.download.main.ArgumentMarshaller;
import chan.download.storage.FileRepository;

public class CrawlerModeFactory {

	public static CrawlerMode create(ArgumentMarshaller marshaller) {
		CrawlerMode mode;
		
		if(marshaller.isNameOnly()) {
			mode = new ThreadNameCrawler();
		} else if(marshaller.isTextOnly()) {
			mode = new TextCrawler();
		} else {
			mode = new ImageCrawler(new FileRepository(marshaller.getDirectory()));
		}
		
		return mode;
	}

}

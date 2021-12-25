package chan.download.crawler;

import java.util.List;

import chan.download.api.Catalog;

public class DummyCrawler implements Crawler {

	public void run(List<Catalog> catalogs) {
		System.out.println("dummy crawler in use --> do nothing");
	}

}

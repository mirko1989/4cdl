package chan.download.crawler;

import java.util.List;

import chan.download.api.Catalog;

public interface Crawler {

	public void run(List<Catalog> catalogs);
	
}

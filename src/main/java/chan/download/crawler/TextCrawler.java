package chan.download.crawler;

import java.util.List;
import chan.download.api.Catalog;
import chan.download.api.Thread;

public class TextCrawler implements Crawler {

	public void run(List<Catalog> catalogs) {
		for(Catalog catalog : catalogs) {
			for(Thread thread : catalog.getThreads()) {
				printComments(thread);
			}
		}
	}

	private void printComments(Thread thread) {
		System.out.println(String.format("%s --> No: %s", thread.getBoard(), thread.getNumber()));
		for(String comment : thread.getComments()) {
			System.out.println(String.format("    %s", comment));
		}
	}

}

package chan.download.main;

import java.util.Arrays;
import java.util.List;

import chan.download.crawler.CrawlerMode;

public class ArgumentMarshaller {

	private List<String> args;
	
	private ArgumentMarshaller(String[] args) {
		this.args = Arrays.asList(args);
	}
	
	public static ArgumentMarshaller fromArgs(String[] args) {
		return new ArgumentMarshaller(args);
	}

	public boolean isValid() {
		boolean valid = true;
		
		try {
			getDirectory();
			getBoards();
			getQuery();
		} catch(Exception e) {
			valid = false;
		}
		
		return valid;
	}
	
	public String getDirectory() {
		return args.get(0);
	}
	
	public List<String> getBoards() {
		return Arrays.asList(args.get(1).split(","));
	}
	
	public String getQuery() {
		return args.get(2);
	}

	public CrawlerMode getMode() {
		CrawlerMode mode;
		
		if(isNameOnly()) {
			mode = CrawlerMode.NAME;
		} else if(isTextOnly()) {
			mode = CrawlerMode.TEXT;
		} else {
			mode = CrawlerMode.IMAGE;
		}
		
		return mode;
	}
	
	private boolean isNameOnly() {
		return isOptionSet("--name-only");
	}
	
	private boolean isOptionSet(String option) {
		for(String arg : args) {
			if(arg.equals(option)) {
				return true;
			}
		}
		
		return false;
	}
	
	private boolean isTextOnly() {
		return isOptionSet("--text-only");
	}
	
	public void printUsage() {
		System.out.println("Usage: java -jar 4cdl.jar directory board[,board2[,board3]] query [--name-only | --text-only]");
	}
	
}

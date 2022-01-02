package chan.download.main;

import java.util.Arrays;
import java.util.List;

import chan.download.crawler.CrawlerMode;
import chan.download.storage.Destination;
import chan.download.storage.RepositoryType;

public class ArgumentMarshaller {

	private List<String> args;
	
	private ArgumentMarshaller(String[] args) {
		this.args = Arrays.asList(args);
	}
	
	public static ArgumentMarshaller fromArgs(String[] args) {
		return new ArgumentMarshaller(args);
	}

	public boolean isValid() {
		return isOptionSet("--boards");
	}
	
	public List<String> getBoards() {
		return Arrays.asList(valueOfArg("--boards").split(","));
	}
	
	public String getQuery() {
		return valueOfArg("--search");
	}
	
	public CrawlerMode getCrawlerMode() {
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
		return indexOfArg(option) >= 0;
	}
	
	private int indexOfArg(String arg) {
		for(int idx = 0; idx < args.size(); idx++) {
			String key = args.get(idx).split("=")[0];
			if(key.equals(arg)) {
				return idx;
			}
		}
		
		return -1;
	}

	private boolean isTextOnly() {
		return isOptionSet("--text-only");
	}
	
	public void printUsage() {
		System.out.println("Usage: java -jar 4cdl.jar --boards=board[,board2[,...]] [--search=query] [--save-dir=directory] [--save-db=user:pass@db_host:port] [--name-only] [--text-only]");
	}
	
	/**
	 * If --name-only or --text-only argument is given, always print to STDOUT
	 */
	public Destination getDestination() {
		Destination dest;
		
		if(isNameOnly() || isTextOnly()) {
			dest = new Destination(RepositoryType.STDOUT, "");
		} else {
			dest = new Destination(getRepositoryType(), getPath()); 
		}
		
		return dest; 
	}

	private RepositoryType getRepositoryType() {
		RepositoryType repoType;
		
		if(isSaveFilesystem()) {
			repoType = RepositoryType.FILESYSTEM;
		} else if(isSaveDatabase()) {
			repoType = RepositoryType.DATABASE;
		} else {
			repoType = RepositoryType.STDOUT;
		}
		
		return repoType;
	}

	private String getPath() {
		String dest;
		
		if(isSaveFilesystem()) {
			dest = getDirectory();
		} else if(isSaveDatabase()) {
			dest = getDBHost();
		} else {
			dest = "";
		}
		
		return dest;
	}
	
	private String getDBHost() {
		return valueOfArg("--save-db");
	}

	private String getDirectory() {
		return valueOfArg("--save-dir");
	}	
	
	private String valueOfArg(String arg) {
		int idx = indexOfArg(arg);
		
		return idx >= 0 ? args.get(idx).split("=")[1] : "";
	}

	private boolean isSaveDatabase() {
		return isOptionSet("--save-db");
	}

	private boolean isSaveFilesystem() {
		return isOptionSet("--save-dir");
	}
	
}

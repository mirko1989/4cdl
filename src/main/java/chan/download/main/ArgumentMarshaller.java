package chan.download.main;

import java.util.Arrays;
import java.util.List;

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
	
	public String[] getBoards() {
		return args.get(1).split(",");
	}
	
	public String getQuery() {
		return args.get(2);
	}
	
	public boolean isNameOnly() {
		boolean printOnly;
		
		try {
			printOnly = args.get(3).equals("--name-only"); 
		} catch(Exception e) {
			printOnly = false;
		}
		
		return printOnly;
	}

	public void printUsage() {
		System.out.println("Usage: java -jar 4cdl.jar directory board[,board2[,board3]] query [--name-only]");
	}
	
}

package chan.download.api;

import java.util.ArrayList;
import java.util.List;

public class Thread {
	
	private String name;
	private List<String> files;

	public Thread(String name) {
		this.name = name;
		files = new ArrayList<String>();
	}
	
	public String getName() {
		return name;
	}
	
	public void addFile(String fileName) {
		files.add(fileName);
	}
	
	public List<String> getFiles() {
		return files;
	}
	
}

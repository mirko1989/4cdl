package chan.download.api;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
	
	private void addFile(String fileName) {
		files.add(fileName);
	}
	
	public List<String> getFiles() {
		return files;
	}
	
	public static Thread fromJSON(JSONObject jsonObject) {
		Thread thread = new Thread(getThreadName(jsonObject));
		
		for(String fileName : getFiles(jsonObject)) {
			thread.addFile(fileName);
		}
		
		return thread;
	}

	private static String getThreadName(JSONObject jsonObject) {
		String value;
		
		try {
			value = jsonObject.getString("com");
		} catch(JSONException e) {
			value = "";
		}
		
		return value;
	}
	
	private static List<String> getFiles(JSONObject jsonObject) {
		List<String> fileNames = new ArrayList<String>();
		
		try {
			fileNames.add(getFileName(jsonObject));	//thread top entry
			JSONArray replies = jsonObject.getJSONArray("last_replies");
			for(int i = 0; i < replies.length(); i++) {
				fileNames.add(getFileName(replies.getJSONObject(i)));
			}
		} catch(JSONException e) {
			//no file
		}
		
		return fileNames;
	}
	
	private static String getFileName(JSONObject jsonObject) throws JSONException {
		long fileName = jsonObject.getLong("tim");
		String fileExtension = jsonObject.getString("ext");
		
		return String.format("%s%s", fileName, fileExtension); 
	}
	
}

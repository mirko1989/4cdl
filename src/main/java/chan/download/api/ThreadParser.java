package chan.download.api;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ThreadParser {
	
	public static Thread parse(JSONObject jsonObject) {
		Thread thread = new Thread(getThreadName(jsonObject));
		
		for(String fileName : parseFileNames(jsonObject)) {
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
	
	private static List<String> parseFileNames(JSONObject jsonObject) {
		List<String> fileNames = new ArrayList<String>();
		
		try {
			try {
				fileNames.add(getFileName(jsonObject));
			} catch(JSONException e) {
				//no file in thread top entry
			}
			
			JSONArray replies = jsonObject.getJSONArray("last_replies");
			for(int i = 0; i < replies.length(); i++) {
				try {
					fileNames.add(getFileName(replies.getJSONObject(i)));
				} catch(JSONException e) {
					//no file in reply
				}
			}
		} catch(JSONException e) {
			//no replies
		}
		
		return fileNames;
	}
	
	private static String getFileName(JSONObject jsonObject) throws JSONException {
		long fileName = jsonObject.getLong("tim");
		String fileExtension = jsonObject.getString("ext");
		
		return String.format("%s%s", fileName, fileExtension); 
	}
	
}

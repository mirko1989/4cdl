package chan.download.api;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import chan.download.util.JSONReader;
import chan.download.util.URLUtil;

public class ThreadParser {
	
	public static String board;
	
	public static Thread parse(JSONObject threadJson) {
		Thread thread = new Thread(getName(threadJson));
		
		for(String fileName : parseFileNames(threadJson)) {
			thread.addFile(fileName);
		}
		
		return thread;
	}
	
	private static String getName(JSONObject threadJson) {
		String name;
		
		try {
			name = threadJson.getString("com");
		} catch(Exception e) {
			name = "";
		}
		
		return name;
	}

	private static List<String> parseFileNames(JSONObject threadJson) {
		List<String> fileNames = new ArrayList<String>();
		
		try {			
			JSONArray posts = getPosts(threadJson);
			for(int i = 0; i < posts.length(); i++) {
				try {
					fileNames.add(getFileName(posts.getJSONObject(i)));
				} catch(JSONException e) {
					//no file in reply
				}
			}
		} catch(JSONException e) {
			//no posts
		}
		
		return fileNames;
	}

	private static JSONArray getPosts(JSONObject threadJson) throws JSONException {
		JSONArray posts;
		
		try {
			String threadUrl = makeThreadUrl(threadJson);
			posts = JSONReader.readJsonObjectFromUrl(threadUrl).getJSONArray("posts");
		} catch(Exception e) {
			 throw new JSONException(e);
		}
		
		return posts;
	}

	private static String makeThreadUrl(JSONObject threadJson) {
		int threadNumber = getNumber(threadJson);
		
		return URLUtil.makeThreadURL(board, threadNumber);
	}
	
	private static int getNumber(JSONObject threadJson) {
		int number;
		
		try {
			number = threadJson.getInt("no");
		} catch(Exception e) {
			number = 0;
		}
		
		return number;
	}
	
	private static String getFileName(JSONObject threadJson) throws JSONException {
		long fileName = threadJson.getLong("tim");
		String fileExtension = threadJson.getString("ext");
		
		return String.format("%s%s", fileName, fileExtension); 
	}
	
}

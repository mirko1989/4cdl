package chan.download.api;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import chan.download.util.JSONReader;
import chan.download.util.URLUtil;

public class Thread {
	
	private String board;
	private JSONObject json;
	private List<String> files;

	public static Thread fromJson(JSONObject threadJson) {
		return new Thread(threadJson);
	}
	
	private Thread(JSONObject threadJson) {
		files = new ArrayList<String>();
		this.json = threadJson;
	}
	
	public void setBoard(String board) {
		this.board = board;	
	}
	
	public String getName() {
		String name;
		
		try {
			name = json.getString("com");
		} catch(Exception e) {
			name = "";
		}
		
		return name;
	}
	
	public List<String> getFiles() {
		for(String fileName : parseFileNames(json)) {
			files.add(fileName);
		}
		
		return files;
	}
	
	private List<String> parseFileNames(JSONObject threadJson) {
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

	private JSONArray getPosts(JSONObject threadJson) throws JSONException {
		JSONArray posts;
		
		try {
			String threadUrl = makeThreadUrl(threadJson);
			posts = JSONReader.readJsonObjectFromUrl(threadUrl).getJSONArray("posts");
		} catch(Exception e) {
			 throw new JSONException(e);
		}
		
		return posts;
	}

	private String makeThreadUrl(JSONObject threadJson) {
		int threadNumber = getNumber(threadJson);
		
		return URLUtil.makeThreadURL(board, threadNumber);
	}
	
	private int getNumber(JSONObject threadJson) {
		int number;
		
		try {
			number = threadJson.getInt("no");
		} catch(Exception e) {
			number = 0;
		}
		
		return number;
	}
	
	private String getFileName(JSONObject threadJson) throws JSONException {
		long fileName = threadJson.getLong("tim");
		String fileExtension = threadJson.getString("ext");
		
		return String.format("%s%s", fileName, fileExtension); 
	}

}

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

	public static Thread fromJson(JSONObject threadJson) {
		return new Thread(threadJson);
	}
	
	private Thread(JSONObject threadJson) {
		this.json = threadJson;
	}
	
	public void setBoard(String board) {
		this.board = board;	
	}
	
	public String getBoard() {
		return board;
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
		List<String> files = new ArrayList<String>();
		
		for(String fileName : getFileNames()) {
			files.add(fileName);
		}
		
		return files;
	}
	
	private List<String> getFileNames() {
		List<String> fileNames = new ArrayList<String>();
		
		try {			
			JSONArray posts = getPosts();
			for(int i = 0; i < posts.length(); i++) {
				try {
					JSONObject post = posts.getJSONObject(i);
					String file = getFileName(post);
					fileNames.add(file);
				} catch(JSONException e) {
					//no file in reply
				}
			}
		} catch(JSONException e) {
			//no posts
		}
		
		return fileNames;
	}

	private JSONArray getPosts() throws JSONException {
		JSONArray posts;
		
		try {
			String threadUrl = makeThreadUrl();
			JSONObject thread = JSONReader.readJsonObjectFromUrl(threadUrl);
			posts = thread.getJSONArray("posts");
		} catch(Exception e) {
			 throw new JSONException(e);
		}
		
		return posts;
	}

	private String makeThreadUrl() {
		int threadNumber = getNumber();
		
		return URLUtil.makeThreadURL(board, threadNumber);
	}
	
	private int getNumber() {
		int number;
		
		try {
			number = json.getInt("no");
		} catch(Exception e) {
			number = 0;
		}
		
		return number;
	}
	
	private String getFileName(JSONObject post) throws JSONException {
		long name = post.getLong("tim");
		String ext = post.getString("ext");
		
		return String.format("%s%s", name, ext); 
	}

}

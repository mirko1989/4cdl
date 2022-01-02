package chan.download.storage;

public class Destination {
	
	private RepositoryType type;
	private String path;

	public Destination(RepositoryType type, String path) {
		this.type = type;
		this.path = path;
	}

	public RepositoryType getType() {
		return type;
	}
	
	public String getPath() {
		return path;
	}
	
}

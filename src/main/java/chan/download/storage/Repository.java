package chan.download.storage;

public interface Repository {

	public void save(String url) throws SaveException;
	
}

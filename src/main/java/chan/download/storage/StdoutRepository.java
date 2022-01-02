package chan.download.storage;

public class StdoutRepository implements Repository {

	public void save(String str) throws SaveException {
		System.out.println(str);
	}

}

package chan.download.storage;

public class RepositoryFactory {

	public static Repository create(Destination dest) {
		String path = dest.getPath();
		Repository repo;
		
		switch(dest.getType()) {
			case FILESYSTEM:
				repo = new FileRepository(path);
				break;
			case DATABASE:
				repo = new DatabaseRepository(path);
				break;
			case STDOUT:
				repo = new StdoutRepository();
				break;
			default:
				repo = new DummyRepository();
		}
		
		return repo;
	}
	
}

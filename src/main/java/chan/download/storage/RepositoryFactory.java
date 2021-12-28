package chan.download.storage;

public class RepositoryFactory {

	public static Repository create(RepositoryType repoType, String destination) {
		Repository repo;
		
		switch(repoType) {
			case FILESYSTEM:
				repo = new FileRepository(destination);
				break;
			case DATABASE:
				repo = new DatabaseRepository(destination);
				break;
			case STDOUT:
				repo = new DummyRepository();
				break;
			default:
				repo = new DummyRepository();
		}
		
		return repo;
	}
	
}

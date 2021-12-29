package chan.download.storage;

import chan.download.main.ArgumentMarshaller;

public class RepositoryFactory {

	public static Repository create(ArgumentMarshaller marshaller) {
		Repository repo;
		
		switch(marshaller.getRepositoryType()) {
			case FILESYSTEM:
				repo = new FileRepository(marshaller.getDestination());
				break;
			case DATABASE:
				repo = new DatabaseRepository(marshaller.getDestination());
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

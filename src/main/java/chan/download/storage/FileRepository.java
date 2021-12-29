package chan.download.storage;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class FileRepository implements Repository {

	private String directory;

	public FileRepository(String directory) {
		this.directory = directory;
	}
	
	public void save(String url) throws SaveException {
		try {
			InputStream in = new URL(url).openStream();
			Path path = getAbsoluteFilePath(url);
			Files.copy(in, path);
		} catch(Exception e) {
			throw new SaveException(e.getMessage());
		}
	}

	private Path getAbsoluteFilePath(String url) {
		List<String> urlParts = Arrays.asList(url.split("/"));
		String fileName = urlParts.get(urlParts.size() - 1);
		
		return Paths.get(directory, fileName);
	}

}

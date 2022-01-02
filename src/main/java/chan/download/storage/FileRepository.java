package chan.download.storage;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import chan.download.util.URLUtil;

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
		} catch(FileAlreadyExistsException e) {
			throw new SaveException("File already exists, skipping.");
		} catch (MalformedURLException e) {
			throw new SaveException("Invalid URL, skipping.");
		} catch (IOException e) {
			throw new SaveException("I/O Error, skipping.");
		} catch (Exception e) {
			System.out.println("Unknown error, skipping. Details see stacktrace below.\n\n");
			e.printStackTrace();
		}
	}

	private Path getAbsoluteFilePath(String url) {
		String fileName = URLUtil.getFileNameFromURL(url);
		
		return Paths.get(directory, fileName);
	}

}

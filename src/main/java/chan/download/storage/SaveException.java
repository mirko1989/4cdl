package chan.download.storage;

public class SaveException extends Throwable {
	
	private static final long serialVersionUID = 1L;
	private String message;

	public SaveException(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}

}

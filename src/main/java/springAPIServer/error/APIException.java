package springAPIServer.error;

public class APIException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	private String message;
	
	public APIException(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
}

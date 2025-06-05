package gpf.dc.basic.exception;

public class ExpressionException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4847864050501039472L;
	private String _attachMsg = "";

	public ExpressionException() {
	}

	public ExpressionException(Throwable cause) {
		super(cause);
	}

	public ExpressionException(String message) {
		super(message);
	}

	public ExpressionException(String message, Throwable cause) {
		super(message, cause);
	}

	public ExpressionException(String message, Throwable cause, String attachMsg) {
		super(message, cause);
		_attachMsg = attachMsg;
	}

	public ExpressionException(String message, String attachMsg) {
		super(message);
		_attachMsg = attachMsg;
	}

	public String getAttchMsg() {
		return _attachMsg;
	}
}
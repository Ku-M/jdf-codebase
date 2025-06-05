package fe.util.exception;

import cmn.enums.ErrorLevel;
import cmn.exception.BaseException;
import cmn.exception.ErrorInfoInterface;

public class VerifyException extends BaseException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4847864050501039472L;

	public VerifyException() {
		super();
	}

	public VerifyException(Throwable cause) {
		super(ErrorLevel.INFO,"",cause);
	}

	public VerifyException(String message) {
		super(ErrorLevel.INFO,"",message);
	}

	public VerifyException(String message, Throwable cause) {
		super(ErrorLevel.INFO,"",message, cause);
	}
	
	public VerifyException(ErrorInfoInterface errorInfo) {
		super(errorInfo);
	}
	
	public VerifyException(ErrorInfoInterface errorInfo, Throwable cause) {
		super(errorInfo,cause);
	}

}
package exceptions;

public class IncorrectPassException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public IncorrectPassException() {
		super();
	}
	
	public IncorrectPassException(String s) {
		super(s);
	}
}

package xyz.fefine.exception;

public class NoHandlerFoundException extends Exception {
	
	@Override
	public void printStackTrace() {
		
		System.err.println("not found RequestHandler");
		
		super.printStackTrace();
	}

}

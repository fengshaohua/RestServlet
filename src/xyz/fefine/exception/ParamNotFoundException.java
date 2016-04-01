package xyz.fefine.exception;

public class ParamNotFoundException extends Exception {
	
	
	    /**
	    * @Fields serialVersionUID : TODO
	    */
	    
	private static final long serialVersionUID = 1L;

	@Override
	public void printStackTrace() {
		// TODO Auto-generated method stub
		System.err.println("参数指定不完全");
		super.printStackTrace();
	}
}

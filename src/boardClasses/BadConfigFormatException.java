package boardClasses;

public class BadConfigFormatException extends Exception {
	
	  public BadConfigFormatException(String message) {
	    super(message);
	  }
	  public BadConfigFormatException() {
	    super("Error with files");

	  }
	  public String toString() {
	   return "Error with files ";
	  }

}

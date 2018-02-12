package asw.agents.webservice.responses.errors;

public abstract class ErrorResponse extends RuntimeException {

	
	public final static String KIND_IS_REQUIRED = "Kind is required";
	public final static String LOGIN_INCORRECT = "Login incorrect, check params";
	public final static String INCORRECT_PASSWORD = "Password Incorrect";
	public final static String KIND_IS_MISSING = "Internal error - Kind is missing in system master file";
	public final static String REQUIRED_USERNAME = "The userName is required";
	public final static String REQUIRED_PASSWORD = "The password is required";
	public final static String USER_NOT_FOUND = "User not found";
	public final static String WRONG_EMAIL_STYLE = "Wrong mail style";
	public final static String INCORRECT_PASSWORD_DO_NOT_MATCH = "Same password";
	public final static String SAME_EMAIL = "Same email ";
	public final static String KIND_FILE_ERROR = "Missing kind codes master file";
	public final static String UNKNOWN_ERROR = " Unknown error";

	private static final long serialVersionUID = 1L;

	public String getMessageJSONFormat() {
		return "{\"reason\": \"" + getMessageStringFormat() + "\"}";
	}

	public abstract String getMessageStringFormat();

}

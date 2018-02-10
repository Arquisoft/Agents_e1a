package asw.agents.webservice.responses.errors;

public abstract class ErrorResponse extends RuntimeException {

	final static String KIND_IS_REQUIRED = "Kind is required";
	final static String LOGIN_INCORRECT = "Login Incorrect";
	final static String INCORRECT_PASSWORD = "Password Incorrect"; 
	final static String KIND_IS_MISSING = "Internal error - Kind is missing in system master file";
	final static String REQUIRED_USERNAME="The userName is required";
	final static String REQUIRED_PASSWORD ="The password is required";
	final static String USER_NOT_FOUND ="User not found";
	final static String WRONG_EMAIL_STYLE="Wrong Email Style";
	final static String INCORRECT_PASSWORD_DO_NOT_MATCH=" The passwords not match";
	final static String SAME_EMAIL="same Email ";
	final static String KIND_FILE_ERROR=" Kind file error ";
	final static String UNKNOWN_ERROR=" Unknown error ";

	private static final long serialVersionUID = 1L;

	public String getMessageJSONFormat() {
		return "{\"reason\": \"" + getMessageStringFormat() + "\"}";
	}

	public abstract String getMessageStringFormat();

}

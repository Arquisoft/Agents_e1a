package asw.agents.webservice.responses.errors;

public abstract class ErrorResponse extends RuntimeException {

	final static String INCORRECT_PASSWORD = "Password Incorrect";
	final static String KIND_IS_MISSING = "Internal error - Kind is missing in system master file";
	final static String KIND_IS_REQUIRED = "Kind is required";
	final static String LOGIN_INCORRECT = "Login Incorrect";

	private static final long serialVersionUID = 1L;

	public String getMessageJSONFormat() {
		return "{\"reason\": \"" + getMessageStringFormat() + "\"}";
	}

	public abstract String getMessageStringFormat();

}

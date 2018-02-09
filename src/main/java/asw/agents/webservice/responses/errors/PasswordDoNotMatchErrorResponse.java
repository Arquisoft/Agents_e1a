package asw.agents.webservice.responses.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Password do not match")
public class PasswordDoNotMatchErrorResponse extends ErrorResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getMessageJSONFormat() {
		return "{\"reason\": \"Password do not match\"}";
	}

	@Override
	public String getMessageStringFormat() {
		return "Password do not match";
	}

}

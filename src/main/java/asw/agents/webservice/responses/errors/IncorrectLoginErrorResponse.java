package asw.agents.webservice.responses.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Login Incorrect")
public class IncorrectLoginErrorResponse extends ErrorResponse {

	private static final long serialVersionUID = 1L;

	@Override
	public String getMessageJSONFormat() {
		return "{\"reason\": \"Login Incorrect\"}";
	}

	@Override
	public String getMessageStringFormat() {
		return "Login Incorrect";
	}

}

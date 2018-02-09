package asw.agents.webservice.responses.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "User name is required")
public class RequiredUserNameErrorResponse extends ErrorResponse {

	private static final long serialVersionUID = 1L;

	@Override
	public String getMessageJSONFormat() {
		return "{\"reason\": \"User name is required\"}";
	}

	@Override
	public String getMessageStringFormat() {
		return "User name is required";
	}

}

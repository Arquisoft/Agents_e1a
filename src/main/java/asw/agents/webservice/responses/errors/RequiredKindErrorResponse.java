package asw.agents.webservice.responses.errors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import asw.agents.webservice.responses.errors.ErrorResponse;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Kind is required")
public class RequiredKindErrorResponse extends ErrorResponse {

	private static final long serialVersionUID = 1L;

	@Override
	public String getMessageJSONFormat() {
		return "{\"reason\": \"Kind is required\"}";
	}

	@Override
	public String getMessageStringFormat() {
		return "Kind is required";
	}

}

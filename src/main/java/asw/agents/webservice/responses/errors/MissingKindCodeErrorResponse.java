package asw.agents.webservice.responses.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import asw.agents.webservice.responses.errors.ErrorResponse;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = ErrorResponse.KIND_IS_MISSING)
public class MissingKindCodeErrorResponse extends ErrorResponse {

	private static final long serialVersionUID = 1L;

	@Override
	public String getMessageStringFormat() {
		return KIND_IS_MISSING;
	}

}

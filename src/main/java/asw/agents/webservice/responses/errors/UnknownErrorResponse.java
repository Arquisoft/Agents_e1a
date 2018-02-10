package asw.agents.webservice.responses.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = ErrorResponse.UNKNOWN_ERROR)
public class UnknownErrorResponse extends ErrorResponse {

	private static final long serialVersionUID = 1L;


	@Override
	public String getMessageStringFormat() {
		return ErrorResponse.UNKNOWN_ERROR;
	}

}

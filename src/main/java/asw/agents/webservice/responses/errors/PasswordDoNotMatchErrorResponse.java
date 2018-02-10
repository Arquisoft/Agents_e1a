package asw.agents.webservice.responses.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = ErrorResponse.INCORRECT_PASSWORD_DO_NOT_MATCH)
public class PasswordDoNotMatchErrorResponse extends ErrorResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	

	@Override
	public String getMessageStringFormat() {
		return ErrorResponse.INCORRECT_PASSWORD_DO_NOT_MATCH;
	}

}

package asw.agents.webservice.responses.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import asw.agents.webservice.responses.errors.ErrorResponse;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = ErrorResponse.KIND_FILE_ERROR)
public class MissingKindCodesMasterFileErrorResponse extends ErrorResponse {

	private static final long serialVersionUID = 1L;


	@Override
	public String getMessageStringFormat() {
		return ErrorResponse.KIND_FILE_ERROR;
	}

}

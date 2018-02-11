package asw.agents.factory;

import asw.agents.webservice.responses.errors.ErrorResponse;
import asw.agents.webservice.responses.errors.IncorrectPetitionErrorResponse;
import asw.agents.webservice.responses.errors.IncorrectPasswordErrorResponse;
import asw.agents.webservice.responses.errors.MissingKindCodeErrorResponse;
import asw.agents.webservice.responses.errors.MissingKindCodesMasterFileErrorResponse;
import asw.agents.webservice.responses.errors.PasswordDoNotMatchErrorResponse;
import asw.agents.webservice.responses.errors.RequiredKindErrorResponse;
import asw.agents.webservice.responses.errors.RequiredUserNameErrorResponse;
import asw.agents.webservice.responses.errors.RequiredPasswordErrorResponse;
import asw.agents.webservice.responses.errors.SameEmailErrorResponse;
import asw.agents.webservice.responses.errors.UnknownErrorResponse;
import asw.agents.webservice.responses.errors.UserNotFoundResponse;
import asw.agents.webservice.responses.errors.WrongEmailStyleErrorResponse;

//Creacion de los distintos tipos de error.
public class ErrorFactory {

	public static enum Errors {
		INCORRECT_PASSWORD, REQUIRED_USERNAME, REQUIRED_PASSWORD, USER_NOT_FOUND, WRONG_EMAIL_STYLE, INCORRECT_PASSWORD_DO_NOT_MATCH, SAME_EMAIL, MISSING_KIND_CODES_MASTER_FILE_ERROR, REQUIRED_KIND, INCORRECT_PETITION, INTERNAL_FAILURE_KIND_DOES_NOT_EXIST
	}

	public static ErrorResponse getError(Errors error) {
		switch (error) {
		case INCORRECT_PASSWORD:
			return new IncorrectPasswordErrorResponse();
		case REQUIRED_USERNAME:
			return new RequiredUserNameErrorResponse();
		case REQUIRED_PASSWORD:
			return new RequiredPasswordErrorResponse();
		case USER_NOT_FOUND:
			return new UserNotFoundResponse();
		case WRONG_EMAIL_STYLE:
			return new WrongEmailStyleErrorResponse();
		case INCORRECT_PASSWORD_DO_NOT_MATCH:
			return new PasswordDoNotMatchErrorResponse();
		case SAME_EMAIL:
			return new SameEmailErrorResponse();
		case REQUIRED_KIND:
			return new RequiredKindErrorResponse();
		case MISSING_KIND_CODES_MASTER_FILE_ERROR:
			return new MissingKindCodesMasterFileErrorResponse();
		case INCORRECT_PETITION:
			return new IncorrectPetitionErrorResponse();
		case INTERNAL_FAILURE_KIND_DOES_NOT_EXIST:
			return new MissingKindCodeErrorResponse();
		default:// en caso de no conocer el error.
			return new UnknownErrorResponse();
		}
	}

	// Generar Constructor privado no queremos que se pueda tener varias
	// instancias de la clase.
	private ErrorFactory() {
	}

}

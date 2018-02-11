package asw.agents.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import asw.agents.ChangeInfo;
import asw.agents.util.Check;
import asw.agents.webservice.request.PeticionChangeEmailREST;
import asw.agents.webservice.request.PeticionChangePasswordREST;
import asw.agents.webservice.responses.RespuestaChangeInfoREST;
import asw.agents.webservice.responses.errors.ErrorResponse;
import asw.dbmanagement.FindAgent;
import asw.dbmanagement.UpdateInfo;
import asw.dbmanagement.model.Agent;

@RestController
public class ChangeInfoRESTController implements ChangeInfo {

	@Autowired
	private FindAgent getAgent;
	@Autowired
	private UpdateInfo updateInfo;

	@Override
	@RequestMapping(value = "/changeEmail", method = RequestMethod.POST, headers = { "Accept=application/json",
			"Accept=application/xml" }, produces = { "application/json", "text/xml" })
	public ResponseEntity<RespuestaChangeInfoREST> changeEmail(
			@RequestBody(required = true) PeticionChangeEmailREST datos) {

		String identifier = datos.getLogin();
		String nuevoEmail = datos.getNewEmail();

		Check.loginString(identifier);

		Agent agent = getAgent.execute(identifier);
		Check.isNotNull(agent);

		updateInfo.updateEmail(agent, nuevoEmail);

		RespuestaChangeInfoREST res = new RespuestaChangeInfoREST(nuevoEmail, "email actualizado correctamente");
		return new ResponseEntity<RespuestaChangeInfoREST>(res, HttpStatus.OK);
	}

	@Override
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST, headers = { "Accept=application/json",
			"Accept=application/xml" }, produces = { "application/json", "text/xml" })
	public ResponseEntity<RespuestaChangeInfoREST> changePassword(
			@RequestBody(required = true) PeticionChangePasswordREST datos) {
		String identifier = datos.getIdentifier();
		String password = datos.getPassword();
		String newPassword = datos.getNewPassword();

		Check.loginString(identifier);

		Agent agent = getAgent.execute(identifier);
		Check.isNotNull(agent);

		updateInfo.updatePassword(agent, password, newPassword);

		RespuestaChangeInfoREST res = new RespuestaChangeInfoREST(identifier, "contraseña actualizada correctamente");
		return new ResponseEntity<RespuestaChangeInfoREST>(res, HttpStatus.OK);
	}

	@ExceptionHandler(ErrorResponse.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String handleErrorResponses(ErrorResponse error) {
		return error.getMessageJSONFormat();
	}

}

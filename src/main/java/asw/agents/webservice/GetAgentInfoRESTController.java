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

import asw.agents.GetAgentInfo;
import asw.agents.util.Check;
import asw.agents.webservice.request.PeticionInfoREST;
import asw.agents.webservice.responses.RespuestaInfoREST;
import asw.agents.webservice.responses.errors.ErrorResponse;
import asw.dbmanagement.FindAgent;
import asw.dbmanagement.model.Agent;

@RestController
public class GetAgentInfoRESTController implements GetAgentInfo {

	@Autowired
	private FindAgent findAgent;

	@Override
	@RequestMapping(value = "/user", method = RequestMethod.POST, headers = { "Accept=application/json",
			"Accept=application/xml" }, produces = { "application/json", "text/xml" })
	public ResponseEntity<RespuestaInfoREST> getPOSTpetition(@RequestBody(required = true) PeticionInfoREST peticion) {

		// Se comprueba que se han pasado correctamente los parámetros del login
		Check.isNotEmailEmpty(peticion.getLogin());
		Check.loginString(peticion.getLogin());
		Check.passwordString(peticion.getPassword());
		Check.kindString(peticion.getKind());

		// Se obtiene el objeto Agent pedido
		Agent agent = findAgent.execute(peticion.getLogin());

		// Se comprueba que el agent existe
		Check.isNotNull(agent);

		// Se comprueba que la combinación login/password/kind tiene correspondencia
		Check.isPetitionCorrect(peticion.getPassword(), peticion.getKind(), agent);

		return new ResponseEntity<RespuestaInfoREST>(new RespuestaInfoREST(agent), HttpStatus.OK);
	}

	@ExceptionHandler(ErrorResponse.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String handleErrorResponses(ErrorResponse error) {
		return error.getMessageJSONFormat();
	}
}

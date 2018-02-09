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
import asw.dbmanagement.GetAgent;
import asw.dbmanagement.model.Agent;

@RestController
public class GetAgentInfoRESTController implements GetAgentInfo {

	@Autowired
	private GetAgent getAgent;

	@Override
	@RequestMapping(value = "/user", method = RequestMethod.POST, headers = { "Accept=application/json",
			"Accept=application/xml" }, produces = { "application/json", "text/xml" })
	public ResponseEntity<RespuestaInfoREST> getPOSTpetition(@RequestBody(required = true) PeticionInfoREST peticion) {

		// TODO deberían de funcionar, comprobar con valores que no existan ...
		Check.loginString(peticion.getLogin());
		Check.passwordString(peticion.getPassword());
		Check.kindString(peticion.getKind());

		Agent agent = getAgent.getByIdentifier(peticion.getLogin());

		Check.isNotNull(agent);

		Check.isLoginCorrect(peticion.getPassword(), peticion.getKind(), agent);

		/*
		 * Añadimos la información al modelo, para que se muestre en la pagina html:
		 * datosAgent
		 */

		return new ResponseEntity<RespuestaInfoREST>(new RespuestaInfoREST(agent), HttpStatus.OK);
	}

	@ExceptionHandler(ErrorResponse.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String handleErrorResponses(ErrorResponse error) {
		return error.getMessageJSONFormat();
	}
}

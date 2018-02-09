package asw.agents;

import org.springframework.http.ResponseEntity;

import asw.agents.webservice.responses.RespuestaInfoREST;
import asw.agents.webservice.request.PeticionInfoREST;

public interface GetAgentInfo {

	public ResponseEntity<RespuestaInfoREST> getPOSTpetition(PeticionInfoREST peticion);

}

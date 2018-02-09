package asw.agents;

import org.springframework.http.ResponseEntity;

import asw.agents.webservice.request.PeticionInfoREST;
import asw.agents.webservice.responses.RespuestaInfoREST;

public interface GetAgentInfo {

	public ResponseEntity<RespuestaInfoREST> getPOSTpetition(PeticionInfoREST peticion);

}

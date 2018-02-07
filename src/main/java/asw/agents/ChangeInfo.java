package asw.agents;

import org.springframework.http.ResponseEntity;

import asw.agents.webservice.request.PeticionChangeEmailREST;
import asw.agents.webservice.request.PeticionChangePasswordREST;
import asw.agents.webservice.responses.RespuestaChangeInfoREST;

public interface ChangeInfo {
	/**
	 * Cambio de email
	 * 
	 * @param datos
	 *            requeridos (email, password, newEmail)
	 * @return respuesta en xml o json
	 */
	public ResponseEntity<RespuestaChangeInfoREST> changeEmail(PeticionChangeEmailREST datos);

	/**
	 * Cambio de contraseña
	 *
	 * @param datos
	 *            requeridos (email, password, newPassword)
	 * @return
	 */
	public ResponseEntity<RespuestaChangeInfoREST> changePassword(PeticionChangePasswordREST datos);
}

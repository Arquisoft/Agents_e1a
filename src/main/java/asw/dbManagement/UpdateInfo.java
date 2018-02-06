package asw.dbManagement;

import asw.dbManagement.model.Agent;

public interface UpdateInfo {
	public void updateEmail(Agent agent, String email);

	/**
	 * Permite la solicitud de cambio de contraseña
	 */
	public void updatePassword(Agent agent, String password, String newPassword);
}

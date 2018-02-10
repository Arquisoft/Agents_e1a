package asw.dbmanagement;

import asw.dbmanagement.model.Agent;

public interface UpdateInfo {
	
	public void updateEmail(Agent agent, String email);

	public void updatePassword(Agent agent, String password, String newPassword);
	
}

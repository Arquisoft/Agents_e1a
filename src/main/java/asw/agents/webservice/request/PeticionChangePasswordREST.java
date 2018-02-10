package asw.agents.webservice.request;

public class PeticionChangePasswordREST {

	private String identifier;
	private String password;
	private String newPassword;

	public PeticionChangePasswordREST() {

	}

	public PeticionChangePasswordREST(String identifier, String password, String newPassword) {
		super();
		this.password = password;
		this.newPassword = newPassword;
		this.identifier = identifier;
	}

	public String getIdentifier() {
		return identifier;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public String getPassword() {
		return password;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}

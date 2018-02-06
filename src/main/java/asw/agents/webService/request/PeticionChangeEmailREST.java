package asw.agents.webService.request;

public class PeticionChangeEmailREST {

	private String email;
	private String password;
	private String newEmail;

	public PeticionChangeEmailREST() {

	}

	public PeticionChangeEmailREST(String email, String password, String newEmail) {
		super();
		this.email = email;
		this.password = password;
		this.newEmail = newEmail;
	}

	public String getEmail() {
		return email;
	}

	public String getNewEmail() {
		return newEmail;
	}

	public String getPassword() {
		return password;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setNewEmail(String newEmail) {
		this.newEmail = newEmail;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}

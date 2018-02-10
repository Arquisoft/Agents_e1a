package asw.agents.webservice.request;

public class PeticionChangeEmailREST {

	private String login;
	private String newEmail;

	public PeticionChangeEmailREST() {

	}

	public PeticionChangeEmailREST(String login, String newEmail) {
		super();
		this.login = login;
		this.newEmail = newEmail;
	}

	public String getLogin() {
		return login;
	}

	public String getNewEmail() {
		return newEmail;
	}

	public void setLogin(String email) {
		this.login = email;
	}

	public void setNewEmail(String newEmail) {
		this.newEmail = newEmail;
	}

}

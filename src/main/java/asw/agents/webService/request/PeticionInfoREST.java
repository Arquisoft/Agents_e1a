package asw.agents.webService.request;

public class PeticionInfoREST {

	private String login;
	private String password;
	private byte kind;

	public PeticionInfoREST() {

	}

	public byte getKind() {
		return kind;
	}

	public void setKind(byte kind) {
		this.kind = kind;
	}

	public PeticionInfoREST(String login, String password) {
		this.login = login;
		this.password = password;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}

package asw.agents.webService.responses;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import asw.dbManagement.model.Agent;

@XmlRootElement(name = "agent")
public class RespuestaInfoREST {

	private String firstName;
	private String ID;
	private String email;

	public RespuestaInfoREST() {
	}

	public RespuestaInfoREST(Agent agent) {
		setFirstName(agent.getNombre());
		setEmail(agent.getEmail());
	}

	public String getEmail() {
		return email;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getID() {
		return ID;
	}

	@XmlElement
	public void setEmail(String email) {
		this.email = email;
	}

	@XmlElement
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

}

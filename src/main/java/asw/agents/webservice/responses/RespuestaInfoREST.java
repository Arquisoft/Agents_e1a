package asw.agents.webservice.responses;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import asw.agents.util.Utilidades;
import asw.dbmanagement.model.Agent;

@XmlRootElement(name = "agent")
public class RespuestaInfoREST {

	private String firstName;
	private String lastName;
	private int edad;
	private String ID;
	private String email;

	public RespuestaInfoREST() {
	}

	public RespuestaInfoREST(Agent agent) {
		setFirstName(agent.getNombre());
		setLastName(agent.getApellidos());
		setEdad(Utilidades.getEdad(agent.getFechaNacimiento()));
		setID(agent.getDNI());
		setEmail(agent.getEmail());
	}

	public int getEdad() {
		return edad;
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

	public String getLastName() {
		return lastName;
	}

	@XmlElement
	public void setEdad(int edad) {
		this.edad = edad;
	}

	@XmlElement
	public void setEmail(String email) {
		this.email = email;
	}

	@XmlElement
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@XmlElement
	public void setID(String iD) {
		ID = iD;
	}

	@XmlElement
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}

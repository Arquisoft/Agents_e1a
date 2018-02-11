package asw.agents.webservice.responses;

import java.io.IOException;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import asw.agents.factory.ErrorFactory;
import asw.agents.factory.ErrorFactory.Errors;
import asw.agents.util.FilesManager;
import asw.dbmanagement.model.Agent;

@XmlRootElement(name = "agent")
public class RespuestaInfoREST {

	private String name;
	private String location;
	private String email;
	private String id;
	private String kind;
	private int kindCode;

	public RespuestaInfoREST() {
	}

	public RespuestaInfoREST(Agent agent) {
		setName(agent.getNombre());
		setLocation(agent.getLocation());
		setEmail(agent.getEmail());
		setId(agent.getIdentifier());
		setKind(agent.getKind());
		try {
			setKindCode(FilesManager.getKindCode(kind));
		} catch (IOException e) {
			throw ErrorFactory.getError(Errors.MISSING_KIND_CODES_MASTER_FILE_ERROR);
		}
	}

	public String getEmail() {
		return email;
	}

	public String getId() {
		return id;
	}

	public String getKind() {
		return kind;
	}

	public int getKindCode() {
		return kindCode;
	}

	public String getLocation() {
		return location;
	}

	public String getName() {
		return name;
	}

	@XmlElement
	public void setEmail(String email) {
		this.email = email;
	}

	@XmlElement
	public void setId(String id) {
		this.id = id;
	}

	@XmlElement
	public void setKind(String kind) {
		this.kind = kind;
	}

	@XmlElement
	public void setKindCode(int kindCode) {
		this.kindCode = kindCode;
	}

	@XmlElement
	public void setLocation(String location) {
		this.location = location;
	}

	@XmlElement
	public void setName(String name) {
		this.name = name;
	}

}

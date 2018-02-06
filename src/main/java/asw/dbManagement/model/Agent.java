package asw.dbManagement.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import asw.agents.util.Location;

@Entity
@Table(name = "Agent")
public class Agent {

	// Id generado automáticamente para diferenciar cada uno (para mapear)
	@Id
	@GeneratedValue
	private Long id;

	// Atributos del agente
	private String nombre;
	private Location location;
	@Column(unique = true)
	private String email;
	private String password;
	private byte kind;
	@Column(unique = true)
	private String identifier;

	/**
	 * Constructor vacío (ya que es para mapear)
	 */
	Agent() {
	}

	/**
	 * @param nombre
	 * @param location
	 * @param email
	 * @param password
	 * @param kind
	 * @param identifier
	 */
	public Agent(String nombre, Location location, String email, String password, byte kind, String identifier) {
		super();
		this.nombre = nombre;
		this.location = location;
		this.email = email;
		this.password = password;
		this.kind = kind;
		this.identifier = identifier;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Agent other = (Agent) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getEmail() {
		return email;
	}

	public Long getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public String getPassword() {
		return password;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Location getLocation() {
		return location;
	}

	public byte getKind() {
		return kind;
	}

	public String getIdentifier() {
		return identifier;
	}

}

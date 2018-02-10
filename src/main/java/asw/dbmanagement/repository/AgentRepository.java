package asw.dbmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import asw.dbmanagement.model.Agent;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {

	/**
	 * Método que devuelve el Agente el cual es buscado por email en la base de
	 * datos
	 * 
	 * @param email del Agente
	 * @return El Agente con dicho email
	 */
	public Agent findByEmail(String email);

	public Agent findByIdentifier(String identificador);

}

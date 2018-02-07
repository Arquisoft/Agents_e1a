package asw.dbmanagement.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import asw.dbmanagement.GetAgent;
import asw.dbmanagement.model.Agent;
import asw.dbmanagement.repository.AgentRepository;

@Service
public class GetAgentImpl implements GetAgent {

	private AgentRepository repository;

	@Autowired
	public GetAgentImpl(AgentRepository repository) {
		this.repository = repository;
	}

	/**
	 * Método que devuelve el agente buscado por email Hace uso del método
	 * findByEmail (mapeador)
	 */
	@Override
	public Agent getAgent(String email) {

		return this.repository.findByEmail(email);
	}

}

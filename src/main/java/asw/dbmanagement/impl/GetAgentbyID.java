package asw.dbmanagement.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import asw.dbmanagement.FindAgent;
import asw.dbmanagement.model.Agent;
import asw.dbmanagement.repository.AgentRepository;

@Service
public class GetAgentbyID implements FindAgent {

	private AgentRepository repository;

	@Autowired
	public GetAgentbyID(AgentRepository repository) {
		this.repository = repository;
	}

	@Override
	public Agent execute(String identificador) {
		return this.repository.findByIdentifier(identificador);
	}

}

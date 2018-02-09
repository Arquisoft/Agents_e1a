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

	@Override
	public Agent getByIdentifier(String identificador) {
		return this.repository.findByIdentifier(identificador);
	}

}

package asw.dbmanagement.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import asw.agents.util.Check;
import asw.dbmanagement.UpdateInfo;
import asw.dbmanagement.model.Agent;
import asw.dbmanagement.repository.AgentRepository;

@Service
public class UpdateInfoImpl implements UpdateInfo {

	private AgentRepository repository;

	@Autowired
	public UpdateInfoImpl(AgentRepository repository) {
		this.repository = repository;
	}

	/**
	 * Método que permite la actualización del email del agente Se comprueba que el
	 * email no esté vacío
	 */
	@Override
	public void updateEmail(Agent agent, String email) {
		Check.isValidEmailAddress(email);
		Check.isNotSameEmail(email, agent.getEmail());
		agent.setEmail(email);
		// TODO hace falta actualizar en el repositorio ??
		this.repository.save(agent);
	}

	/**
	 * Método que permite la actualización de la contraseña del Agente Se comprueba
	 * que las contraseñas no estén vacías, sean distintas y la actual coincida con
	 * la del Agente
	 */
	@Override
	public void updatePassword(Agent agent, String password, String newPassword) {
		Check.passwordString(password);
		Check.passwordString(newPassword);
		Check.isSamePassword(password, agent.getPassword());
		Check.isNotSamePassword(password, newPassword);

		agent.setPassword(newPassword);
		// TODO hace falta actualizar en el repositorio esplícitamente??
		this.repository.save(agent);
	}

}

package asw.agents.util;

import asw.agents.factory.ErrorFactory;
import asw.agents.factory.ErrorFactory.Errors;
import asw.dbmanagement.model.Agent;

public class Check {

	/**
	 * 
	 * @param agent
	 * @return devuelve false si no es null o excepcion
	 */
	public static void isNotNull(Agent agent) {
		if (isNull(agent)) {
			throw ErrorFactory.getError(Errors.USER_NOT_FOUND);
		}
	}

	public static void isAgentNotNull(Agent agent) {
		if (isNull(agent)) {
			throw ErrorFactory.getError(Errors.USER_NOT_FOUND);
		}
	}

	public static <T> boolean isNull(T arg) {
		return arg == null;
	}

	/**
	 * 
	 * @param email
	 * @return excepcion si esta vacio
	 */
	public static boolean isEmailEmpty(String email) {
		if (email.trim().isEmpty())
			throw ErrorFactory.getError(Errors.REQUIRED_USERNAME);
		else
			return false;
	}

	/**
	 * Comprobacion de si el correo es valido
	 * 
	 * @param email
	 * @return true si es valido.
	 */
	public static boolean isEmailValid(String email) {
		String[] mailSplit = email.split("@");
		if (mailSplit.length != 2) {
			throw ErrorFactory.getError(Errors.WRONG_EMAIL_STYLE);
		}
		mailSplit = email.split("\\.");
		if (mailSplit.length != 2 || mailSplit[0].length() == 0 || mailSplit[1].length() == 0) {
			throw ErrorFactory.getError(Errors.WRONG_EMAIL_STYLE);
		}
		return true;
	}

	public static boolean isPasswordCorrect(String password, Agent agent) {
		if (!password.equals(agent.getPassword())) {
			throw ErrorFactory.getError(Errors.INCORRECT_PASSWORD_DO_NOT_MATCH);
		}
		return true;
	}

	public static boolean isSameEmail(String email, String email2) {
		if (email.equals(email2)) {
			throw ErrorFactory.getError(Errors.SAME_EMAIL);
		}
		return true;
	}

	public static boolean isSamePassword(String password, String password2) {
		if (password.equals(password2)) {
			throw ErrorFactory.getError(Errors.INCORRECT_PASSWORD);
		}
		return true;
	}

	public static void loginString(String login) {
		if (isNull(login) || isEmpty(login)) {
			throw ErrorFactory.getError(Errors.USER_NOT_FOUND);
		}
	}

	private static boolean isEmpty(String string) {
		return string.trim().isEmpty();
	}

	public static void passwordString(String password) {
		if(isNull(password)||isEmpty(password)) {
			throw ErrorFactory.getError(Errors.REQUIRED_PASSWORD);
		}
		
	}

	public static void kindString(String kind) {
		if(isNull(kind)||isEmpty(kind)) {
			throw ErrorFactory.getError(Errors.REQUIRED_KIND);
		}
	}

}

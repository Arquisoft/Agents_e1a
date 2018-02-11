package asw.agents.util;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import asw.agents.factory.ErrorFactory;
import asw.agents.factory.ErrorFactory.Errors;
import asw.dbmanagement.model.Agent;

public class Check {



	public static void isValidEmailAddress(String email) {
		try {			
			new InternetAddress(email).validate();
		} catch (AddressException ex) {
			throw ErrorFactory.getError(Errors.WRONG_EMAIL_STYLE);
		}
	}

	private static boolean isEmpty(String string) {
		return string.trim().isEmpty();
	}

	public static void isPetitionCorrect(String password, String kind, Agent agent) {
		if (!password.equals(agent.getPassword()) || !kind.equals(agent.getKind())) {
			throw ErrorFactory.getError(Errors.INCORRECT_PETITION);
		}
	}

	public static void isNotNull(Agent agent) {
		if (isNull(agent)) {
			throw ErrorFactory.getError(Errors.USER_NOT_FOUND);
		}
	}

	private static <T> boolean isNull(T arg) {
		return arg == null;
	}

	public static void isSamePassword(String p1, String p2) {
		if (!p1.equals(p2)) {
			throw ErrorFactory.getError(Errors.INCORRECT_PASSWORD);
		}
	}

	public static void kindString(String kind) {
		if (isNull(kind) || isEmpty(kind)) {
			throw ErrorFactory.getError(Errors.REQUIRED_KIND);
		}
	}

	public static void loginString(String login) {
		if (isNull(login) || isEmpty(login)) {
			throw ErrorFactory.getError(Errors.REQUIRED_USERNAME);
		}
	}

	public static void passwordString(String password) {
		if (isNull(password) || isEmpty(password)) {
			throw ErrorFactory.getError(Errors.REQUIRED_PASSWORD);
		}

	}

	public static void isNotSamePassword(String p1, String p2) {
		if (p1.equals(p2)) {
			throw ErrorFactory.getError(Errors.INCORRECT_PASSWORD_DO_NOT_MATCH);
		}

	}

	public static void isNotSameEmail(String e1, String e2) {
		if (e1.equals(e2)) {
			throw ErrorFactory.getError(Errors.SAME_EMAIL);
		}

	}

	public static void existsKindCode(Integer kindCode) {
		if(isNull(kindCode)) {
			throw ErrorFactory.getError(Errors.INTERNAL_FAILURE_KIND_DOES_NOT_EXIST);
		}
		
	}

}

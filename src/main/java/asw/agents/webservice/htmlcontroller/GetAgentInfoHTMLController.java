package asw.agents.webservice.htmlcontroller;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import asw.agents.util.Check;
import asw.agents.util.KindManager;
import asw.agents.webservice.responses.errors.ErrorResponse;
import asw.dbmanagement.GetAgent;
import asw.dbmanagement.model.Agent;

@Controller
public class GetAgentInfoHTMLController {

	@Autowired
	private GetAgent getAgent;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String getLogin(HttpSession session, @RequestParam String identifier, @RequestParam String password,
			@RequestParam String kind, Model model) {

		Check.loginString(identifier);
		Check.passwordString(password);
		Check.kindString(kind);

		Agent agent = getAgent.getByIdentifier(identifier);

		Check.isNotNull(agent);
		Check.isPasswordCorrect(password, agent);

		session.setAttribute("agent", agent);

		Integer kindCode = 0;
		try {
			kindCode = new KindManager().getKindCode(agent.getKind());
		} catch (IOException e) {
			// TODO excepción ??
		}

		session.setAttribute("kindCode", kindCode);

		return "datosAgent";

	}

	@ExceptionHandler(ErrorResponse.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String handleErrorResponseNotFound(ErrorResponse excep, Model model) {
		model.addAttribute("error", excep.getMessageStringFormat());

		return "error";
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String inicalicerLogin(Model model) {
		return "login";
	}
}

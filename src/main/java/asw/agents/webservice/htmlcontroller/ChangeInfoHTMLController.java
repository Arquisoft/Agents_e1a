package asw.agents.webservice.htmlcontroller;

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
import asw.agents.webservice.responses.errors.ErrorResponse;
import asw.dbmanagement.UpdateInfo;
import asw.dbmanagement.model.Agent;

@Controller
public class ChangeInfoHTMLController {
	@Autowired
	private UpdateInfo updateInfo;

	@RequestMapping(value = "/confirmEmail", method = RequestMethod.POST)
	public String changeEmail(HttpSession session, @RequestParam String email, Model model) {
		Check.isEmailEmpty(email);
		Check.isEmailValid(email);

		// Agent que se ha logeado antes
		Agent p = (Agent) session.getAttribute("agent");
		Check.isNotNull(p);
		Check.isSameEmail(email, p.getEmail());

		// Actualizo sus datos
		updateInfo.updateEmail(p, email);

		// Mensaje a mostrar en HTML
		model.addAttribute("info", "Email actualizado correctamente");
		return "datosAgent";
	}

	@RequestMapping(value = "/changeInfo", method = RequestMethod.POST)
	public String changeInfo() {
		return "changeInfo";
	}

	@RequestMapping(value = "/confirmPassword", method = RequestMethod.POST)
	public String changePassword(HttpSession session, @RequestParam String password, @RequestParam String newPassword,
			Model model) {
		Check.passwordString(password);
		Check.passwordString(newPassword);
		Check.isSamePassword(password, newPassword);

		// Agent que se ha logeado antes
		Agent p = (Agent) session.getAttribute("agent");
		Check.isNotNull(p);
		// TODO Check.isLoginCorrect(password, p);

		// Actualizo sus datos
		updateInfo.updatePassword(p, password, newPassword);

		// Mensaje a mostrar en HTML
		model.addAttribute("info", "Contraseña actualizada correctamente");
		return "datosAgent";
	}

	@ExceptionHandler(ErrorResponse.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String handleErrorResponseNotFound(ErrorResponse excep, Model model) {
		model.addAttribute("error", excep.getMessageStringFormat());
		return "error";
	}
}
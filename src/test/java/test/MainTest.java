package test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import asw.Application;
import asw.agents.webService.request.PeticionChangeEmailREST;
import asw.agents.webService.request.PeticionChangePasswordREST;
import asw.agents.webService.request.PeticionInfoREST;
import asw.dbManagement.GetAgent;
import asw.dbManagement.model.Agent;

@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest({ "server.port=0" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MainTest {

	// Cabecera HTTP para pedir respuesta en XML
	public class AcceptInterceptor implements ClientHttpRequestInterceptor {
		@Override
		public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
				throws IOException {
			HttpHeaders headers = request.getHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML));
			return execution.execute(request, body);
		}
	}

	@Value("${local.server.port}")
	private int port;
	private URL base;

	private RestTemplate template;

	@Autowired
	private GetAgent getAgent;

	// @Test
	public void emailChangeCorrectXML() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changeEmail";
		String correctChange = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" + "<ChangeInfoResponse>"
				+ "<agent>carmen@yahoo.com</agent>" + "<message>email actualizado correctamente</message>"
				+ "</ChangeInfoResponse>";

		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
		interceptors.add(new AcceptInterceptor());

		template.setInterceptors(interceptors);

		response = template.postForEntity(userURI,
				new PeticionChangeEmailREST("fhfyg@hotmail.com", "123456", "carmen@yahoo.com"), String.class);
		assertThat(response.getBody(), equalTo(correctChange));
	}

	@Before
	public void setUp() throws Exception {
		this.base = new URL("http://localhost:" + port + "/");
		template = new TestRestTemplate();
	}

	/*
	 * @Test public void T2domainModelToString() { Agent agent1 =
	 * getAgent.getAgent("paco@hotmail.com"); assertEquals(agent1.toString(),
	 * "Agent [nombre=" + agent1.getNombre() + ", apellidos=" +
	 * agent1.getApellidos() + ", fechaNacimiento=" + agent1.getFechaNacimiento() +
	 * ", email=" + agent1.getEmail() + ", DNI=" + agent1.getDNI() + ", direccion="
	 * + agent1.getDireccion() + ", nacionalidad=" + agent1.getNacionalidad() +
	 * ", isAdmin=false, isPolitician=false]"); }
	 */

	@Test
	public void T1peticionCorrecta() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/user";
		response = template.postForEntity(userURI, new PeticionInfoREST("usuario", "123456", "Person"), String.class);
		System.out.println(response.getBody());
		String expected = "{\"name\":\"nombre\",\"location\":\"1,2\",\"email\":\"nombre@uniovi.es\",\"id\":\"usuario\",\"kind\":\"Person\",\"kindCode\":1}";
		assertThat(response.getBody(), equalTo(expected));
	}
}

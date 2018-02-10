package test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
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
import asw.agents.util.FilesManager;
import asw.agents.webservice.request.PeticionInfoREST;
import asw.dbmanagement.FindAgent;

@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest({ "server.port=0" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MainTest {

	private URL base;
	@SuppressWarnings("unused")
	@Autowired
	private FindAgent getAgent;
	private int kindPerson = 0, kindSensor = 0, kindEntity = 0;
	@Value("${local.server.port}")
	private int port;

	private RestTemplate template;

	String userURI;

	@Before
	public void setUp() throws Exception {
		this.base = new URL("http://localhost:" + port + "/");
		template = new TestRestTemplate();

		// Se inicializan variables kindCode con los valores del fichero maestro
		try {
			kindPerson = FilesManager.getKindCode("Person");
			kindSensor = FilesManager.getKindCode("Sensor");
			kindEntity = FilesManager.getKindCode("Entity");
		} catch (IOException e) {
			fail("Error de entrada salida al leer del fichero maestro tipos");
		}

		userURI = base.toString() + "/user";
	}

	@Test
	public void t1peticionCorrecta() {
		// Se prueba enviando parámetros de agentes que existen en la base de datos

		// {"login": usuarioJuan, "password": password, "kind": Person}
		ResponseEntity<String> response = template.postForEntity(userURI,
				new PeticionInfoREST("usuarioJuan", "password", "Person"), String.class);
		String expected = "{\"name\":\"Juan\",\"location\":\"1.0,0.2\",\"email\":\"juan@uniovi.es\",\"id\":\"usuarioJuan\",\"kind\":\"Person\",\"kindCode\":"
				+ kindPerson + "}";
		assertThat(response.getBody(), equalTo(expected));

		// {"login": usuarioRace, "password": password, "kind": Entity}
		response = template.postForEntity(userURI, new PeticionInfoREST("usuarioRace", "password", "Entity"),
				String.class);
		expected = "{\"name\":\"RACE\",\"location\":\"1.123,-2.123\",\"email\":\"avisos@race.es\",\"id\":\"usuarioRace\",\"kind\":\"Entity\",\"kindCode\":"
				+ kindEntity + "}";
		assertThat(response.getBody(), equalTo(expected));

		// {"login": usuarioRace, "password": password, "kind": Entity}
		response = template.postForEntity(userURI, new PeticionInfoREST("usuarioA6-PK27", "password", "Sensor"),
				String.class);
		expected = "{\"name\":\"SensorTemperatura-A6-PK27\",\"location\":\"23.231,123.2\",\"email\":\"tecnico@copinsa.es\",\"id\":\"usuarioA6-PK27\",\"kind\":\"Sensor\",\"kindCode\":"
				+ kindSensor + "}";
		assertThat(response.getBody(), equalTo(expected));

	}

	@Test
	public void t2peticionNoExisteUsuario() {
		// {"login": NO_EXISTE, "password": password, "kind": Person}
		ResponseEntity<String> response = template.postForEntity(userURI,
				new PeticionInfoREST("NO_EXISTE", "password", "Person"), String.class);
		String expected = "{\"reason\": \"User not found\"}";
		assertThat(response.getBody(), equalTo(expected));
	}
	
	/**
	 * Cabecera HTTP para pedir respuesta en XML. TODO no se usa de momento pero
	 * puede ser útil más adelante
	 * 
	 * @author 2016-17
	 *
	 */
	@SuppressWarnings("unused")
	private class AcceptInterceptor implements ClientHttpRequestInterceptor {
		@Override
		public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
				throws IOException {
			HttpHeaders headers = request.getHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML));
			return execution.execute(request, body);
		}
	}
}

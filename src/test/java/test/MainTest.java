package test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
import asw.agents.util.FilesManager;
import asw.agents.webservice.request.PeticionChangeEmailREST;
import asw.agents.webservice.request.PeticionChangePasswordREST;
import asw.agents.webservice.request.PeticionInfoREST;
import asw.dbmanagement.FindAgent;
import asw.dbmanagement.model.Agent;

@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest({ "server.port=0" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MainTest {

	

		@Value("${local.server.port}")
		private int port;
		private URL base;
		private int kindPerson = 0, kindSensor = 0, kindEntity = 0;
		String userURI;

		private RestTemplate template;

		@SuppressWarnings("unused")
		@Autowired
		private FindAgent getAgent;

		void print(String s) {
			System.out.println(s);
		}
		
		// Cabecera HTTP para pedir respuesta en XML
			private class AcceptInterceptor implements ClientHttpRequestInterceptor {
					@Override
					public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
							throws IOException {
						HttpHeaders headers = request.getHeaders();
						headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML));
						return execution.execute(request, body);
					}
				}
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

	///PRUEBAS DE DOMINIO//
	
	@Test

	public void t1domainModelEqualsTest() {
		Agent agent1 = getAgent.execute("usuarioJuan");
		Agent agent2 = getAgent.execute("usuarioRa");
		Agent agent3 = getAgent.execute("usuarioJuan");
		Agent agent4 = getAgent.execute("usuarioRace");
		assertFalse(agent1.equals(agent2));
		assertFalse(agent1.equals(4));
		assertTrue(agent1.equals(agent3));
		assertTrue(agent1.equals(agent1));
		assertFalse(agent1.equals(agent4));
	}
	
	@Test 
	public void t2domainModelToString() {
		Agent agent1 = getAgent.execute("usuarioJuan");
		assertEquals(agent1.toString(),
				"Agent [id="+agent1.getId()+", nombre=" + agent1.getNombre() +", location="  + ", email=" + agent1.getEmail() + ", identifier=" + agent1.getIdentifier()
				+ ", password="+agent1.getPassword()+	", kind=" + agent1.getKind() + "]");
	}
	
	@Test
	public void t3domainModelHashCodeTest() {
		Agent agent1 = getAgent.execute("usuarioRace");
		Agent agent3 = getAgent.execute("usuarioRace");
		assertEquals(agent1.hashCode(), agent3.hashCode());
	}
	
	////PETICION LOGIN tESTS////
	

	
	@Test
	
	public void t4peticionCorrecta() {

		// Se prueba enviando parámetros de agentes que existen en la base de datos

		// {"login": usuarioJuan, "password": password, "kind": Person}
		ResponseEntity<String> response = template.postForEntity(userURI,
				new PeticionInfoREST("usuarioJuan", "password", "Person"), String.class);
		String expected = "{\"name\":\"Juan\",\"location\":\"1.0,0.2\",\"email\":\"juan@hotmail.com\",\"id\":\"usuarioJuan\",\"kind\":\"Person\",\"kindCode\":"
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

	public void t5peticionNoExisteUsuario() {

		// {"login": NO_EXISTE, "password": password, "kind": Person}
		ResponseEntity<String> response = template.postForEntity(userURI,
				new PeticionInfoREST("NO_EXISTE", "password", "Person"), String.class);
		String expected = "{\"reason\": \"User not found\"}";
		assertThat(response.getBody(), equalTo(expected));
	}


	@Test
	public void t6incorrectLogin() {
		
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/user";
		String incorrectPassword = "{\"reason\": \"Login incorrect\"}";
		response = template.postForEntity(userURI, new PeticionInfoREST("usuarioJuan", "pass","Person"), String.class);
		assertThat(response.getBody(), equalTo(incorrectPassword));

		response = template.postForEntity(userURI, new PeticionInfoREST("usuarioRace", "word","Person"), String.class);
		assertThat(response.getBody(), equalTo(incorrectPassword));

		response = template.postForEntity(userURI, new PeticionInfoREST("usuarioA6-PK27", "password","Person"), String.class);
		assertThat(response.getBody(), equalTo(incorrectPassword));

		response = template.postForEntity(userURI, new PeticionInfoREST("usuarioRace", "23456","Sensor"), String.class);
		assertThat(response.getBody(), equalTo(incorrectPassword));
	}
	
	@Test
	public void t7emptyUser() { 
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/user";
		String emptyEmail = "{\"reason\": \"The userName is required\"}";
		response = template.postForEntity(userURI, new PeticionInfoREST("", "","Person"), String.class);
		assertThat(response.getBody(), equalTo(emptyEmail));

		response = template.postForEntity(userURI, new PeticionInfoREST("", "1223","Person"), String.class);
		assertThat(response.getBody(), equalTo(emptyEmail));

		response = template.postForEntity(userURI, new PeticionInfoREST("", "iewgs","Person"), String.class);
		assertThat(response.getBody(), equalTo(emptyEmail));

		response = template.postForEntity(userURI, new PeticionInfoREST("   ", "","Person"), String.class);
		assertThat(response.getBody(), equalTo(emptyEmail));
	}
	
	@Test
	public void t8emptyKind() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/user";
		String emptyPassword = "{\"reason\": \"Kind is required\"}";

		response = template.postForEntity(userURI, new PeticionInfoREST("usuarioRace", "password",""), String.class);
		assertThat(response.getBody(), equalTo(emptyPassword));

		response = template.postForEntity(userURI, new PeticionInfoREST("usuarioJuan", "password",""), String.class);
		assertThat(response.getBody(), equalTo(emptyPassword));

		response = template.postForEntity(userURI, new PeticionInfoREST("usuarioRace", "password",""), String.class);
		assertThat(response.getBody(), equalTo(emptyPassword));

		response = template.postForEntity(userURI, new PeticionInfoREST("usuarioA6-PK27", "paswword",""), String.class);
		assertThat(response.getBody(), equalTo(emptyPassword));
	}
	
	@Test
	public void t9emptyPassword() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/user";
		String emptyPassword = "{\"reason\": \"The password is required\"}";

		response = template.postForEntity(userURI, new PeticionInfoREST("usuarioRace", "","Person"), String.class);
		assertThat(response.getBody(), equalTo(emptyPassword));

		response = template.postForEntity(userURI, new PeticionInfoREST("usuarioJuan", "","Person"), String.class);
		assertThat(response.getBody(), equalTo(emptyPassword));

		response = template.postForEntity(userURI, new PeticionInfoREST("usuarioRace", "","Person"), String.class);
		assertThat(response.getBody(), equalTo(emptyPassword));

		response = template.postForEntity(userURI, new PeticionInfoREST("usuarioA6-PK27", "","Person"), String.class);
		assertThat(response.getBody(), equalTo(emptyPassword));
	}

	

	////CAMBIO DE EMAIL tESTS////
	@Test
	public void emailChangeCorrect() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changeEmail";

		String correctChange = "{\"agent\":\"j@hotmail.com\",\"message\":\"email actualizado correctamente\"}";
		response = template.postForEntity(userURI,
				new PeticionChangeEmailREST("usuarioJuan","j@hotmail.com"), String.class);
		assertThat(response.getBody(), equalTo(correctChange));

		correctChange = "{\"agent\":\"race@gmail.com\",\"message\":\"email actualizado correctamente\"}";
		response = template.postForEntity(userURI,
				new PeticionChangeEmailREST("usuarioRace","race@gmail.com"), String.class);
		assertThat(response.getBody(), equalTo(correctChange));

		correctChange = "{\"agent\":\"juan@hotmail.com\",\"message\":\"email actualizado correctamente\"}";
		response = template.postForEntity(userURI,
				new PeticionChangeEmailREST("usuarioJuan","juan@hotmail.com"), String.class);
		assertThat(response.getBody(), equalTo(correctChange));
	}

	@Test
	public void emailChangeCorrectXML() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changeEmail";
		String correctChange = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
				+ "<ChangeInfoResponse>"
				+ "<agent>avisos@race.es</agent>"
				+ "<message>email actualizado correctamente</message>"
				+ "</ChangeInfoResponse>";

		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
		interceptors.add(new AcceptInterceptor());

		template.setInterceptors(interceptors);

		response = template.postForEntity(userURI,
				new PeticionChangeEmailREST("usuarioRace","avisos@race.es"), String.class);
		assertThat(response.getBody(), equalTo(correctChange));
	}
	
	@Test
	public void t10emailRequiredChange() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changeEmail";
		String emptyEmail = "{\"reason\": \"The userName is required\"}";
 
		response = template.postForEntity(userURI, new PeticionChangeEmailREST("","kgchg"), String.class);
		assertThat(response.getBody(), equalTo(emptyEmail));

		response = template.postForEntity(userURI, new PeticionChangeEmailREST("","vggv"), String.class);
		assertThat(response.getBody(), equalTo(emptyEmail));

		response = template.postForEntity(userURI, new PeticionChangeEmailREST("","vghjñp"), String.class);
		assertThat(response.getBody(), equalTo(emptyEmail));
	}
	
	@Test
	public void t13invalidEmailChange() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changeEmail";
		String wrongEmailStyle = "{\"reason\": \"Wrong mail style\"}";

		response = template.postForEntity(userURI, new PeticionChangeEmailREST("usuarioRace","paco"), String.class);
		assertThat(response.getBody(), equalTo(wrongEmailStyle));

		response = template.postForEntity(userURI, new PeticionChangeEmailREST("usuarioA6-PK27","paco@"), String.class);
		assertThat(response.getBody(), equalTo(wrongEmailStyle));

		response = template.postForEntity(userURI, new PeticionChangeEmailREST("usuarioJuan","paco.hotmail"),
				String.class);
		assertThat(response.getBody(), equalTo(wrongEmailStyle));
	}
		
	@Test
	public void t15emailChangeUserNotFound() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changeEmail";
		String userNotFound = "{\"reason\": \"User not found\"}";

		response = template.postForEntity(userURI,
				new PeticionChangeEmailREST("usuarioJuanito","juan@hotmail.com"), String.class);
		assertThat(response.getBody(), equalTo(userNotFound));

		response = template.postForEntity(userURI,
				new PeticionChangeEmailREST("user","pepe@gmail.com"), String.class);
		assertThat(response.getBody(), equalTo(userNotFound));

		response = template.postForEntity(userURI,
				new PeticionChangeEmailREST("usarioInexistente","pa@hotmail.com"), String.class);
		assertThat(response.getBody(), equalTo(userNotFound));
	}
	
	/*@Test No se porque no va este
	public void t16sameEmailErrorChange() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changeEmail";
		String sameEmail = "{\"reason\": \"Same email\"}";

		response = template.postForEntity(userURI,
				new PeticionChangeEmailREST("usuarioJuan","juan@uniovi.es"), String.class);
		assertThat(response.getBody(), equalTo(sameEmail));

		response = template.postForEntity(userURI, new PeticionChangeEmailREST("usuarioRace","avisos@race.es"),
				String.class);
		assertThat(response.getBody(), equalTo(sameEmail));

		response = template.postForEntity(userURI,
				new PeticionChangeEmailREST("usuarioA6-PK27","tecnico@copinsa.es"), String.class);
		assertThat(response.getBody(), equalTo(sameEmail));
	}*/
	
	///CAMBIO DE PASSWORD tEST///
	@Test
	public void correctPasswordChange() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changePassword";
		String correctPassword = "{\"agent\":\"usuarioJuan\",\"message\":\"contraseña actualizada correctamente\"}";

		response = template.postForEntity(userURI,
				new PeticionChangePasswordREST("usuarioJuan", "password", "djfhr"), String.class);
		assertThat(response.getBody(), equalTo(correctPassword));
	}

	@Test
	public void correctPasswordChangeXML() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changePassword";
		String correctChange = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
				+ "<ChangeInfoResponse>"
				+"<agent>usuarioJuan</agent>"
				+ "<message>contraseÃ±a actualizada correctamente</message>"
				+ "</ChangeInfoResponse>";

		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
		interceptors.add(new AcceptInterceptor());

		template.setInterceptors(interceptors);

		response = template.postForEntity(userURI,
				new PeticionChangePasswordREST("usuarioJuan", "djfhr", "password"), String.class);
		assertThat(response.getBody(), equalTo(correctChange));
	}

	@Test
	public void t17URequiredPasswordChange() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changePassword";
		String emptyEmail = "{\"reason\": \"The userName is required\"}";

		response = template.postForEntity(userURI, new PeticionChangePasswordREST("", "", ""), String.class);
		assertThat(response.getBody(), equalTo(emptyEmail));

		response = template.postForEntity(userURI, new PeticionChangePasswordREST("	", "chsh", ""), String.class);
		assertThat(response.getBody(), equalTo(emptyEmail));

		response = template.postForEntity(userURI, new PeticionChangePasswordREST("", "dfhe", "dhdgx"), String.class);
		assertThat(response.getBody(), equalTo(emptyEmail));
	}

	@Test
	public void t19passwordRequiredPasswordChange() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changePassword";
		String passwordRequired = "{\"reason\": \"The password is required\"}";

		response = template.postForEntity(userURI, new PeticionChangePasswordREST("usuarioJuan", "", ""),
				String.class);
		assertThat(response.getBody(), equalTo(passwordRequired));

		response = template.postForEntity(userURI, new PeticionChangePasswordREST("usuarioRace", "", "dkdddd"),
				String.class);
		assertThat(response.getBody(), equalTo(passwordRequired));

		response = template.postForEntity(userURI, new PeticionChangePasswordREST("usuarioA6-PK27", "", "dkejd"),
				String.class);
		assertThat(response.getBody(), equalTo(passwordRequired));
	}
	
	@Test
	public void t20newPasswordRequiredPasswordChange() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changePassword";
		String passwordRequired = "{\"reason\": \"The password is required\"}";

		response = template.postForEntity(userURI, new PeticionChangePasswordREST("usuarioJuan", "djfhr", ""),
				String.class);
		assertThat(response.getBody(), equalTo(passwordRequired));

		response = template.postForEntity(userURI, new PeticionChangePasswordREST("usuarioJuan", "djvhrhc", ""),
				String.class);
		assertThat(response.getBody(), equalTo(passwordRequired));

		response = template.postForEntity(userURI, new PeticionChangePasswordREST("usuarioJuan", "dkejd", ""),
				String.class);
		assertThat(response.getBody(), equalTo(passwordRequired));
	}
	
	@Test
	public void t21samePasswordChange() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changePassword";
		String passwordRequired = "{\"reason\": \"Password Incorrect\"}";

		response = template.postForEntity(userURI, new PeticionChangePasswordREST("usuarioJuan", "djfhr", "djfhr"),
				String.class);
		assertThat(response.getBody(), equalTo(passwordRequired));

		response = template.postForEntity(userURI,
				new PeticionChangePasswordREST("usuarioJuan", "djvhrhc", "djvhrhc"), String.class);
		assertThat(response.getBody(), equalTo(passwordRequired));

		response = template.postForEntity(userURI, new PeticionChangePasswordREST("usuarioJuan", "dkejd", "dkejd"),
				String.class);
		assertThat(response.getBody(), equalTo(passwordRequired));
	}
	
	@Test
	public void t22notFoundAgentPasswordChange() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changePassword";
		String userNotFound = "{\"reason\": \"User not found\"}";

		response = template.postForEntity(userURI,
				new PeticionChangePasswordREST("UsuarioJuanito", "djfhr", "djfhrtt"), String.class);
		assertThat(response.getBody(), equalTo(userNotFound));

		response = template.postForEntity(userURI,
				new PeticionChangePasswordREST("user", "djvhrhc", "tt"), String.class);
		assertThat(response.getBody(), equalTo(userNotFound));

		response = template.postForEntity(userURI, new PeticionChangePasswordREST("UsuarioRacee", "dkejd", "tt"),
				String.class);
		assertThat(response.getBody(), equalTo(userNotFound));
	}
	
	@Test
	public void t23notSamePasswordChange() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changePassword";
		String passwordIncorrect = "{\"reason\": \"Password Incorrect\"}";

		response = template.postForEntity(userURI, new PeticionChangePasswordREST("usuarioA6-PK27", "djfhr", "djfhr"),
				String.class);
		assertThat(response.getBody(), equalTo(passwordIncorrect));

		response = template.postForEntity(userURI,
				new PeticionChangePasswordREST("usuarioRace", "djvhrhc", "djvhrhc"), String.class);
		assertThat(response.getBody(), equalTo(passwordIncorrect));

		response = template.postForEntity(userURI, new PeticionChangePasswordREST("usuarioJuan", "dkejd", "dkejd"),
				String.class);
		assertThat(response.getBody(), equalTo(passwordIncorrect));
	}
	



}

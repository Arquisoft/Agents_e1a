package asw;

import java.text.ParseException;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import asw.dbmanagement.model.Agent;
import asw.dbmanagement.repository.AgentRepository;

@EnableAutoConfiguration
@ComponentScan
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner initDB(AgentRepository repository) throws ParseException {

		return (args) -> {
			repository.save(new Agent("Juan", "1.0,0.2", "juan@uniovi.es", "password", "Person", "usuarioJuan"));
			repository.save(new Agent("RACE", "1.123,-2.123", "avisos@race.es", "password", "Entity", "usuarioRace"));
			repository.save(new Agent("SensorTemperatura-A6-PK27", "23.231,123.2", "tecnico@copinsa.es", "password",
					"Sensor", "usuarioA6-PK27"));
		};
	}
}
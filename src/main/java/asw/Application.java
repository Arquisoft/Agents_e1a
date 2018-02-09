package asw;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import asw.agents.util.Location;
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
			Agent agent = repository.save(new Agent("nombre", /* new Location(1, 2) */"1,2", "nombre@uniovi.es",
					"password", "Person", "usuario"));
			System.out.println("Se han introducido el agente: " + agent + "\n\n");
			System.out.println("agentes en la base de datos: ");
			repository.findAll().stream().forEach(a -> System.out.println(a));
		};
	}
}
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
import asw.dbManagement.model.Agent;
import asw.dbManagement.repository.AgentRepository;

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
			// Inserción en la base de datos
			//repository.save(new Agent("", null, "a@s.es", "pass", (byte) 1, "ident"));

		};
	}
}
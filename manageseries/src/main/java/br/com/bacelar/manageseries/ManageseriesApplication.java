package br.com.bacelar.manageseries;

import br.com.bacelar.manageseries.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ManageseriesApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ManageseriesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.exibeMenu();

	}
}

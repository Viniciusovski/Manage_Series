package br.com.bacelar.manageseries;

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
		System.out.println("Primeiro projeto Spring sem Web");
	}
}

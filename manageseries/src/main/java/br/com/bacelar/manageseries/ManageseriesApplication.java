package br.com.bacelar.manageseries;

import br.com.bacelar.manageseries.model.DadosSerie;
import br.com.bacelar.manageseries.service.ConsumoAPI;
import br.com.bacelar.manageseries.service.ConverteDados;
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
		var consumoApi = new ConsumoAPI();
		var json = consumoApi.obterDados("https://www.omdbapi.com/?t=gilmore+girls&apikey=6585022c");
		System.out.println(json);

		ConverteDados conversor = new ConverteDados();
		DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
		System.out.println(dados);

	}
}

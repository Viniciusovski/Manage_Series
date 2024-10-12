package br.com.bacelar.manageseries;

import br.com.bacelar.manageseries.model.DadosEpisodio;
import br.com.bacelar.manageseries.model.DadosSerie;
import br.com.bacelar.manageseries.model.DadosTemporada;
import br.com.bacelar.manageseries.service.ConsumoAPI;
import br.com.bacelar.manageseries.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

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

		json = consumoApi.obterDados("https://omdbapi.com/?t=gilmore+girls&season=1&episode=2&apikey=6585022c");
		DadosEpisodio dadosEpisodio = conversor.obterDados(json, DadosEpisodio.class);
		System.out.println(dadosEpisodio);

		List<DadosTemporada> temporadas = new ArrayList<>();

		for(int i = 1; i<=dados.totalTemporadas(); i++){
			json = consumoApi.obterDados("https://omdbapi.com/?t=gilmore+girls&season="+ i +"&apikey=6585022c");
			DadosTemporada dadosTemporada = conversor.obterDados(json,DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}

		// For each implementando system out
		temporadas.forEach(System.out::println);

	}
}

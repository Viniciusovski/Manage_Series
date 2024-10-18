package br.com.bacelar.manageseries.principal;

import br.com.bacelar.manageseries.model.DadosEpisodio;
import br.com.bacelar.manageseries.model.DadosSerie;
import br.com.bacelar.manageseries.model.DadosTemporada;
import br.com.bacelar.manageseries.model.Episodio;
import br.com.bacelar.manageseries.service.ConsumoAPI;
import br.com.bacelar.manageseries.service.ConverteDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://omdbapi.com/?t=";
    private final String API_KEY = "&apikey=6585022c";

    public void exibeMenu(){
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = leitura.nextLine();
        var json = consumoApi.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);

		List<DadosTemporada> temporadas = new ArrayList<>();

		for(int i = 1; i<=dados.totalTemporadas(); i++){
			json = consumoApi.obterDados(ENDERECO + nomeSerie.replace(" ", "+")+ "&season=" + i + API_KEY);
			DadosTemporada dadosTemporada = conversor.obterDados(json,DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}
        // For each implementando system out
	    // temporadas.forEach(System.out::println);

        // Percorre para recuperar apenas o nome dos episodios
        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

//        List<String> nomes = Arrays.asList("Vinícius", "Leonardo", "Galadriel", "Lúcio", "Rihanna");
//
//        nomes.stream()
//                .sorted()
//                .limit(3)
//                .filter(n -> n.startsWith("L"))
//                .map(n -> n.toUpperCase())
//                .forEach(System.out::println);

        // FlatMap é usado para "achatar" uma estrutura de dados complexa em uma estrutura mais simples
        // Pega cada um dos episódios e aglutiná-os na mesma lista
        List<DadosEpisodio> dadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());

        //Ordenar por avaliação de forma decrescente os top 5 melhores epsisodios
        System.out.println("\nTop 5 episódios");
        dadosEpisodios.stream()
                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
                .limit(5)
                .forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numero(), d))
                ).collect(Collectors.toList());

        episodios.forEach(System.out::println);

        System.out.println("\nDigite o nome ou trecho do titulo do episódio");
        var trechoTitulo = leitura.nextLine();
        // Optional é usado para evitar erro de nulo quando buscar um objeto. Evita referencia nula
        Optional<Episodio> episodioOptional = episodios.stream()
                .filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))
                .findFirst();
        if(episodioOptional.isPresent()){
            System.out.println("Episódio encontrado!");
            System.out.println("Temporada: " +episodioOptional.get().getTemporada());
        }else{
            System.out.println("Episódio não encontrado :(");
        }

//        System.out.println("\nA partir de que ano você deseja ver os episódios? ");
//        var ano = leitura.nextInt();
//        leitura.nextLine();
//
//        LocalDate dataBusca = LocalDate.of(ano, 1, 1);
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        episodios.stream()
//                .filter(e -> e != null && e.getDataLancamento().isAfter(dataBusca))
//                .forEach(e -> System.out.println(
//                        "Temporada: " + e.getTemporada() +
//                                "\nEpisódio: " + e.getTitulo() +
//                                "\nData de Lançamento: " + e.getDataLancamento().format(formatter) +
//                                "\n*************************************************"
//                ));

        // Agrupamento de dados pela média das avaliações do episodios por temporada
        Map<Integer, Double> avaliacoesPorTemporada = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada, Collectors.averagingDouble(Episodio::getAvaliacao)));

        System.out.println(avaliacoesPorTemporada);

        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));

        System.out.println("Média: "+est.getAverage());
        System.out.println("Melhor episódio: "+est.getMax());
        System.out.println("Pior episódio: "+est.getMin());
        System.out.println("Quantidade de episódios: "+est.getCount());
    }
}

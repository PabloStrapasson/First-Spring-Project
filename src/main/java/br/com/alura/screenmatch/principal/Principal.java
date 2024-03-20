package br.com.alura.screenmatch.principal;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import br.com.alura.screenmatch.models.DadoEpisodio;
import br.com.alura.screenmatch.models.DadoSerie;
import br.com.alura.screenmatch.models.DadoTemporada;
import br.com.alura.screenmatch.models.Episodio;
import br.com.alura.screenmatch.service.ConsumoAPI;
import br.com.alura.screenmatch.service.ConverteDados;

public class Principal {

    private Scanner scanner = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();
    private final String URL = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=ab295c72";
    
    public void exibeMenu(){
        System.out.println("Digite o nome da Série");
        var nome_serie = scanner.nextLine();
        var json = consumoAPI.obterDados(URL + nome_serie.replace(" ", "+") + API_KEY);

		DadoSerie dados = conversor.obterDados(json, DadoSerie.class);

        List<DadoTemporada> temporadas = new ArrayList<>();
		for(int i = 1; i <= dados.total_season(); i++){
			json = consumoAPI.obterDados(URL + nome_serie.replace(" ", "+") + "&season=" + i + API_KEY);
			DadoTemporada dado_temporada =conversor.obterDados(json, DadoTemporada.class);
			temporadas.add(dado_temporada);
		}
		//temporadas.forEach(System.out::println);
    
        //"https://www.omdbapi.com/?t=gilmore+girls&apikey=ab295c72"

        //lista todos os titulos dos episódios em todas as temporadas
        //temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

        List<DadoEpisodio> listaEpisodios = temporadas.stream()
                                                        .flatMap(t -> t.episodios().stream())
                                                        .collect(Collectors.toList());

        // imprime os top 5 episódios de uma série
        /*System.out.println("Top 5 Episódios");
        listaEpisodios.stream()
                        .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                        .peek(e -> System.out.println("Primeiro filtro(N/A) " + e))
                        .sorted(Comparator.comparing(DadoEpisodio::avaliacao).reversed())
                        .peek(e -> System.out.println("Ordenação " + e))
                        .limit(5)
                        .peek(e -> System.out.println("Limite " + e))
                        .forEach(System.out::println);
        */
        // instancia os episódios com base nos dados da api
        List<Episodio> episodios = temporadas.stream()
                                             .flatMap(t -> t.episodios().stream()
                                                                        .map(ep -> new Episodio(t.numero(), ep)))
                                             .collect(Collectors.toList());
        
        episodios.forEach(System.out::println);
        /*
        System.out.println("Digite o titulo do episódio");
        var trechoTitulo = scanner.nextLine();
        Optional<Episodio> episodioBuscado = episodios.stream().filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase())).findFirst();

        if(episodioBuscado.isPresent()){
            System.out.println("Episódio Encontrado:\n"+ episodioBuscado.get());
        } else {
            System.out.println("Episódio não encontrado :(");
        }
        */

        // Filtagrem por ano de lançamento
        /*System.out.println("A partir de que ano você deseja ver os episódios?");
        var ano = scanner.nextInt();
        scanner.nextLine();

        LocalDate dataBusca = LocalDate.of(ano, 1, 1);
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        episodios.stream()
                    .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
                    .forEach(e-> System.out.println(
                        "Temporada: " + e.getTemporada()+
                        "Episódio: " + e.getTitulo()+
                        "Data Lançamento: " + e.getDataLancamento().format(formatador)
                    ));
        */

        // Calcula a avaliação das temporadas
        Map<Integer, Double> avaliacoesPorTemporada = episodios.stream()
            .filter(e -> e.getAvaliacao() > 0.0)
            .collect(Collectors.groupingBy(Episodio::getTemporada,
                Collectors.averagingDouble(Episodio::getAvaliacao)));
        System.out.println(avaliacoesPorTemporada);

        // Coleta algumas outras estatisticas
        DoubleSummaryStatistics est = episodios.stream()
            .filter(e -> e.getAvaliacao() > 0.0)
            .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));
        System.out.println("Média: " + est.getAverage());
        System.out.println("Melhor episódio: " + est.getMax());
        System.out.println("Pior episódio: " + est.getMin());
        System.out.println("Quantidade: " + est.getCount());

    }
}

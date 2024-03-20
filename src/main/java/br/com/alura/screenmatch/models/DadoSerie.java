package br.com.alura.screenmatch.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadoSerie(@JsonAlias("Title") String title, 
                        @JsonAlias("totalSeasons") Integer total_season, 
                        @JsonAlias("imdbRating") String avaliacao) {

                            
}
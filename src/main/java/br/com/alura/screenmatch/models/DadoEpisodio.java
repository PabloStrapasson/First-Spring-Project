package br.com.alura.screenmatch.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown =  true)
public record DadoEpisodio(@JsonAlias("Title")String titulo, 
                           @JsonAlias("Episode")Integer n_ep, 
                           @JsonAlias("imdbRating")String avaliacao, 
                           @JsonAlias("Released")String data_lancamento) {
    
}

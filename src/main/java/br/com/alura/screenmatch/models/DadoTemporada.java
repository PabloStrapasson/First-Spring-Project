package br.com.alura.screenmatch.models;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown =  true)
public record DadoTemporada(@JsonAlias("Season")Integer numero, 
                            @JsonAlias("Episodes")List<DadoEpisodio> episodios) {
    
}

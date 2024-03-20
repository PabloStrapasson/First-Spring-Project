package br.com.alura.screenmatch.models;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episodio {
    
    private Integer temporada;
    private String titulo;
    private Integer numeroEP;
    private Double avaliacao;
    private LocalDate dataLancamento;

    public Episodio(Integer nTemp, DadoEpisodio ep){
        this.temporada = nTemp;
        this.titulo = ep.titulo();
        this.numeroEP = ep.n_ep();

        try {
            this.avaliacao = Double.valueOf(ep.avaliacao());
        } catch(NumberFormatException e) {
            this.avaliacao = 0.0;
        }
        try {
            this.dataLancamento = LocalDate.parse(ep.data_lancamento());
        } catch(DateTimeParseException e){
            this.dataLancamento = null;
        }
    }

    // getters e setters
    public Integer getTemporada() {
        return temporada;
    }
    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public Integer getNumeroEP() {
        return numeroEP;
    }
    public void setNumeroEP(Integer numeroEP) {
        this.numeroEP = numeroEP;
    }
    public Double getAvaliacao() {
        return avaliacao;
    }
    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }
    public LocalDate getDataLancamento() {
        return dataLancamento;
    }
    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    @Override
    public String toString(){
        return "Episodio: " + titulo + "\n" +
                "EP: " + numeroEP + "\n" +
                "Temporada: " + temporada + "\n" +
                "Avaliação: " + avaliacao + "\n" +
                "Data de Lançamento: " + dataLancamento;
    }
    
}

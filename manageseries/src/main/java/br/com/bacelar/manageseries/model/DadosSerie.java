package br.com.bacelar.manageseries.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// Anotação que ignora propriedades que não foram chamadas
@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosSerie(
        @JsonAlias("Title") String titulo,
        @JsonAlias("totalSeasons") Integer totalTemporadas,
        @JsonAlias("imdbRating") String avaliacao
) {
}

//@JsonAlias e @JsonProperty são anotações em Jackson, uma biblioteca Java para processar JSON,
//que ajudam a mapear propriedades de classe para campos JSON.

//@JsonProperty
//Essa anotação é usada para definir o nome da propriedade JSON que está associada ao campo Java.

//@JsonAlias
//O @JsonAlias é usado para definir um ou mais apelidos para o nome da propriedade JSON associada ao campo Java.



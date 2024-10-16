package br.com.bacelar.manageseries.service;

public interface IConverteDados {

    // Generics
    <T> T obterDados(String json, Class<T> classe);

}

//    Em Java, generics permitem criar classes, interfaces e métodos que podem trabalhar com tipos
//    desconhecidos ou parâmetros genéricos. Eles fornecem uma forma de escrever código flexível e reutilizável,
//    tornando-o independente de tipos específicos e permitindo que ele funcione com diferentes tipos de dados.

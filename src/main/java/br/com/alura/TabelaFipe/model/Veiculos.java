package br.com.alura.TabelaFipe.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Veiculos(
        @JsonAlias("Valor") String valor,
        @JsonAlias("Marca") String marca,
        @JsonAlias("Modelo") String modelo,
        @JsonAlias("AnoModelo") Integer anoModelo,
        @JsonAlias("Combustivel") String combustivel) {
    @Override
    public String toString() {
        return "\nVeículo: " + modelo +
                "\nMarca: " + marca +
                "\nAno: " + anoModelo +
                "\nValor FIPE: " + valor +
                "\nCombustivel: " + combustivel;
    }
}

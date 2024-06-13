package br.com.alura.TabelaFipe.model;

public record Generics(String codigo, String nome) {

    @Override
    public String toString() {
        return "\nCódigo: " + codigo +
                " | Nome: " + nome;
    }
}

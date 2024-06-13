package br.com.alura.TabelaFipe.service;

import java.util.List;

public interface IConverteDados {
    <T> T recebeDados (String arqJson, Class<T> escolhaClasse);

    <T> List<T> recebeLista (String arqJson, Class<T> escolhaClasse);
}

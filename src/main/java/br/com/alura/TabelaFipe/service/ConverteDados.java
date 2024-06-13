package br.com.alura.TabelaFipe.service;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

public class ConverteDados implements IConverteDados{
    private ObjectMapper conversorDados = new ObjectMapper();

    @Override
    public <T> T recebeDados(String arqJson, Class<T> escolhaClasse) {
        try {
            return conversorDados.readValue(arqJson, escolhaClasse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> List<T> recebeLista(String arqJson, Class<T> escolhaClasse) {
        CollectionType listagem = conversorDados.getTypeFactory()
                .constructCollectionType(List.class, escolhaClasse);
        try {
            return conversorDados.readValue(arqJson, listagem);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

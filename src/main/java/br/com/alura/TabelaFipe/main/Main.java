package br.com.alura.TabelaFipe.main;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import br.com.alura.TabelaFipe.model.Generics;
import br.com.alura.TabelaFipe.model.Modelos;
import br.com.alura.TabelaFipe.model.Veiculos;
import br.com.alura.TabelaFipe.service.ConsomeAPI;
import br.com.alura.TabelaFipe.service.ConverteDados;

public class Main {

    private ConsomeAPI consomeAPI = new ConsomeAPI();
    private ConverteDados converteDados = new ConverteDados();

    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1";
    Scanner leitor = new Scanner(System.in);

    public void initializr() {
        var exibirMenu = """
                
                  OPÇÕES
                
                - Carros
                - Motos
                - Caminhões
                
                Informe a opção de busca:
                """;
        System.out.println(exibirMenu);
        var recebeMenu = leitor.nextLine();

        String campoURL;
        if (recebeMenu.toLowerCase().contains("carros")) {
            campoURL = URL_BASE + "/carros/marcas";
        } else if (recebeMenu.toLowerCase().contains("motos")) {
            campoURL = URL_BASE + "/motos/marcas";
        } else {
            campoURL = URL_BASE + "/caminhoes/marcas";
        }

        var consumoDados = consomeAPI.consomeDados(campoURL);
        var exibeMarcas = converteDados.recebeLista(consumoDados, Generics.class);
        System.out.println("\nMarcas encontradas no sistema");
        exibeMarcas.stream()
                .sorted(Comparator.comparing(Generics::codigo))
                .forEach(System.out::println);

        System.out.println("\nInsira o código para mais informações da marca");
        var recebeMarca = leitor.nextLine();

        campoURL = campoURL + "/" + recebeMarca + "/modelos";
        consumoDados = consomeAPI.consomeDados(campoURL);
        var exibeModelos = converteDados.recebeDados(consumoDados, Modelos.class);
        System.out.println("\nModelos encontrados");
        exibeModelos.modelos().stream()
                .sorted(Comparator.comparing(Generics::codigo))
                .forEach(System.out::println);

        System.out.println("\nInsira o modelo referente a marca, para realizar a busca");
        var recebeModelo = leitor.nextLine();

        List<Generics> modelosFiltrados = exibeModelos.modelos().stream()
                .filter(m -> m.nome().toLowerCase().contains(recebeModelo.toLowerCase()))
                .collect(Collectors.toList());

        System.out.println("\nModelos encontrados na busca: ");
        modelosFiltrados.forEach(System.out::println);

        System.out.println("\nInforme o código de um modelo, para comparação de valores por ano");
        var recebeCodigo = leitor.nextLine();

        campoURL = campoURL + "/" + recebeCodigo + "/anos";
        consumoDados = consomeAPI.consomeDados(campoURL);
        List<Generics> modelosAnos = converteDados.recebeLista(consumoDados, Generics.class);

        List<Veiculos> modelosVeiculos = new ArrayList<>();

        for (int i = 0; i < modelosAnos.size(); i++) {
            var campoURLFinal = campoURL + "/" + modelosAnos.get(i).codigo();
            consumoDados = consomeAPI.consomeDados(campoURLFinal);
            Veiculos veiculos = converteDados.recebeDados(consumoDados, Veiculos.class);
            modelosVeiculos.add(veiculos);
        }

        System.out.println("\nVeiculos encontrados");
        modelosVeiculos.stream()
                .sorted(Comparator.comparing(Veiculos::anoModelo))
                .forEach(System.out::println);
        System.out.println("\nFinalizado!");
    }
}

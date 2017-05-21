package controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.RelacaoCsvBib;

/**
 *
 * @author Jônatas Trabuco Belotti [jonatas.t.belotti@hotmail.com]
 */
public class Conversor {

    private List<String> identificadoresOriginais;
    private List<RelacaoCsvBib> identificadoresRelacionados;
    private int numero;

    public Conversor() {
        this.identificadoresOriginais = new ArrayList<>();
        this.identificadoresRelacionados = new ArrayList<>();
        this.numero = 0;
    }

    public List<String> getIdentificadoresOriginais() {
        return identificadoresOriginais;
    }

    public void setIdentificadoresOriginais(List<String> identificadoresOriginais) {
        this.identificadoresOriginais = identificadoresOriginais;
    }

    public List<RelacaoCsvBib> getIdentificadoresRelacionados() {
        return identificadoresRelacionados;
    }

    public void setIdentificadoresRelacionados(List<RelacaoCsvBib> identificadoresRelacionados) {
        this.identificadoresRelacionados = identificadoresRelacionados;
    }

    public List<String> identificarNomes(String caminho, List<String> nomes) throws IOException {
        String linha;

        for (String nome : nomes) {
            try {
                File file = new File(caminho + nome);
                Scanner inputStream = new Scanner(file);

                linha = inputStream.nextLine();

                for (String nomeCSV : linha.split(",")) {
                    identificadoresOriginais.add(nomeCSV.toUpperCase());
                }
            } catch (FileNotFoundException ex) {
                System.out.println("Erro: " + ex.getMessage());
            }
        }

        return this.identificadoresOriginais;
    }

    public boolean converterCSVParaBib(String caminhoAbrirArquivos, List<String> nomesAbrirArquivos, String caminhoSalvarArquivo, String nomeSalvarArquivo) {
        String linha;
        HashMap<String, String> resultado;
        int i;

        for (String nome : nomesAbrirArquivos) {
            try {
                File file = new File(caminhoAbrirArquivos + nome);
                Scanner inputStream = new Scanner(file);

                linha = inputStream.nextLine();
                while (inputStream.hasNextLine()) {
                    resultado = new HashMap<>();
                    linha = inputStream.nextLine();
                    i = 0;

                    for (String s : linha.split("\",")) {
                        s = s.replaceAll("\"", "");

                        if (!s.equals("")) {
                            resultado.put(this.identificadoresRelacionados.get(i).getNomeBib().toUpperCase(), s);
                        }

                        i++;
                    }

                    imprimirResultado(resultado, caminhoSalvarArquivo + nomeSalvarArquivo);
                }
            } catch (FileNotFoundException ex) {
                System.out.println("Erro: " + ex.getMessage());
            }
        }

        return true;
    }

    private void imprimirResultado(HashMap<String, String> dados, String nomeArquivo) {
        String tipo = "article", codigo;
        String referencia = "";
        this.numero++;

        if (dados.containsKey("TYPE")) {
            tipo = dados.get("TYPE");
        }

        referencia = "@" + tipo.toLowerCase() + "{REFERENCIA" + this.numero + ",\n";

        for (Map.Entry<String, String> campo : dados.entrySet()) {
            if (!campo.getKey().toUpperCase().equals("TYPE")) {
                referencia += "    " + campo.getKey().toUpperCase() + " = {" + campo.getValue() + "},\n";
            }
        }

        referencia += "}";

        PrintWriter arquivo;
        try {
            arquivo = new PrintWriter(new BufferedWriter(new FileWriter(nomeArquivo, true)));
            arquivo.println(referencia);
            arquivo.close();
        } catch (IOException ex) {
            Logger.getLogger(Conversor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

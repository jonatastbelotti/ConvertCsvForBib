package model;

/**
 *
 * @author Jônatas Trabuco Belotti [jonatas.t.belotti@hotmail.com]
 */
public class RelacaoCsvBib {
    private String nomeCSV;
    private String nomeBib;

    public RelacaoCsvBib() {
    }

    public RelacaoCsvBib(String nomeCSV, String nomeBib) {
        this.nomeCSV = nomeCSV;
        this.nomeBib = nomeBib;
    }

    public String getNomeCSV() {
        return nomeCSV;
    }

    public void setNomeCSV(String nomeCSV) {
        this.nomeCSV = nomeCSV;
    }

    public String getNomeBib() {
        return nomeBib;
    }

    public void setNomeBib(String nomeBib) {
        this.nomeBib = nomeBib;
    }
}

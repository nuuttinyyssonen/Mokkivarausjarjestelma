package com.example.ohjelmistotuotanto;

public class Mokki {
    private int alue_id;
    private String postinro;
    private String mokkinimi;
    private String katuosoite;
    private double hinta;
    private String kuvaus;
    private int henkilomaara;
    private String varustelu;

    public Mokki(int alue_id, String postinro, String mokkinimi, String katuosoite,
                 double hinta, String kuvaus, int henkilomaara, String varustelu) {
        this.alue_id = alue_id;
        this.postinro = postinro;
        this.mokkinimi = mokkinimi;
        this.katuosoite = katuosoite;
        this.hinta = hinta;
        this.kuvaus = kuvaus;
        this.henkilomaara = henkilomaara;
        this.varustelu = varustelu;
    }

    public String getPostinro() {
        return postinro;
    }

    public String getMokkinimi() {
        return mokkinimi;
    }

    public void setMokkinimi(String mokkinimi) {
        this.mokkinimi = mokkinimi;
    }

    public String getKatuosoite() {
        return katuosoite;
    }

    public double getHinta() {
        return hinta;
    }

    public void setHinta(double hinta) {
        this.hinta = hinta;
    }

    public String getKuvaus() {
        return kuvaus;
    }

    public void setKuvaus(String kuvaus) {
        this.kuvaus = kuvaus;
    }

    public int getHenkilomaara() {
        return henkilomaara;
    }

    public void setHenkilomaara(int henkilomaara) {
        this.henkilomaara = henkilomaara;
    }

    public String getVarustelu() {
        return varustelu;
    }

    public void setVarustelu(String varustelu) {
        this.varustelu = varustelu;
    }

    public String toString() {
        return "Mokki{" +
                "alue_id=" + alue_id +
                ", postinro=" + postinro +
                ", mokkinimi='" + mokkinimi + '\'' +
                ", katuosoite='" + katuosoite + '\'' +
                ", hinta=" + hinta +
                ", kuvaus='" + kuvaus + '\'' +
                ", henkilomaara=" + henkilomaara +
                ", varustelu='" + varustelu + '\'' +
                '}';
    }

    public static void main(String[] args) {
        Mokki mokki = new Mokki(1, "12345", "Kesämökki", "Mökkitie 123", 150.00,
                "Viihtyisä mökki järven rannalla", 4, "Sauna, vene");
        System.out.println(mokki);
    }
}

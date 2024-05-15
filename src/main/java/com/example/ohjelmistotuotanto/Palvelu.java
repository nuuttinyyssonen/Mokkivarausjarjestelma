package com.example.ohjelmistotuotanto;

public class Palvelu {
    private int id;
    private int alue_id;
    private String nimi;
    private String kuvaus;
    private double hinta;
    private double alv;

    public Palvelu(int id, int alue_id, String nimi, String kuvaus, double hinta) {
        this.alue_id = alue_id;
        this.nimi = nimi;
        this.kuvaus = kuvaus;
        this.hinta = hinta;
        this.alv = hinta * 0.10;
        this.id = id;
    }

    public String getNimi() {
        return this.nimi;
    }

    public String getKuvaus() {
        return this.kuvaus;
    }

    public double getHinta() {
        return this.hinta;
    }

    public double getAlv() {
        return this.alv;
    }
    public int getId() {
        return this.id;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public void setKuvaus(String kuvaus) {
        this.kuvaus = kuvaus;
    }

    public void setHinta(double hinta) {
        this.hinta = hinta;
    }

    public String toString() {
        return "Palvelun nimi: "+this.nimi + "\n" + "Palvelun hinta: "+ this.getHinta() + "\n" + "Palvelun kuvaus: " + this.getKuvaus();
    }


    public int getAlue_id() {

        return this.alue_id;
    }
}
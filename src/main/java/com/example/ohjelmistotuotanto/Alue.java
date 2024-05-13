package com.example.ohjelmistotuotanto;

public class Alue {
    private String nimi;
    private int alue_id;

    public Alue(String nimi, int alue_id) {
        this.nimi = nimi;
        this.alue_id = alue_id;
    }

    public String getNimi() {
        return this.nimi;
    }

    public int getAlue_id() {
        return this.alue_id;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }
}

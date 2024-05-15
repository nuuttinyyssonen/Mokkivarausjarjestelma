package com.example.ohjelmistotuotanto;

public class Mokki {
    private int alue_id;
    private  int mokki_id;
    private String postinro;
    private String mokkinimi;
    private String katuosoite;
    private double hinta;
    private String kuvaus;
    private int henkilomaara;
    private String varustelu;

    public Mokki( int mokki_id,int alue_id, String postinro, String mokkinimi, String katuosoite,
                 double hinta, String kuvaus, int henkilomaara, String varustelu) {
        this.alue_id = alue_id;
        this.mokki_id = mokki_id;
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

    public int getAlue_id() {
        return alue_id;
    }

    public void setAlue_id(int alue_id) {
        this.alue_id = alue_id;
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

    public int getMokki_id() {
        return mokki_id;
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
        String alueenNimi = DatabaseUtils.getAlueNameByAlueID(this.alue_id);
        return "Mökin nimi: " + mokkinimi + "\n" + "Alueen nimi: "+ alueenNimi + "\n" + "Hinta: " + this.getHinta() + "\n" + "Henkilömäärä: " + this.getHenkilomaara() + "\n" + "Varustelu: " + this.getVarustelu();
    }


}

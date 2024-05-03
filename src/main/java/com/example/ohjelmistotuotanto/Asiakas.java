package com.example.ohjelmistotuotanto;

public class Asiakas {
    private int postinro;
    private String etunimi;
    private String sukunimi;
    private String lahiosoite;
    private String email;
    private String puhelinnro;

    public Asiakas(int postinro, String etunimi, String sukunimi, String lahiosoite, String email, String puhelinnro) {
        this.postinro = postinro;
        this.etunimi = etunimi;
        this.sukunimi = sukunimi;
        this.lahiosoite = lahiosoite;
        this.email = email;
        this.puhelinnro = puhelinnro;
    }

    public int getPostinro() {
        return this.postinro;
    }

    public String getEtunimi() {
        return this.etunimi;
    }

    public String getSukunimi() {
        return this.sukunimi;
    }

    public String getLahiosoite() {
        return this.lahiosoite;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPuhelinnro() {
        return this.puhelinnro;
    }

    public void setPostinro(int postinro) {
        this.postinro = postinro;
    }

    public void setEtunimi(String etunimi) {
        this.etunimi = etunimi;
    }

    public void setSukunimi(String sukunimi) {
        this.sukunimi = sukunimi;
    }

    public void setLahiosoite(String lahiosoite) {
        this.lahiosoite = lahiosoite;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPuhelinnro(String puhelinnro) {
        this.puhelinnro = puhelinnro;
    }

    public String toString() {
        return "Asiakas{" +
                ", postinro=" + postinro +
                ", etunimi='" + etunimi + '\'' +
                ", sukunimi='" + sukunimi + '\'' +
                ", lahiosoite='" + lahiosoite + '\'' +
                ", email='" + email + '\'' +
                ", puhelinnro='" + puhelinnro + '\'' +
                '}';
    }

    public static void main(String[] args) {
        Asiakas testi = new Asiakas(70870, "Nuutti", "Nyyssönen", "Keilanrinteenkatu 8 A 34", "nuutti.nyyssonen@gmail.com", "+358453577988");
        System.out.println(testi.getPuhelinnro());
        testi.setPuhelinnro("0453577988");
        System.out.println(testi.toString());
    }
}

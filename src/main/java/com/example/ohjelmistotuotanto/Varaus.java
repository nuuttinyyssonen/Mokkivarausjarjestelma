package com.example.ohjelmistotuotanto;

import java.time.LocalDate;
import java.util.Date;

public class Varaus {
    private int asiakas_id;
    private int varaus_id;
    private int mokki_id;
    private LocalDate varattu_pvm;
    private LocalDate vahvistus_pvm;
    private LocalDate varattu_alkupvm;
    private LocalDate varattu_loppupvm;

    public Varaus(int asiakas_id, int mokki_id, LocalDate varattu_alkupvmu, LocalDate varattu_loppupvmu, LocalDate varattu_pvm, LocalDate vahvistus_pvm, int varaus_id) {
        this.asiakas_id = asiakas_id;
        this.mokki_id = mokki_id;
        this.varaus_id = varaus_id;
        this.varattu_pvm = varattu_pvm;
        this.vahvistus_pvm = vahvistus_pvm;
        this.varattu_alkupvm = varattu_alkupvmu;
        this.varattu_loppupvm = varattu_loppupvmu;
    }

    public int getAsiakas_id() {
        return asiakas_id;
    }

    public int getMokki_id() {
        return mokki_id;
    }

    public LocalDate getVarattu_pvm() {
        return this.varattu_pvm;
    }

    public LocalDate getVahvistus_pvm() {
        return this.vahvistus_pvm;
    }

    public LocalDate getVarattu_alkupvm() {
        return this.varattu_alkupvm;
    }

    public LocalDate getVarattu_loppupvm() {
        return this.varattu_loppupvm;
    }
    public int getVaraus_id() {
        return this.varaus_id;
    }


    public void setVarattu_alkupvm(LocalDate varattu_alkupvm) {
        this.varattu_alkupvm = varattu_alkupvm;
    }

    public void setVarattu_loppupvm(LocalDate varattu_loppupvm) {
        this.varattu_loppupvm = varattu_loppupvm;
    }

    public String toString() {
        String mokinNimi = DatabaseUtils.getMokkiNameByMokkiID(this.mokki_id);
        return "Mökin nimi: " +mokinNimi + "\n " + "Asiakas ID: " + this.asiakas_id + "\n" + "Varattu: " + this.getVarattu_pvm() + "\n" + "Varauksen alkaminen: " + this.getVarattu_alkupvm() + "\n" + "Varauksen loppuminen: " + this.getVarattu_loppupvm();
    }
}


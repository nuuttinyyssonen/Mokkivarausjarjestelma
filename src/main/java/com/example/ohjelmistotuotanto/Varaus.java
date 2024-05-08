package com.example.ohjelmistotuotanto;

import java.util.Date;

public class Varaus {
    private int asiakas_id;
    private int mokki_id;
    private Date varattu_pvm;
    private Date vahvistus_pvm;
    private Date varattu_alkupvm;
    private Date varattu_loppupvm;

    public Varaus(int asiakas_id, int mokki_id, Date varattu_alkupvm, Date varattu_loppupvm, Date varattu_pvm, Date vahvistus_pvm) {
        this.asiakas_id = asiakas_id;
        this.mokki_id = mokki_id;
        this.varattu_pvm = varattu_pvm;
        this.vahvistus_pvm = vahvistus_pvm;
        this.varattu_alkupvm = varattu_alkupvm;
        this.varattu_loppupvm = varattu_loppupvm;
    }

    public int getAsiakas_id() {
        return asiakas_id;
    }

    public int getMokki_id() {
        return mokki_id;
    }

    public Date getVarattu_pvm() {
        return this.varattu_pvm;
    }

    public Date getVahvistus_pvm() {
        return this.vahvistus_pvm;
    }

    public Date getVarattu_alkupvm() {
        return this.varattu_alkupvm;
    }

    public Date getVarattu_loppupvm() {
        return this.varattu_loppupvm;
    }

    public void setVarattu_alkupvm(Date varattu_alkupvm) {
        this.varattu_alkupvm = varattu_alkupvm;
    }

    public void setVarattu_loppupvm(Date varattu_loppupvm) {
        this.varattu_loppupvm = varattu_loppupvm;
    }

    public String toString() {
        String mokinNimi = DatabaseUtils.getMokkiNameByMokkiID(this.mokki_id);
        return "Mökin nimi: "+mokinNimi + "\n Asiakas ID: "+ this.asiakas_id;
    }
}


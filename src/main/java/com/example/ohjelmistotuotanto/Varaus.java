package com.example.ohjelmistotuotanto;

import java.time.LocalDate;

public class Varaus {
    private int asiakas_id;
    private int mokki_id;
    private LocalDate varattu_pvm;
    private LocalDate vahvistus_pvm;
    private LocalDate varattu_alkupvm;
    private LocalDate varattu_loppupvm;

    public Varaus(int asiakas_id, int mokki_id, int alku_vuosi, int alku_kk, int alku_pv, int loppu_vuosi, int loppu_kk, int loppu_pv) {
        this.asiakas_id = asiakas_id;
        this.mokki_id = mokki_id;
        this.varattu_pvm = LocalDate.now();
        this.vahvistus_pvm = LocalDate.now();
        this.varattu_alkupvm = LocalDate.of(alku_vuosi, alku_kk, alku_pv);
        this.varattu_loppupvm = LocalDate.of(loppu_vuosi, loppu_kk, loppu_pv);
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

    public void setVarattu_alkupvm(LocalDate varattu_alkupvm) {
        this.varattu_alkupvm = varattu_alkupvm;
    }

    public void setVarattu_loppupvm(LocalDate varattu_loppupvm) {
        this.varattu_loppupvm = varattu_loppupvm;
    }
}

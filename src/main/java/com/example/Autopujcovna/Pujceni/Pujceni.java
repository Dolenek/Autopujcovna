package com.example.Autopujcovna.Pujceni;

import com.example.Autopujcovna.Vozidlo.Vozidlo;
import com.example.Autopujcovna.Zakaznik.Zakaznik;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table
public class Pujceni {

    @Id
    @SequenceGenerator(name = "pujceni_sequence",
            sequenceName = "pujceni_sequence",
            allocationSize = 1)

    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "pujceni_sequence"
    )

    private Long id;

    @ManyToOne
    @JoinColumn(name = "vozidlo_id")
    //@JsonIgnore
    private Vozidlo vozidlo; //VOZIDLO ID JENOM

    @ManyToOne
    @JoinColumn(name = "zakaznik_id")
    //@JsonIgnore
    private Zakaznik zakaznik;

    private LocalDateTime datumPujceni;
    private LocalDateTime datumVraceni;

    public Pujceni() {
    }

    public Pujceni(Vozidlo vozidlo, Zakaznik zakaznik, LocalDateTime datumPujceni, LocalDateTime datumVraceni) {
        this.vozidlo = vozidlo;
        this.zakaznik = zakaznik;
        this.datumPujceni = datumPujceni;
        this.datumVraceni = datumVraceni;
    }

    public Pujceni(Long id, Vozidlo vozidlo, Zakaznik zakaznik, LocalDateTime datumPujceni, LocalDateTime datumVraceni) {
        this.id = id;
        this.vozidlo = vozidlo;
        this.zakaznik = zakaznik;
        this.datumPujceni = datumPujceni;
        this.datumVraceni = datumVraceni;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vozidlo getVozidlo() {
        return vozidlo;
    }

    public void setVozidlo(Vozidlo vozidlo) {
        this.vozidlo = vozidlo;
    }

    public Zakaznik getZakaznik() {
        return zakaznik;
    }

    public void setZakaznik(Zakaznik zakaznik) {
        this.zakaznik = zakaznik;
    }

    public LocalDateTime getDatumVraceni() {
        return datumVraceni;
    }

    public void setDatumVraceni(LocalDateTime datumVraceni) {
        this.datumVraceni = datumVraceni;
    }

    public LocalDateTime getDatumPujceni() {
        return datumPujceni;
    }

    public void setDatumPujceni(LocalDateTime datumPujceni) {
        this.datumPujceni = datumPujceni;
    }


}

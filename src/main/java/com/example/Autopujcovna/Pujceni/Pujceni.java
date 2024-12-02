package com.example.Autopujcovna.Pujceni;

import com.example.Autopujcovna.Vozidlo.Vozidlo;
import com.example.Autopujcovna.ZakaznikTest.Zakaznik;
import com.fasterxml.jackson.annotation.JsonProperty;
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


    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private LocalDateTime datumPujceni;
    private LocalDateTime datumVraceni;

    @ManyToOne
    @JoinColumn(name = "vozidlo_id")
    private Vozidlo vozidlo;

    @ManyToOne
    @JoinColumn(name = "zakaznik_id")
    private Zakaznik zakaznik;


    public Pujceni() {
    }

    public Pujceni(Vozidlo vozidlo, Zakaznik zakaznik, LocalDateTime datumPujceni, LocalDateTime datumVraceni) {
        this.vozidlo = vozidlo;
        this.datumPujceni = datumPujceni;
        this.datumVraceni = datumVraceni;
        this.zakaznik = zakaznik;
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

package com.example.Autopujcovna.Vozidlo;

import com.example.Autopujcovna.Pujceni.Pujceni;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Schema(description = "Entita reprezentující vozidlo")
public class Vozidlo {

    @Id
    @SequenceGenerator(
            name = "vozidlo_sequence",
            sequenceName = "vozidlo_sequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "vozidlo_sequence"
    )

    private Long id;

    @NotBlank(message = "Značka je povinná")
    private String znacka;
    @NotBlank(message = "Model je povinný")
    private String model;

    private String barva;
    @Min(value = 1886, message = "Neplatný rok výroby")
    private Integer rokVyroby;

    private Integer stavKilometru;
    private Boolean dostupnost;

    @OneToMany(mappedBy = "vozidlo", cascade = CascadeType.ALL, orphanRemoval = true)
    //@JsonIgnore
    private List<Pujceni> pujceni = new ArrayList<>();

    public Vozidlo() {
    }

    public Vozidlo(Long id,
                   String barva,
                   String znacka,
                   String model,
                   Integer rokVyroby,
                   Integer stavKilometru,
                   Boolean dostupnost) {
        this.id = id;
        this.barva = barva;
        this.znacka = znacka;
        this.model = model;
        this.rokVyroby = rokVyroby;
        this.stavKilometru = stavKilometru;
        this.dostupnost = dostupnost;
    }

    public Vozidlo(String znacka,
                   String model,
                   String barva,
                   Integer rokVyroby,
                   Integer stavKilometru,
                   Boolean dostupnost) {
        this.znacka = znacka;
        this.model = model;
        this.barva = barva;
        this.rokVyroby = rokVyroby;
        this.stavKilometru = stavKilometru;
        this.dostupnost = dostupnost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getZnacka() {
        return znacka;
    }

    public void setZnacka(String znacka) {
        this.znacka = znacka;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBarva() {
        return barva;
    }

    public void setBarva(String barva) {
        this.barva = barva;
    }

    public Integer getRokVyroby() {
        return rokVyroby;
    }

    public void setRokVyroby(Integer rokVyroby) {
        this.rokVyroby = rokVyroby;
    }

    public Integer getStavKilometru() {
        return stavKilometru;
    }

    public void setStavKilometru(Integer stavKilometru) {
        this.stavKilometru = stavKilometru;
    }

    public Boolean getDostupnost() {
        return dostupnost;
    }

    public void setDostupnost(Boolean dostupnost) {
        this.dostupnost = dostupnost;
    }

    public List<Pujceni> getPujceni() {
        return pujceni;
    }

    public void setPujceni(List<Pujceni> pujceni) {
        this.pujceni = pujceni;
    }
}

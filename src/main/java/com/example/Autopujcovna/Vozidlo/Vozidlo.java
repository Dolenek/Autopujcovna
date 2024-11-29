package com.example.Autopujcovna.Vozidlo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

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

    @Schema(description = "Jedinečné ID vozidla", example = "1")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank(message = "Značka je povinná")
    private String znacka;
    @NotBlank(message = "Model je povinný")
    private String model;
    @NotBlank(message = "Barva je povinná")
    private String barva;
    @Min(value = 1886, message = "Neplatný rok výroby")
    private Integer rokVyroby;
    @NotBlank(message = "Stav kilometrů je povinný")
    private Integer stavKilometru;
    private Boolean dostupnost;

    public Vozidlo() {
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

    public Vozidlo(String znacka, String model, String barva, Integer rokVyroby, Integer stavKilometru)
    {
        this.znacka = znacka;
        this.model = model;
        this.barva = barva;
        this.rokVyroby = rokVyroby;
        this.stavKilometru = stavKilometru;
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
}

package com.example.Autopujcovna.Zakaznik;

import com.example.Autopujcovna.Pujceni.Pujceni;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Schema(description = "Entita reprezentující zákazníka")
public class Zakaznik {

    @Id
    @SequenceGenerator(
            name = "zakaznik_sequence",
            sequenceName = "zakaznik_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "zakaznik_sequence"
    )
    @Schema(description = "Jedinečné ID zákazníka", example = "1")
    private Long id;
    private String jmeno;
    private String prijmeni;
    private String email;
    private String telefon;

    @OneToMany(mappedBy = "zakaznik", cascade = CascadeType.ALL, orphanRemoval = true)
    //@JsonIgnore
    private List<Pujceni> pujcky = new ArrayList<>();

    public Zakaznik() {
    }

    public Zakaznik(Long id,
                    String jmeno,
                    String prijmeni,
                    String email,
                    String telefon) {
        this.id = id;
        this.jmeno = jmeno;
        this.prijmeni = prijmeni;
        this.email = email;
        this.telefon = telefon;
    }

    public Zakaznik(String jmeno,
                    String prijmeni,
                    String email,
                    String telefon) {
        this.jmeno = jmeno;
        this.prijmeni = prijmeni;
        this.telefon = telefon;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJmeno() {
        return jmeno;
    }

    public void setJmeno(String jmeno) {
        this.jmeno = jmeno;
    }

    public String getPrijmeni() {
        return prijmeni;
    }

    public void setPrijmeni(String prijmeni) {
        this.prijmeni = prijmeni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public List<Pujceni> getPujcky() {
        return pujcky;
    }

    public void setPujcky(List<Pujceni> pujcky) {
        this.pujcky = pujcky;
    }
}

package com.example.Autopujcovna.ZakaznikTest;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

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
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank(message = "Jméno je povinné")
    private String jmeno;
    @NotBlank(message = "Příjmení je povinné")
    private String prijmeni;
    @NotBlank(message = "Email je povinný")
    @Email
    private String email;
    private String telefon;

    public Zakaznik() {
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
}

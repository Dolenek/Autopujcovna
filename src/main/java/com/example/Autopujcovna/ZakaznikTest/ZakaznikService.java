package com.example.Autopujcovna.ZakaznikTest;

import com.example.Autopujcovna.Pujceni.PujceniRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class ZakaznikService {

    private final ZakaznikRepository zakaznikRepository;

    private final PujceniRepository pujceniRepository;

    @Autowired
    public ZakaznikService(ZakaznikRepository zakaznikRepository, PujceniRepository pujceniRepository) {
        this.zakaznikRepository = zakaznikRepository;
        this.pujceniRepository = pujceniRepository;
    }


    public List<Zakaznik> getZakaznici() {
        return zakaznikRepository.findAll();
    }

    boolean emailIsTaken(Zakaznik zakaznik)
    {
        Optional<Zakaznik> zakaznikOptional = zakaznikRepository.findZakaznikByEmail(zakaznik.getEmail());
        if (zakaznikOptional.isPresent())
            return true;
        else return false;
    }

    public void addNewZakaznik(Zakaznik zakaznik) {

        if (emailIsTaken(zakaznik))
        {
            throw new IllegalStateException("Email "+zakaznik.getEmail()+" již existuje");
        }
        zakaznikRepository.save(zakaznik);
    }

    public void deleteZakaznik(Long zakaznikId) {
        boolean exists = zakaznikRepository.existsZakaznikById(zakaznikId);
        if (!exists){
            throw new IllegalStateException("Zákazník s ID " + zakaznikId + " neexistuje");
        }
        exists = pujceniRepository.existsByZakaznik_Id(zakaznikId);
        if (exists) //Pokud má zákazník nějakou půjčku tak vymaže i tu půjčku
        {
            pujceniRepository.deleteById(zakaznikId);
        }
        zakaznikRepository.deleteById(zakaznikId);

    }


    @Transactional
    public void updateZakaznik(Long zakaznikId, String jmeno, String email, String telefon) {

        //Najde zákazníka s ID a pokud takové ID neexistuje tak vyhodí error
        Zakaznik zakaznik = zakaznikRepository.findById(zakaznikId)
                .orElseThrow(() -> new IllegalStateException("Zakaznik s ID" + zakaznikId + "neexistuje"));

        if (jmeno != null && !jmeno.isEmpty() && !Objects.equals(zakaznik.getJmeno(), jmeno))
        {
            zakaznik.setJmeno(jmeno);
        }

        if(email != null && !email.isEmpty() && !Objects.equals(zakaznik.getEmail(), email))
        {
            if (emailIsTaken(zakaznik))
                throw new IllegalStateException(email);

            zakaznik.setEmail(email);
        }
        if(telefon != null && !telefon.isEmpty() && !Objects.equals(zakaznik.getTelefon(), telefon))
        {
            zakaznik.setTelefon(email);
        }

    }
}

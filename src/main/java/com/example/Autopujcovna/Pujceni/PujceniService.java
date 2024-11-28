package com.example.Autopujcovna.Pujceni;

import com.example.Autopujcovna.Vozidlo.Vozidlo;
import com.example.Autopujcovna.Vozidlo.VozidloRepository;
import com.example.Autopujcovna.Zakaznik.Zakaznik;
import com.example.Autopujcovna.Zakaznik.ZakaznikRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PujceniService {

    private final PujceniRepository pujceniRepository;
    private final VozidloRepository vozidloRepository;
    private final ZakaznikRepository zakaznikRepository;

    public PujceniService(PujceniRepository pujceniRepository, VozidloRepository vozidloRepository, ZakaznikRepository zakaznikRepository) {
        this.pujceniRepository = pujceniRepository;
        this.vozidloRepository = vozidloRepository;
        this.zakaznikRepository = zakaznikRepository;
    }

    public List<Pujceni> getPujceniByZakaznikId(Long zakaznikId) {
        return pujceniRepository.findByZakaznikId(zakaznikId);
    }

    public Pujceni pujceniVozidla(Long vozidloId, Long customerId) {
        Vozidlo vozidlo = vozidloRepository.findById(vozidloId)
                .orElseThrow(() -> new IllegalStateException("Vozidlo nenalezeno"));
        if (!vozidlo.getDostupnost()) {
            throw new IllegalStateException("Vozidlo je jiz pujceno");
        }

        Zakaznik zakaznik = zakaznikRepository.findById(customerId)
                .orElseThrow(() -> new IllegalStateException("Zakaznik nenalezen"));

        Pujceni pujceni = new Pujceni();
        pujceni.setVozidlo(vozidlo);
        pujceni.setZakaznik(zakaznik);
        pujceni.setDatumPujceni(LocalDateTime.now());

        vozidlo.setDostupnost(false); // Aktualizujeme dostupnost vozidla
        vozidloRepository.save(vozidlo);

        return pujceniRepository.save(pujceni);
    }

    public Pujceni returnVozidlo(Long pujceniId) {
        Pujceni pujceni = pujceniRepository.findById(pujceniId)
                .orElseThrow(() -> new IllegalStateException("Pujceni nenalezeno"));

        if (pujceni.getDatumVraceni() != null) {
            throw new IllegalStateException("Vozidlo bylo jiz vraceno");
        }

        pujceni.setDatumVraceni(LocalDateTime.now());
        pujceniRepository.save(pujceni);

        Vozidlo vozidlo = pujceni.getVozidlo();
        vozidlo.setDostupnost(true); // Vozidlo je opět dostupné
        vozidloRepository.save(vozidlo);

        return pujceni;
    }

    public List<Pujceni> getPujceniHistoryByZakaznik(Long zakaznikId) {
        return pujceniRepository.findByZakaznikId(zakaznikId);
    }

    public List<Pujceni> getPujceniHistoryByVozidlo(Long vozidloId) {
        return pujceniRepository.findByVozidloId(vozidloId);
    }

    public List<Pujceni> getVsechnaVypujcenaVozidla()
    {
        return pujceniRepository.findAll();
    }
}

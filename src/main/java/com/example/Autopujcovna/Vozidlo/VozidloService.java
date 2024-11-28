package com.example.Autopujcovna.Vozidlo;

import com.example.Autopujcovna.Zakaznik.Zakaznik;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class VozidloService {

    private final VozidloRepository vozidloRepository;

    @Autowired
    public VozidloService(VozidloRepository vozidloRepository) {
        this.vozidloRepository = vozidloRepository;
    }

    public List<Vozidlo> getVozidla() {
        return vozidloRepository.findAll();
    }

    public void addNewVozidlo(Vozidlo vozidlo) {
        vozidloRepository.save(vozidlo);
    }

    @Transactional
    public void updateVozidlo(Long vozidloId, String barva, Integer stavKilometru, Boolean dostupnost)
    {
        //Najde Vozidlo s ID a pokud takové ID neexistuje tak vyhodí error
        Vozidlo vozidlo = vozidloRepository.findById(vozidloId)
                .orElseThrow(() -> new IllegalStateException("Vozidlo s ID " + vozidloId + " neexistuje"));

        vozidlo.setDostupnost(dostupnost);

        if (barva != null && !barva.isEmpty() && !Objects.equals(vozidlo.getBarva(), barva))
        {
            vozidlo.setBarva(barva);
        }

        vozidlo.setStavKilometru(stavKilometru);
    }

    public void deleteVozidlo(Long vozidloId)
    {
        boolean exists = vozidloRepository.existsVozidloById(vozidloId);
        if (!exists)
        {
            throw new IllegalStateException("Vozidlo s ID "+vozidloId+" neexistuje");
        }
        vozidloRepository.deleteById(vozidloId);
    }
}
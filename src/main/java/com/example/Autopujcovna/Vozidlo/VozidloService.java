package com.example.Autopujcovna.Vozidlo;

import com.example.Autopujcovna.Pujceni.PujceniRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class VozidloService {

    private final VozidloRepository vozidloRepository;

    private final PujceniRepository pujceniRepository;

    @Autowired
    public VozidloService(VozidloRepository vozidloRepository, PujceniRepository pujceniRepository) {
        this.vozidloRepository = vozidloRepository;
        this.pujceniRepository = pujceniRepository;
    }

    public List<Vozidlo> getVozidla() {
        return vozidloRepository.findAll();
    }

    public Vozidlo addNewVozidlo(Vozidlo vozidlo) {
        return vozidloRepository.save(vozidlo);
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

        vozidloRepository.save(vozidlo);
    }

    public void deleteVozidlo(Long vozidloId)
    {
        boolean exists = vozidloRepository.existsVozidloById(vozidloId);
        if (!exists)
        {
            throw new IllegalStateException("Vozidlo s ID "+vozidloId+" neexistuje");
        }
        exists = pujceniRepository.existsByVozidlo_Id(vozidloId);
        if (exists) //Pokud je vozidlo vypůjčené tak vymaže i půjčku
        {
            pujceniRepository.deleteById(vozidloId);
        }
        vozidloRepository.deleteById(vozidloId);
    }

    public List<Vozidlo> getNevypujcenaVozidla()
    {
        return vozidloRepository.findVozidloByDostupnost(true);
    }

    public List<Vozidlo> getVypujcenaVozidla()
    {
        return vozidloRepository.findVozidloByDostupnost(false);
    }
}

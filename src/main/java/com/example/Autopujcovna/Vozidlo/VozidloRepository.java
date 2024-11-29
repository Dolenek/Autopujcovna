package com.example.Autopujcovna.Vozidlo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VozidloRepository extends JpaRepository<Vozidlo, Long> {


    List<Vozidlo> findVozidloByZnacka(String znacka);

    List<Vozidlo> findVozidloByDostupnost(boolean dostupnost);

    List<Vozidlo> findVozidloByBarva(String barva);

    boolean existsVozidloById(Long id);

    Vozidlo[] getByDostupnost(Boolean dostupnost);
}

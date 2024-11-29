package com.example.Autopujcovna.Pujceni;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PujceniRepository extends JpaRepository<Pujceni, Long> {

    List<Pujceni> findByZakaznikId(Long zakaznikId);

    List<Pujceni> findByVozidloId(Long vozidloId);

    boolean existsByVozidlo_Id(Long vozidloId);

    boolean existsByZakaznik_Id(Long zakaznikId);
}

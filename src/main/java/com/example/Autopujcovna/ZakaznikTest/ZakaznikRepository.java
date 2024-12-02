package com.example.Autopujcovna.ZakaznikTest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository //Responsible for data acess z databáze
//Když chceme něco používat uvnitř Service

public interface ZakaznikRepository extends JpaRepository<Zakaznik, Long> {

    Optional<Zakaznik> findZakaznikByEmail(String email);

    List<Zakaznik> findZakaznikById(Long id);

    boolean existsZakaznikById(Long id);
}

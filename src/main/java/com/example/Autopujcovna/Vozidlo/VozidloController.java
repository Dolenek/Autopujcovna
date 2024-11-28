package com.example.Autopujcovna.Vozidlo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "api/v1/vozidlo")
@Tag(name = "Vozidlo", description = "API pro správu Vozidel")
public class VozidloController {

    private final VozidloService vozidloService;

    @Autowired
    public VozidloController(VozidloService vozidloService)
    {
        this.vozidloService = vozidloService;
    }

    @Operation(summary = "Získá seznam všech vozidel")
    @GetMapping
    public List<Vozidlo> getVozidla()
    {
        return vozidloService.getVozidla();
    }

    @Operation(summary = "Přidá vozidlo do seznamu")
    @PostMapping("/pridat")
    public void addNewVozidlo(@Valid @RequestBody Vozidlo vozidlo)
    {
        vozidloService.addNewVozidlo(vozidlo);
    }

    @Operation(summary = "Aktualizuje údaje vozidla")
    @PutMapping(path = "/aktualizovat/{vozidloId}")
    public void updateVozidlo(
            @PathVariable("vozidloId") Long vozidloId,
            @RequestParam(required = false) String barva,
            @RequestParam(required = false) Integer stavKilometru,
            @RequestParam(required = false) Boolean dostupnost)
    {
        vozidloService.updateVozidlo(vozidloId, barva, stavKilometru, dostupnost);
    }

    @Operation(summary = "Pomocí ID odstraní vozidlo ze seznamu")
    @DeleteMapping(path = "/odstranit/{vozidloId}")
    public void deleteVozidlo(@PathVariable("vozidloId") Long vozidloId)
    {
        vozidloService.deleteVozidlo(vozidloId);
    }
}

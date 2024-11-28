package com.example.Autopujcovna.Zakaznik;

import com.example.Autopujcovna.Pujceni.Pujceni;
import com.example.Autopujcovna.Pujceni.PujceniService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping(path = "api/v1/zakaznik")
@Tag(name = "Zákazník", description = "API pro správu zákazníků")
public class ZakaznikController {

    private final ZakaznikService zakaznikService;

    @Autowired
    private PujceniService pujceniService;

    @Autowired
    public ZakaznikController(ZakaznikService zakaznikService) {
        this.zakaznikService = zakaznikService;
    }

    @Operation(summary = "Získá seznam všech zákazníků")
    @GetMapping
    public List<Zakaznik> getZakaznici(){
        return zakaznikService.getZakaznici();
    }

    @Operation(summary = "Přidá zákazníka do seznamu")
    @PostMapping("/pridat")
    public void addNewZakaznik(@Valid @RequestBody Zakaznik zakaznik)
    {
        zakaznikService.addNewZakaznik(zakaznik);
        //return new ResponseEntity<>(novyZakaznik, HttpStatus.CREATED);
    }

    @Operation(summary = "Pomocí ID odstraní zákazníka ze seznamu")
    @DeleteMapping(path = "/odstranit/{zakaznikId}")
    public void deleteZakaznik(@PathVariable("zakaznikId") Long zakaznikId)
    {
        zakaznikService.deleteZakaznik(zakaznikId);
    }

    @Operation(summary = "Aktualizuje zákazníkovi údaje")
    @PutMapping(path = "/aktualizovat/{zakaznikId}")
    public void updateZakaznik(
            @PathVariable("zakaznikId") Long zakaznikId,
            @RequestParam(required = false) String jmeno,
            @RequestParam(required = false) String email){
        zakaznikService.updateZakaznik(zakaznikId, jmeno, email);
    }

    @Operation(summary = "Získá historii všech půjčených aut od zákazníka")
    @GetMapping("/historie/{zakaznikId}")
    public List<Pujceni> getHistoriePujceni(@PathVariable("zakaznikId") Long zakaznikId){
        return pujceniService.getPujceniByZakaznikId(zakaznikId);
    }




}

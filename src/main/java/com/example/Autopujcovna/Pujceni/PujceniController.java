package com.example.Autopujcovna.Pujceni;

import com.example.Autopujcovna.Vozidlo.VozidloRepository;
import com.example.Autopujcovna.Zakaznik.ZakaznikRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/pujceni")
@Tag(name = "Půjčení", description = "API pro správu půjček vozidel zákazníky")
public class PujceniController {

    private final PujceniService pujceniService;

    private VozidloRepository vozidloRepository;

    private ZakaznikRepository zakaznikRepository;

    @Autowired
    public PujceniController(PujceniService pujceniService) {
        this.pujceniService = pujceniService;
    }

    @Operation(summary = "Půjčí vozidlo zákazníkovi")
    @PostMapping("/pujcit")
    public Pujceni pujceniVozidla(@RequestParam Long vozidloId, @RequestParam Long customerId) {
        return pujceniService.pujceniVozidla(vozidloId, customerId);
    }

    @Operation(summary = "Vrátí vozidlo")
    @PostMapping("/return")
    public Pujceni returnVozidlo(@RequestParam Long rentalId) {
        return pujceniService.returnVozidlo(rentalId);
    }

    @Operation(summary = "Získá historii zákazníka")
    @GetMapping("/zakaznik/{zakaznikId}")
    public List<Pujceni> getHistoriePujceniPodleZakaznik(@PathVariable Long zakaznikId) {
        return pujceniService.getPujceniHistoryByZakaznik(zakaznikId);
    }

    @Operation(summary = "Získá historii vozidla")
    @GetMapping("/vozidlo/{vozidloId}")
    public List<Pujceni> getHistoriePujceniPodleVozidlo(@PathVariable Long vozidloId) {
        return pujceniService.getPujceniHistoryByVozidlo(vozidloId);
    }

    @Operation(summary = "Získá seznam všech vypůjčených vozidel")
    @GetMapping("vozidla")
    public List<Pujceni> getVsechnaVypujcenaVozidla()
    {
        return  pujceniService.getVsechnaVypujcenaVozidla();
    }
}

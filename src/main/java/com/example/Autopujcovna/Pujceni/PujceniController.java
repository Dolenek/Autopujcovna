package com.example.Autopujcovna.Pujceni;

import com.example.Autopujcovna.Vozidlo.VozidloRepository;
import com.example.Autopujcovna.ZakaznikTest.ZakaznikRepository;
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
    public Pujceni pujceniVozidla(@RequestParam Long vozidloId, @RequestParam Long zakaznikId) {
        return pujceniService.pujceniVozidla(vozidloId, zakaznikId);
    }

    @Operation(summary = "Vrátí půjčené vozidlo")
    @PostMapping("/vratit")
    public Pujceni vratitVozidlo(@RequestParam Long pujceniId) {
        return pujceniService.vratitVozidlo(pujceniId);
    }

    @Operation(summary = "Získá historii zákazníka")
    @GetMapping("/zakaznik/{zakaznikId}")
    public List<Pujceni> getHistoriePujceniPodleZakaznik(@PathVariable Long zakaznikId) {
        return pujceniService.getHistoriePujceniPodleZakaznik(zakaznikId);
    }

    @Operation(summary = "Získá historii vozidla")
    @GetMapping("/vozidlo/{vozidloId}")
    public List<Pujceni> getHistoriePujceniPodleVozidlo(@PathVariable Long vozidloId) {
        return pujceniService.getHistoriePujceniPodleVozidlo(vozidloId);
    }

    @Operation(summary = "Získá historii všech půjček")
    @GetMapping("/historie/vozidla")
    public List<Pujceni> getHistoriePujcek()
    {
        return  pujceniService.getHistoriePujcek();
    }
}

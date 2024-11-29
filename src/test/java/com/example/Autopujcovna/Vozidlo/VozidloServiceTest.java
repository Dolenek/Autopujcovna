package com.example.Autopujcovna.Vozidlo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class VozidloServiceTest {

    @Mock
    private VozidloRepository vozidloRepository;

    @InjectMocks
    private VozidloService vozidloService;

    @Test
    public void testAddNewVozidlo()
    {
        // Vytvoření testovacího vozidla
        Vozidlo vozidlo = new Vozidlo();
        vozidlo.setZnacka("Škoda");
        vozidlo.setModel("Octavia");
        vozidlo.setRokVyroby(2020);
        vozidlo.setBarva("Modrá");
        vozidlo.setStavKilometru(15000);

        // Nastavení chování mocku
        when(vozidloRepository.save(any(Vozidlo.class))).thenReturn(vozidlo);

        // Volání testované metody
        Vozidlo result = vozidloService.addNewVozidlo(vozidlo);

        // Ověření výsledku
        assertNotNull(result);
        assertEquals("Škoda", result.getZnacka());
        verify(vozidloRepository, times(1)).save(vozidlo);
    }
}
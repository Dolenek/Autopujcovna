package com.example.Autopujcovna.Vozidlo;

import com.example.Autopujcovna.Pujceni.PujceniRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//Testovací třída pro VozidloService.
@ExtendWith(MockitoExtension.class)
public class VozidloServiceTest {

    @Mock
    private VozidloRepository vozidloRepository;

    @Mock
    private PujceniRepository pujceniRepository;

    @InjectMocks
    private VozidloService vozidloService;

    private Vozidlo vozidlo1;
    private Vozidlo vozidlo2;

    @BeforeEach
    void setUp() {
        vozidlo1 = new Vozidlo();
        vozidlo1.setId(1L);
        vozidlo1.setBarva("Červená");
        vozidlo1.setStavKilometru(10000);
        vozidlo1.setDostupnost(true);

        vozidlo2 = new Vozidlo();
        vozidlo2.setId(2L);
        vozidlo2.setBarva("Modrá");
        vozidlo2.setStavKilometru(20000);
        vozidlo2.setDostupnost(false);
    }

    //Testuje metodu getVozidla().
    @Test
    void testGetVozidla() {
        // Arrange
        List<Vozidlo> expectedVozidla = Arrays.asList(vozidlo1, vozidlo2);
        when(vozidloRepository.findAll()).thenReturn(expectedVozidla);

        // Act
        List<Vozidlo> actualVozidla = vozidloService.getVozidla();

        // Assert
        assertEquals(expectedVozidla, actualVozidla);
        verify(vozidloRepository, times(1)).findAll();
    }

    //Testuje metodu addNewVozidlo().
    @Test
    void testAddNewVozidlo() {
        // Arrange
        Vozidlo newVozidlo = new Vozidlo();
        newVozidlo.setBarva("Zelená");
        newVozidlo.setStavKilometru(5000);
        newVozidlo.setDostupnost(true);

        when(vozidloRepository.save(newVozidlo)).thenReturn(newVozidlo);

        // Act
        Vozidlo savedVozidlo = vozidloService.addNewVozidlo(newVozidlo);

        // Assert
        assertEquals(newVozidlo, savedVozidlo);
        verify(vozidloRepository, times(1)).save(newVozidlo);
    }

    //Testuje metodu updateVozidlo() při úspěšném aktualizování.
    @Test
    void testUpdateVozidlo_Success() {
        // Arrange
        Long vozidloId = 1L;
        String newBarva = "Černá";
        Integer newStavKilometru = 15000;
        Boolean newDostupnost = false;

        when(vozidloRepository.findById(vozidloId)).thenReturn(Optional.of(vozidlo1));

        // Act
        vozidloService.updateVozidlo(vozidloId, newBarva, newStavKilometru, newDostupnost);

        // Assert
        verify(vozidloRepository, times(1)).findById(vozidloId);
        // Není potřeba ověřovat save, protože není voláno

        // Ověření, že atributy byly aktualizovány
        assertEquals(newBarva, vozidlo1.getBarva(), "Barva by měla být aktualizována na Černá");
        assertEquals(newStavKilometru, vozidlo1.getStavKilometru(), "Stav kilometrů by měl být aktualizován na 15000");
        assertEquals(newDostupnost, vozidlo1.getDostupnost(), "Dostupnost by měla být aktualizována na false");
    }

    //Testuje metodu updateVozidlo() při aktualizaci neexistujícího Vozidla.
    @Test
    void testUpdateVozidlo_VozidloNotFound() {
        // Arrange
        Long vozidloId = 3L;
        String newBarva = "Bílá";
        Integer newStavKilometru = 12000;
        Boolean newDostupnost = true;

        when(vozidloRepository.findById(vozidloId)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            vozidloService.updateVozidlo(vozidloId, newBarva, newStavKilometru, newDostupnost);
        });

        assertEquals("Vozidlo s ID " + vozidloId + " neexistuje", exception.getMessage());
        verify(vozidloRepository, times(1)).findById(vozidloId);
        verify(vozidloRepository, never()).save(any(Vozidlo.class));
    }

    //Testuje metodu deleteVozidlo() při úspěšném smazání vozidla bez půjček.
    @Test
    void testDeleteVozidlo_Success_NoPujceni() {
        // Arrange
        Long vozidloId = 1L;

        when(vozidloRepository.existsVozidloById(vozidloId)).thenReturn(true);
        when(pujceniRepository.existsByVozidlo_Id(vozidloId)).thenReturn(false);

        // Act
        vozidloService.deleteVozidlo(vozidloId);

        // Assert
        verify(vozidloRepository, times(1)).existsVozidloById(vozidloId);
        verify(pujceniRepository, times(1)).existsByVozidlo_Id(vozidloId);
        verify(pujceniRepository, never()).deleteById(anyLong());
        verify(vozidloRepository, times(1)).deleteById(vozidloId);
    }

    //Testuje metodu deleteVozidlo() při smazání vozidla s půjčkami.
    @Test
    void testDeleteVozidlo_Success_WithPujceni() {
        // Arrange
        Long vozidloId = 2L;

        when(vozidloRepository.existsVozidloById(vozidloId)).thenReturn(true);
        when(pujceniRepository.existsByVozidlo_Id(vozidloId)).thenReturn(true);

        // Act
        vozidloService.deleteVozidlo(vozidloId);

        // Assert
        verify(vozidloRepository, times(1)).existsVozidloById(vozidloId);
        verify(pujceniRepository, times(1)).existsByVozidlo_Id(vozidloId);
        verify(pujceniRepository, times(1)).deleteById(vozidloId);
        verify(vozidloRepository, times(1)).deleteById(vozidloId);
    }

    //Testuje metodu deleteVozidlo() při pokusu o smazání neexistujícího vozidla.
    @Test
    void testDeleteVozidlo_VozidloNotFound() {
        // Arrange
        Long vozidloId = 4L;

        when(vozidloRepository.existsVozidloById(vozidloId)).thenReturn(false);

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            vozidloService.deleteVozidlo(vozidloId);
        });

        assertEquals("Vozidlo s ID " + vozidloId + " neexistuje", exception.getMessage());
        verify(vozidloRepository, times(1)).existsVozidloById(vozidloId);
        verify(pujceniRepository, never()).existsByVozidlo_Id(anyLong());
        verify(pujceniRepository, never()).deleteById(anyLong());
        verify(vozidloRepository, never()).deleteById(anyLong());
    }

    //Testuje metodu getNevypujcenaVozidla().
    @Test
    void testGetNevypujcenaVozidla() {
        // Arrange
        List<Vozidlo> expectedVozidla = Arrays.asList(vozidlo1);
        when(vozidloRepository.findVozidloByDostupnost(true)).thenReturn(expectedVozidla);

        // Act
        List<Vozidlo> actualVozidla = vozidloService.getNevypujcenaVozidla();

        // Assert
        assertEquals(expectedVozidla, actualVozidla);
        verify(vozidloRepository, times(1)).findVozidloByDostupnost(true);
    }

    //Testuje metodu getVypujcenaVozidla().
    @Test
    void testGetVypujcenaVozidla() {
        // Arrange
        List<Vozidlo> expectedVozidla = Arrays.asList(vozidlo2);
        when(vozidloRepository.findVozidloByDostupnost(false)).thenReturn(expectedVozidla);

        // Act
        List<Vozidlo> actualVozidla = vozidloService.getVypujcenaVozidla();

        // Assert
        assertEquals(expectedVozidla, actualVozidla);
        verify(vozidloRepository, times(1)).findVozidloByDostupnost(false);
    }
}

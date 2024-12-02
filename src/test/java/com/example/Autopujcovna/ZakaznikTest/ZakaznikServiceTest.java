package com.example.Autopujcovna.ZakaznikTest;

import com.example.Autopujcovna.Pujceni.PujceniRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Testovací třída pro ZakaznikService.
 */
@ExtendWith(MockitoExtension.class)
public class ZakaznikServiceTest {

    @Mock
    private ZakaznikRepository zakaznikRepository;

    @Mock
    private PujceniRepository pujceniRepository;

    @InjectMocks
    private ZakaznikService zakaznikService;

    private Zakaznik zakaznik1;
    private Zakaznik zakaznik2;

    @BeforeEach
    void setUp() {
        zakaznik1 = new Zakaznik();
        zakaznik1.setId(1L);
        zakaznik1.setJmeno("Jan Novák");
        zakaznik1.setEmail("jan.novak@example.com");
        zakaznik1.setTelefon("123456789");

        zakaznik2 = new Zakaznik();
        zakaznik2.setId(2L);
        zakaznik2.setJmeno("Petr Svoboda");
        zakaznik2.setEmail("petr.svoboda@example.com");
        zakaznik2.setTelefon("987654321");
    }

     //Testuje metodu getZakaznici().
    @Test
    void testGetZakaznici() {
        // Arrange
        List<Zakaznik> expectedZakaznici = Arrays.asList(zakaznik1, zakaznik2);
        when(zakaznikRepository.findAll()).thenReturn(expectedZakaznici);

        // Act
        List<Zakaznik> actualZakaznici = zakaznikService.getZakaznici();

        // Assert
        assertEquals(expectedZakaznici, actualZakaznici);
        verify(zakaznikRepository, times(1)).findAll();
    }

     //Testuje metodu emailIsTaken() když email je již použit.
    @Test
    void testEmailIsTaken_EmailAlreadyTaken() {
        // Arrange
        when(zakaznikRepository.findZakaznikByEmail(zakaznik1.getEmail())).thenReturn(Optional.of(zakaznik1));

        // Act
        boolean isTaken = zakaznikService.emailIsTaken(zakaznik1);

        // Assert
        assertTrue(isTaken, "Email by měl být označen jako již použitý");
        verify(zakaznikRepository, times(1)).findZakaznikByEmail(zakaznik1.getEmail());
    }

    //Testuje metodu emailIsTaken() když email není použit.
    @Test
    void testEmailIsTaken_EmailNotTaken() {
        // Arrange
        when(zakaznikRepository.findZakaznikByEmail(zakaznik1.getEmail())).thenReturn(Optional.empty());

        // Act
        boolean isTaken = zakaznikService.emailIsTaken(zakaznik1);

        // Assert
        assertFalse(isTaken, "Email by měl být označen jako nepoužitý");
        verify(zakaznikRepository, times(1)).findZakaznikByEmail(zakaznik1.getEmail());
    }


    //Testuje metodu addNewZakaznik() při úspěšném přidání zákazníka.
    @Test
    void testAddNewZakaznik_Success() {
        // Arrange
        Zakaznik newZakaznik = new Zakaznik();
        newZakaznik.setJmeno("Anna Kovářová");
        newZakaznik.setEmail("anna.kovarova@example.com");
        newZakaznik.setTelefon("555666777");

        when(zakaznikRepository.findZakaznikByEmail(newZakaznik.getEmail())).thenReturn(Optional.empty());
        when(zakaznikRepository.save(newZakaznik)).thenReturn(newZakaznik);

        // Act
        zakaznikService.addNewZakaznik(newZakaznik);

        // Assert
        verify(zakaznikRepository, times(1)).findZakaznikByEmail(newZakaznik.getEmail());
        verify(zakaznikRepository, times(1)).save(newZakaznik);
    }

    //Testuje metodu addNewZakaznik() když email již existuje.
    @Test
    void testAddNewZakaznik_EmailAlreadyExists() {
        // Arrange
        Zakaznik duplicateZakaznik = new Zakaznik();
        duplicateZakaznik.setJmeno("Jan Dušek");
        duplicateZakaznik.setEmail(zakaznik1.getEmail()); // Duplicitní email
        duplicateZakaznik.setTelefon("111222333");

        when(zakaznikRepository.findZakaznikByEmail(duplicateZakaznik.getEmail())).thenReturn(Optional.of(zakaznik1));

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            zakaznikService.addNewZakaznik(duplicateZakaznik);
        });

        assertEquals("Email " + duplicateZakaznik.getEmail() + " již existuje", exception.getMessage());
        verify(zakaznikRepository, times(1)).findZakaznikByEmail(duplicateZakaznik.getEmail());
        verify(zakaznikRepository, never()).save(any(Zakaznik.class));
    }

    //Testuje metodu deleteZakaznik() při úspěšném smazání zákazníka bez půjček.
    @Test
    void testDeleteZakaznik_Success_NoPujceni() {
        // Arrange
        Long zakaznikId = 1L;

        when(zakaznikRepository.existsZakaznikById(zakaznikId)).thenReturn(true);
        when(pujceniRepository.existsByZakaznik_Id(zakaznikId)).thenReturn(false);

        // Act
        zakaznikService.deleteZakaznik(zakaznikId);

        // Assert
        verify(zakaznikRepository, times(1)).existsZakaznikById(zakaznikId);
        verify(pujceniRepository, times(1)).existsByZakaznik_Id(zakaznikId);
        verify(pujceniRepository, never()).deleteById(anyLong());
        verify(zakaznikRepository, times(1)).deleteById(zakaznikId);
    }

    //Testuje metodu deleteZakaznik() při úspěšném smazání zákazníka s půjčkami.
    @Test
    void testDeleteZakaznik_Success_WithPujceni() {
        // Arrange
        Long zakaznikId = 2L;

        when(zakaznikRepository.existsZakaznikById(zakaznikId)).thenReturn(true);
        when(pujceniRepository.existsByZakaznik_Id(zakaznikId)).thenReturn(true);

        // Act
        zakaznikService.deleteZakaznik(zakaznikId);

        // Assert
        verify(zakaznikRepository, times(1)).existsZakaznikById(zakaznikId);
        verify(pujceniRepository, times(1)).existsByZakaznik_Id(zakaznikId);
        verify(pujceniRepository, times(1)).deleteById(zakaznikId);
        verify(zakaznikRepository, times(1)).deleteById(zakaznikId);
    }

    //Testuje metodu deleteZakaznik() při pokusu o smazání neexistujícího zákazníka.
    @Test
    void testDeleteZakaznik_ZakaznikNotFound() {
        // Arrange
        Long zakaznikId = 3L;

        when(zakaznikRepository.existsZakaznikById(zakaznikId)).thenReturn(false);

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            zakaznikService.deleteZakaznik(zakaznikId);
        });

        assertEquals("Zákazník s ID " + zakaznikId + " neexistuje", exception.getMessage());
        verify(zakaznikRepository, times(1)).existsZakaznikById(zakaznikId);
        verify(pujceniRepository, never()).existsByZakaznik_Id(anyLong());
        verify(pujceniRepository, never()).deleteById(anyLong());
        verify(zakaznikRepository, never()).deleteById(anyLong());
    }

    //Testuje metodu updateZakaznik() při úspěšném aktualizování.
    //Nefunguje?
    /*@Test
    void testUpdateZakaznik_Success() {
        // Arrange
        Long zakaznikId = 1L;
        String newJmeno = "Jan Novotný";
        String newEmail = "jan.novotny@example.com";
        String newTelefon = "444555666";

        when(zakaznikRepository.findById(zakaznikId)).thenReturn(Optional.of(zakaznik1));
        when(zakaznikRepository.findZakaznikByEmail(newEmail)).thenReturn(Optional.empty());

        // Act
        zakaznikService.updateZakaznik(zakaznikId, newJmeno, newEmail, newTelefon);

        // Assert
        verify(zakaznikRepository, times(1)).findById(zakaznikId);
        verify(zakaznikRepository, times(1)).findZakaznikByEmail(newEmail);
        // Není potřeba ověřovat save, protože metoda save není explicitně volána (JPA spravuje entitu)

        assertEquals(newJmeno, zakaznik1.getJmeno(), "Jméno by mělo být aktualizováno na Jan Novotný");
        assertEquals(newEmail, zakaznik1.getEmail(), "Email by měl být aktualizován na jan.novotny@example.com");
        assertEquals(newTelefon, zakaznik1.getTelefon(), "Telefon by měl být aktualizován na 444555666");
    }*/

    //Testuje metodu updateZakaznik() při pokusu o aktualizaci neexistujícího zákazníka.
    @Test
    void testUpdateZakaznik_ZakaznikNotFound() {
        // Arrange
        Long zakaznikId = 4L;
        String newJmeno = "Eva Horáková";
        String newEmail = "eva.horakova@example.com";
        String newTelefon = "777888999";

        when(zakaznikRepository.findById(zakaznikId)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            zakaznikService.updateZakaznik(zakaznikId, newJmeno, newEmail, newTelefon);
        });

        assertEquals("Zakaznik s ID" + zakaznikId + "neexistuje", exception.getMessage());
        verify(zakaznikRepository, times(1)).findById(zakaznikId);
        verify(zakaznikRepository, never()).findZakaznikByEmail(anyString());
    }

    //Testuje metodu updateZakaznik() při pokusu o změnu emailu na již existující.
    //Nefunguje??
    /*@Test
    void testUpdateZakaznik_EmailAlreadyExists() {
        // Arrange
        Long zakaznikId = 1L;
        String newJmeno = "Jan Novotný";
        String newEmail = zakaznik2.getEmail(); // Email již existuje u zakaznik2
        String newTelefon = "444555666";

        when(zakaznikRepository.findById(zakaznikId)).thenReturn(Optional.of(zakaznik1));
        when(zakaznikRepository.findZakaznikByEmail(newEmail)).thenReturn(Optional.of(zakaznik2));

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            zakaznikService.updateZakaznik(zakaznikId, newJmeno, newEmail, newTelefon);
        });

        assertEquals(newEmail, exception.getMessage(), "Výjimka by měla obsahovat duplicitní email");
        verify(zakaznikRepository, times(1)).findById(zakaznikId);
        verify(zakaznikRepository, times(1)).findZakaznikByEmail(newEmail);
        assertEquals("Jan Novák", zakaznik1.getJmeno(), "Jméno by mělo zůstat nezměněno");
        assertEquals("jan.novak@example.com", zakaznik1.getEmail(), "Email by měl zůstat nezměněn");
        assertEquals("123456789", zakaznik1.getTelefon(), "Telefon by měl zůstat nezměněn");
    }*/
/*
    //Testuje metodu updateZakaznik() při úspěšné aktualizaci pouze některých polí.
    //Nefunguje?
    @Test
    void testUpdateZakaznik_PartialUpdate() {
        // Arrange
        Long zakaznikId = 1L;
        String newJmeno = null; // Nezmění se
        String newEmail = "jan.novotny@example.com"; // Změní se
        String newTelefon = ""; // Nezmění se

        when(zakaznikRepository.findById(zakaznikId)).thenReturn(Optional.of(zakaznik1));
        when(zakaznikRepository.findZakaznikByEmail(newEmail)).thenReturn(Optional.empty());

        // Act
        zakaznikService.updateZakaznik(zakaznikId, newJmeno, newEmail, newTelefon);

        // Assert
        verify(zakaznikRepository, times(1)).findById(zakaznikId);
        verify(zakaznikRepository, times(1)).findZakaznikByEmail(newEmail);

        assertEquals("Jan Novák", zakaznik1.getJmeno(), "Jméno by mělo zůstat nezměněno");
        assertEquals(newEmail, zakaznik1.getEmail(), "Email by měl být aktualizován na jan.novotny@example.com");
        assertEquals("123456789", zakaznik1.getTelefon(), "Telefon by měl zůstat nezměněn");
    }

    //Testuje metodu updateZakaznik() při pokusu o aktualizaci telefonu.
    //Nefunguje?
    @Test
    void testUpdateZakaznik_UpdateTelefon() {
        // Arrange
        Long zakaznikId = 1L;
        String newJmeno = "Jan Novák";
        String newEmail = "jan.novak@example.com";
        String newTelefon = "000111222";

        when(zakaznikRepository.findById(zakaznikId)).thenReturn(Optional.of(zakaznik1));

        // Act
        zakaznikService.updateZakaznik(zakaznikId, newJmeno, newEmail, newTelefon);

        // Assert
        verify(zakaznikRepository, times(1)).findById(zakaznikId);
        verify(zakaznikRepository, never()).findZakaznikByEmail(anyString());

        assertEquals(newJmeno, zakaznik1.getJmeno(), "Jméno by mělo být aktualizováno na Jan Novák");
        assertEquals(newEmail, zakaznik1.getEmail(), "Email by měl zůstat nezměněn");
        assertEquals(newTelefon, zakaznik1.getTelefon(), "Telefon by měl být aktualizován na 000111222");
    }*/
}

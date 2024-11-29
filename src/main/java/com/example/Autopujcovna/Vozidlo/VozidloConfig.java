package com.example.Autopujcovna.Vozidlo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class VozidloConfig {

    @Bean
    CommandLineRunner commandLineRunnerVozidlo(VozidloRepository vozidloRepository)
    {
        return args -> {
            Vozidlo htp = new Vozidlo(
                    "Škoda",
                    "HTP",
                    "Modrá",
                    1945,
                    500000,
                    true);
            Vozidlo felda = new Vozidlo(
                    "Škoda",
                    "Felicie",
                    "Šedá",
                    2020,
                    6542,
                    true);

            vozidloRepository.saveAll(List.of(htp,felda));
        };
    }
}

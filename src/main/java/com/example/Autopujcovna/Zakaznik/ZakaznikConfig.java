package com.example.Autopujcovna.Zakaznik;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ZakaznikConfig {

    @Bean
    //                                  Access to Repo
    CommandLineRunner commandLineRunner(ZakaznikRepository repository)
    {
        return args -> {
            Zakaznik jakub = new Zakaznik(
                            "Jakub",
                            "Dolenek",
                            "jakub.dolenek@seznam.cz",
                            "123456789");
            Zakaznik honza = new Zakaznik(
                    "Honza",
                    "Dolenek",
                    "honza.dolenek@seznam.cz",
                    "123459789");

            repository.saveAll(List.of(honza,jakub));
        };
    }
}

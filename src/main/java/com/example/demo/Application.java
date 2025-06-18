package com.example.demo;

import com.github.javafaker.App;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

@SpringBootApplication
public class Application {


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

    }

    /**
     * @Bean indique à Spring que la méthode produit un composant (bean) à gérer dans son conteneur d'injection de dépendances.

     * CommandLineRunner : est une interface spéciale que Spring Boot reconnaît automatiquement et quelle peux etre
        exécute au démarrage.

    SAUF QUE SANS LE COMBO @BEAN,COMMANDLINERUNNER, le code a l'interieur ne sera pas exécuté au démarrage de l'application.

     * runner(...)	Ton bloc de code exécuté pour insérer des données de test.

     *PS : On peux aussi avoir plusieurs runners, chacun avec un nom différent (@Bean(name = "initData"), etc.).
     *
     *PS : si on utilise l'annotaion @Bean, avec une methode lambda(ex test()), rien ne va s'exécuter au démarrage de l'application.
      il Faut donc utilisr l'annotation @Bean avec une interface spéciale comme CommandLineRunner.
     */
    @Bean(name = "initData")
        CommandLineRunner runner (StudentRepository studentRepository){

            return args -> {

            };

            }

}
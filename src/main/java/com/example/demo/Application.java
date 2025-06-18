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

                // Methode pour trier les donnees dans la base de donnee
                sortingMethode(studentRepository);

                // Methode pour paginer les donnees dans la base de donnee..
                pagingMethode(studentRepository);

            };

            }



    private void pagingMethode(StudentRepository studentRepository) {
        PageRequest pageRequest = PageRequest.of(
                0,
                5,
                Sort.by("firstName").ascending());
        System.out.println("First List of Students with Pagination: ");
        Page page =  studentRepository.findAll(pageRequest);

        page.forEach(System.out::println);

        System.out.println("page.nextPageable() : ");
        System.out.println(page.nextPageable());

        System.out.println("page.offset");
        System.out.println(page.nextPageable().getOffset());
    }

    private void sortingMethode(StudentRepository studentRepository) {
        /// Dans le sorting on a dois d'utiliser les nom des attributs de la class et non pas de la BD.
        System.out.println("All Students Sorting ASC by first_name: ");
        Sort sort = Sort.by(Sort.Direction.ASC, "firstName");
        studentRepository.findAll(sort).forEach(System.out::println);

        System.out.println("Ou encore Sorting ASC by FirstName AND DES by AGE");
        sort = Sort.by(Sort.Direction.ASC, "firstName")
                .and(Sort.by(Sort.Direction.DESC, "age"));
        studentRepository.findAll(sort).forEach(student ->
                System.out.println("Student: " + student.getFirstName() + " " + student.getAge()));
    }


    private void getFakerData(StudentRepository studentRepository) {
        Faker faker = new Faker();
        for(int i = 0; i < 20; i++) {
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = String.format("%s.%s@amigoscode.edu", firstName, lastName);
            int age = faker.number().numberBetween(17, 55);

            Student student = new Student(firstName, lastName, email, age);
            studentRepository.save(student);
            System.out.println("compteur : "+i+"  Student : "+student);
    }






            /// L'ancienne version de la methode runner pour ajouter des donnees dans la base de donnee
        /*
            return args -> {
                Student faycal = new Student("faycal", "Jose", "faycal.jose@gmail.com", 29);
                Student nasro = new Student("nasro", "Jose", "nasro.jose@gmail.com", 23);
                Student manel = new Student("manel", "draoua", "manel.draoua@gmail.com", 19);
                Student faycal2 = new Student("faycal", "derbouz", "faycal2.jose@gmail.com", 30);
                Student imane = new Student("imane", "kalbez", "imane.kalbez@gmail.com", 29);




                studentRepository.saveAll(List.of(faycal, nasro,manel,faycal2,imane));

                System.out.println("All Students: ");
                studentRepository.findAll().forEach(System.out::println);

                System.out.println("Student with ID 1: ");
                studentRepository.findById(1L).ifPresentOrElse(System.out::println,
                        () -> System.out.println("Student with ID 1 Not Found"));


                System.out.println("Total number of students: " + studentRepository.count());

                System.out.println("find by email: ");
                studentRepository.findStudentByEmail("faycal.jose@gmail.com")
                        .ifPresentOrElse(System.out::println,
                                () -> System.out.println("Student with email not existe"));

                studentRepository.findStudentByFirstNameEqualsAndAgeGreaterThanEqual("faycal",29)
                .forEach(System.out::println);

                System.out.println("find by Age");
                studentRepository.testTrouverLage(29).forEach(System.out::println);

                System.out.println("Fid by first and last name");
                System.out.println(studentRepository.findStudent("imane", "kalbez"));

                System.out.println("find all the students with the First Name X and age greater than 20 with testing Native Query");
                System.out.println(studentRepository.testTheNatiQuerytoreturnStudent("imane",20));


                System.out.println("Another way to find all the students with the First Name X and age greater than 20 with testing Native Query");
                studentRepository.testTheNatiQuerytoreturnStudent2("faycal",20)
                        .forEach(System.out::println);

                System.out.println("Supprimer un Student by ID");
                System.out.println("l'ID "+studentRepository.deleteStudentById(1L)+" a bien ete supprimer");

            };
            */
        }

}
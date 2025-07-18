package com.example.demo;

import com.github.javafaker.App;
import com.github.javafaker.Faker;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.sql.SQLOutput;
import java.time.LocalDateTime;
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
        CommandLineRunner runner (StudentRepository studentRepository,
                                  StudentIdCardRepository studentIdCardRepository,
                                  EnrolmentRepository enrolmentRepository,
                                  CourseRepository courseRepository) {

            return args -> {


                Student student = new Student(
                        "John",
                        "Doe",
                        "John.Doe@amigoscode.com",
                        20
                );

                StudentIdCard studentIdCard = new StudentIdCard(
                        "1234567890",
                        student
                );

                /** Si on remarque bien, on a pas besoin de faire studentRepository.save(student);
                 * Pourquoi ? tt simplement Parce que dans l'annotation @OneToOne, on a mis CascadeType.ALL,
                    c'est a dire a chaque fois que je veux ajoutrer un StudentIdCard, Student va etre ajouté automatiquement aussi.
                 */

                studentIdCardRepository.findById(1L)
                        .ifPresent(System.out::println);

                studentRepository.findById(1L)
                        .ifPresent(System.out::println);

                /*
                System.out.println("creartion dun nv student");

                student = new Student(
                        "faycal",
                        "Draoua",
                        "faycal.draouaamigoscode.com",
                        29
                );

                studentRepository.save(student);


                System.out.println("delete a student 1 ");

                studentRepository.deleteById(1L);

                System.out.println("delete a student 2 ");
                studentRepository.deleteById(2L);

                */


                System.out.println("add Book");

                Book book1 = new Book("Spring Boot", LocalDateTime.now());
                Book book2 = new Book("Java", LocalDateTime.now());

                student.addBook(book1);
                student.addBook(book2);

                /**
                 * On ajoutant seulement studentIdCard, par cascadeType.ALL, student sera aussi ajouté automatiquement,
                    et aussi par cascadeType.Persiste, Book sera aussi ajouté automatiquement.
                 */
                studentIdCardRepository.save(studentIdCard);

                ///  afficher les books du student
                System.out.println("affiche les books du student");
                studentRepository.findById(1L)
                        .ifPresent(s->{
                                System.out.println("Fetch Eager...");
                student.getBooks().forEach(book -> System.out.println(student.firstName + " - "+book.getBookName()));
                        });


                System.out.println("Modifier le numero de la carte ");


                studentIdCard.setCardNumber("19641199631");

                /**
                 * ici je vais modifer CardNumer dans l'instance Student, et puisque j'ai mis CascadeType.ALL, dans
                    la class Student sur l'attribut studentIdCard, alors StudentIdCard va etre modifié aussi.
                    Il suffit juste de faire studentIdCardRepository.save(studentIdCard), comme on a fait ci-dessous.
                 */
                student.setStudentIdCard(studentIdCard);


                studentRepository.save(student);

                /// juste pour tester si y'aura une erreur ou pas. apparemment non, il va verifier si le student
                /// existe deja ou pas. si oui, il va soit rien faire, soit le modifier, si non, il va l'ajouter.
                studentRepository.save(student);




//                System.out.println("Inscrir des Student a des cours");
//
//                Course course1 = new Course("Java","info");
//                Course course2 = new Course("Finance","ESG");
//
//                Student studentPaul = new Student(
//                        "Paul",
//                        "Smith",
//                        "Paul.smith@gmail.com",
//                        25);
//
//                studentPaul.enrolToCourse(course1);
//                studentPaul.enrolToCourse(course2);
//
//                studentRepository.save(studentPaul);
//
//                studentRepository.deleteById(2L);
//
//                System.out.println("les Cours sont : "+studentPaul.getCourses());

                System.out.println("Ajouter des enrolments pour le student");

                Course courJava = new Course("Java", "info");
                Course courseFinance = new Course("Finance", "ESG");

                courseRepository.saveAll(List.of(courJava, courseFinance));


                Enrolment enrolmentFinance = new Enrolment(student, courseFinance);
                Enrolment enrolmentJava = new Enrolment(student, courJava);

                student.addEnrolment(enrolmentJava);
                student.addEnrolment(enrolmentFinance);

                courseFinance.addEnrolment(enrolmentJava);
                courJava.addEnrolment(enrolmentFinance);

                enrolmentRepository.saveAll(List.of(enrolmentFinance, enrolmentJava));



            };

    }

}
package com.example.demo;

import jakarta.transaction.Transactional;
import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    /**
     *Le JPQL (Java Persistence Query Language) c'est quand on cre une methode comme celle de bas et qu'on lui donne
        une annotation @Query sur ca tete qui contient une requete JPQL, et cette requete JPQL est traduite en SQL
        par Spring Data JPA

     *On peut aussi utiliser des methodes de type "JPQL" en utilisant l'annotation @Query

     * Il faut savoir que le Query JPQL utilise le nom de la class et pas le nom de la table, et il utilise les noms
     des attributs de la class et pas les noms des colonnes de la table.

     */
     @Query("SELECT s FROM Student s WHERE s.age = ?1")
     List<Student> testTrouverLage(int age);

     @Query("SELECT s FROM Student s where s.firstName = ?1 AND s.lastName = ?2")
     Student findStudent(String firstName, String lastName);




    /**
     * Ici vous remarquer que la synxtaxe de la requette est la meme que celle du SQL, car vous voyez que le nom de la table
      est bien celui de la table dans la base de donnee, et pas le nom de la class, et les noms des colonnes sont
      bien ceux de la table dans la base de donnee, et pas les noms des attributs de la class.
      Donc comment ca marche ? Et bien c'est parce que j'ai utiliser le mot cle "nativeQuery = true" dans l'annotation @Query

     * Donc cette requete est une requete SQL native, et pas une requete JPQL.
     */

     @Query(value = "SELECT *FROM student where first_name = ?1  AND age >= ?2", nativeQuery = true) // ici on utilise une requete SQL native
     Student testTheNatiQuerytoreturnStudent(String firstName,int age);

     /// ici je vais Recrir la meme requete que celle de la methode testTheNatiQuerytoreturnStudent mais d'une autre maniere
        @Query(value = "SELECT * FROM student WHERE first_name = :FIRSTNAME AND age >= :AGE", nativeQuery = true)
     List<Student> testTheNatiQuerytoreturnStudent2(@Param("FIRSTNAME") String firstName, @Param("AGE") int age);

    /// A vous de choisir la methode que vous voulez utiliser, les deux sont valides.


    /**
     * @Modifying indique que cest une requete de modification (INSERT, UPDATE, DELETE) et non pas de lecture (SELECT).
        donc cette annotation est obligatoire pour les requetes qui modifient la base de donnee.

     * @Transactional indique que cette methode doit etre execute dans une transaction, et donc elle est obligatoire
        pour que si la methode echoue, toutes les modifications faites par cette methode seront annulées.
     */
    @Transactional
    @Modifying
    @Query("DELETE FROM Student s WHERE s.id= ?1")
    int deleteStudentById(Long id);



    /**Mais la, les methode que vous voyez (sans syntaxe JPQL) en bas c'est des methodes qu'on appele "JPA Query Methods" qui
        peuvent etre traduite grace a leur nom(mots cles) en requete SQL.

      *PS : voici un lien : https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html
      *        ou vous pouvez trouver les mots cles de JPA Query Methods

    */
    Optional<Student> findStudentByEmail(String email);

    List<Student> findStudentByFirstNameEqualsAndAgeGreaterThanEqual(String firstName, Integer age);

    /**
        PS:
     * Le methode qui utilisent JPQL Contrairement au methode qui utilisent JPA Query Methods, elles sont plus flexibles,
     Pour quoi ! parce qu'elles ont pas besoin d'utiliser les mots cles de JPA Query Methods.

     * L'utilite de SQL native Query et quand par exemple on a commencer le projet avec postgres ensuite on pense qu'on peux
        changer de base de donnee, et donc on utilise des requetes SQL natives, et donc on peut changer de base de donnee
        car cela ne va pas causer de probleme, peux importe la base de donnee qu'on va utiliser.

     * On fini ca avec un conseille:
       Utilisez JPQL le maximum possible et quand on a une requete complexe ou une requete qui n'est pas supportee par JPQL,
       dans ce cas la utilisez SQL native Query.

     SO : ✅ Bonnes pratiques

            🟢 Privilégie JPQL en premier.

            🟡 Utilise Query Methods pour les requêtes simples.

            🔴 Réserve SQL natif aux cas où JPQL ne suffit pas.
     */

}

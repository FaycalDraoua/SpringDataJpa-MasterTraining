package com.example.demo;

import jakarta.persistence.*;

/**
 *Le paramètre name dans @Entity est optionnel.
 *Il sert à définir le nom logique de l'entité dans JPQL (Java Persistence Query Language).
 */
@Entity(name = "Student")
/**
 *Cette annotation mappe l'entité à une table réelle dans ta base de données.
 *name = "student" désigne le nom physique de la table SQL.
 *Si tu ne mets pas @Table, Spring utilisera le nom de la classe par défaut "student" donc tous en miniscules
 */
@Table(name = "student",
        uniqueConstraints = {
                @UniqueConstraint(name = "student_email_unique", columnNames = "email")
        })
public class Student {

    @Id
    @SequenceGenerator(
            name = "student_id_seq",
            sequenceName = "student_id_seq",
            initialValue = 1,
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "student_id_seq")
    @Column(name = "id",
            nullable = false,
            updatable = false,
            columnDefinition = "BIGINT")
    private Long id;

    @Column(name ="first_name",
            nullable = false,
            columnDefinition = "TEXT")
    String firstName;

    @Column(name="last_name",
            nullable = false,
            columnDefinition = "TEXT")
    String lastName;

    @Column(name="email",
            nullable = false,
            columnDefinition = "TEXT")
    String email;

    @Column(name="age",
            nullable = false,
            columnDefinition = "INTEGER")
    int age;

    public Student() {
    }

    public Student(String firstName, String lastName, String email, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }
}

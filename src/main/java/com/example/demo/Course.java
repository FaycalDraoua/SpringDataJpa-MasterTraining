package com.example.demo;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "Course")
@Table(name = "course")
public class Course {

    @Id
    @SequenceGenerator(
            name = "course_sequence",
            sequenceName = "course_sequence_id",
            initialValue = 1,
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "course_sequence"
    )
    private Long id;

    @Column(
            name = "name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String name;

    @Column(
            name = "departement",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String departement;


    /// ----- l'ancien code pour la relation ManyToMany entre Student et course-----
    /**
     * mappedBy = "courses" : cela signifie que c’est Student qui possède la relation.

     * On ne redéfinit pas @JoinTable ici car cette classe est le côté inverse de la relation.
     */
//    @ManyToMany(mappedBy = "courses", cascade = CascadeType.ALL)
//    private List<Student> students = new ArrayList<>();
    /// ----- FIN de l'ancien code pour la relation ManyToMany entre Student et course-----

    @OneToMany(
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            mappedBy = "course"
    )
    private List<Enrolment> enrolments = new ArrayList<>();

    public Course() {
    }

    public Course(String name, String departement) {
        this.name = name;
        this.departement = departement;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartement() {
        return this.departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

//    public List<Student> getStudents() {
//        return students;
//    }


    public List<Enrolment> getEnrolments() {
        return enrolments;
    }

    public void addEnrolment(Enrolment enrolment) {
        if (!this.enrolments.contains(enrolment)) {
            this.enrolments.add(enrolment);
        }
    }

    public void removeEnrolment(Enrolment enrolment) {
        if (this.enrolments.contains(enrolment)) {
            this.enrolments.remove(enrolment);
        }
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", departement='" + departement + '\'' +
                '}';
    }
}

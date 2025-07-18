package com.example.demo;

import jakarta.persistence.*;

@Entity(name = "Enrolment")
@Table(name = "enrolment")
public class Enrolment {

    /**
     * EmbeddedId : Signifie que l’attribut id est une clé primaire composée de (student_id,course_id).

     * EnrolmentId est une classe Java annotée avec @Embeddable contenant deux champs : studentId et courseId.

     * Hibernate utilisera les valeurs de studentId et courseId comme clé primaire de la table enrolment.

      */
    @EmbeddedId
    private EnrolmentId id;

    /**
     * ManyToMany:

     * MapsId : on va dire a JPA "Va chercher l'id de student et ajoute le dans le champ id.studentId de EnrolmentId. Avec
       ça, on a pu deja remplir la moitié de la clé primaire composée(student_id,course_id) de EnrolmentId. Et on va faire
       pareil pour courseId (voir en bas).

     * JoinColumn :
       - name , pour dire a JPA qu'on veut une columne qui s'appelle student_id dans la table enrolment,
       et qui qui va jouer le role de Fk , relie a la cle primere id de la table student(par default, si on ne precise pas)
       - foreignKey , pour donner un nom explicite à la contrainte de clé étrangère dans ta base de données.
       Cela ne change rien fonctionnellement, mais c’est utile pour la clarté, la maintenance et le débogage.

     */
    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id",
                foreignKey = @ForeignKey(name = "enrolment_student_id_fk")
    )
    private Student student;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "course_id",
                foreignKey = @ForeignKey(name = "enrolment_course_id_fk")
    )
    private Course course;

    public Enrolment(Student student, Course course) {
        this.student = student;
        this.course = course;
        this.id = new EnrolmentId(student.getId(), course.getId());
    }

    public Enrolment() {
    }

    public EnrolmentId getId() {
        return id;
    }

    public void setId(EnrolmentId id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}

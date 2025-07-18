package com.example.demo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;


/**
     Pourquoi utiliser cette solution avec EnrolmentId @Embeddable  ??
         Parce que :
             ✅ Tu veux un ManyToMany
             ✅ Avec données supplémentaires dans la table de jointure
             ✅ Et avec une clé primaire composite
 */


@Embeddable
public class EnrolmentId implements Serializable {

    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "course_id")
    private Long courseId;

    public EnrolmentId() {
    }

    public EnrolmentId(Long studentId, Long courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }


    /// pour la class Annote avec @Embeddable, il faut obligatoirement generer equals et hashCode.

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EnrolmentId that = (EnrolmentId) o;
        return Objects.equals(studentId, that.studentId) && Objects.equals(courseId, that.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, courseId);
    }
}




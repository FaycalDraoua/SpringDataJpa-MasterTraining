package com.example.demo;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity(name ="Book")
@Table(name = "book")
public class Book {

    @Id
    @SequenceGenerator(
            name = "book_seq",
            sequenceName = "book_seq_id",
            allocationSize = 1,
            initialValue = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "book_seq"
    )
    @Column(
            name = "id",
            nullable = false,
            updatable = false,
            columnDefinition = "BIGINT"
    )
    private int id;

    @Column(
            name ="book_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String bookName;

    @Column(
            name = "created_at",
            nullable = false,
            columnDefinition = "TIMESTAMP WITHOUT TIME ZONE"
    )
    private LocalDateTime createdAT;

    /// ici ManyToOne psq, plusieur Book peuvent appartenit a un seul Student, donc plusieur "Book" to "un seul Student".
    /// Une petite astuce : le premier mot comme ici "Many" reviens toujour a la class ici presente, dans ce cas "Book".
    @ManyToOne
    @JoinColumn(
            name = "student_id",
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey(name = "student_book_fk")
    )
    private Student student;

    public Book() {}

    public Book(String bookName, LocalDateTime createdAT) {
        this.bookName = bookName;
        this.createdAT = createdAT;
    }

    public int getId() {
        return id;
    }

    public String getBookName() {
        return bookName;
    }

    public LocalDateTime getCreatedAT() {
        return createdAT;
    }

    public Student getStudent() {
        return student;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setCreatedAT(LocalDateTime createdAT) {
        this.createdAT = createdAT;
    }

    public void setStudent(Student student) {
        this.student = student;
    }


    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", bookName='" + bookName + '\'' +
                ", createdAT=" + createdAT +
                ", student=" + student +
                '}';
    }
}

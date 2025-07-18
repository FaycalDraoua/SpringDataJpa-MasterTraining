package com.example.demo;

import jakarta.persistence.*;

@Entity(name = "StudentIdCard")
@Table(
        name = "student_id_card",
            uniqueConstraints = {
                @UniqueConstraint(name = "student_card_number_unique", columnNames = "card_number")
            })
public class StudentIdCard {

    @Id
    @SequenceGenerator(
            name = "student_card_id_seq",
            sequenceName = "student_id_card_id_seq",
            initialValue = 1,
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_card_id_seq")
    @Column(name = "id",
            nullable = false,
            updatable = false,
            columnDefinition = "BIGINT")
    private Long id;

    @Column(name = "card_number",
            nullable = false,
            columnDefinition = "VarChar(20)")
    private String cardNumber;

    /** FetchType.EAGER: Dans la Relation OneToOne, Par defaut c'est du Eager(meme si on le mentionne pas),
        et ça sa sert a loader tous les objets liés (Objet parent et enfant) en une seule requete.
         Par exemple ici j'ai demande select *from student_id_card...
         les informations de l'objet Student qui est un des attribut de StudentIdCrd seront aussi chargées
         en une seule requete.

     PS: dans la relation OneToOne, peux importe de quelle cote(class) on mettera le FetchType.EAGER, ou LAZY,
     ca vas s'appliquer des deux cotes. Contrairement à la relation OneToMany, ou ManyToOne, ou le FetchType.EAGER ou lAZY
     (qui est par default) on doit le mettre du cote OneToMany donc du cote de la Liste<>.


     * Cascade = CascadeType.ALL :
        veux dire que Toutes les opérations sur StudentIdCard (persist, delete, update…) seront aussi appliquées à student automatiquement.
         Autrement dit si je vais ajouter un numero de carte avec un objet student qui n'existe pas encore dans la
          base de donnees, et bin il va etre ajouter automatiquement, par Cascading.

     PS : Persiste = Sauvgarde , Merge = Update

     * PS: Donc en gros Fetch est pour le Join dans le Select, et Cascade est pour le Persist(save), delete, update, Merge...

     * foreignKey = @ForeignKey(name = "student_id_fk") : afin de renomme le cle etrangere dans la Bd,
        cest une Tres Bonne pratique mieux que d'a voir un nom randome dans la Bd pour le Foreign Key

     * name : pour dire a JPA, dans la table stuIdCard, on veux un columne qui s'appelle student_id.

     * referencedColumnName : pour indiquer a JPA que cette column est relie avec clé primaire de Student (par défaut : id)
       donc on peux ne pas la mettre. Par contre elle sera obligatoir si on veut la relie avec une autre column que le cle primaire.
     * */
    @OneToOne(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
              )
    @JoinColumn(
            name = "student_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "student_id_fk")
    )
    private Student student;




    public StudentIdCard(String cardNumber, Student student) {
        this.cardNumber = cardNumber;
        this.student = student;
    }

    public StudentIdCard(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public StudentIdCard() {
    }

    public Long getId() {
        return id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public Student getStudent() { return this.student; }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setStudent(Student student) { this.student = student; }

    @Override
    public String toString() {
        return "StudentIdCard{" +
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                '}';
    }
}

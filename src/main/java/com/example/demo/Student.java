package com.example.demo;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

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


    /**
     * mappedBy = "student" : sert a mapper l'instance de student creer dans la bd et trouver son
        studentIdCard(si il se trouve), et pouvoir meme modifier le studentIdCard associer. Et Toute ca
         est possible sans creer une autre cle etrangere ici dans student vers StudentIdCard(comme on a fait dans
          StudentIdCard vers Student) Grace a mappedBy = "student"

     * Et Cela permet de creer ce qu'on appele une RELATION BIDIRECTIONNEL.

     * "student" : fait reference a l'attribut student declarer dans la class StudentIdCard.

     *PS : la notion de UNIDIRECTIONNEL et BIDIRECTIONNEL est assez simple avec les relation OneToOne
            par contre elle devient un peux plus complique pour Hibernate dans les Relation OneToMany ou ManyToMany

     * orphanRemoval = true : on est bien d'accord que une carte Student ne peux etre creer sans que le student
        est deja creer, car la carte dois etre associer a un student, ok ! donc imaginez que je veux supprimer
        ce student ! et bin logiquement je dois supprimer aussi ce studentIdCard qui est asscier a ce student
        Right !! donc cest a ce moment la que "orphanRemoval = true" entre en jeux.

    orphanRemoval = true, elle va aller verifier si ce student est associer avec une student Card, si oui !
    Alors elle va suprimer le student et la student card.

    Sans orphanRemoval = true , si le student est assicier a un student card alors rien ne va etre supprimer,
    si non si le student n'est associer avec aucune student card, la le student peux etre supprimer (meme sans orphanRemoval = true)

     * Cascade = CascadeType.ALL :
        veux dire que Toutes les opérations sur Student (persist, delete, update…) seront aussi appliquées à studentIdCard
        automatiquement
     */
    @OneToOne(mappedBy = "student",
             orphanRemoval = true,
             cascade = {CascadeType.ALL})
    private StudentIdCard studentIdCard;

    /** ici OneToMany psq , un Student peux avoir plusieur Books. donc "un student" to "plusieur Book".
     Une Petite astuce : le premier mot "One" comme ici, reviens toujours a la class ici presente donc "student" dans ce cas.

     * cascade = {CascadeType.PERSIST, CascadeType.REMOVE} : cest pour dire que si je veux ajouter(persister) ou
        supprimer(remove) un student, les Book associer vont aussi etre ajouter(si elle existe pas) ou supprimer(si elle existe pas)

     * FetchType.LAZY : est deja la valeur par defaut pour la relation OneToMany, ManyToOne, Et LAZY contrairement a EAGER
       signifie que aucune donnee liee a cette relation student (parent ou enfant) ne doit etre charger. donc aucun Book liee
       student ne dois etre charge, et ce comportement est logique car imaginez quil y'a un gros nombre de Book associer
      a un student (donc un gros volume de donnees) ca devient tres lourd.

      PS : quand on la reletion OneToMany, ManyToOne, on utilise toujours FetchType.LAZY du cote de la List<>,
           ca veux dire du cote OneToMany, Et dans ce cas ca sera du cote Student, Contrairement a la relation
           OneToOne, ou le cote n'a pas d'importance.

     * */
    @OneToMany(mappedBy = "student",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY
            )
    private List<Book> books =   new ArrayList<>();



    /// ------------------- l'ancienne solution pour la relation ManyToMany entre Student et Course ------------------- ///

    /** ----------------Cette Solution est utiliser quand on veux creer une table intermediaire pour la relation
                      ManyToMany entre Student et Course, et que cette table intermediaire contient uniquement
                        la cle unique (student,course) et pas dautre attribut(champ) de plus-------------------
                        si non, ca sera la solution ci-dessou **/

    /**
     * @ManyToMany : un étudiant peut avoir plusieurs cours.

     * @JoinTable(...) : on dit à JPA de créer une table intermédiaire appelée enrolment.

     * @JoinColumn(name = "student_id", ...) : indique que c’est la colonne student_id dans la table enrolment.
        Et comme on est dans la class Student et que cette annotation est utilisée dans la classe Student, cela signifie que  :
        👉 Cette colonne est la clé étrangère vers la table student.

     * inverseJoinColumns = @JoinColumn(name = "course_id", ...) : indique que c’est la colonne course_id dans la table enrolment.
       Et comme on est dans la classe Student, cette colonne est l'autre côté de la relation, donc elle pointe vers Course.

     PS : la Regle de JPA: Le côté qui contient l’annotation @JoinTable est le propriétaire de la relation.
          l'autre cote cest celui qui contient @ManyToMany ou autre relation avec le (mappedBy = "....")
     */
//    @ManyToMany(cascade = {CascadeType.PERSIST })
//    @JoinTable(
//            name = "enrolment",
//            joinColumns = @JoinColumn(
//             name = "student_id",
//             foreignKey = @ForeignKey(name = "enrolement_student_id_fk")
//            ),
//            inverseJoinColumns = @JoinColumn(
//                    name = "course_id",
//                    foreignKey = @ForeignKey(name = "enrolement_course_id_fk")
//            ),
//            uniqueConstraints = {
//                    @UniqueConstraint(name = "enrolment_student_course_unique",
//                            columnNames = {"student_id", "course_id"})
//            }
//    )
//    private List<Course> courses =   new ArrayList<>();

/// -------------------- FIN de l'ancienne solution pour la relation ManyToMany entre Student et Course ------------------- ///


/// ------- Nouvelle solution pour la relation ManyToMany entre Student et Course avec Enrolment ------ ///

    @OneToMany(
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            mappedBy = "student"
    )
    private List<Enrolment> enrolments =   new ArrayList<>();


    public Student() {
    }

    public Student(String firstName, String lastName, String email, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;

    }

    public Student(String firstName, String lastName, String email, int age, StudentIdCard studentIdCard) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
        this.studentIdCard = studentIdCard;
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

    public StudentIdCard getStudentIdCard(StudentIdCard studentIdCard) { return studentIdCard;}

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

    public void setStudentIdCard(StudentIdCard studentIdCard) {
        this.studentIdCard = studentIdCard;
    }

    public void

    addBook(Book book) {
        if(!this.books.contains(book)){
            this.books.add(book);
            book.setStudent(this);
        }
    }

    public void removeBook(Book book) {
        if(this.books.contains(book)){
            this.books.remove(book);
            book.setStudent(null);
        }
    }

    public List<Book> getBooks() {
        return books;
    }

//    public void enrolToCourse(Course course){
//        courses.add(course);
//        course.getStudents().add(this);
//    }
//
//    public void unEnrolToCourse(Course course){
//        courses.remove(course);
//        course.getStudents().remove(this);
//    }
//
//    public List<Course> getCourses() {
//        return courses;
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
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", studentIdCard=" + studentIdCard +
                '}';
    }
}

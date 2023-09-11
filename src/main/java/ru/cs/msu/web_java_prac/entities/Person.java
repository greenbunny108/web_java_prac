package ru.cs.msu.web_java_prac.entities;

import ru.cs.msu.web_java_prac.types.GenderEnum;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender_type")
    private GenderEnum gender;

    @Column(name = "birth_date")
    private LocalDateTime birthDate;

    @Column(name = "death_date")
    private LocalDateTime deathDate;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

}
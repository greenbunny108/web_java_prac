package ru.cs.msu.web_java_prac.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;
import java.time.LocalDateTime;

@Entity
@Table(name = "person")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Person implements Common<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NonNull
    @Column(name = "first_name")
    private String firstName;

    @NonNull
    @Column(name = "last_name")
    private String lastName;

    @NonNull
    @Column(name = "gender_type")
    private String gender;

    @NonNull
    @Column(name = "birth_date")
    private LocalDateTime birthDate;

    @NonNull
    @Column(name = "death_date")
    private LocalDateTime deathDate;

    @NonNull
    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;


    public Integer getBirthYear() {
        return this.birthDate.getYear();
    }

    public Integer getDeathYear() {
        return this.deathDate.getYear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person other = (Person) o;
        return Objects.equals(id, other.id)
                && firstName.equals(other.firstName)
                && gender.equals(other.gender)
                && Objects.equals(birthDate, other.birthDate)
                && Objects.equals(deathDate, other.deathDate);
    }

}
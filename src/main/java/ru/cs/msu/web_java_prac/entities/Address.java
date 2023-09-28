package ru.cs.msu.web_java_prac.entities;
import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "address")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Address implements Common<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "street_address")
    private String streetAddress;

    @Column(name = "city")
    @NonNull
    private String city;

    @Column(name = "state_province")
    @NonNull
    private String stateProvince;

    @Column(name = "country")
    private String country;

}
package ru.cs.msu.web_java_prac.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "relation")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Relation implements Common<Long> {

    public enum RelationType {
        CHILD,
        SPOUSE,
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person1_id")
    @NonNull
    private Person person1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person2_id")
    @NonNull
    private Person person2;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "relationship_type")
    @NonNull
    private RelationType relationshipType;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;
}
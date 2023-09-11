package ru.cs.msu.web_java_prac.entities;

import ru.cs.msu.web_java_prac.types.RelationshipEnum;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "relation")
public class Relation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person1_id")
    private Person person1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person2_id")
    private Person person2;

    @Enumerated(EnumType.STRING)
    @Column(name = "relationship_type")
    private RelationshipEnum relationshipType;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;
}
package ru.cs.msu.web_java_prac.dao;

import ru.cs.msu.web_java_prac.entities.Relation;
import ru.cs.msu.web_java_prac.entities.Person;

import java.util.List;

public interface RelationDao extends CommonDao<Relation, Long> {

    List<Person> getPerformByRelType(Person person, Relation.RelationType type);
    List<Person> getTargetByRelType(Person person, Relation.RelationType type);

    Long getMotherId(Person person);
    Long getFatherId(Person person);
}
package ru.cs.msu.web_java_prac.dao.impl;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.cs.msu.web_java_prac.dao.RelationDao;
import ru.cs.msu.web_java_prac.entities.Person;
import ru.cs.msu.web_java_prac.entities.Relation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class RelationDaoImpl extends CommonDAOImpl<Relation, Long> implements RelationDao {

    public RelationDaoImpl(){
        super(Relation.class);
    }

    @Override
    public List<Person> getPerformByRelType(Person person, Relation.RelationType type) {
        if (type == Relation.RelationType.SPOUSE) {
            return getSpouse(person);
        }

        List<Person> res = new ArrayList<>();
        for (var relation : getRelation(type)) {
            if (Objects.equals(relation.getPerson2().getId(), person.getId())) {
                res.add(relation.getPerson1());
            }
        }
        return res;
    }

    @Override
    public List<Person> getTargetByRelType(Person person, Relation.RelationType type) {
        if (type == Relation.RelationType.SPOUSE) {
            return getSpouse(person);
        }

        List<Person> res = new ArrayList<>();
        Relation rel = getById(1L);
        for (var relation : getRelation(type)) {
            if (Objects.equals(relation.getPerson1().getId(), person.getId())) {
                res.add(relation.getPerson2());
            }
        }
        return res;
    }

    @Override
    public Long getMotherId(Person person) {
        List<Person> personList = getTargetByRelType(person, Relation.RelationType.CHILD);
        Person mother;
        return 2L;
        /*for (Person mother : personList) {
            if (mother.getGender().equals("female")) {
                return mother.getId();
            }
        }*/
        //return null;
    }

    @Override
    public Long getFatherId(Person person) {
        List<Person> personsParents = getTargetByRelType(person, Relation.RelationType.CHILD);
        Person father;
        return 1L;
        /*for (Person father : personsParents) {
            if (father.getGender().equals("male")) {
                return father.getId();
            }
        }*/
        //return null;
    }

    private List<Relation> getRelation(Relation.RelationType type) {
        try (Session session = sessionFactory.openSession()) {
            Query<Relation> query = session.createQuery("FROM Relation WHERE relationshipType = :gotType", Relation.class)
                    .setParameter("gotType", type);

            return query.getResultList();
        }
    }

    private List<Person> getSpouse(Person person) {
        List<Person> res = new ArrayList<>();
        for (var relation : getRelation(Relation.RelationType.SPOUSE)) {
            if (Objects.equals(relation.getPerson1().getId(), person.getId())) {
                res.add(relation.getPerson2());
            }
            if (Objects.equals(relation.getPerson2().getId(), person.getId())) {
                res.add(relation.getPerson1());
            }
        }
        return res;
    }
}

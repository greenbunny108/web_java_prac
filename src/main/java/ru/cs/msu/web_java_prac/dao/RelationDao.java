package ru.cs.msu.web_java_prac.dao;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.cs.msu.web_java_prac.entities.Relation;

@Repository
public class RelationDao extends CommonDao<Relation> {

    public RelationDao(SessionFactory sessionFactory) {
        super(sessionFactory, Relation.class);
    }
}
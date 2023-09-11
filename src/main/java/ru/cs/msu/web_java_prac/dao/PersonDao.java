package ru.cs.msu.web_java_prac.dao;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.cs.msu.web_java_prac.entities.Person;

@Repository
public class PersonDao extends CommonDao<Person> {

    public PersonDao(SessionFactory sessionFactory) {
        super(sessionFactory, Person.class);
    }
}
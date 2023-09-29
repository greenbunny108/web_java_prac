package ru.cs.msu.web_java_prac.dao.impl;

import antlr.StringUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.cs.msu.web_java_prac.dao.PersonDAO;
import ru.cs.msu.web_java_prac.entities.Address;
import ru.cs.msu.web_java_prac.entities.Person;
import ru.cs.msu.web_java_prac.entities.Relation;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.spi.PersistenceUnitInfo;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonDAOImpl extends CommonDAOImpl<Person, Long> implements PersonDAO {

    public PersonDAOImpl(){
        super(Person.class);
    }

    @Override
    public List<Person> getAllPersonByName(String personName) {
        try (Session session = sessionFactory.openSession()) {
            Query<Person> query = session.createQuery("FROM Person WHERE firstName LIKE :gotName",
                            Person.class)
                    .setParameter("gotName", likeExpr(personName));
            return query.getResultList().size() == 0 ? null : query.getResultList();
        }
    }

    @Override
    public Person getSinglePersonByName(String personName) {
        List<Person> candidates = this.getAllPersonByName(personName);
        return candidates == null ? null :
                candidates.size() == 1 ? candidates.get(0) : null;
    }

    @Override
    public Integer getYearsOfLife(Person person) {
        return (person.getDeathDate().getYear() -
                person.getBirthDate().getYear());
    }

    @Override
    public List<Person> getByFilter(Filter filter) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Person> criteriaQuery = builder.createQuery(Person.class);
            Root<Person> root = criteriaQuery.from(Person.class);
            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public String getDynastyName(Person person) {
        String ret = person.getLastName();
        if (ret.substring(ret.length() - 1).equals("а")) {
            ret = ret.substring(0, ret.length() - 1);
        }
        return (ret + "ы");
    }

    private String likeExpr(String param) {
        return "%" + param + "%";
    }
}

package ru.cs.msu.web_java_prac;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.cs.msu.web_java_prac.dao.AddressDao;
import ru.cs.msu.web_java_prac.dao.PersonDAO;
import ru.cs.msu.web_java_prac.dao.RelationDao;
import ru.cs.msu.web_java_prac.entities.Address;
import ru.cs.msu.web_java_prac.entities.Person;
import ru.cs.msu.web_java_prac.entities.Relation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
public class RelationTest {

    @Autowired
    private PersonDAO personDAO;
    @Autowired
    private RelationDao relationDAO;
    @Autowired
    private AddressDao addressDao;
    @Autowired
    private SessionFactory sessionFactory;

    @Test
    void testChild() {
        List<Person> personsParents = relationDAO.getTargetByRelType(
                personDAO.getSinglePersonByName("Николай I"),
                Relation.RelationType.CHILD
        );

        assertEquals(2, personsParents.size());

            }

    @Test
    void testSpouse() {
        List<Person> personSpouse = relationDAO.getTargetByRelType(
                personDAO.getSinglePersonByName("Павел I"),
                Relation.RelationType.SPOUSE
        );

        assertEquals(1, personSpouse.size());
        assertEquals(personDAO.getSinglePersonByName("Мария Фёдоровна").getId(), personSpouse.get(0).getId());

    }

    @Test
    void testParents() {
        Person nik = personDAO.getSinglePersonByName("Николай I");
        assertEquals(personDAO.getSinglePersonByName("Павел I").getId(),
                relationDAO.getFatherId(nik));
        assertEquals(personDAO.getSinglePersonByName("Мария Фёдоровна").getId(),
                relationDAO.getMotherId(nik));
    }

    @BeforeEach
    void beforeEach() {
        List<Person> personList = new ArrayList<>();




        personList.add(new Person(1L, "Павел I", "Романов", "male",
                LocalDateTime.of(1754, Month.SEPTEMBER, 20, 0, 0, 0),
                LocalDateTime.of(1801, Month.MARCH, 23, 0, 0, 0),
                "", addressDao.getAddressByName("Михайловский замок")));
        personList.add(new Person(2L, "Мария Фёдоровна", "Романова", "female",
                LocalDateTime.of(1759, Month.OCTOBER, 25, 0, 0, 0),
                LocalDateTime.of(1828, Month.DECEMBER, 5, 0, 0, 0),
                "", addressDao.getAddressByName("Михайловский замок")));
        personList.add(new Person(3L, "Николай I", "Романов", "male",
                LocalDateTime.of(1796, Month.JULY, 6, 0, 0, 0),
                LocalDateTime.of(1855, Month.FEBRUARY, 18, 0, 0, 0),
                "", addressDao.getAddressByName("Зимний дворец")));

        personDAO.saveCollection(personList);

        List<Relation> relationList = new ArrayList<>();
        {
            relationList.add(new Relation(
                    null,
                    personDAO.getSinglePersonByName("Николай I"),
                    personDAO.getSinglePersonByName("Павел I"),
                    Relation.RelationType.CHILD,
                    LocalDateTime.of(1796, Month.JULY, 6, 0, 0, 0),
                    LocalDateTime.of(1801, Month.MARCH, 23, 0, 0, 0)
            ));

            relationList.add(new Relation(
                    null,
                    personDAO.getSinglePersonByName("Николай I"),
                    personDAO.getSinglePersonByName("Мария Фёдоровна"),
                    Relation.RelationType.CHILD,
                    LocalDateTime.of(1796, Month.JULY, 6, 0, 0, 0),
                    LocalDateTime.of(1828, Month.DECEMBER, 5, 0, 0, 0)
            ));

            relationList.add(new Relation(
                    null,
                    personDAO.getSinglePersonByName("Павел I"),
                    personDAO.getSinglePersonByName("Мария Фёдоровна"),
                    Relation.RelationType.SPOUSE,
                    LocalDateTime.of(1796, Month.JULY, 6, 0, 0, 0),
                    LocalDateTime.of(1801, Month.MARCH, 23, 0, 0, 0)
            ));
        }

        relationDAO.saveCollection(relationList);


    }

    @BeforeAll
    @AfterEach
    void annihilation() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE person RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("TRUNCATE relation RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE person_id_seq RESTART WITH 1;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE relation_id_seq RESTART WITH 1;").executeUpdate();
            session.getTransaction().commit();
        }
    }
}

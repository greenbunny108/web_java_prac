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

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
public class PersonTest {

    @Autowired
    private PersonDAO personDAO;
    @Autowired
    private AddressDao addressDao;
    @Autowired
    private SessionFactory sessionFactory;

    @Test
    void testSimple() {
        List<Person> personListAll = (List<Person>) personDAO.getAll();
        assertEquals(1, personListAll.size());

        List<Person> paulQuery = personDAO.getAllPersonByName("Павел");

        assertEquals(1, paulQuery.size());
        assertEquals("Павел I", paulQuery.get(0).getFirstName());

        assertEquals(1801 - 1754, personDAO.getYearsOfLife(personDAO.getSinglePersonByName("Павел")));

        Person personId3 = personDAO.getById(1L);
        assertEquals(1, personId3.getId());

        Person personNotExist = personDAO.getById(100L);
        assertNull(personNotExist);
    }

    @Test
    void testUpdate() {
        LocalDateTime birth = LocalDateTime.of(1754, Month.SEPTEMBER, 20, 0, 0, 0);
        LocalDateTime death = LocalDateTime.of(1801, Month.MARCH, 23, 0, 0, 0);

        Person updatePerson = personDAO.getSinglePersonByName("Павел");
        updatePerson.setBirthDate(birth);
        updatePerson.setDeathDate(death);
        personDAO.update(updatePerson);

        Person paul = personDAO.getSinglePersonByName("Павел I");
        assertEquals(birth, paul.getBirthDate());
        assertEquals(death, paul.getDeathDate());
    }

    @Test
    void testDelete() {
        Person deletePerson = personDAO.getSinglePersonByName("Павел I");
        personDAO.delete(deletePerson);

        Person paul = personDAO.getSinglePersonByName("Павел I");
        assertNull(paul);
    }
    @BeforeEach
    void beforeEach() {
        LocalDateTime birth = LocalDateTime.of(1754, Month.SEPTEMBER, 20, 0, 0, 0);
        LocalDateTime death = LocalDateTime.of(1801, Month.MARCH, 23, 0, 0, 0);

        List<Person> personList = new ArrayList<>();
        Address spb = addressDao.getAddressByName("Зимний дворец");
        personList.add(new Person(1L, "Павел I", "Романов",
                "male", birth, death,  "", spb));
        personDAO.saveCollection(personList);
    }

    @BeforeAll
    @AfterEach
    void annihilation() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE person RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE person_id_seq RESTART WITH 1;").executeUpdate();
            session.getTransaction().commit();
        }
    }

}

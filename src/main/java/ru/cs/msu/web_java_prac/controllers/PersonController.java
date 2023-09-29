package ru.cs.msu.web_java_prac.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.cs.msu.web_java_prac.dao.PersonDAO;
import ru.cs.msu.web_java_prac.dao.AddressDao;
import ru.cs.msu.web_java_prac.dao.RelationDao;
import ru.cs.msu.web_java_prac.dao.impl.PersonDAOImpl;
import ru.cs.msu.web_java_prac.dao.impl.AddressDaoImpl;
import ru.cs.msu.web_java_prac.dao.impl.RelationDaoImpl;

import ru.cs.msu.web_java_prac.entities.Person;
import ru.cs.msu.web_java_prac.entities.Address;
import ru.cs.msu.web_java_prac.entities.Relation;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import java.util.List;

@Controller
public class PersonController {

    @Autowired
    private final PersonDAO personDAO = new PersonDAOImpl();

    @Autowired
    private final AddressDao addressDao = new AddressDaoImpl();

    @Autowired
    private final RelationDao relationDAO = new RelationDaoImpl();

    @GetMapping("/persons")
    public String peopleListPage(Model model) {
        List<Person> people = (List<Person>) personDAO.getAll();
        model.addAttribute("people", people);
        model.addAttribute("personService", personDAO);
        model.addAttribute("relationService", relationDAO);
        return "persons";
    }

    @GetMapping("/person")
    public String personPage(@RequestParam(name = "personId") Long personId, Model model) {
        Person person = personDAO.getById(personId);

        if (person == null) {
            model.addAttribute("error_msg", "В базе нет человека с ID = " + personId);
            return "errorPage";
        }

        model.addAttribute("person", person);
        model.addAttribute("personService", personDAO);
        model.addAttribute("addressService", addressDao);
        model.addAttribute("relationService", relationDAO);
        return "person";
    }

    @GetMapping("/editPerson")
    public String editPersonPage(@RequestParam(name = "personId", required = false) Long personId, Model model) {
        if (personId == null) {
            model.addAttribute("person", new Person());
            model.addAttribute("personService", personDAO);
            model.addAttribute("addressService", addressDao);
            return "editPerson";
        }

        Person person = personDAO.getById(personId);

        if (person == null) {
            model.addAttribute("error_msg", "В базе нет человека с ID = " + personId);
            return "errorPage";
        }

        model.addAttribute("person", person);
        model.addAttribute("personService", personDAO);
        model.addAttribute("addressService", addressDao);
        return "editPerson";
    }

    @PostMapping("/savePerson")
    public String savePersonPage(@RequestParam(name = "firstName") String firstName,
                                 @RequestParam(name = "lastName") String lastName,
                                 @RequestParam(name = "gender") String gender,
                                 @RequestParam(name = "birthDate", required = false) LocalDateTime birthDate,
                                 @RequestParam(name = "deathDate", required = false) LocalDateTime deathDate,
                                 @RequestParam(name = "description", required = false) String desc,
                                 Model model) {

        Person person = new Person(firstName, lastName, gender, birthDate, deathDate, "");
        personDAO.save(person);

        return String.format("redirect:/persons?personId=%d", person.getId());
    }

    @PostMapping("/removePerson")
    public String removePersonPage(@RequestParam(name = "personId") Long personId) {
        personDAO.deleteById(personId);
        return "redirect:/persons";
    }
}
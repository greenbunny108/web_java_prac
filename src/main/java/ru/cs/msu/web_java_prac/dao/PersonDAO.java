package ru.cs.msu.web_java_prac.dao;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

import ru.cs.msu.web_java_prac.entities.Address;
import ru.cs.msu.web_java_prac.entities.Person;

public interface PersonDAO extends CommonDao<Person, Long> {

    List<Person> getAllPersonByName(String personName);

    Person getSinglePersonByName(String personName);
    Integer getYearsOfLife(Person person);
    String getDynastyName(Person person);
    List<Person> getByFilter(Filter filter);

    @Builder
    @Getter
    class Filter {
        private String name;
    }

    static Filter.FilterBuilder getFilterBuilder() {
        return Filter.builder();
    }
}
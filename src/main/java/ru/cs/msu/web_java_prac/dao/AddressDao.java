package ru.cs.msu.web_java_prac.dao;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.cs.msu.web_java_prac.entities.Address;
import ru.cs.msu.web_java_prac.entities.Person;

import java.util.List;

public interface AddressDao extends CommonDao<Address, Long> {

    Address getAddressByName(String addressName);
}
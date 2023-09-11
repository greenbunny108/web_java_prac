package ru.cs.msu.web_java_prac.dao;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.cs.msu.web_java_prac.entities.Address;

@Repository
public class AddressDao extends CommonDao<Address> {

    public AddressDao(SessionFactory sessionFactory) {
        super(sessionFactory, Address.class);
    }
}
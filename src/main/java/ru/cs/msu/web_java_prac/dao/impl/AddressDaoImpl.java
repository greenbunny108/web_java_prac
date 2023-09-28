package ru.cs.msu.web_java_prac.dao.impl;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.cs.msu.web_java_prac.dao.AddressDao;
import ru.cs.msu.web_java_prac.entities.Address;
import ru.cs.msu.web_java_prac.entities.Address;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.List;

@Repository
public class AddressDaoImpl extends CommonDAOImpl<Address, Long> implements AddressDao {

    public AddressDaoImpl() {
        super(Address.class);
    }

    @Override
    public Address getAddressByName(String addressName) {
        try (Session session = sessionFactory.openSession()) {
            Query<Address> query = session.createQuery("FROM Address WHERE streetAddress LIKE :gotName", Address.class)
                    .setParameter("gotName", likeExpr(addressName));
            List<Address> candidates = query.getResultList().size() == 0 ? null : query.getResultList();
            return candidates == null ? null :
                    candidates.size() == 1 ? candidates.get(0) : null;
        }
    }

    private String likeExpr(String param) {
        return "%" + param + "%";
    }

}
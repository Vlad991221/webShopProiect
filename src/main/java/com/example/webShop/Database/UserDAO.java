package com.example.webShop.Database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
public interface UserDAO extends CrudRepository<User, Integer> {
    List<User> findAllByEmail(String email);

//    @Autowired
//    JdbcTemplate jdbcTemplate;

//    @PersistenceContext
//    EntityManager entityManager;
//
//    public List<User> findAll() {
////        List<User> userList = jdbcTemplate.query("select * from user", new UserRowMapper());
////        return userList;
//        Query query = entityManager.createNativeQuery("select * from user");
//        return query.getResultList();
//    }
//
//    public List<User> findByEmail(String email) {
////        List<User> userList = jdbcTemplate.query("select * from user where email = '" + email + "'", new UserRowMapper());
////        return userList;
//        Query query = entityManager.createNativeQuery("select * from user where email = '" + email + "'", User.class);
//        return query.getResultList();
//    }
//
//    @Transactional
//    public void save(String email, String password) {
////        jdbcTemplate.update("insert into user values(null, ?, ?)", email, password);
//        User user = new User();
//        user.setEmail(email);
//        user.setPassword(password);
//        entityManager.persist(user);
//    }
}

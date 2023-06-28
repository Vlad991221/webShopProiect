package com.example.webShop.Service;

import com.example.webShop.Database.User;
import com.example.webShop.Database.UserDAO;
import com.example.webShop.Database.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserDAO userDAO;

    public void registerUser(String email, String password1, String password2) throws UserException {
        if (!password1.equals(password2)) {
            throw new UserException("Passwords not identical!");
        }

//        jdbcTemplate.update("insert into user values(null, ?, ?)", email, password1);
        User user = new User();
        user.setPassword(password1);
        user.setEmail(email);
        userDAO.save(user);
    }

    public List<User> loginUser(String email, String password) throws UserException {
        List<User> userList = userDAO.findAllByEmail(email);

        if (userList.isEmpty()) {
            throw new UserException("user/password incorrect!");
        }

        User user = userList.get(0);

        if (!user.getPassword().equals(password)) {
            throw new UserException("user/password incorrect!");
        }

        return userList;
    }
}

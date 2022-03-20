package com.revature.service;

import com.revature.dao.UserDao;
import com.revature.model.User;
import io.javalin.http.BadRequestResponse;

import java.sql.SQLException;

public class UserService {
    private final UserDao userDao;

    public UserService() {
        this.userDao = new UserDao();
    }

    public UserService(UserDao mockDao) {
        this.userDao = mockDao;
    }

    public User login(String username, String password) throws SQLException {
        User user = this.userDao.getUserByLogin(username,password);

        if (user == null) {
            throw new BadRequestResponse("Invalid username and/or password was provided");
        }
        return user;
    }
}

package com.revature.service;

import com.revature.dao.UserDao;
import com.revature.dto.UserDTO;
import com.revature.model.User;
import io.javalin.http.BadRequestResponse;

import java.sql.SQLException;
import java.util.List;

public class UserService {
    private UserDao userDao;

    public UserService() {
        this.userDao = new UserDao();
    }

    public User login(String username, String password) throws SQLException {
        User user = this.userDao.getUserByLogin(username,password);

        if (user == null) {
            throw new BadRequestResponse("Invalid username and/or password was provided");
        }
        return user;
    }

    public List<UserDTO> getUsers() throws SQLException {
        return this.userDao.getUsersModel();
    }
}

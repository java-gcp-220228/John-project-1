package com.revature.service;

import com.revature.dao.UserDao;
import com.revature.model.User;
import io.javalin.http.BadRequestResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserDao mockDao;

    @InjectMocks
    private UserService userService;

    @Test
    void testLogin_positive() throws SQLException {
        when(mockDao.getUserByLogin("Tester","Testing"))
                .thenReturn(new User(100, "Tester", "hashedstring",
                        "Test", "Test", "test@test.com", "EMPLOYEE"));

        User actual = userService.login("Tester", "Testing");

        User expected = new User(100, "Tester", "hashedstring", "Test",
                "Test", "test@test.com", "EMPLOYEE");

        Assertions.assertEquals(expected,actual);
    }

    @Test
    void testLogin_negative() {
        Assertions.assertThrows(BadRequestResponse.class, () -> {
            userService.login("Tester", "Testing");
        });
    }

}

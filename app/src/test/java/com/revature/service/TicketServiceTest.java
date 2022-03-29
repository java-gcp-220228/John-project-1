package com.revature.service;

import com.revature.dao.TicketDao;
import com.revature.dto.EmployeeAddTicketDTO;
import com.revature.dto.ResolveTicketDTO;
import com.revature.dto.UserDTO;
import io.javalin.http.BadRequestResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {
    @Mock
    private TicketDao mockDao;

    @InjectMocks
    private TicketService ticketService;

    @Test
    void testResolveTicket_positive() throws SQLException {
        ResolveTicketDTO initial = new ResolveTicketDTO();
        initial.setId(200);
        initial.setStatus("APPROVED");
        UserDTO resolver = new UserDTO(1, "admin", "admin", "admin", "admin@admin.com");
        initial.setResolver(resolver);
        ResolveTicketDTO expected = new ResolveTicketDTO(initial.getId(),
                new Timestamp(System.currentTimeMillis()), initial.getResolver(), initial.getStatus());
        when(mockDao.patchTicket(initial)).thenReturn(expected);

        ResolveTicketDTO actual = ticketService.resolveTicket("200", "APPROVED", resolver);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testResolveTicket_negativeType() {
        Assertions.assertThrows(BadRequestResponse.class, () -> {
            ticketService.resolveTicket("200", "PARTIAL",
                    new UserDTO(1, "admin", "admin", "admin", "admin@admin.com"));
        });
    }

    @Test
    void testResolveTicket_negativeId() {
        Assertions.assertThrows(BadRequestResponse.class, () -> {
            ticketService.resolveTicket("abc", "DENIED",
                    new UserDTO(1, "admin", "admin", "admin", "admin@admin.com"));
        });
    }

    @Test
    void testAddEmployeeTicket_positive() throws SQLException, IOException {
        EmployeeAddTicketDTO initial = new EmployeeAddTicketDTO();
        initial.setAmount(100.0);
        initial.setDescription("Dinner");
        initial.setType("FOOD");
        UserDTO author = new UserDTO(250, "DDT", "Diamond", "Turner", "DDT@example.com");
        initial.setAuthor(author);
        EmployeeAddTicketDTO expected = new EmployeeAddTicketDTO();
        expected.setId(600);
        expected.setAmount(initial.getAmount());
        expected.setDescription(initial.getDescription());
        expected.setType(initial.getType());
        expected.setAuthor(initial.getAuthor());
        expected.setStatus("PENDING");
        expected.setSubmitted(new Timestamp(System.currentTimeMillis()));
        when(mockDao.createTicketByEmployeeId(initial)).thenReturn(expected);

        EmployeeAddTicketDTO actual = ticketService.addEmployeeTicket(initial, null);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testAddEmployeeTicket_negativeAmount() {
        EmployeeAddTicketDTO initial = new EmployeeAddTicketDTO();
        initial.setAmount(0);
        initial.setDescription("Dinner");
        initial.setType("FOOD");
        UserDTO author = new UserDTO(250, "DDT", "Diamond", "Turner", "DDT@example.com");
        initial.setAuthor(author);
        Assertions.assertThrows(BadRequestResponse.class, () -> {
            ticketService.addEmployeeTicket(initial, null);
        });
    }

    @Test
    void testAddEmployeeTicket_negativeType() {
        EmployeeAddTicketDTO initial = new EmployeeAddTicketDTO();
        initial.setAmount(230);
        initial.setDescription("Xbox");
        initial.setType("GAMING");
        UserDTO author = new UserDTO(250, "DDT", "Diamond", "Turner", "DDT@example.com");
        initial.setAuthor(author);
        Assertions.assertThrows(BadRequestResponse.class, () -> {
            ticketService.addEmployeeTicket(initial, null);
        });
    }

}

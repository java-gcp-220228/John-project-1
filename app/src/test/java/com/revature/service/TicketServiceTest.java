package com.revature.service;

import com.revature.dao.TicketDao;
import com.revature.dto.ResolveTicketDTO;
import com.revature.dto.UserDTO;
import io.javalin.http.BadRequestResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    public void testResolveTicket_positive() throws SQLException {
        ResolveTicketDTO initial = new ResolveTicketDTO();
        initial.setId(200);
        initial.setStatus("APPROVED");
        UserDTO resolver = new UserDTO(1, "admin", "admin", "admin", "admin@admin.com");
        initial.setResolver(resolver);
        ResolveTicketDTO expected = new ResolveTicketDTO(initial.getId(),
                new Timestamp(System.currentTimeMillis()), initial.getResolver(), initial.getStatus());
        when(mockDao.patchTicket(eq(initial))).thenReturn(expected);

        ResolveTicketDTO actual = ticketService.resolveTicket("200", "APPROVED", resolver);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testResolveTicket_negativeType() {
        Assertions.assertThrows(BadRequestResponse.class, () -> {
            ticketService.resolveTicket("200", "PARTIAL",
                    new UserDTO(1, "admin", "admin", "admin", "admin@admin.com"));
        });
    }

    @Test
    public void testResolveTicket_negativeId() {
        Assertions.assertThrows(NumberFormatException.class, () -> {
            ticketService.resolveTicket("abc", "DENIED",
                    new UserDTO(1, "admin", "admin", "admin", "admin@admin.com"));
        });
    }
}

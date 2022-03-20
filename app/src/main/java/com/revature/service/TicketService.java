package com.revature.service;

import com.revature.dao.TicketDao;
import com.revature.model.Ticket;

import java.sql.SQLException;
import java.util.List;

public class TicketService {
    private final TicketDao ticketDao;

    public TicketService() {
        this.ticketDao = new TicketDao();
    }

    public TicketService(TicketDao mockDao) {
        this.ticketDao = mockDao;
    }

    public List<Ticket> getAllTickets() throws SQLException {
        return this.ticketDao.getAllTickets();
    }
}

package com.revature.dto;

import java.time.LocalDateTime;

public class EmployeeAddTicketDTO {
    private int id;
    private double amount;
    private LocalDateTime submitted;
    private String description;
    private String receiptLink;
    private UserDTO author;
    private String type;
}

package com.revature.model;

import com.revature.dto.UserDTO;

import java.time.LocalDateTime;
import java.util.Objects;

public class Ticket {
    private int id;
    private double amount;
    private LocalDateTime submitted;
    private LocalDateTime resolved;
    private String description;
    private String receiptLink;
    private UserDTO author;
    private UserDTO resolver;
    private String status;
    private String type;

    public enum ReimbursementType {
        LODGING(1),
        TRAVEL(2),
        FOOD(3),
        OTHER(4);

        private final int value;
        ReimbursementType(int i) {
            this.value = i;
        }
        public int getValue() {
            return value;
        }
    }

    public Ticket() {
    }

    public Ticket(int id, double amount, LocalDateTime submitted, String status, String type) {
        this.id = id;
        this.amount = amount;
        this.submitted = submitted;
        this.status = status;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getSubmitted() {
        return submitted;
    }

    public void setSubmitted(LocalDateTime submitted) {
        this.submitted = submitted;
    }

    public LocalDateTime getResolved() {
        return resolved;
    }

    public void setResolved(LocalDateTime resolved) {
        this.resolved = resolved;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReceiptLink() {
        return receiptLink;
    }

    public void setReceiptLink(String receiptLink) {
        this.receiptLink = receiptLink;
    }

    public UserDTO getAuthor() {
        return author;
    }

    public void setAuthor(UserDTO author) {
        this.author = author;
    }

    public UserDTO getResolver() {
        return resolver;
    }

    public void setResolver(UserDTO resolver) {
        this.resolver = resolver;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return id == ticket.id && Double.compare(ticket.amount, amount) == 0 && Objects.equals(submitted, ticket.submitted) && Objects.equals(resolved, ticket.resolved) && Objects.equals(description, ticket.description) && Objects.equals(receiptLink, ticket.receiptLink) && Objects.equals(author, ticket.author) && Objects.equals(resolver, ticket.resolver) && Objects.equals(status, ticket.status) && Objects.equals(type, ticket.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, submitted, resolved, description, receiptLink, author, resolver, status, type);
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", amount=" + amount +
                ", submitted=" + submitted +
                ", resolved=" + resolved +
                ", description='" + description + '\'' +
                ", receiptLink='" + receiptLink + '\'' +
                ", author=" + author +
                ", resolver=" + resolver +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}

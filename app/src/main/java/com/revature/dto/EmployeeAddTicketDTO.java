package com.revature.dto;



import java.sql.Timestamp;
import java.util.Objects;

public class EmployeeAddTicketDTO {
    private int id;
    private double amount;
    private Timestamp submitted;
    private String description;
    private String receiptLink;
    private UserDTO author;
    private String status;
    private String type;

    public EmployeeAddTicketDTO() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeAddTicketDTO that = (EmployeeAddTicketDTO) o;
        return id == that.id && Double.compare(that.amount, amount) == 0 && Objects.equals(submitted, that.submitted) && Objects.equals(description, that.description) && Objects.equals(receiptLink, that.receiptLink) && Objects.equals(author, that.author) && Objects.equals(status, that.status) && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, submitted, description, receiptLink, author, status, type);
    }

    @Override
    public String toString() {
        return "EmployeeAddTicketDTO{" +
                "id=" + id +
                ", amount=" + amount +
                ", submitted=" + submitted +
                ", description='" + description + '\'' +
                ", receiptLink='" + receiptLink + '\'' +
                ", author=" + author +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                '}';
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

    public Timestamp getSubmitted() {
        return submitted;
    }

    public void setSubmitted(Timestamp submitted) {
        this.submitted = submitted;
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
}

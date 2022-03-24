package com.revature.dto;

import java.sql.Timestamp;
import java.util.Objects;

public class ResolveTicketDTO {
    private int id;
    private Timestamp resolved;
    private UserDTO resolver;
    private String status;

    public ResolveTicketDTO() {
    }

    public ResolveTicketDTO(int id, Timestamp resolved, UserDTO resolver, String status) {
        this.id = id;
        this.resolved = resolved;
        this.resolver = resolver;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getResolved() {
        return resolved;
    }

    public void setResolved(Timestamp resolved) {
        this.resolved = resolved;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResolveTicketDTO that = (ResolveTicketDTO) o;
        return id == that.id && Objects.equals(resolved, that.resolved) && Objects.equals(resolver, that.resolver) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, resolved, resolver, status);
    }

    @Override
    public String toString() {
        return "ResolveTicketDTO{" +
                "id=" + id +
                ", resolved=" + resolved +
                ", resolver=" + resolver +
                ", status='" + status + '\'' +
                '}';
    }
}

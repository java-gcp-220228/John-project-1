package com.revature.dao;

import com.revature.dto.UserDTO;
import com.revature.model.Ticket;
import com.revature.utility.ConnectionUtility;
import org.json.JSONObject;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TicketDao {
    public List<Ticket> getAllTickets() throws SQLException {
        try (Connection con = ConnectionUtility.getConnection()) {

            List<Ticket> tickets = new ArrayList<>();

            String query = "select tk.reimb_id, tk.reimb_amount, tk.reimb_submitted, " +
                    "tk.reimb_resolved, tk.reimb_description, tk.reimb_receipt, " +
                    "status.reimb_status, rt.reimb_type, " +
                    "json_build_object('username', users.ers_username, 'firstName', users.user_first_name, " +
                    "'lastName', users.user_last_name, 'email', users.user_email) as author, " +
                    "json_build_object('username', mng.ers_username, 'firstName', mng.user_first_name, " +
                    "'lastName', mng.user_last_name, 'email', mng.user_email) as resolver " +
                    "from ers_reimbursements as tk " +
                    "inner join ers_reimbursement_status status " +
                    "on tk.reimb_status_id = status.reimb_status_id " +
                    "inner join ers_reimbursement_type rt " +
                    "on tk.reimb_type_id = rt.reimb_type_id " +
                    "inner join ers_users users " +
                    "on tk.reimb_author = users.ers_users_id " +
                    "left join ers_users mng " +
                    "on tk.reimb_resolver = mng.ers_users_id";

            PreparedStatement pstmt = con.prepareStatement(query);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("reimb_id");
                double amount = rs.getDouble("reimb_amount");
                LocalDateTime submitted = rs.getObject("reimb_submitted", LocalDateTime.class);
                LocalDateTime resolved = rs.getObject("reimb_resolved", LocalDateTime.class);
                String description = rs.getString("reimb_description");
                String receiptLink = rs.getString("reimb_receipt");
                String status = rs.getString("reimb_status");
                String type = rs.getString("reimb_type");
                JSONObject jsonObject = new JSONObject(rs.getString("author"));
                UserDTO author = new UserDTO(jsonObject.getString("username"), jsonObject.getString("firstName"),
                    jsonObject.getString("lastName"), jsonObject.getString("email"));

                Ticket ticket = new Ticket(id, amount, submitted, status, type);
                ticket.setAuthor(author);

                if (resolved != null) {
                    JSONObject json = new JSONObject(rs.getString("resolver"));
                    UserDTO resolver = new UserDTO(json.getString("username"), json.getString("firstName"),
                            json.getString("lastName"), json.getString("email"));
                    ticket.setResolved(resolved);
                    ticket.setResolver(resolver);
                }
                if (description != null) {
                    ticket.setDescription(description);
                }
                if (receiptLink != null) {
                    ticket.setReceiptLink(receiptLink);
                }
                tickets.add(ticket);
            }
            return tickets;
        }
    }

    public List<Ticket> getTicketFromEmployeeId(int id) throws SQLException {
        try (Connection con = ConnectionUtility.getConnection()) {

            List<Ticket> tickets = new ArrayList<>();
            String query = "select tk.reimb_id, tk.reimb_amount, tk.reimb_submitted, " +
                    "tk.reimb_resolved, tk.reimb_description, tk.reimb_receipt, " +
                    "status.reimb_status, rt.reimb_type, " +
                    "json_build_object('username', users.ers_username, 'firstName'" +
                    ", users.user_first_name, 'lastName', users.user_last_name, 'email', users.user_email) as author, " +
            "json_build_object('username', mng.ers_username, 'firstName', mng.user_first_name, 'lastName', " +
                    "mng.user_last_name, 'email', mng.user_email) as resolver " +
            "from ers_reimbursements as tk " +
            "inner join ers_reimbursement_status status " +
            "on tk.reimb_status_id = status.reimb_status_id " +
            "inner join ers_reimbursement_type rt " +
            "on tk.reimb_type_id = rt.reimb_type_id " +
            "inner join ers_users users " +
            "on tk.reimb_author = users.ers_users_id " +
            "left join ers_users mng " +
            "on tk.reimb_resolver = mng.ers_users_id "+
            "where tk.reimb_author = ?";

            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1,id);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int reimb_id = rs.getInt("reimb_id");
                double amount = rs.getDouble("reimb_amount");
                LocalDateTime submitted = rs.getObject("reimb_submitted", LocalDateTime.class);
                LocalDateTime resolved = rs.getObject("reimb_resolved", LocalDateTime.class);
                String description = rs.getString("reimb_description");
                String receiptLink = rs.getString("reimb_receipt");
                String status = rs.getString("reimb_status");
                String type = rs.getString("reimb_type");
                JSONObject jsonObject = new JSONObject(rs.getString("author"));
                UserDTO author = new UserDTO(jsonObject.getString("username"), jsonObject.getString("firstName"),
                jsonObject.getString("lastName"), jsonObject.getString("email"));

                Ticket ticket = new Ticket(reimb_id, amount, submitted, status, type);
                ticket.setAuthor(author);

                if (resolved != null) {
                    JSONObject json = new JSONObject(rs.getString("resolver"));
                    UserDTO resolver = new UserDTO(json.getString("username"), json.getString("firstName"),
                            json.getString("lastName"), json.getString("email"));
                    ticket.setResolved(resolved);
                    ticket.setResolver(resolver);
                }
                if (description != null) {
                    ticket.setDescription(description);
                }
                if (receiptLink != null) {
                    ticket.setReceiptLink(receiptLink);
                }
                tickets.add(ticket);
            }
            return tickets;

        }
    }
}

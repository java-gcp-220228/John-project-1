package com.revature.dao;

import com.revature.model.User;
import com.revature.utility.ConnectionUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    public User getUserByLogin(String username, String password) throws SQLException {
        try(Connection con = ConnectionUtility.getConnection()) {
            String query = "select u.ers_users_id , u.ers_username, u.ers_password, u.user_first_name, u.user_last_name, u.user_email, r.user_role  " +
                    "from ers_users as u " +
                    "inner join ers_user_roles as r " +
                    "on u.user_role_id = r.ers_user_role_id " +
                    "where u.ers_username = ? and u.ers_password = public.crypt(?, u.ers_password)";

            PreparedStatement pstmt = con.prepareStatement(query);

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("ers_users_id");
                String hash = rs.getString("ers_password");
                String firstName = rs.getString("user_first_name");
                String lastName = rs.getString("user_last_name");
                String email = rs.getString("user_email");
                String role = rs.getString("user_role");

                return new User(id, username, hash, firstName, lastName, email, role);
            }
            pstmt.close();
            return null;
        }
    }
}

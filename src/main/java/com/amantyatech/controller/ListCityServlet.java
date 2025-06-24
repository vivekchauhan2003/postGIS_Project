package com.amantyatech.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amantyatech.repository.DBUtil;

@WebServlet("/listCities")
public class ListCityServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT id, name, ST_AsText(location) FROM cities";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                res.getWriter().write(rs.getInt("id") + " | " + rs.getString("name") + " | " + rs.getString(3) + "\n");
            }
        } catch (SQLException e) {
            res.getWriter().write("Error: " + e.getMessage());
        }
    }
}


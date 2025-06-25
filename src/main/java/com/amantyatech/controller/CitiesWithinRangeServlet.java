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

@WebServlet("/citiesWithinRange")
public class CitiesWithinRangeServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        double lon = Double.parseDouble(req.getParameter("lon"));
        double lat = Double.parseDouble(req.getParameter("lat"));
        double radius_meters = Double.parseDouble(req.getParameter("radius_meters"));

        res.setContentType("text/plain");

        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT name FROM cities " +
                         "WHERE ST_DWithin(" +
                         "  geography(location), " +
                         "  geography(ST_SetSRID(ST_MakePoint(?, ?), 4326)), " +
                         "  ?" +
                         ");";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setDouble(1, lon);
                stmt.setDouble(2, lat);
                stmt.setDouble(3, radius_meters);

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        res.getWriter().println(rs.getString("name"));
                    }
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            res.getWriter().println("Database error: " + e.getMessage());
        }
    }
}

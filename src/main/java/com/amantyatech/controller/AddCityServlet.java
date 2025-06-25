package com.amantyatech.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amantyatech.repository.DBUtil;

@WebServlet("/addCity")
public class AddCityServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String name = req.getParameter("name").trim();
        double lon = Double.parseDouble(req.getParameter("lon"));
        double lat = Double.parseDouble(req.getParameter("lat"));

        try (Connection conn = DBUtil.getConnection()) {

            // Step 1: Check if city already exists
            String checkSql = "SELECT COUNT(*) FROM cities WHERE name = ?";
            PreparedStatement checkPs = conn.prepareStatement(checkSql);
            checkPs.setString(1, name);
            var rs = checkPs.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            if (count > 0) {
                res.getWriter().write("City already exists!");
                return;
            }

            // Step 2: Find the smallest missing ID
            String findGapSql = """
                SELECT MIN(t1.id + 1) AS missing_id
                FROM cities t1
                LEFT JOIN cities t2 ON t1.id + 1 = t2.id
                WHERE t2.id IS NULL
            """;
            PreparedStatement gapPs = conn.prepareStatement(findGapSql);
            var gapRs = gapPs.executeQuery();

            int insertId;
            if (gapRs.next() && gapRs.getInt("missing_id") != 0) {
                insertId = gapRs.getInt("missing_id");
            } else {
                // No gap found; insert at max + 1
                PreparedStatement maxIdPs = conn.prepareStatement("SELECT COALESCE(MAX(id), 0) + 1 AS next_id FROM cities");
                var maxIdRs = maxIdPs.executeQuery();
                maxIdRs.next();
                insertId = maxIdRs.getInt("next_id");
            }

            // Step 3: Insert with manually specified ID
            String insertSql = "INSERT INTO cities (id, name, location) VALUES (?, ?, ST_SetSRID(ST_MakePoint(?, ?), 4326))";
            PreparedStatement ps = conn.prepareStatement(insertSql);
            ps.setInt(1, insertId);
            ps.setString(2, name);
            ps.setDouble(3, lon);
            ps.setDouble(4, lat);
            ps.executeUpdate();

            // Step 4: Reset sequence just in case
            String resetSeqSql = "SELECT setval('cities_id_seq', (SELECT MAX(id) FROM cities))";
            PreparedStatement resetPs = conn.prepareStatement(resetSeqSql);
            resetPs.execute();

            res.getWriter().write("City added at ID: " + insertId);

        } catch (SQLException e) {
            res.getWriter().write("Error: " + e.getMessage());
        }
    }
}





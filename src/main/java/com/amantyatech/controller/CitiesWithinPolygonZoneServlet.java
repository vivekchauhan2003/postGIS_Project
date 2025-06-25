package com.amantyatech.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amantyatech.repository.DBUtil;

@WebServlet("/citiesWithinPolygonZoneServlet")
public class CitiesWithinPolygonZoneServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String polygonCoordinates = req.getParameter("polygonText"); // Expects: (70 25, 90 25, 90 30, 70 30, 70 25)
        String polygonWKT = "POLYGON(" + polygonCoordinates + ")";

		
		res.setContentType("text/plain");

        try (Connection conn = DBUtil.getConnection()) {
            
            String sql = " SELECT name FROM cities WHERE ST_Within( location, ST_GeomFromText(?, 4326));";
            
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, polygonWKT);

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

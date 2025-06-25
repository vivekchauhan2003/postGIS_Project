package com.amantyatech.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amantyatech.repository.DBUtil;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/testclass")
public class TestClass extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("application/json");
        PrintWriter out = res.getWriter();

        String polygonText = req.getParameter("polygonText");

        if (polygonText == null || polygonText.isBlank()) {
            out.print("{\"error\":\"Missing or empty 'polygonText' parameter\"}");
            return;
        }

        // Clean and ensure polygon closes
        String[] points = polygonText.split(",");
        for (int i = 0; i < points.length; i++) {
            points[i] = points[i].trim(); // remove extra spaces
        }
        if (!points[0].equals(points[points.length - 1])) {
            polygonText += "," + points[0];
        }

        String polygonWKT = "POLYGON((" + polygonText + "))";
        JSONArray cityArray = new JSONArray();

        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT name, ST_X(location) AS lon, ST_Y(location) AS lat " +
                         "FROM cities WHERE ST_Within(location, ST_GeomFromText(?, 4326));";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, polygonWKT);

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        JSONObject city = new JSONObject();
                        city.put("name", rs.getString("name"));
                        city.put("lat", rs.getDouble("lat"));
                        city.put("lon", rs.getDouble("lon"));
                        cityArray.put(city);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JSONObject error = new JSONObject();
            error.put("error", "Database error: " + e.getMessage());
            out.print(error.toString());
            return;
        }

        // ✅ Return both polygon and cities
        JSONObject response = new JSONObject();
        JSONArray polygonCoords = new JSONArray();

        for (String point : polygonText.split(",")) {
            String[] parts = point.trim().split(" ");
            if (parts.length == 2) {
                JSONArray coord = new JSONArray();
                coord.put(Double.parseDouble(parts[1])); // lat
                coord.put(Double.parseDouble(parts[0])); // lon
                polygonCoords.put(coord);
            }
        }

        response.put("polygon", polygonCoords);
        response.put("cities", cityArray);
        out.print(response.toString());
    }
}

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

@WebServlet("/updateCity")
public class UpdateCityServlet extends HttpServlet {
    protected void doPut(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        double lon = Double.parseDouble(req.getParameter("lon"));
        double lat = Double.parseDouble(req.getParameter("lat"));

        try (Connection conn = DBUtil.getConnection()) {
            String sql = "UPDATE cities SET name = ?, location = ST_SetSRID(ST_MakePoint(?, ?), 4326) WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setDouble(2, lon);
            ps.setDouble(3, lat);
            ps.setInt(4, id);
            ps.executeUpdate();
            res.getWriter().write("City updated!");
        } catch (SQLException e) {
            res.getWriter().write("Error: " + e.getMessage());
        }
    }
}

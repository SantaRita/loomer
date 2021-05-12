package org.srcom.loomerdb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@RestController
@RequestMapping("/loomer-rest/api")
public class EjecutarSentencia {

    @Autowired
    DataSource ds;

    @GetMapping("/greeting")
    public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {


        String query = "select cdasiste from ex_expedientes where rownum < 10";
        String cadena = null;
        try {
            Connection con = ds.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int CDASISTE = rs.getInt("CDASISTE");

                cadena += "  asistencia " + CDASISTE;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new String("hola" + cadena);
    }
}

package org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IAlumnos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.utilidades.MySQL;



import java.util.ArrayList;


import java.sql.*;

import java.util.List;


public class Alumnos implements IAlumnos {
    private static Alumnos instancia;
    private Connection conection;


    public Alumnos() {
        comenzar();
    }

    // Método Singleton
    public static Alumnos getInstancia() {
        if (instancia == null) {
            instancia = new Alumnos();
        }
        return instancia;
    }


    public void comenzar() {
        conection = MySQL.establecerConexion();
    }


    public void terminar() {
        MySQL.cerrarConexion();
        conection = null;
    }




    // Devuelve todos los alumnos ordenados por dni
    public List<Alumno> get() {
        List<Alumno> lista = new ArrayList<>();
        String sql = "SELECT * FROM alumno ORDER BY dni";
        try (PreparedStatement ps = conection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Alumno alumno = new Alumno(
                        rs.getString("nombre"), // Nombre
                        rs.getString("dni"),    // DNI
                        rs.getString("correo"), // Correo
                        rs.getString("telefono"), // Teléfono
                        rs.getDate("fechaNacimiento").toLocalDate() // Fecha de nacimiento
                );
                lista.add(alumno);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener alumnos: " + e.getMessage());
        }
        return lista;
    }

    // Devuelve el número de alumnos
    public int getTamano() {
        String sql = "SELECT COUNT(*) FROM alumno";
        try (PreparedStatement ps = conection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error al contar alumnos: " + e.getMessage());
        }
        return 0;
    }

    // Inserta un nuevo alumno
    public void insertar(Alumno alumno) {
        String sql = "INSERT INTO alumno (nombre, telefono, correo, dni, fechaNacimiento) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conection.prepareStatement(sql)) {
            ps.setString(1, alumno.getNombre());
            ps.setString(2, alumno.getTelefono());
            ps.setString(3, alumno.getCorreo());
            ps.setString(4, alumno.getDni());
            ps.setDate(5, Date.valueOf(alumno.getFechaNacimiento()));
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al insertar alumno: " + e.getMessage());
        }
    }

    // Busca un alumno por dni
    public Alumno buscar(Alumno alumno) {
        String sql = "SELECT * FROM alumno WHERE dni = ?";
        try (PreparedStatement ps = conection.prepareStatement(sql)) {
            ps.setString(1, alumno.getDni());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Alumno(
                            rs.getString("nombre"),    // Nombre
                            rs.getString("dni"),       // DNI
                            rs.getString("correo"),    // Correo
                            rs.getString("telefono"),  // Teléfono
                            rs.getDate("fechaNacimiento").toLocalDate() // Fecha de nacimiento
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar alumno: " + e.getMessage());
        }
        return null;
    }

    // Elimina un alumno
    public void borrar(Alumno alumno) {
        String sql = "DELETE FROM alumno WHERE dni = ?";
        try (PreparedStatement ps = conection.prepareStatement(sql)) {
            ps.setString(1, alumno.getDni());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al borrar alumno: " + e.getMessage());
        }
    }
}

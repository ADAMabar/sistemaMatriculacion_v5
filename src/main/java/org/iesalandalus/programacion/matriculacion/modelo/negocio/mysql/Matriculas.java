package org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.*;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IMatriculas;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.utilidades.MySQL;

import javax.naming.OperationNotSupportedException;
import java.sql.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.naming.OperationNotSupportedException;


import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.naming.OperationNotSupportedException;

public class Matriculas implements IMatriculas {

    private static Matriculas instancia;
    private Connection conexion;

    public Matriculas() {
        comenzar();
    }

    public static Matriculas getInstancia() {
        if (instancia == null) {
            instancia = new Matriculas();
        }
        return instancia;
    }

    @Override
    public void comenzar() {
        conexion = MySQL.establecerConexion();
    }

    @Override
    public void terminar() {
        MySQL.cerrarConexion();
        conexion = null;
    }

    @Override
    public List<Matricula> get() throws OperationNotSupportedException {
        List<Matricula> matriculas = new ArrayList<>();
        try {
            String sql = """
                SELECT m.*, a.*
                FROM matricula m
                JOIN alumno a ON m.dni = a.dni
                ORDER BY m.fechaMatriculacion DESC, a.nombre ASC
                """;
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Alumno alumno = new Alumno(
                        rs.getString("nombre"),
                        rs.getString("dni"),
                        rs.getString("correo"),
                        rs.getString("telefono"),
                        rs.getDate("fechaNacimiento").toLocalDate()
                );

                List<Asignatura> asignaturas = getAsignaturasMatricula(rs.getInt("idMatricula"));

                Matricula matricula = new Matricula(
                        rs.getInt("idMatricula"),
                        rs.getString("cursoAcademico"),
                        rs.getDate("fechaMatriculacion").toLocalDate(),
                        alumno,
                        asignaturas
                );



                matriculas.add(new Matricula(matricula));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new OperationNotSupportedException("ERROR: No se pudieron obtener las matrículas.");
        }
        return matriculas;
    }

    @Override
    public int getTamano() {
        int total = 0;
        try {
            String sql = "SELECT COUNT(*) FROM matricula";
            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    private List<Asignatura> getAsignaturasMatricula(int idMatricula) {
        List<Asignatura> asignaturas = new ArrayList<>();
        try {
            String sql = """
                SELECT a.*, c.*, am.*
                FROM asignaturasMatricula am
                JOIN asignatura a ON am.codigo = a.codigo
                JOIN cicloFormativo c ON a.codigo = c.codigo
                WHERE am.idMatricula = ?
                """;
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, idMatricula);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Grado grado = CiclosFormativos.getInstancia().getGrado(
                        rs.getString("grado"),
                        rs.getString("nombreGrado"),
                        rs.getInt("numAniosGrado"),
                        rs.getString("modalidad"),
                        rs.getInt("numEdiciones")
                );

                CicloFormativo ciclo = new CicloFormativo(
                        rs.getInt("codigo"),
                        rs.getString("familiaProfesional"),
                        grado,
                        rs.getString("nombre"),
                        rs.getInt("horas")
                );

                Asignatura asignatura = new Asignatura(
                        rs.getString("codigo"),
                        rs.getString("nombre"),
                        rs.getInt("horasAnuales"),
                        Curso.valueOf(rs.getString("curso")),
                        rs.getInt("horasDesdoble"),
                        EspecialidadProfesorado.valueOf(rs.getString("especialidadProfesorado")),
                        ciclo
                );

                asignaturas.add(asignatura);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return asignaturas;
    }

    private void insertarAsignaturasMatricula(int idMatricula, List<Asignatura> asignaturas) {
        try {
            String sql = "INSERT INTO asignaturasMatricula (idMatricula, codigo) VALUES (?, ?)";
            PreparedStatement ps = conexion.prepareStatement(sql);
            for (Asignatura asignatura : asignaturas) {
                ps.setInt(1, idMatricula);
                ps.setString(2, asignatura.getCodigo());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertar(Matricula matricula) throws OperationNotSupportedException {
        if (matricula == null) {
            throw new NullPointerException("ERROR: No se puede insertar una matrícula nula.");
        }

        if (buscar(matricula) != null) {
            throw new OperationNotSupportedException("ERROR: Ya existe una matrícula con ese identificador.");
        }

        try {
            String sql = "INSERT INTO matricula (idMatricula, cursoAcademico, fechaMatriculacion, dni) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, matricula.getIdMatricula());
            ps.setString(2, matricula.getCursoAcademico());
            ps.setDate(3, Date.valueOf(matricula.getFechaMatriculacion()));
            ps.setString(4, matricula.getAlumno().getDni());
            ps.executeUpdate();

            insertarAsignaturasMatricula(matricula.getIdMatricula(), matricula.getColeccionAsignaturas());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Matricula buscar(Matricula matricula) {
        if (matricula == null) {
            throw new NullPointerException("ERROR: La matrícula no puede ser nula.");
        }

        Matricula resultado = null;
        try {
            String sql = """
                SELECT m.*, a.nombre, a.correo, a.telefono, a.fechaNacimiento
                FROM matricula m
                JOIN alumno a ON m.dni = a.dni
                WHERE m.idMatricula = ?
                """;
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, matricula.getIdMatricula());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Alumno alumno = new Alumno(
                        rs.getString("nombre"),
                        rs.getString("dni"),
                        rs.getString("correo"),
                        rs.getString("telefono"),
                        rs.getDate("fechaNacimiento").toLocalDate()
                );

                List<Asignatura> asignaturas = getAsignaturasMatricula(rs.getInt("idMatricula"));

                resultado = new Matricula(
                        rs.getInt("idMatricula"),
                        rs.getString("cursoAcademico"),
                        rs.getDate("fechaMatriculacion").toLocalDate(),
                        alumno,
                        asignaturas
                );
            }
        } catch (SQLException | OperationNotSupportedException e) {
            e.printStackTrace();
        }

        return resultado;
    }

    @Override
    public void borrar(Matricula matricula) throws OperationNotSupportedException {
        if (matricula == null) {
            throw new NullPointerException("ERROR: No se puede borrar una matrícula nula.");
        }

        if (buscar(matricula) == null) {
            throw new OperationNotSupportedException("ERROR: No existe ninguna matrícula como la indicada.");
        }

        try {
            String sql = "DELETE FROM matricula WHERE idMatricula = ?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, matricula.getIdMatricula());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Matricula> get(Alumno alumno) {
        List<Matricula> resultado = new ArrayList<>();
        try {
            String sql = "SELECT * FROM matricula WHERE dni = ?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, alumno.getDni());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                List<Asignatura> asignaturas = getAsignaturasMatricula(rs.getInt("idMatricula"));
                Matricula matricula = new Matricula(
                        rs.getInt("idMatricula"),
                        rs.getString("cursoAcademico"),
                        rs.getDate("fechaMatriculacion").toLocalDate(),
                        alumno,
                        asignaturas
                );
                resultado.add(matricula);
            }
        } catch (SQLException | OperationNotSupportedException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    @Override
    public List<Matricula> get(CicloFormativo ciclo) {
        List<Matricula> resultado = new ArrayList<>();
        try {
            for (Matricula m : get()) {
                for (Asignatura a : m.getColeccionAsignaturas()) {
                    if (a.getCicloFormativo().equals(ciclo)) {
                        resultado.add(m);
                        break;
                    }
                }
            }
        } catch (OperationNotSupportedException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    @Override
    public List<Matricula> get(String cursoAcademico) {
        List<Matricula> resultado = new ArrayList<>();
        try {
            for (Matricula m : get()) {
                if (m.getCursoAcademico().equals(cursoAcademico)) {
                    resultado.add(m);
                }
            }
        } catch (OperationNotSupportedException e) {
            e.printStackTrace();
        }
        return resultado;
    }
}

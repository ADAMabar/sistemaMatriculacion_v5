package org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.*;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IAsignaturas;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.CiclosFormativos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.utilidades.MySQL;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.List;

import java.sql.*;
public class Asignaturas implements IAsignaturas {
    private static Asignaturas instancia;
    private Connection conexion;

    public Asignaturas() {
        comenzar();
    }

    public static Asignaturas getInstancia() {
        if (instancia == null) {
            instancia = new Asignaturas();
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
    }

    private Curso getCurso(String valor) {
        for (Curso c : Curso.values()) {
            if (c.toString().equalsIgnoreCase(valor)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Curso no válido: " + valor);
    }

    private EspecialidadProfesorado getEspecialidadProfesorado(String valor) {
        for (EspecialidadProfesorado e : EspecialidadProfesorado.values()) {
            if (e.toString().equalsIgnoreCase(valor)) {
                return e;
            }
        }
        throw new IllegalArgumentException("Especialidad no válida: " + valor);
    }

    @Override
    public List<Asignatura> get() {
        List<Asignatura> asignaturas = new ArrayList<>();
        String sql = "SELECT * FROM asignatura ORDER BY nombre";

        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String codigo = rs.getString("codigo");
                String nombre = rs.getString("nombre");
                int horasAnuales = rs.getInt("horasAnuales");
                Curso curso = getCurso(rs.getString("curso"));
                int horasDesdoble = rs.getInt("horasDesdoble");
                EspecialidadProfesorado especialidad = getEspecialidadProfesorado(rs.getString("especialidadProfesorado"));
                int codigoCiclo = rs.getInt("codigoCicloFormativo");

                Grado grado = new GradoE("FALSO",1,1);
                CicloFormativo ciclo = new CicloFormativo(codigoCiclo, "Desconocido", grado, "Desconocido", 1222);
                CiclosFormativos ciclos = new CiclosFormativos();
                 CicloFormativo ciclow=ciclos.buscar(ciclo);


                Asignatura asignatura = new Asignatura(codigo, nombre, horasAnuales, curso, horasDesdoble, especialidad, ciclow);
                asignaturas.add(asignatura);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener asignaturas: " + e.getMessage());
        }

        return asignaturas;
    }

    @Override
    public int getTamano() {
        int cantidad = 0;
        String sql = "SELECT COUNT(*) FROM asignatura";

        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                cantidad = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error al contar asignaturas: " + e.getMessage());
        }

        return cantidad;
    }

    @Override
    public void insertar(Asignatura asignatura) {
        if (asignatura == null) {
            throw new NullPointerException("ERROR: No se puede insertar una asignatura nula.");
        }

        String sql = "INSERT INTO asignatura (codigo, nombre, horasAnuales, curso, horasDesdoble, especialidadProfesorado, codigoCicloFormativo) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, asignatura.getCodigo());
            ps.setString(2, asignatura.getNombre());
            ps.setInt(3, asignatura.getHorasAnuales());
            ps.setString(4, asignatura.getCurso().toString());
            ps.setInt(5, asignatura.getHorasDesdoble());
            ps.setString(6, asignatura.getEspecialidadProfesorado().toString());
            ps.setInt(7, asignatura.getCicloFormativo().getCodigo());

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al insertar asignatura: " + e.getMessage());
        }
    }

    @Override
    public Asignatura buscar(Asignatura asignatura) {
        if (asignatura == null) {
            throw new NullPointerException("ERROR: La asignatura no puede ser nula.");
        }

        String sql = "SELECT * FROM asignatura WHERE codigo = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, asignatura.getCodigo());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String codigo = rs.getString("codigo");
                    String nombre = rs.getString("nombre");
                    int horasAnuales = rs.getInt("horasAnuales");
                    Curso curso = getCurso(rs.getString("curso"));
                    int horasDesdoble = rs.getInt("horasDesdoble");
                    EspecialidadProfesorado especialidad = getEspecialidadProfesorado(rs.getString("especialidadProfesorado"));
                    int codigoCiclo = rs.getInt("codigoCicloFormativo");

                    Grado grado = new GradoD("FALSO", 2, Modalidad.PRESENCIAL);
                    CicloFormativo ciclo = new CicloFormativo(codigoCiclo, "Desconocido", grado, "Desconocido", 1222);


                    return new Asignatura(codigo, nombre, horasAnuales, curso, horasDesdoble, especialidad, ciclo);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar asignatura: " + e.getMessage());
        }

        return null;
    }
//
    @Override
    public void borrar(Asignatura asignatura) {
        if (asignatura == null) {
            throw new NullPointerException("ERROR: No se puede borrar una asignatura nula.");
        }

        String sql = "DELETE FROM asignatura WHERE codigo = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, asignatura.getCodigo());
            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas == 0) {
                throw new OperationNotSupportedException("ERROR: No existe ninguna asignatura como la indicada.");
            }

        } catch (SQLException | OperationNotSupportedException e) {
            System.out.println("Error al borrar asignatura: " + e.getMessage());
        }
    }
}

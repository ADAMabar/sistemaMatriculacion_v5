package org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.*;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.ICiclosFormativos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.utilidades.MySQL;



import java.util.ArrayList;
import java.util.List;



import java.sql.*;





public class CiclosFormativos implements ICiclosFormativos {
    private static CiclosFormativos instancia;
    private Connection conexion;

    public CiclosFormativos() {
        comenzar();
    }

    public static CiclosFormativos getInstancia() {
        if (instancia == null) {
            instancia = new CiclosFormativos();
        }
        return instancia;
    }

    public void comenzar() {
        conexion = MySQL.establecerConexion();
    }

    public void terminar() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }

    public Grado getGrado(String tipoGrado, String nombreGrado, int numAniosGrado, String modalidad, int numEdiciones) {
        if (tipoGrado.equalsIgnoreCase("gradod")) {
            return new GradoD(nombreGrado, numAniosGrado, Modalidad.valueOf(modalidad.toUpperCase()));
        } else if (tipoGrado.equalsIgnoreCase("gradoe")) {
            return new GradoE(nombreGrado, numAniosGrado, numEdiciones);
        } else {
            throw new IllegalArgumentException("Tipo de grado no válido.");
        }
    }

    public List<CicloFormativo> get() {
        List<CicloFormativo> ciclos = new ArrayList<>();
        String sql = "SELECT * FROM cicloFormativo ORDER BY nombre";

        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String tipoGrado = rs.getString("grado");
                Grado grado = getGrado(
                        tipoGrado,
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
                ciclos.add(ciclo);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener ciclos formativos: " + e.getMessage());
        }

        return ciclos;
    }

    public int getTamano() {
        String sql = "SELECT COUNT(*) FROM cicloFormativo";
        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error al contar ciclos formativos: " + e.getMessage() );
        }
        return 0;
    }

    public void insertar(CicloFormativo ciclo) {
        String sql = "INSERT INTO cicloFormativo (codigo, familiaProfesional, grado, nombre, horas, " +
                "nombreGrado, numAniosGrado, modalidad, numEdiciones) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, ciclo.getCodigo());
            ps.setString(2, ciclo.getFamiliaProfesional());
            ps.setString(3, ciclo.getGrado() instanceof GradoD ? "gradod" : "gradoe");
            ps.setString(4, ciclo.getNombre());
            ps.setInt(5, ciclo.getHoras());
            ps.setString(6, ciclo.getGrado().getNombre());
            ps.setInt(7, ciclo.getGrado().getNumAnios());

            if (ciclo.getGrado() instanceof GradoD gradoD) {
                ps.setString(8, gradoD.getModalidad().name().toLowerCase());
                ps.setNull(9, Types.INTEGER);
            } else if (ciclo.getGrado() instanceof GradoE gradoE) {
                ps.setNull(8, Types.VARCHAR);
                ps.setInt(9, gradoE.getNumEdiciones());
            }

            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al insertar ciclo formativo: " + e.getMessage());
        }
    }

    public CicloFormativo buscar(CicloFormativo ciclo) {
        String sql = "SELECT * FROM cicloFormativo WHERE codigo = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, ciclo.getCodigo());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Grado grado = getGrado(
                            rs.getString("grado"),
                            rs.getString("nombreGrado"),
                            rs.getInt("numAniosGrado"),
                            rs.getString("modalidad"),
                            rs.getInt("numEdiciones")
                    );
                    return new CicloFormativo(
                            rs.getInt("codigo"),
                            rs.getString("familiaProfesional"),
                            grado,
                            rs.getString("nombre"),
                            rs.getInt("horas")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar ciclo formativo: " + e.getMessage());
        }
        return null;
    }

    public void borrar(CicloFormativo ciclo) {
        String sql = "DELETE FROM cicloFormativo WHERE codigo = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, ciclo.getCodigo());
            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas == 0) {
                System.err.println("No se encontró el ciclo formativo para borrar.");
            }
        } catch (SQLException e) {
            System.err.println("Error al borrar ciclo formativo: " + e.getMessage());
        }
    }
}


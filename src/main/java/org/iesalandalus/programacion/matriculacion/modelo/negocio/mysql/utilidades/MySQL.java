package org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.utilidades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {
    private static final String HOST="dbsistemamatriculacion.c14ys4eamwb1.us-east-1.rds.amazonaws.com";
    private static final String ESQUEMA="sistemamatriculacion";
    private static final String USUARIO = "admin";
    private static final String CONTRASENA = "abar809uopUi33";
    private static Connection conexion;

    public static Connection establecerConexion() {

        if (conexion == null) {
            String url = "jdbc:mysql://" + HOST + ":3306/" + ESQUEMA;
            try {
                conexion = DriverManager.getConnection(url, USUARIO, CONTRASENA);
                System.out.println("Conexión establecida correctamente.");
            } catch (SQLException e) {
                System.err.println("Error: No se pudo establecer la conexión con la base de datos:");
                e.printStackTrace();
            }
        }
       return conexion;
    }
    public static void cerrarConexion() {
        if (conexion != null) {
            try {
                conexion.close();
                conexion = null;
                System.out.println("Conexión cerrada correctamente.");
            } catch (SQLException e) {
                System.err.println("Error: No se pudo cerrar la conexión");
                e.printStackTrace();
            }
        }
    }


}

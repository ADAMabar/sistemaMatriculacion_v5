package org.iesalandalus.programacion.matriculacion.modelo.negocio.memoria;

import org.iesalandalus.programacion.matriculacion.modelo.negocio.*;



public class FuenteDatosMemoria implements IFuenteDatos {
    @Override
    public IAlumnos crearAlumnos() {
        return new Alumnos(); // Alumnos del paquete memoria
    }

    @Override
    public ICiclosFormativos crearCiclosFormativos() {
        return new CiclosFormativos(); // CiclosFormativos del paquete memoria
    }

    @Override
    public IAsignaturas crearAsignaturas() {
        return new Asignaturas(); // Asignaturas del paquete memoria
    }

    @Override
    public IMatriculas crearMatriculas() {
        return new Matriculas(); // Matriculas del paquete memoria
    }
}

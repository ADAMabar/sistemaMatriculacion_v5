package org.iesalandalus.programacion.matriculacion.modelo.negocio;

import org.iesalandalus.programacion.matriculacion.modelo.negocio.memoria.Alumnos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.memoria.Asignaturas;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.memoria.CiclosFormativos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.memoria.Matriculas;

public interface IFuenteDatos {

    IAlumnos crearAlumnos();

    ICiclosFormativos crearCiclosFormativos();

    IAsignaturas crearAsignaturas();

    IMatriculas crearMatriculas();
}


package org.iesalandalus.programacion.matriculacion.modelo.negocio;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;

import javax.naming.OperationNotSupportedException;
import java.util.List;

public interface ICiclosFormativos {

    // Métodos que debe tener la clase CiclosFormativos
    void insertar(CicloFormativo cicloFormativo) throws OperationNotSupportedException;

    CicloFormativo buscar(CicloFormativo cicloFormativo);

    CicloFormativo borrar(CicloFormativo cicloFormativo) throws OperationNotSupportedException;

    List<CicloFormativo> get();

    int getTamano();

    // Métodos comenzando y terminando
    void comenzar();

    void terminar();
}

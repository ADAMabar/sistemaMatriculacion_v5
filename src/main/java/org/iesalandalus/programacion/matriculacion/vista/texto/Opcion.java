package org.iesalandalus.programacion.matriculacion.vista.texto;

public enum Opcion {
        SALIR("Salir") {
                @Override
                public void ejecutar() {
                        System.out.println("Hasta luego!!");
                        vistaTexto.terminar();
                }
        },
        INSERTAR_ALUMNO("Insertar Alumno") {
                @Override
                public void ejecutar() {
                        System.out.println("Insertar alumno");
                        vistaTexto.insertarAlumno();
                }
        },
        BUSCAR_ALUMNO("Buscar Alumno") {
                @Override
                public void ejecutar() {
                        System.out.println("Buscar alumno");
                        vistaTexto.buscarAlumno();
                }
        },
        BORRAR_ALUMNO("Borrar Alumno") {
                @Override
                public void ejecutar() {
                        System.out.println("Borrar alumno");
                        vistaTexto.borrarAlumno();
                }
        },
        MOSTRAR_ALUMNOS("Mostrar Alumnos") {
                @Override
                public void ejecutar() {
                        System.out.println("Mostrar alumnos");
                        vistaTexto.mostrarAlumnos();
                }
        },
        INSERTAR_CICLO_FORMATIVO("Insertar ciclo formativo") {
                @Override
                public void ejecutar() {
                        System.out.println("Insertar ciclo formativo");
                        vistaTexto.insertarCicloFormativo();
                }
        },
        BUSCAR_CICLO_FORMATIVO("Buscar Ciclo Formativo") {
                @Override
                public void ejecutar() {
                        System.out.println("Buscar ciclo formativo");
                        vistaTexto.buscarCicloFormativo();
                }
        },
        BORRAR_CICLO_FORMATIVO("Borrar Ciclo Formativo") {
                @Override
                public void ejecutar() {
                        System.out.println("Borrar ciclo formativo");
                        vistaTexto.borrarCicloFormativo();
                }
        },
        MOSTRAR_CICLOS_FORMATIVOS("Mostrar Ciclos Formativos") {
                @Override
                public void ejecutar() {
                        System.out.println("Mostrar ciclos formativos");
                        vistaTexto.mostrarCicloFormativos();
                }
        },
        INSERTAR_ASIGNATURA("Insertar Asignatura") {
                @Override
                public void ejecutar() {
                        System.out.println("Insertar asignatura");
                        vistaTexto.insertarAsignatura();
                }
        },
        BUSCAR_ASIGNATURA("Buscar Asignatura") {
                @Override
                public void ejecutar() {
                        System.out.println("Buscar asignatura");
                        vistaTexto.buscarAsignatura();
                }
        },
        BORRAR_ASIGNATURA("Borrar Asignatura") {
                @Override
                public void ejecutar() {
                        System.out.println("Borrar asignatura");
                        vistaTexto.borrarAsignatura();
                }
        },
        MOSTRAR_ASIGNATURAS("Mostrar Asignaturas") {
                @Override
                public void ejecutar() {
                        System.out.println("Mostrar asignaturas");
                        vistaTexto.mostrarAsignaturas();
                }
        },
        INSERTAR_MATRICULA("Insertar Matricula") {
                @Override
                public void ejecutar() {
                        System.out.println("Insertar matricula");
                        vistaTexto.insertarMatricula();
                }
        },
        BUSCAR_MATRICULA("Buscar Matricula") {
                @Override
                public void ejecutar() {
                        System.out.println("Buscar matricula");
                        vistaTexto.buscarMatricula();
                }
        },
        ANULAR_MATRICULA("Anular Matricula") {
                @Override
                public void ejecutar() {
                        System.out.println("Borrar matricula");
                        vistaTexto.anularMatricula();
                }
        },
        MOSTRAR_MATRICULAS("Mostrar Matriculas") {
                @Override
                public void ejecutar() {
                        System.out.println("Mostrar matriculas");
                        vistaTexto.mostrarMatriculas();
                }
        },
        MOSTRAR_MATRICULAS_ALUMNO("Mostrar Matriculas Alumno") {
                @Override
                public void ejecutar() {
                        System.out.println("Mostrar matriculas alumno");
                        vistaTexto.mostrarMatriculasPorAlumno();
                }
        },
        MOSTRAR_MATRICULAS_CICLO_FORMATIVO("Mostrar Matriculas Ciclo Formativo") {
                @Override
                public void ejecutar() {
                        System.out.println("Mostrar matriculas ciclo formativo");
                        vistaTexto.mostrarMatriculasPorCicloFormativo();
                }
        },
        MOSTRAR_MATRICULAS_CURSO_ACADEMICO("Mostrar Matriculas Curso Academico") {
                @Override
                public void ejecutar() {
                        System.out.println("Mostrar matriculas curso academico");
                        vistaTexto.mostrarMatriculasPorCursoAcademico();
                }
        };


        private final String cadenaAMostrar;
        private static VistaTexto vistaTexto;


        private Opcion(String cadenaAMostrar)
        {
                this.cadenaAMostrar=cadenaAMostrar;
        }
        public abstract void ejecutar();

        public static void setVista(VistaTexto vistaTexto) {
                Opcion.vistaTexto = vistaTexto;
        }


        @Override
        public String toString() {
                return ordinal()+".-"+cadenaAMostrar;
        }
}
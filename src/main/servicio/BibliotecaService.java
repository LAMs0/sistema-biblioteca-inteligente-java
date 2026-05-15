package main.servicio;

import main.modelo.Libro;
import main.estructuras.TablaHashLibros;

import java.util.List;

public class BibliotecaService {

    private TablaHashLibros tablaHash;

    public BibliotecaService() {
        tablaHash = new TablaHashLibros(50);

        cargarDatosPrueba();
    }

    public List<Libro> obtenerTodosLosLibros() {
        return tablaHash.obtenerTodos();
    }

    private void cargarDatosPrueba() {

        Libro l1 = new Libro("978-1", "Programacion Java", "Deitel", 2020);
        Libro l2 = new Libro("978-2", "Programacion Web", "Juan Perez", 2021);
        Libro l3 = new Libro("978-3", "Clean Code", "Robert Martin", 2008);

        tablaHash.insertar(l1);
        tablaHash.insertar(l2);
        tablaHash.insertar(l3);
    }
}
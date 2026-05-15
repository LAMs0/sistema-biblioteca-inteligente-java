package main.servicio;

import main.modelo.Libro;
import main.estructuras.TablaHashLibros;
import main.estructuras.AVLPrestamos;

import java.util.List;

public class BibliotecaService {

    private TablaHashLibros tablaHash;
    private AVLPrestamos avl;

    public BibliotecaService() {
        tablaHash = new TablaHashLibros(50);
        avl = new AVLPrestamos();

        cargarDatosPrueba();
    }

    public List<Libro> obtenerTodosLosLibros() {
        return tablaHash.obtenerTodos();
    }

    public boolean registrarLibro(
            String isbn,
            String titulo,
            String autor,
            int anio
    ) {

        if (isbn.isEmpty() || titulo.isEmpty() || autor.isEmpty()) {
            return false;
        }

        Libro libro = new Libro(isbn, titulo, autor, anio);

        return tablaHash.insertar(libro);
    }
    private void cargarDatosPrueba() {

        Libro l1 = new Libro("978-1", "Programacion Java", "Deitel", 2020);
        Libro l2 = new Libro("978-2", "Programacion Web", "Juan Perez", 2021);
        Libro l3 = new Libro("978-3", "Clean Code", "Robert Martin", 2008);

        tablaHash.insertar(l1);
        tablaHash.insertar(l2);
        tablaHash.insertar(l3);
    }

    public Libro buscarPorISBN(String isbn) {
        return tablaHash.buscar(isbn);
    }

    public boolean registrarPrestamo(String isbn) {
        Libro libro = tablaHash.buscar(isbn);

        if (libro != null) {
            libro.aumentarPrestamos();
            return true;
        }

        return false;
    }

    public List<Libro> obtenerLibrosOrdenadosPorPrestamos() {
        reconstruirAVL();
        return avl.obtenerOrdenado();
    }

    private void reconstruirAVL() {
        avl = new AVLPrestamos();

        for (Libro libro : tablaHash.obtenerTodos()) {
            avl.insertar(libro);
        }
    }

}
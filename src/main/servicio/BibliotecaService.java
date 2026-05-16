package main.servicio;

import main.estructuras.AVLPrestamos;
import main.estructuras.TablaHashLibros;
import main.estructuras.TrieTitulos;
import main.modelo.Libro;

import java.util.List;

public class BibliotecaService {

    private TablaHashLibros tablaHash;
    private AVLPrestamos avl;
    private TrieTitulos trie;

    public BibliotecaService() {
        tablaHash = new TablaHashLibros(50);
        avl = new AVLPrestamos();
        trie = new TrieTitulos();

        cargarDatosPrueba();
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

        boolean agregado = tablaHash.insertar(libro);

        if (agregado) {
            trie.insertar(titulo);
        }

        return agregado;
    }

    public List<Libro> obtenerTodosLosLibros() {
        return tablaHash.obtenerTodos();
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

    public List<String> autocompletarTitulos(String prefijo) {
        return trie.autocompletar(prefijo);
    }

    private void reconstruirAVL() {
        avl = new AVLPrestamos();

        for (Libro libro : tablaHash.obtenerTodos()) {
            avl.insertar(libro);
        }
    }

    private void cargarDatosPrueba() {
        registrarLibro("978-1", "Programacion Java", "Deitel", 2020);
        registrarLibro("978-2", "Programacion Web", "Juan Perez", 2021);
        registrarLibro("978-3", "Clean Code", "Robert Martin", 2008);
    }
}
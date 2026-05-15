package main.estructuras;

import main.modelo.Libro;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TablaHashLibros {

    private static class Nodo {
        String clave;
        Libro libro;

        Nodo(String clave, Libro libro) {
            this.clave = clave;
            this.libro = libro;
        }
    }

    private LinkedList<Nodo>[] tabla;
    private int tamaño;

    @SuppressWarnings("unchecked")
    public TablaHashLibros(int tamaño) {
        this.tamaño = tamaño;
        tabla = new LinkedList[tamaño];

        for (int i = 0; i < tamaño; i++) {
            tabla[i] = new LinkedList<>();
        }
    }

    private int hash(String clave) {
        return Math.abs(clave.hashCode()) % tamaño;
    }

    public boolean insertar(Libro libro) {
        int indice = hash(libro.getIsbn());

        for (Nodo nodo : tabla[indice]) {
            if (nodo.clave.equals(libro.getIsbn())) {
                System.out.println("El ISBN ya existe.");
                return false;
            }
        }

        tabla[indice].add(new Nodo(libro.getIsbn(), libro));
        System.out.println("main.modelo.Libro agregado correctamente.");
        return true;
    }

    public Libro buscar(String isbn) {
        int indice = hash(isbn);

        for (Nodo nodo : tabla[indice]) {
            if (nodo.clave.equals(isbn)) {
                return nodo.libro;
            }
        }

        return null;
    }

    public void mostrarTodos() {
        for (LinkedList<Nodo> lista : tabla) {
            for (Nodo nodo : lista) {
                System.out.println(nodo.libro);
            }
        }
    }
    public void reinsertarEnAVL(AVLPrestamos avl) {
        for (LinkedList<Nodo> lista : tabla) {
            for (Nodo nodo : lista) {
                avl.insertar(nodo.libro);
            }
        }
    }

    public List<Libro> obtenerTodos() {
        List<Libro> libros = new ArrayList<>();

        for (LinkedList<Nodo> lista : tabla) {
            for (Nodo nodo : lista) {
                libros.add(nodo.libro);
            }
        }

        return libros;
    }
}


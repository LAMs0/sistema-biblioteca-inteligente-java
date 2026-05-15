package main.estructuras;

import main.modelo.Libro;

import java.util.ArrayList;
import java.util.List;

public class AVLPrestamos {

    private static class NodoAVL {
        Libro libro;
        NodoAVL izquierda;
        NodoAVL derecha;
        int altura;

        NodoAVL(Libro libro) {
            this.libro = libro;
            this.altura = 1;
        }
    }

    private NodoAVL raiz;

    private int altura(NodoAVL nodo) {
        return nodo == null ? 0 : nodo.altura;
    }

    private int balance(NodoAVL nodo) {
        return nodo == null ? 0 : altura(nodo.izquierda) - altura(nodo.derecha);
    }

    private NodoAVL rotacionDerecha(NodoAVL y) {
        NodoAVL x = y.izquierda;
        NodoAVL temp = x.derecha;

        x.derecha = y;
        y.izquierda = temp;

        y.altura = Math.max(altura(y.izquierda), altura(y.derecha)) + 1;
        x.altura = Math.max(altura(x.izquierda), altura(x.derecha)) + 1;

        return x;
    }

    private NodoAVL rotacionIzquierda(NodoAVL x) {
        NodoAVL y = x.derecha;
        NodoAVL temp = y.izquierda;

        y.izquierda = x;
        x.derecha = temp;

        x.altura = Math.max(altura(x.izquierda), altura(x.derecha)) + 1;
        y.altura = Math.max(altura(y.izquierda), altura(y.derecha)) + 1;

        return y;
    }

    public void insertar(Libro libro) {
        raiz = insertarRec(raiz, libro);
    }

    private NodoAVL insertarRec(NodoAVL nodo, Libro libro) {
        if (nodo == null) {
            return new NodoAVL(libro);
        }

        if (libro.getPrestamos() < nodo.libro.getPrestamos()) {
            nodo.izquierda = insertarRec(nodo.izquierda, libro);
        } else {
            nodo.derecha = insertarRec(nodo.derecha, libro);
        }

        nodo.altura = 1 + Math.max(altura(nodo.izquierda), altura(nodo.derecha));

        int balance = balance(nodo);

        if (balance > 1 && libro.getPrestamos() < nodo.izquierda.libro.getPrestamos()) {
            return rotacionDerecha(nodo);
        }

        if (balance < -1 && libro.getPrestamos() >= nodo.derecha.libro.getPrestamos()) {
            return rotacionIzquierda(nodo);
        }

        if (balance > 1 && libro.getPrestamos() >= nodo.izquierda.libro.getPrestamos()) {
            nodo.izquierda = rotacionIzquierda(nodo.izquierda);
            return rotacionDerecha(nodo);
        }

        if (balance < -1 && libro.getPrestamos() < nodo.derecha.libro.getPrestamos()) {
            nodo.derecha = rotacionDerecha(nodo.derecha);
            return rotacionIzquierda(nodo);
        }

        return nodo;
    }

    public List<Libro> obtenerOrdenado() {
        List<Libro> libros = new ArrayList<>();
        mostrarInOrden(raiz, libros);
        return libros;
    }

    private void mostrarInOrden(NodoAVL nodo, List<Libro> libros) {

        if (nodo != null) {

            mostrarInOrden(nodo.izquierda, libros);

            libros.add(nodo.libro);

            mostrarInOrden(nodo.derecha, libros);
        }
    }
}
package main;

import main.modelo.Libro;
import main.estructuras.TablaHashLibros;
import main.estructuras.AVLPrestamos;
import main.estructuras.TrieTitulos;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        TablaHashLibros tablaHash = new TablaHashLibros(20);
        TrieTitulos trie = new TrieTitulos();
        AVLPrestamos avl = new AVLPrestamos();

        int opcion;

        Libro l1 = new Libro("978-1", "Programacion Java", "Deitel", 2020);
        Libro l2 = new Libro("978-2", "Programacion Web", "Juan Perez", 2021);
        Libro l3 = new Libro("978-3", "Clean Code", "Robert Martin", 2008);

        tablaHash.insertar(l1);
        tablaHash.insertar(l2);
        tablaHash.insertar(l3);

        trie.insertar(l1.getTitulo());
        trie.insertar(l2.getTitulo());
        trie.insertar(l3.getTitulo());

        avl.insertar(l1);
        avl.insertar(l2);
        avl.insertar(l3);

        do {
            System.out.println("======================================");
            System.out.println("   SISTEMA DE BIBLIOTECA INTELIGENTE");
            System.out.println("======================================");
            System.out.println("1. Registrar libro");
            System.out.println("2. Buscar libro por ISBN");
            System.out.println("3. Autocompletar título");
            System.out.println("4. Registrar préstamo");
            System.out.println("5. Mostrar libros ordenados por préstamos");
            System.out.println("6. Mostrar todos los libros");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {

                case 1:
                    System.out.print("Ingrese ISBN: ");
                    String isbn = sc.nextLine();

                    System.out.print("Ingrese título: ");
                    String titulo = sc.nextLine();

                    System.out.print("Ingrese autor: ");
                    String autor = sc.nextLine();

                    System.out.print("Ingrese año: ");
                    int anio = sc.nextInt();
                    sc.nextLine();

                    Libro libro = new Libro(isbn, titulo, autor, anio);

                    boolean agregado = tablaHash.insertar(libro);
                    if (agregado) {
                        trie.insertar(titulo);
                        avl.insertar(libro);
                    }

                    break;

                case 2:
                    System.out.print("Ingrese ISBN a buscar: ");
                    String buscarIsbn = sc.nextLine();

                    Libro encontrado = tablaHash.buscar(buscarIsbn);

                    if (encontrado != null) {
                        System.out.println("main.modelo.Libro encontrado:");
                        System.out.println(encontrado);
                    } else {
                        System.out.println("No se encontró ningún libro con ese ISBN.");
                    }

                    break;

                case 3:
                    System.out.print("Ingrese prefijo del título: ");
                    String prefijo = sc.nextLine();

                    List<String> sugerencias = trie.autocompletar(prefijo);

                    if (sugerencias.isEmpty()) {
                        System.out.println("No hay sugerencias para ese prefijo.");
                    } else {
                        System.out.println("Sugerencias:");
                        for (String sugerencia : sugerencias) {
                            System.out.println("- " + sugerencia);
                        }
                    }

                    break;

                case 4:
                    System.out.print("Ingrese ISBN del libro prestado: ");
                    String isbnPrestamo = sc.nextLine();

                    Libro libroPrestado = tablaHash.buscar(isbnPrestamo);

                    if (libroPrestado != null) {
                        libroPrestado.aumentarPrestamos();

                        // Reconstruimos el AVL para actualizar el orden
                        avl = new AVLPrestamos();
                        tablaHash.reinsertarEnAVL(avl);

                        System.out.println("Préstamo registrado correctamente.");
                    } else {
                        System.out.println("No se encontró el libro.");
                    }

                    break;

                case 5:
                    System.out.println("\nLibros ordenados por cantidad de préstamos:");
                    avl.mostrarOrdenado();
                    break;

                case 6:
                    System.out.println("\nTodos los libros registrados:");
                    tablaHash.mostrarTodos();
                    break;

                case 7:
                    System.out.println("Saliendo del sistema...");
                    break;

                default:
                    System.out.println("Opción inválida.");
            }

        } while (opcion != 7);

        sc.close();
    }
}
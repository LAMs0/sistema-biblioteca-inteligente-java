package main.modelo;

public class Libro {
    private String isbn;
    private String titulo;
    private String autor;
    private int anio;
    private int prestamos;

    public Libro(String isbn, String titulo, String autor, int anio) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.anio = anio;
        this.prestamos = 0;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public int getAnio() {
        return anio;
    }

    public int getPrestamos() {
        return prestamos;
    }

    public void aumentarPrestamos() {
        prestamos++;
    }

    @Override
    public String toString() {
        return "ISBN: " + isbn +
                " | Título: " + titulo +
                " | Autor: " + autor +
                " | Año: " + anio +
                " | Préstamos: " + prestamos;
    }
}
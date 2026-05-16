package main.vista.paneles;

import main.modelo.Libro;
import main.util.FuentesUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelTablaLibros extends JPanel {

    private JTable tablaLibros;
    private DefaultTableModel modeloTabla;

    public PanelTablaLibros() {
        setLayout(new BorderLayout());

        String[] columnas = {"ISBN", "Título", "Autor", "Año", "Préstamos"};

        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaLibros = new JTable(modeloTabla);
        tablaLibros.setRowHeight(28);
        tablaLibros.setFont(FuentesUI.NORMAL);
        tablaLibros.getTableHeader().setFont(FuentesUI.SUBTITULO);

        JScrollPane scrollPane = new JScrollPane(tablaLibros);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void cargarTabla(List<Libro> libros) {
        modeloTabla.setRowCount(0);

        for (Libro libro : libros) {
            modeloTabla.addRow(new Object[]{
                    libro.getIsbn(),
                    libro.getTitulo(),
                    libro.getAutor(),
                    libro.getAnio(),
                    libro.getPrestamos()
            });
        }
    }
}
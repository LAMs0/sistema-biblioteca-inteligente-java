package main.vista;

import javax.swing.*;
import java.awt.*;
import main.modelo.Libro;
import main.servicio.BibliotecaService;
import main.vista.BibliotecaFrame;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class BibliotecaFrame extends JFrame {

    private BibliotecaService service;
    private JTable tablaLibros;
    private DefaultTableModel modeloTabla;

    public BibliotecaFrame() {
        service = new BibliotecaService();

        setTitle("Sistema de Biblioteca Inteligente");
        setSize(1100, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(900, 550));

        inicializarComponentes();
        cargarTabla(service.obtenerTodosLosLibros());
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout());

        JPanel panelSuperior = new JPanel();
        panelSuperior.setBackground(new Color(18, 18, 18));
        panelSuperior.setPreferredSize(new Dimension(100, 70));

        JLabel titulo = new JLabel("Sistema de Biblioteca Inteligente");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));

        panelSuperior.add(titulo);

        JPanel panelLateral = new JPanel();
        panelLateral.setBackground(new Color(30, 30, 30));
        panelLateral.setPreferredSize(new Dimension(230, 100));
        panelLateral.setLayout(new GridLayout(6, 1, 10, 10));

        JButton btnRegistrar = new JButton("Registrar libro");
        JButton btnBuscar = new JButton("Buscar ISBN");
        JButton btnPrestamo = new JButton("Registrar préstamo");
        JButton btnOrdenar = new JButton("Ordenar por préstamos");
        JButton btnActualizar = new JButton("Actualizar tabla");
        JButton btnSalir = new JButton("Salir");

        panelLateral.add(btnRegistrar);
        panelLateral.add(btnBuscar);
        panelLateral.add(btnPrestamo);
        panelLateral.add(btnOrdenar);
        panelLateral.add(btnActualizar);
        panelLateral.add(btnSalir);

        JPanel panelCentral = new JPanel();
        panelCentral.setBackground(new Color(245, 245, 245));
        panelCentral.setLayout(new BorderLayout());

        String[] columnas = {"ISBN", "Título", "Autor", "Año", "Préstamos"};

        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaLibros = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaLibros);

        panelCentral.add(scrollPane, BorderLayout.CENTER);

        add(panelSuperior, BorderLayout.NORTH);
        add(panelLateral, BorderLayout.WEST);
        add(panelCentral, BorderLayout.CENTER);

        btnSalir.addActionListener(e -> System.exit(0));
        btnActualizar.addActionListener(e -> cargarTabla(service.obtenerTodosLosLibros()));
    }

    private void cargarTabla(List<Libro> libros) {
        modeloTabla.setRowCount(0);

        for (Libro libro : libros) {
            Object[] fila = {
                    libro.getIsbn(),
                    libro.getTitulo(),
                    libro.getAutor(),
                    libro.getAnio(),
                    libro.getPrestamos()
            };

            modeloTabla.addRow(fila);
        }
    }
}
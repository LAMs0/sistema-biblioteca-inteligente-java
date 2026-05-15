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
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new GridLayout(2, 4, 10, 10));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Registrar Libro"));

        JTextField txtISBN = new JTextField();
        JTextField txtTitulo = new JTextField();
        JTextField txtAutor = new JTextField();
        JTextField txtAnio = new JTextField();

        panelFormulario.add(new JLabel("ISBN"));
        panelFormulario.add(new JLabel("Título"));
        panelFormulario.add(new JLabel("Autor"));
        panelFormulario.add(new JLabel("Año"));

        panelFormulario.add(txtISBN);
        panelFormulario.add(txtTitulo);
        panelFormulario.add(txtAutor);
        panelFormulario.add(txtAnio);
        JButton btnGuardar = new JButton("Guardar Libro");
        panelFormulario.add(btnGuardar);

        btnBuscar.addActionListener(e -> {
            String isbn = JOptionPane.showInputDialog(
                    this,
                    "Ingrese el ISBN del libro:"
            );

            if (isbn != null && !isbn.isEmpty()) {
                Libro libro = service.buscarPorISBN(isbn);

                if (libro != null) {
                    JOptionPane.showMessageDialog(
                            this,
                            libro.toString(),
                            "Libro encontrado",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "No se encontró ningún libro con ese ISBN."
                    );
                }
            }
        });

        btnPrestamo.addActionListener(e -> {
            String isbn = JOptionPane.showInputDialog(
                    this,
                    "Ingrese el ISBN del libro prestado:"
            );

            if (isbn != null && !isbn.isEmpty()) {
                boolean prestamoRegistrado = service.registrarPrestamo(isbn);

                if (prestamoRegistrado) {
                    cargarTabla(service.obtenerTodosLosLibros());

                    JOptionPane.showMessageDialog(
                            this,
                            "Préstamo registrado correctamente."
                    );
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "No se encontró el libro."
                    );
                }
            }
        });

        btnOrdenar.addActionListener(e -> {
            cargarTabla(service.obtenerLibrosOrdenadosPorPrestamos());

            JOptionPane.showMessageDialog(
                    this,
                    "Libros ordenados por cantidad de préstamos."
            );
        });

        String[] columnas = {"ISBN", "Título", "Autor", "Año", "Préstamos"};

        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaLibros = new JTable(modeloTabla);

        tablaLibros.setRowHeight(28);

        tablaLibros.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        tablaLibros.getTableHeader().setFont(
                new Font("Segoe UI", Font.BOLD, 14)
        );

        tablaLibros.getTableHeader().setBackground(
                new Color(30, 30, 30)
        );

        tablaLibros.getTableHeader().setForeground(Color.WHITE);

        tablaLibros.setSelectionBackground(
                new Color(70, 130, 180)
        );

        JScrollPane scrollPane = new JScrollPane(tablaLibros);

        panelCentral.add(scrollPane, BorderLayout.CENTER);
        panelCentral.add(panelFormulario, BorderLayout.NORTH);

        add(panelSuperior, BorderLayout.NORTH);
        add(panelLateral, BorderLayout.WEST);
        add(panelCentral, BorderLayout.CENTER);

        btnSalir.addActionListener(e -> System.exit(0));
        btnActualizar.addActionListener(e -> cargarTabla(service.obtenerTodosLosLibros()));
            try {

            String isbn = txtISBN.getText();
            String tituloLibro = txtTitulo.getText();
            String autorLibro = txtAutor.getText();
            int anio = Integer.parseInt(txtAnio.getText());

            boolean agregado = service.registrarLibro(
                    isbn,
                    tituloLibro,
                    autorLibro,
                    anio
            );

            if (agregado) {

                cargarTabla(service.obtenerTodosLosLibros());

                txtISBN.setText("");
                txtTitulo.setText("");
                txtAutor.setText("");
                txtAnio.setText("");

                JOptionPane.showMessageDialog(
                        this,
                        "Libro registrado correctamente."
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "No se pudo registrar el libro."
                );
            }

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "El año debe ser numérico."
            );
        }

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
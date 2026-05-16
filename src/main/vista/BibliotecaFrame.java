package main.vista;

import main.modelo.Libro;
import main.servicio.BibliotecaService;
import main.util.ColoresUI;
import main.util.FuentesUI;
import main.vista.componentes.Boton;
import main.vista.paneles.PanelBusqueda;
import main.vista.paneles.PanelFormulario;
import main.vista.paneles.PanelTablaLibros;

import javax.swing.*;
import java.awt.*;

public class BibliotecaFrame extends JFrame {

    private BibliotecaService service;

    private PanelFormulario panelFormulario;
    private PanelTablaLibros panelTabla;
    private PanelBusqueda panelBusqueda;

    public BibliotecaFrame() {

        service = new BibliotecaService();

        setTitle("Sistema de Biblioteca Inteligente");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        inicializarComponentes();

        panelTabla.cargarTabla(service.obtenerTodosLosLibros());
    }

    private void inicializarComponentes() {

        setLayout(new BorderLayout());

        /*
         =========================
         PANEL SUPERIOR
         =========================
        */

        JPanel panelSuperior = new JPanel(new BorderLayout());

        panelSuperior.setBackground(ColoresUI.FONDO_OSCURO);
        panelSuperior.setPreferredSize(new Dimension(100, 80));

        JLabel titulo = new JLabel(
                "Sistema de Biblioteca Inteligente"
        );

        titulo.setForeground(ColoresUI.TEXTO_BLANCO);
        titulo.setFont(FuentesUI.TITULO);

        JPanel contenedorTitulo = new JPanel(new FlowLayout(
                FlowLayout.LEFT,
                20,
                20
        ));

        contenedorTitulo.setBackground(ColoresUI.FONDO_OSCURO);

        contenedorTitulo.add(titulo);

        panelBusqueda = new PanelBusqueda(service);

        panelSuperior.add(contenedorTitulo, BorderLayout.WEST);
        panelSuperior.add(panelBusqueda, BorderLayout.EAST);

        /*
         =========================
         PANEL LATERAL
         =========================
        */

        JPanel panelLateral = new JPanel();

        panelLateral.setBackground(ColoresUI.PANEL_OSCURO);
        panelLateral.setPreferredSize(new Dimension(230, 100));

        panelLateral.setLayout(new GridLayout(
                6,
                1,
                10,
                10
        ));

        Boton btnRegistrar = new Boton("Registrar libro");
        Boton btnBuscar = new Boton("Buscar ISBN");
        Boton btnPrestamo = new Boton("Registrar préstamo");
        Boton btnOrdenar = new Boton("Ordenar por préstamos");
        Boton btnActualizar = new Boton("Actualizar tabla");
        Boton btnSalir = new Boton("Salir");

        panelLateral.add(btnRegistrar);
        panelLateral.add(btnBuscar);
        panelLateral.add(btnPrestamo);
        panelLateral.add(btnOrdenar);
        panelLateral.add(btnActualizar);
        panelLateral.add(btnSalir);

        /*
         =========================
         PANEL CENTRAL
         =========================
        */

        JPanel panelCentral = new JPanel(
                new BorderLayout()
        );

        panelCentral.setBackground(
                ColoresUI.FONDO_CLARO
        );

        panelFormulario = new PanelFormulario();

        panelTabla = new PanelTablaLibros();

        panelCentral.add(
                panelFormulario,
                BorderLayout.NORTH
        );

        panelCentral.add(
                panelTabla,
                BorderLayout.CENTER
        );

        /*
         =========================
         AGREGAR PANELES
         =========================
        */

        add(panelSuperior, BorderLayout.NORTH);
        add(panelLateral, BorderLayout.WEST);
        add(panelCentral, BorderLayout.CENTER);

        /*
         =========================
         EVENTOS
         =========================
        */

        btnSalir.addActionListener(
                e -> System.exit(0)
        );

        btnActualizar.addActionListener(e ->
                panelTabla.cargarTabla(
                        service.obtenerTodosLosLibros()
                )
        );

        /*
         =========================
         GUARDAR LIBRO
         =========================
        */

        panelFormulario.btnGuardar.addActionListener(e -> {

            try {

                String isbn =
                        panelFormulario.txtISBN.getText();

                String tituloLibro =
                        panelFormulario.txtTitulo.getText();

                String autorLibro =
                        panelFormulario.txtAutor.getText();

                int anio = Integer.parseInt(
                        panelFormulario.txtAnio.getText()
                );

                boolean agregado =
                        service.registrarLibro(
                                isbn,
                                tituloLibro,
                                autorLibro,
                                anio
                        );

                if (agregado) {

                    panelTabla.cargarTabla(
                            service.obtenerTodosLosLibros()
                    );

                    panelFormulario.txtISBN.setText("");
                    panelFormulario.txtTitulo.setText("");
                    panelFormulario.txtAutor.setText("");
                    panelFormulario.txtAnio.setText("");

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
        });

        /*
         =========================
         BUSCAR ISBN
         =========================
        */

        btnBuscar.addActionListener(e -> {

            String isbn = JOptionPane.showInputDialog(
                    this,
                    "Ingrese el ISBN:"
            );

            if (isbn != null && !isbn.isEmpty()) {

                Libro libro =
                        service.buscarPorISBN(isbn);

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
                            "No se encontró el libro."
                    );
                }
            }
        });

        /*
         =========================
         REGISTRAR PRÉSTAMO
         =========================
        */

        btnPrestamo.addActionListener(e -> {

            String isbn = JOptionPane.showInputDialog(
                    this,
                    "Ingrese ISBN del préstamo:"
            );

            if (isbn != null && !isbn.isEmpty()) {

                boolean registrado =
                        service.registrarPrestamo(isbn);

                if (registrado) {

                    panelTabla.cargarTabla(
                            service.obtenerTodosLosLibros()
                    );

                    JOptionPane.showMessageDialog(
                            this,
                            "Préstamo registrado."
                    );

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Libro no encontrado."
                    );
                }
            }
        });

        /*
         =========================
         ORDENAR AVL
         =========================
        */

        btnOrdenar.addActionListener(e -> {

            panelTabla.cargarTabla(
                    service.obtenerLibrosOrdenadosPorPrestamos()
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Libros ordenados por préstamos."
            );
        });
    }
}
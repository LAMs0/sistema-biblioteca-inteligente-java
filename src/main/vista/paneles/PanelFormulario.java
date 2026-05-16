package main.vista.paneles;

import main.vista.componentes.Boton;
import main.vista.componentes.CampoTexto;

import javax.swing.*;
import java.awt.*;

public class PanelFormulario extends JPanel {

    public CampoTexto txtISBN;
    public CampoTexto txtTitulo;
    public CampoTexto txtAutor;
    public CampoTexto txtAnio;
    public Boton btnGuardar;

    public PanelFormulario() {
        setLayout(new GridLayout(2, 5, 10, 10));
        setBorder(BorderFactory.createTitledBorder("Registrar Libro"));

        txtISBN = new CampoTexto();
        txtTitulo = new CampoTexto();
        txtAutor = new CampoTexto();
        txtAnio = new CampoTexto();
        btnGuardar = new Boton("Guardar Libro");

        add(new JLabel("ISBN"));
        add(new JLabel("Título"));
        add(new JLabel("Autor"));
        add(new JLabel("Año"));
        add(new JLabel("Acción"));

        add(txtISBN);
        add(txtTitulo);
        add(txtAutor);
        add(txtAnio);
        add(btnGuardar);
    }
}
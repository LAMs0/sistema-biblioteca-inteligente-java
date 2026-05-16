package main.vista.paneles;

import main.servicio.BibliotecaService;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.List;

public class PanelBusqueda extends JPanel {

    private JTextField txtBuscarTitulo;
    private JPopupMenu popupSugerencias;

    public PanelBusqueda(BibliotecaService service) {
        setBackground(new Color(18, 18, 18));

        JLabel lblBuscar = new JLabel("Buscar título:");
        lblBuscar.setForeground(Color.WHITE);

        txtBuscarTitulo = new JTextField(20);
        popupSugerencias = new JPopupMenu();

        add(lblBuscar);
        add(txtBuscarTitulo);

        txtBuscarTitulo.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                mostrarSugerencias(service);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                mostrarSugerencias(service);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                mostrarSugerencias(service);
            }
        });
    }

    private void mostrarSugerencias(BibliotecaService service) {
        popupSugerencias.removeAll();

        String texto = txtBuscarTitulo.getText();

        if (texto.isEmpty()) {
            popupSugerencias.setVisible(false);
            return;
        }

        List<String> sugerencias = service.autocompletarTitulos(texto);

        if (sugerencias.isEmpty()) {
            popupSugerencias.setVisible(false);
            return;
        }

        for (String sugerencia : sugerencias) {
            JMenuItem item = new JMenuItem(sugerencia);

            item.addActionListener(e -> {
                txtBuscarTitulo.setText(sugerencia);
                popupSugerencias.setVisible(false);
            });

            popupSugerencias.add(item);
        }

        popupSugerencias.show(txtBuscarTitulo, 0, txtBuscarTitulo.getHeight());
    }
}
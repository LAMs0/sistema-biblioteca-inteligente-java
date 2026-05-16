package main.vista.componentes;

import main.util.ColoresUI;
import main.util.FuentesUI;

import javax.swing.*;
import java.awt.*;

public class Boton extends JButton {

    public Boton(String texto) {
        super(texto);

        setFont(FuentesUI.BOTON);
        setForeground(Color.BLACK);
        setBackground(new Color(230, 240, 250));
        setFocusPainted(false);
        setBorderPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}
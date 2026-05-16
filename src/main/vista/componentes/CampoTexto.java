package main.vista.componentes;

import main.util.FuentesUI;

import javax.swing.*;
import java.awt.*;

public class CampoTexto extends JTextField {

    public CampoTexto() {
        setFont(FuentesUI.NORMAL);
        setPreferredSize(new Dimension(160, 28));
    }

    public CampoTexto(int columnas) {
        super(columnas);
        setFont(FuentesUI.NORMAL);
    }
}
package main.estructuras;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrieTitulos {

    private static class NodoTrie {
        Map<Character, NodoTrie> hijos = new HashMap<>();
        boolean finDePalabra = false;
        String tituloCompleto = null;
    }

    private NodoTrie raiz;

    public TrieTitulos() {
        raiz = new NodoTrie();
    }

    public void insertar(String titulo) {
        NodoTrie actual = raiz;
        String tituloMinuscula = titulo.toLowerCase();

        for (char letra : tituloMinuscula.toCharArray()) {
            actual.hijos.putIfAbsent(letra, new NodoTrie());
            actual = actual.hijos.get(letra);
        }

        actual.finDePalabra = true;
        actual.tituloCompleto = titulo;
    }

    public List<String> autocompletar(String prefijo) {
        List<String> resultados = new ArrayList<>();
        NodoTrie actual = raiz;

        String prefijoMinuscula = prefijo.toLowerCase();

        for (char letra : prefijoMinuscula.toCharArray()) {
            if (!actual.hijos.containsKey(letra)) {
                return resultados;
            }

            actual = actual.hijos.get(letra);
        }

        buscarTitulos(actual, resultados);
        return resultados;
    }

    private void buscarTitulos(NodoTrie nodo, List<String> resultados) {
        if (nodo.finDePalabra) {
            resultados.add(nodo.tituloCompleto);
        }

        for (NodoTrie hijo : nodo.hijos.values()) {
            buscarTitulos(hijo, resultados);
        }
    }
}
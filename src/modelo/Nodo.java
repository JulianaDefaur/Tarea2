// Benjamin Bravo y Juliana Defaur
package modelo;

import java.util.Arrays;

public class Nodo {
    String palabra;
    int[] p; // arreglo dinámico de frecuencias por página
    Nodo izq, der;
    int altura;

    public Nodo(String palabra) {
        this.palabra = palabra;
        this.p = new int[2]; // tamaño inicial pequeño, se expandirá si es necesario
    }

    public void aumentarFrecuencia(int pagina) {
        if (pagina < 1) return;
        if (p == null || p.length <= pagina) {
            p = Arrays.copyOf(p, pagina + 1);
        }
        p[pagina]++;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(palabra);
        for (int i = 1; i < p.length; i++) {
            if (p[i] > 0) {
                sb.append(" ").append(i);
                if (p[i] > 1) {
                    sb.append("(").append(p[i]).append(")");
                }
            }
        }
        return sb.toString();
    }
}


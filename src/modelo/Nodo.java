package modelo;

import java.util.*;

public class Nodo {
    String palabra;
    int[] p = new int[2]; // Esto representa las frecuencias como {0, 3, 0, 6, 0}
    Nodo izq, der;
    int altura;

    public Nodo(String palabra){
        this.palabra = palabra;
    }

    // Aumenta la frecuencia de la palabra en una pagina dada
    public void aumentarFrecuencia(int pagina){
        if (pagina < 1) return;
        // Como el tamaño de p es limitado, debemos expandirlo si no existe el índice
        // Por ejemplo, si queremos aumentar la frecuencia en la pagina 5 cuando p solo nos
        // Permite 3 paginas, debemos aumentar el tamaño de p
        if (p == null) {
            p = new int[pagina + 1];
        } else if (p.length <= pagina) {
            p = Arrays.copyOf(p, pagina + 1);
        }
        p[pagina]++;
    }

    @Override
    public String toString(){
        String sb = " ";
        for (int i = 0; i < p.length; i++) {
            if (p[i] > 0) {
                sb = sb+(" "+i);
                if (p[i] > 1) sb = sb+("("+p[i+1]+") ");
                sb += " ";
            }
        }
        return palabra+sb;
    }
}

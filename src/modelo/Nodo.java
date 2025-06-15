package modelo;

import java.util.*;

public class Nodo {
    String palabra;
    NodoPagina raizPaginas;
    int[] p; // Esto representa las paginas {0, 3, 0, 6, 0}
    Nodo izq, der;
    int altura;

    public Nodo(String palabra, int pagina) {
        this.palabra = palabra;
        this.raizPaginas = insertarPagina(null, pagina);
        this.altura = 1;
        this.sumarPagina(pagina);
    }

    // Aumenta la frecuencia de la palabra en una pagina dada
    public void sumarPagina(int pagina){
        if (pagina < 1) return;
        // Como el tamaño de p es limitado, debemos expandirlo si no existe el índice
        // Por ejemplo, si queremos aumentar la frecuencia en la pagina 5 cuando p solo nos
        // Permite 3 paginas, debemos aumentar el tamaño
        if (p == null) {
            p = new int[pagina + 1];
        } else if (p.length <= pagina) {
            p = Arrays.copyOf(p, pagina + 1);
        }
        p[pagina]++;
    }

    public NodoPagina insertarPagina(NodoPagina nodo, int pagina) {
        if (nodo == null) return new NodoPagina(pagina);
        if (pagina < nodo.pagina)
            nodo.izq = insertarPagina(nodo.izq, pagina);
        else if (pagina > nodo.pagina)
            nodo.der = insertarPagina(nodo.der, pagina);
        else {
            nodo.frecuencia++;
            return nodo;
        }

        nodo.altura = 1 + Math.max(alturaPagina(nodo.izq), alturaPagina(nodo.der));
        return balancearPagina(nodo);
    }

    public int alturaPagina(NodoPagina n) {
        if (n == null) {
            return 0;
        } else {
            return n.altura;
        }
    }

    public int balancePagina(NodoPagina n) {
        if (n == null) {
            return 0;
        } else {
            return alturaPagina(n.izq) - alturaPagina(n.der);
        }
    }


    public NodoPagina rotarDerechaPagina(NodoPagina y) {
        NodoPagina x = y.izq;
        NodoPagina T2 = x.der;

        x.der = y;
        y.izq = T2;

        y.altura = Math.max(alturaPagina(y.izq), alturaPagina(y.der)) + 1;
        x.altura = Math.max(alturaPagina(x.izq), alturaPagina(x.der)) + 1;

        return x;
    }

    public NodoPagina rotarIzquierdaPagina(NodoPagina x) {
        NodoPagina y = x.der;
        NodoPagina T2 = y.izq;

        y.izq = x;
        x.der = T2;

        x.altura = Math.max(alturaPagina(x.izq), alturaPagina(x.der)) + 1;
        y.altura = Math.max(alturaPagina(y.izq), alturaPagina(y.der)) + 1;

        return y;
    }

    public NodoPagina balancearPagina(NodoPagina n) {
        int balance = balancePagina(n);

        if (balance > 1) {
            if (balancePagina(n.izq) < 0)
                n.izq = rotarIzquierdaPagina(n.izq);
            return rotarDerechaPagina(n);
        }
        if (balance < -1) {
            if (balancePagina(n.der) > 0)
                n.der = rotarDerechaPagina(n.der);
            return rotarIzquierdaPagina(n);
        }
        return n;
    }

    public void agregarPagina(int pagina) {
        raizPaginas = insertarPagina(raizPaginas, pagina);
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

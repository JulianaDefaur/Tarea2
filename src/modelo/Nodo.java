package modelo;

public class Nodo {
    String palabra;
    NodoPagina raizPaginas;
    Nodo izq, der;
    int altura;

    Nodo(String palabra, int pagina) {
        this.palabra = palabra;
        this.raizPaginas = insertarPagina(null, pagina);
        this.altura = 1;
    }

    NodoPagina insertarPagina(NodoPagina nodo, int pagina) {
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

    int alturaPagina(NodoPagina n) {
        return (n == null) ? 0 : n.altura;
    }

    int balancePagina(NodoPagina n) {
        return (n == null) ? 0 : alturaPagina(n.izq) - alturaPagina(n.der);
    }

    NodoPagina rotarDerechaPagina(NodoPagina y) {
        NodoPagina x = y.izq;
        NodoPagina T2 = x.der;

        x.der = y;
        y.izq = T2;

        y.altura = Math.max(alturaPagina(y.izq), alturaPagina(y.der)) + 1;
        x.altura = Math.max(alturaPagina(x.izq), alturaPagina(x.der)) + 1;

        return x;
    }

    NodoPagina rotarIzquierdaPagina(NodoPagina x) {
        NodoPagina y = x.der;
        NodoPagina T2 = y.izq;

        y.izq = x;
        x.der = T2;

        x.altura = Math.max(alturaPagina(x.izq), alturaPagina(x.der)) + 1;
        y.altura = Math.max(alturaPagina(y.izq), alturaPagina(y.der)) + 1;

        return y;
    }

    NodoPagina balancearPagina(NodoPagina n) {
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

    void agregarPagina(int pagina) {
        raizPaginas = insertarPagina(raizPaginas, pagina);
    }
}

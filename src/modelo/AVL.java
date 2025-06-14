package modelo;

import java.util.ArrayList;
import java.util.List;

public class AVL {
    private class NodoPagina{
        int pagina, frecuencia, altura;
        NodoPagina izq, der;

        NodoPagina(int pagina) {
            this.pagina = pagina;
            frecuencia = 1;
            altura = 1;
        }
    }

    private class Nodo {
        String palabra;
        NodoPagina raizPaginas; // AVL para páginas
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

    private Nodo raiz;

    public void insertar(String palabra, int pagina) {
        raiz = insertar(raiz, palabra.toLowerCase(), pagina);
    }

    private Nodo insertar(Nodo nodo, String palabra, int pagina) {
        if (nodo == null) return new Nodo(palabra, pagina);

        int cmp = palabra.compareTo(nodo.palabra);
        if (cmp < 0)
            nodo.izq = insertar(nodo.izq, palabra, pagina);
        else if (cmp > 0)
            nodo.der = insertar(nodo.der, palabra, pagina);
        else
            nodo.agregarPagina(pagina);

        nodo.altura = 1 + Math.max(altura(nodo.izq), altura(nodo.der));
        return balancear(nodo);
    }

    private int altura(Nodo n) {
        return (n == null) ? 0 : n.altura;
    }

    private int balance(Nodo n) {
        return (n == null) ? 0 : altura(n.izq) - altura(n.der);
    }

    private Nodo rotarDerecha(Nodo y) {
        Nodo x = y.izq;
        Nodo T2 = x.der;

        x.der = y;
        y.izq = T2;

        y.altura = Math.max(altura(y.izq), altura(y.der)) + 1;
        x.altura = Math.max(altura(x.izq), altura(x.der)) + 1;

        return x;
    }

    private Nodo rotarIzquierda(Nodo x) {
        Nodo y = x.der;
        Nodo T2 = y.izq;

        y.izq = x;
        x.der = T2;

        x.altura = Math.max(altura(x.izq), altura(x.der)) + 1;
        y.altura = Math.max(altura(y.izq), altura(y.der)) + 1;

        return y;
    }

    private Nodo balancear(Nodo n) {
        int balance = balance(n);

        if (balance > 1) {
            if (balance(n.izq) < 0)
                n.izq = rotarIzquierda(n.izq);
            return rotarDerecha(n);
        }
        if (balance < -1) {
            if (balance(n.der) > 0)
                n.der = rotarDerecha(n.der);
            return rotarIzquierda(n);
        }
        return n;
    }

    public List<Integer> buscarPaginas(String palabra) {
        Nodo nodo = buscar(raiz, palabra.toLowerCase());
        List<Integer> resultado = new ArrayList<>();
        if (nodo != null) {
            inorderPaginas(nodo.raizPaginas, resultado);
        }
        return resultado;
    }

    private void inorderPaginas(NodoPagina nodo, List<Integer> resultado) {
        if (nodo == null) return;
        inorderPaginas(nodo.izq, resultado);
        resultado.add(nodo.pagina);
        inorderPaginas(nodo.der, resultado);
    }

    private Nodo buscar(Nodo nodo, String palabra) {
        if (nodo == null) return null;
        int cmp = palabra.compareTo(nodo.palabra);
        if (cmp == 0) return nodo;
        return (cmp < 0) ? buscar(nodo.izq, palabra) : buscar(nodo.der, palabra);
    }

    // NUEVO: Imprimir todo el índice ordenado
    public void imprimirIndice() {
        imprimirIndice(raiz);
    }

    private void imprimirIndice(Nodo nodo) {
        if (nodo == null) return;
        imprimirIndice(nodo.izq);
        System.out.print(nodo.palabra + ": ");
        List<Integer> paginas = new ArrayList<>();
        inorderPaginas(nodo.raizPaginas, paginas);
        System.out.println(paginas);
        imprimirIndice(nodo.der);
    }

    // NUEVO: Intersección de páginas entre dos palabras/frases
    public List<Integer> buscarInterseccion(String w1, String w2) {
        List<Integer> paginas1 = buscarPaginas(w1);
        List<Integer> paginas2 = buscarPaginas(w2);
        List<Integer> resultado = new ArrayList<>();
        int i = 0, j = 0;
        while (i < paginas1.size() && j < paginas2.size()) {
            int p1 = paginas1.get(i);
            int p2 = paginas2.get(j);
            if (p1 == p2) {
                resultado.add(p1);
                i++; j++;
            } else if (p1 < p2) {
                i++;
            } else {
                j++;
            }
        }
        return resultado;
    }

    // NUEVO: Unión de páginas entre dos palabras/frases
    public List<Integer> buscarUnion(String w1, String w2) {
        List<Integer> paginas1 = buscarPaginas(w1);
        List<Integer> paginas2 = buscarPaginas(w2);
        List<Integer> resultado = new ArrayList<>();
        int i = 0, j = 0;
        while (i < paginas1.size() && j < paginas2.size()) {
            int p1 = paginas1.get(i);
            int p2 = paginas2.get(j);
            if (p1 == p2) {
                resultado.add(p1);
                i++; j++;
            } else if (p1 < p2) {
                resultado.add(p1);
                i++;
            } else {
                resultado.add(p2);
                j++;
            }
        }
        while (i < paginas1.size()) {
            resultado.add(paginas1.get(i++));
        }
        while (j < paginas2.size()) {
            resultado.add(paginas2.get(j++));
        }
        return resultado;
    }
}

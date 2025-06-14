package modelo;

import java.util.*;

public class AVL {
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

    public void imprimirIndice() {
        Map<Character, List<String>> indice = new TreeMap<>();
        construirIndice(raiz, indice);
        for (char letra : indice.keySet()) {
            System.out.println("-" + Character.toUpperCase(letra) + "-");
            for (String entrada : indice.get(letra)) {
                System.out.println(entrada);
            }
        }
    }

    private void construirIndice(Nodo nodo, Map<Character, List<String>> indice) {
        if (nodo == null) return;
        construirIndice(nodo.izq, indice);
        String palabra = nodo.palabra;
        List<String> lista = indice.computeIfAbsent(palabra.charAt(0), k -> new ArrayList<>());
        lista.add(formatearEntrada(palabra, nodo.raizPaginas));
        construirIndice(nodo.der, indice);
    }

    private String formatearEntrada(String palabra, NodoPagina raiz) {
        StringBuilder sb = new StringBuilder();
        sb.append(capitalizar(palabra)).append(" ");
        List<String> paginas = new ArrayList<>();
        formatearPaginas(raiz, paginas);
        sb.append(String.join(", ", paginas));
        return sb.toString();
    }

    private void formatearPaginas(NodoPagina nodo, List<String> paginas) {
        if (nodo == null) return;
        formatearPaginas(nodo.izq, paginas);
        if (nodo.frecuencia == 1)
            paginas.add(String.valueOf(nodo.pagina));
        else
            paginas.add(nodo.pagina + "(" + nodo.frecuencia + ")");
        formatearPaginas(nodo.der, paginas);
    }

    private String capitalizar(String palabra) {
        if (palabra == null || palabra.isEmpty()) return palabra;
        String[] partes = palabra.split(" ");
        StringBuilder resultado = new StringBuilder();
        for (String parte : partes) {
            if (!parte.isEmpty()) {
                resultado.append(Character.toUpperCase(parte.charAt(0)))
                        .append(parte.substring(1))
                        .append(" ");
            }
        }
        return resultado.toString().trim();
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

    public List<Integer> buscarInterseccion(String w1, String w2) {
        List<Integer> p1 = buscarPaginas(w1);
        List<Integer> p2 = buscarPaginas(w2);
        List<Integer> res = new ArrayList<>();
        int i = 0, j = 0;
        while (i < p1.size() && j < p2.size()) {
            if (p1.get(i).equals(p2.get(j))) {
                res.add(p1.get(i));
                i++;
                j++;
            } else if (p1.get(i) < p2.get(j)) {
                i++;
            } else {
                j++;
            }
        }
        return res;
    }

    public List<Integer> buscarUnion(String w1, String w2) {
        List<Integer> p1 = buscarPaginas(w1);
        List<Integer> p2 = buscarPaginas(w2);
        List<Integer> res = new ArrayList<>();
        int i = 0, j = 0;
        while (i < p1.size() && j < p2.size()) {
            if (p1.get(i).equals(p2.get(j))) {
                res.add(p1.get(i));
                i++;
                j++;
            } else if (p1.get(i) < p2.get(j)) {
                res.add(p1.get(i++));
            } else {
                res.add(p2.get(j++));
            }
        }
        while (i < p1.size()) res.add(p1.get(i++));
        while (j < p2.size()) res.add(p2.get(j++));
        return res;
    }
}

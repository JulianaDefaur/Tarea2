package modelo;

import java.util.*;

public class AVL {
    private Nodo raiz;

    public void insertar(String palabra, int pagina) {
        raiz = insertar(raiz, new Nodo(palabra), pagina);
    }

    private Nodo insertar(Nodo nodo, Nodo n, int pagina) {
        if (nodo == null) {
            n.aumentarFrecuencia(pagina);
            return n;
        }
        int cmp = n.palabra.compareTo(nodo.palabra);
        if (cmp < 0) {
            nodo.izq = insertar(nodo.izq, n, pagina);
        } else if (cmp > 0) {
            nodo.der = insertar(nodo.der, n, pagina);
        } else {
            nodo.aumentarFrecuencia(pagina);
        }

        nodo.altura = 1 + Math.max(altura(nodo.izq), altura(nodo.der));
        return balancear(nodo);
    }

    private int altura(Nodo n) {
        return (n == null) ? 0 : n.altura;
    }

    private int calcularBalance(Nodo n) {
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
        int balance = calcularBalance(n);
        if (balance > 1) {
            if (calcularBalance(n.izq) < 0) {
                n.izq = rotarIzquierda(n.izq);
            }
            return rotarDerecha(n);
        }
        if (balance < -1) {
            if (calcularBalance(n.der) > 0) {
                n.der = rotarDerecha(n.der);
            }
            return rotarIzquierda(n);
        }
        return n;
    }

    public void imprimirIndice() {
        Map<Character, List<String>> indice = new TreeMap<>();
        construirIndice(raiz, indice);
        for (char letra : indice.keySet()) {
            System.out.println("\n  -" + Character.toUpperCase(letra) + "   -");
            for (String entrada : indice.get(letra)) {
                System.out.println(entrada);
            }
        }
    }

    private void construirIndice(Nodo nodo, Map<Character, List<String>> indice) {
        if (nodo == null) return;
        construirIndice(nodo.izq, indice);
        String palabra = nodo.palabra;
        List<String> lista = indice.computeIfAbsent(Character.toLowerCase(palabra.charAt(0)), k -> new ArrayList<>());
        lista.add(formatearEntrada(palabra, nodo.p));
        construirIndice(nodo.der, indice);
    }

    private String formatearEntrada(String palabra, int[] p) {
        StringBuilder sb = new StringBuilder();
        sb.append(capitalizar(palabra)).append(" ");
        List<String> paginas = new ArrayList<>();
        for (int i = 0; i < p.length; i++) {
            if (p[i] > 0) {
                if (p[i] == 1) paginas.add(String.valueOf(i));
                else paginas.add(i + "(" + p[i] + ")");
            }
        }
        sb.append(String.join(", ", paginas));
        return sb.toString();
    }

    private String capitalizar(String palabra) {
        if (palabra == null || palabra.isEmpty()) return palabra;
        String[] partes = palabra.split("\\s+");
        StringBuilder resultado = new StringBuilder();
        for (String parte : partes) {
            resultado.append(parte.charAt(0))
                    .append(parte.substring(1))
                    .append(" ");
        }
        return resultado.toString().trim();
    }

    public int[] obtenerPaginasDe(String palabra) {
        Nodo n = buscar(raiz, palabra.toLowerCase());
        return (n != null && n.p != null) ? n.p : null;
    }

    public Nodo buscar(String palabra) {
        return  buscar(raiz, palabra.toLowerCase());
    }

    private Nodo buscar(Nodo n, String palabra) {
        if (n == null) return null;
        int cmp = palabra.compareTo(n.palabra);
        if (cmp == 0) return n;
        return (cmp < 0) ? buscar(n.izq, palabra) : buscar(n.der, palabra);
    }

    public List<Integer> buscarInterseccion(String w1, String w2) {
        Nodo n1 = buscar(w1);
        Nodo n2 = buscar(w2);

        int[] pw1 = (n1 != null && n1.p != null) ? n1.p : new int[0];
        int[] pw2 = (n2 != null && n2.p != null) ? n2.p : new int[0];

        List<Integer> out = new ArrayList<>();

        for (int i = 0; i < Math.min(pw1.length, pw2.length); i++) {
            if (pw1[i] > 0 && pw2[i] > 0) {
                out.add(i);
            }
        }
        return out;
    }

    public List<Integer> buscarUnion(String w1, String w2) {
        Nodo n1 = buscar(w1);
        Nodo n2 = buscar(w2);

        int[] pw1 = (n1 != null && n1.p != null) ? n1.p : new int[0];
        int[] pw2 = (n2 != null && n2.p != null) ? n2.p : new int[0];
        List<Integer> out = new ArrayList<>();

        for (int i = 1; i < Math.max(pw1.length, pw2.length); i++) { // suponiendo páginas empiezan en 1
            int f1 = (i < pw1.length) ? pw1[i] : 0;
            int f2 = (i < pw2.length) ? pw2[i] : 0;
            if (f1 > 0 || f2 > 0) {
                out.add(i);
            }
        }
        return out;
    }

}
// Benjamin Bravo y Juliana Defaur
package modelo;

import java.util.ArrayList;
import java.util.List;

public class AVL {
    private Nodo raiz;

    //Inserta una palabra en un nuevo nodo, que tambien tiene el numero de pagina
    public void insertar(String palabra, int pagina) {
        raiz = insertar(raiz, new Nodo(palabra.toLowerCase()), pagina);
    }

    private Nodo insertar(Nodo nodo, Nodo nuevo, int pagina) {
        if (nodo == null) {
            nuevo.aumentarFrecuencia(pagina);
            return nuevo;
        }

        int cmp = nuevo.palabra.compareTo(nodo.palabra);
        if (cmp < 0) {
            nodo.izq = insertar(nodo.izq, nuevo, pagina);
        } else if (cmp > 0) {
            nodo.der = insertar(nodo.der, nuevo, pagina);
        } else {
            nodo.aumentarFrecuencia(pagina);
        }

        nodo.altura = 1 + Math.max(altura(nodo.izq), altura(nodo.der));
        return balancear(nodo);
    }

    private int altura(Nodo n) {
        if (n == null) {
            return 0;
        }
        return n.altura;
    }

    private int calcularBalance(Nodo n) {
        if (n == null) {
            return 0;
        }
        return altura(n.izq) - altura(n.der);
    }
    //Hace una rotacion hacia la derecha en el AVL
    private Nodo rotarDerecha(Nodo y) {
        Nodo x = y.izq;
        Nodo T2 = x.der;
        x.der = y;
        y.izq = T2;

        y.altura = Math.max(altura(y.izq), altura(y.der)) + 1;
        x.altura = Math.max(altura(x.izq), altura(x.der)) + 1;

        return x;
    }
    //Hace una rotacion hacia la izquierda en el AVL
    private Nodo rotarIzquierda(Nodo x) {
        Nodo y = x.der;
        Nodo T2 = y.izq;
        y.izq = x;
        x.der = T2;

        x.altura = Math.max(altura(x.izq), altura(x.der)) + 1;
        y.altura = Math.max(altura(y.izq), altura(y.der)) + 1;
        return y;
    }

    //Calcula el balance para comprobar que rotaciones tiene que realizar el AVL
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

    //Imprime el indice, esto junto al metodo imprimirInOrden
    public void mostrarIndice() {
        char[] letraActual = {0}; // letra anterior
        imprimirInOrden(raiz, letraActual);
    }
    //Imprime el arbol en inOrder
    private void imprimirInOrden(Nodo nodo, char[] letraAnterior) {
        if (nodo == null) return;
        imprimirInOrden(nodo.izq, letraAnterior);

        char inicial = Character.toUpperCase(nodo.palabra.charAt(0));
        if (letraAnterior[0] != inicial) {
            letraAnterior[0] = inicial;
            System.out.println("\n  -" + inicial + "-");
        }
        System.out.println(formatearPalabraYPaginas(nodo.palabra, nodo.p));
        imprimirInOrden(nodo.der, letraAnterior);
    }

    private String formatearPalabraYPaginas(String palabra, int[] p) {
        StringBuilder sb = new StringBuilder();
        sb.append(ponerMayusculaInicial(palabra)).append(" ");
        List<String> paginas = new ArrayList<>();
        for (int i = 1; i < p.length; i++) {
            if (p[i] > 0) {
                if (p[i] == 1) paginas.add(String.valueOf(i));
                else paginas.add(i + "(" + p[i] + ")");
            }
        }
        sb.append(String.join(", ", paginas));
        return sb.toString();
    }
    //Cambia el primer caracter a mayuscula si esta en minuscula
    private String ponerMayusculaInicial(String palabra) {
        if (palabra == null || palabra.isEmpty()) return palabra;
        String[] partes = palabra.split("\\s+");
        StringBuilder resultado = new StringBuilder();
        for (String parte : partes) {
            resultado.append(Character.toUpperCase(parte.charAt(0)))
                    .append(parte.substring(1))
                    .append(" ");
        }
        return resultado.toString().trim();
    }

    //Busca el nodo por palabra
    public Nodo buscar(String palabra) {
        return buscar(raiz, palabra.toLowerCase());
    }

    private Nodo buscar(Nodo nodo, String palabra) {
        if (nodo == null) return null;
        int cmp = palabra.compareTo(nodo.palabra);
        if (cmp == 0) return nodo;
        return (cmp < 0) ? buscar(nodo.izq, palabra) : buscar(nodo.der, palabra);

    }

    //Busca las páginas en las que se encuentra la palabra ingresada
    public int[] obtenerPaginasDe(String palabra) {
        Nodo n = buscar(palabra);
        return (n != null) ? n.p : null;
    }

    //Busca si dos palabras intersectan en alguna pagina
    public List<Integer> buscarInterseccion(String w1, String w2) {
        int[] p1 = obtenerPaginasDe(w1);
        int[] p2 = obtenerPaginasDe(w2);

        int max = Math.min((p1 != null ? p1.length : 0), (p2 != null ? p2.length : 0));
        List<Integer> out = new ArrayList<>();
        for (int i = 1; i < max; i++) {
            if (p1[i] > 0 && p2[i] > 0) {
                out.add(i);
            }
        }
        //Retorna una lista con el numero de cada pagina en la que las palabras coinciden
        return out;
    }

    //Lista las paginas en las que aparece la palabra 1 y/o la palabra 2
    public List<Integer> buscarUnion(String w1, String w2) {
        int[] p1 = obtenerPaginasDe(w1);
        int[] p2 = obtenerPaginasDe(w2);

        int max = Math.max((p1 != null ? p1.length : 0), (p2 != null ? p2.length : 0));
        List<Integer> out = new ArrayList<>();
        for (int i = 1; i < max; i++) {
            boolean aparece = (p1 != null && i < p1.length && p1[i] > 0)
                    || (p2 != null && i < p2.length && p2[i] > 0);
            if (aparece) out.add(i);
        }
        //Retorna una lista con el numero de las paginas
        return out;
    }
}

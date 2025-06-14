package Vista;

import modelo.AVL;
import Persistencia.ProcesarDoc;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AVL arbol = new AVL();
        ProcesarDoc doc = new ProcesarDoc(arbol);
        doc.procesarDoc("src/Persistencia/Documento");

        System.out.println("::Índice::");
        arbol.imprimirIndice();

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\nOpciones de consulta:");
            System.out.println("1. Buscar páginas de palabra/frase");
            System.out.println("2. Buscar páginas donde aparecen ambas palabras/frases");
            System.out.println("3. Buscar páginas donde aparece al menos una palabra/frase");
            System.out.println("0. Salir");
            System.out.print("Opción: ");

            int op;
            try {
                op = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Opción inválida.");
                continue;
            }

            if (op == 0) break;

            switch (op) {
                case 1 -> {
                    System.out.print("Ingrese palabra/frase: ");
                    String w = sc.nextLine().trim().toLowerCase();
                    List<Integer> paginas = arbol.buscarPaginas(w);
                    if (paginas.isEmpty()) {
                        System.out.println("No se encontró la palabra/frase.");
                    } else {
                        System.out.println("Aparece en páginas: " + paginas);
                    }
                }
                case 2 -> {
                    System.out.print("Ingrese palabra/frase 1: ");
                    String w1 = sc.nextLine().trim().toLowerCase();
                    System.out.print("Ingrese palabra/frase 2: ");
                    String w2 = sc.nextLine().trim().toLowerCase();

                    List<Integer> paginas = arbol.buscarInterseccion(w1, w2);
                    if (paginas.isEmpty()) {
                        System.out.println("No hay páginas donde aparezcan ambas palabras/frases.");
                    } else {
                        System.out.println("Aparecen ambas palabras/frases en páginas: " + paginas);
                    }
                }
                case 3 -> {
                    System.out.print("Ingrese palabra/frase 1: ");
                    String w1 = sc.nextLine().trim().toLowerCase();
                    System.out.print("Ingrese palabra/frase 2: ");
                    String w2 = sc.nextLine().trim().toLowerCase();

                    List<Integer> paginas = arbol.buscarUnion(w1, w2);
                    if (paginas.isEmpty()) {
                        System.out.println("No hay páginas donde aparezca alguna de las palabras/frases.");
                    } else {
                        System.out.println("Aparece al menos una palabra/frase en páginas: " + paginas);
                    }
                }
                default -> System.out.println("Opción inválida.");
            }
        }
        sc.close();
        System.out.println("Programa finalizado.");
    }
}

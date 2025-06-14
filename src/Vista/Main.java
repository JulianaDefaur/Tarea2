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

        boolean ejecutando = true;

        while (ejecutando) {
            System.out.println("Opciones de consulta:");
            System.out.println("1. Buscar páginas de palabra o frase");
            System.out.println("2. Buscar páginas donde aparecen ambas palabras o frases");
            System.out.println("3. Buscar páginas donde aparece al menos una palabra o frase");
            System.out.println("4. Finalizar");
            System.out.print("Ingrese la opción que desea: ");
            String entrada = sc.next();

            // Aqui se etsa validando la opción que nos dé el ususario o tendrá una excepción
            int op = -1;
            boolean opcionValida = false;

            try {
                op = Integer.parseInt(entrada);
                opcionValida = true;
            } catch (NumberFormatException e) {
                System.out.println("ERROR!! Opción inválida");
            }

            if (opcionValida) {
                switch (op) {
                    case 1 -> {
                        System.out.print("Ingrese palabra o frase-> ");
                        String w = sc.nextLine().trim().toLowerCase();
                        List<Integer> paginas = arbol.buscarPaginas(w);
                        if (paginas.isEmpty()) {
                            System.out.println("No se encontró la palabra o frase");
                        } else {
                            System.out.println("La palabra o frase aparece en la/s página/s " + paginas);
                        }
                    }
                    case 2 -> {
                        System.out.print("Ingrese palabra o frase 1-> ");
                        String w1 = sc.nextLine().trim().toLowerCase();
                        System.out.print("Ingrese palabra o frase 2-> ");
                        String w2 = sc.nextLine().trim().toLowerCase();
                        List<Integer> paginas = arbol.buscarInterseccion(w1, w2);
                        if (paginas.isEmpty()) {
                            System.out.println("No hay páginas donde aparezcan ambas palabras o frases");
                        } else {
                            System.out.println("Aparecen ambas palabras o frases en páginas: " + paginas);
                        }
                    }
                    case 3 -> {
                        System.out.print("Ingrese palabra o frase 1-> ");
                        String w1 = sc.nextLine().trim().toLowerCase();
                        System.out.print("Ingrese palabra o frase 2-> ");
                        String w2 = sc.nextLine().trim().toLowerCase();
                        List<Integer> paginas = arbol.buscarUnion(w1, w2);
                        if (paginas.isEmpty()) {
                            System.out.println("No hay páginas donde aparezca alguna de las palabras o frases");
                        } else {
                            System.out.println("Aparece al menos una palabra o frase en páginas: " + paginas);
                        }
                    }
                    case 4 -> {
                        System.out.println("Finalizando...");
                        ejecutando = false;
                    }
                    default -> System.out.println("Opción inválida");
                }
            }
        }

    }
}

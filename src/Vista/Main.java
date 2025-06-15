// Benjamin Bravo y Juliana Defaur
package Vista;

import modelo.AVL;
import Persistencia.ProcesarDoc;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        AVL avl = new AVL();  // creamos un árbol AVL para guardar las palabras o frases
        ProcesarDoc procesador = new ProcesarDoc(avl); // se crea el procesador del documento y se le pasa el árbol para llenarlo
        procesador.procesarDoc("src/Persistencia/Documento"); // se procesa el archivo de entrada, extrayendo las palabras o frases

        System.out.println("::::::Índice::::::"); // se imprime el índice generado por el árbol
        avl.imprimirIndice(); // muestra el índice completo con encabezados por letra y páginas asociadas

        boolean continuarEjecutando = true;

        while (continuarEjecutando) {
            System.out.println("::::::::::::::::::::::::::::::::Menú::::::::::::::::::::::::::::::::");
            System.out.println("1) Buscar páginas de una palabra o frase");
            System.out.println("2) Buscar páginas donde aparecen ambas palabras o frases");
            System.out.println("3) Buscar páginas donde aparece al menos una palabra o frase");
            System.out.println("4) Finalizar");
            System.out.print("Ingrese la opción que desea: ");
            String opcionUsuario = scanner.nextLine();

            switch (opcionUsuario) {
                case "1" -> { // Buscar páginas de una palabra o frase
                    System.out.print("Ingrese palabra o frase: ");
                    String palabra = scanner.nextLine().trim().toLowerCase(); // estandarizar entrada para coincidencias

                    int[] paginasEncontradas = avl.obtenerPaginasDe(palabra); // búsqueda exacta

                    if (paginasEncontradas == null) {
                        System.out.println("No se encontró la palabra o frase");
                    } else {
                        System.out.print("La palabra o frase aparece en la/s página/s: ");
                        for (int i = 0; i < paginasEncontradas.length; i++) {
                            if (paginasEncontradas[i] > 0) {
                                System.out.print(i);
                                if (paginasEncontradas[i] > 1) System.out.print(" ("+paginasEncontradas[i]+" veces)");
                                if (i < paginasEncontradas.length-1) System.out.print(", ");
                            }
                        }
                        System.out.println();
                    }
                }

                case "2" -> { // Buscar páginas donde aparecen ambas palabras o frases para la intersección
                    System.out.print("Ingrese palabra o frase 1: ");
                    String palabra1 = scanner.nextLine().trim().toLowerCase();

                    System.out.print("Ingrese palabra o frase 2: ");
                    String palabra2 = scanner.nextLine().trim().toLowerCase();

                    if (palabra1.equals(palabra2)) {
                        System.out.println("Ingrese palabras distintas, por favor");
                        continue;
                    }

                    List<Integer> paginasAmbas = avl.buscarInterseccion(palabra1, palabra2); // páginas comunes

                    if (paginasAmbas.isEmpty()) {
                        System.out.println("No hay páginas donde aparezcan ambas palabras o frases");
                    } else {
                        System.out.print("Aparecen ambas palabras o frases en páginas: ");
                        for (Integer p : paginasAmbas) {
                            System.out.print(p+" ");
                        }
                        System.out.println();
                    }
                }

                case "3" -> { // Buscar páginas donde aparece al menos una palabra o frase para la unión
                    System.out.print("Ingrese palabra o frase 1: ");
                    String palabra1 = scanner.nextLine().trim().toLowerCase();

                    System.out.print("Ingrese palabra o frase 2: ");
                    String palabra2 = scanner.nextLine().trim().toLowerCase();

                    if (palabra1.equals(palabra2)) {
                        System.out.println("Ingrese palabras distintas, por favor");
                        continue;
                    }

                    List<Integer> paginasUnion = avl.buscarUnion(palabra1, palabra2); // combinación de ambas

                    if (paginasUnion.isEmpty()) {
                        System.out.println("No hay páginas donde aparezca alguna de las palabras o frases");
                    } else {
                        System.out.print("Aparece al menos una palabra o frase en páginas: ");
                        for (Integer p : paginasUnion) {
                            System.out.print(p+" ");
                        }
                        System.out.println();
                    }
                }

                case "4" -> { // finalizar
                    System.out.println("Finalizando...");
                    continuarEjecutando = false; //sale del ciclo
                }

                default -> System.out.println("Opción inválida!!");
            }
        }
    }
}

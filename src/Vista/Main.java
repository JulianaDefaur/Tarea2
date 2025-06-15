// Benjamin Bravo y Juliana Defaur
package Vista;

import modelo.AVL;
import Persistencia.ProcesarDoc;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        AVL arbolIndice = new AVL();  // creamos un árbol AVL para guardar las palabras o frases
        ProcesarDoc procesador = new ProcesarDoc(arbolIndice); // se crea el procesador del documento y se le pasa el árbol para llenarlo
        procesador.procesarDoc("src/Persistencia/Documento"); // se procesa el archivo de entrada, extrayendo las palabras o frases

        System.out.println("::::::Índice::::::"); // se imprime el índice generado por el árbol
        arbolIndice.imprimirIndice(); // muestra el índice completo con encabezados por letra y páginas asociadas

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

                    List<Integer> paginasEncontradas = arbolIndice.buscarPaginas(palabra); // búsqueda exacta

                    if (paginasEncontradas.isEmpty()) {
                        System.out.println("No se encontró la palabra o frase");
                    } else {
                        System.out.println("La palabra o frase aparece en la/s página/s: " + paginasEncontradas);
                    }
                }

                case "2" -> { // Buscar páginas donde aparecen ambas palabras o frases para la intersección
                    System.out.print("Ingrese palabra o frase 1: ");
                    String palabra1 = scanner.nextLine().trim().toLowerCase();

                    System.out.print("Ingrese palabra o frase 2: ");
                    String palabra2 = scanner.nextLine().trim().toLowerCase();

                    List<Integer> paginasAmbas = arbolIndice.buscarInterseccion(palabra1, palabra2); // páginas comunes

                    if (paginasAmbas.isEmpty()) {
                        System.out.println("No hay páginas donde aparezcan ambas palabras o frases");
                    } else {
                        System.out.println("Aparecen ambas palabras o frases en páginas: " + paginasAmbas);
                    }
                }

                case "3" -> { // Buscar páginas donde aparece al menos una palabra o frase para la unión
                    System.out.print("Ingrese palabra o frase 1: ");
                    String palabra1 = scanner.nextLine().trim().toLowerCase();

                    System.out.print("Ingrese palabra o frase 2: ");
                    String palabra2 = scanner.nextLine().trim().toLowerCase();

                    List<Integer> paginasUnion = arbolIndice.buscarUnion(palabra1, palabra2); // combinación de ambas

                    if (paginasUnion.isEmpty()) {
                        System.out.println("No hay páginas donde aparezca alguna de las palabras o frases");
                    } else {
                        System.out.println("Aparece al menos una palabra o frase en páginas: " + paginasUnion);
                    }
                }

                case "4" -> { // finalizar
                    System.out.println("Finalizando...");
                    continuarEjecutando = false; //sale del ciclo
                }

                default -> {
                    System.out.println("Opción inválida!!");
                }
            }
        }
    }
}

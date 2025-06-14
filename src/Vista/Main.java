
package Vista;

import modelo.AVL;
import Exception.Excepcion;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AVL arbol = new AVL();
        int pagina = 1;

        try (BufferedReader br = new BufferedReader(new FileReader("src/Persistencia/Documento"))) {
            String linea;
            boolean leyendo = false;
            StringBuilder palabra = new StringBuilder();

            while ((linea = br.readLine()) != null) {
                for (int i = 0; i < linea.length(); i++) {
                    char c = linea.charAt(i);
                    if (c == '|') {
                        pagina++;
                        continue;
                    }
                    if (c == '\\') {
                        if (leyendo) {
                            String p = palabra.toString().trim().toLowerCase();
                            if (!p.isEmpty() && p.length() <= 20) {
                                arbol.insertar(p, pagina);
                            }
                            palabra.setLength(0);
                        }
                        leyendo = !leyendo;
                    } else if (leyendo) {
                        palabra.append(c);
                    }
                }
            }
        } catch (IOException e) {
            throw new Excepcion("Error leyendo el archivo: " + e.getMessage());
        }

        System.out.println("Índice cargado.");
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

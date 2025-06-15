package Persistencia;

import java.io.BufferedReader;
import java.io.FileReader;

import modelo.*;

public class ProcesarDoc {
    private AVL arbol; // arbol donde se insertarán las palabras o frases

    public ProcesarDoc(AVL arbol) {
        this.arbol = arbol;
    }

    public void procesarDoc(String rutaArchivo) {
        int pagina = 1; // contador de las páginas

        try (BufferedReader doc = new BufferedReader(new FileReader(rutaArchivo))) {// el archivo se abrirá para leerlo línea a línea

            String linea;
            boolean leyendo = false; // indica si estamos leyendo entre los caracteres "\"
            String palabra = "";     // acumulador de la palabra o frase que leemos

            while ((linea = doc.readLine()) != null) {
                for (int i = 0; i < linea.length(); i++) {
                    char caracter = linea.charAt(i); // carácter actual de la línea

                    if (caracter == '|') { // si encuentra "|", aumenta el número de página
                        pagina++;
                    } else if (caracter == '\\') {
                        if (leyendo) { // si ya estábamos leyendo una palabra, ya que este "\" la cierra
                            String p = palabra.trim().toLowerCase(); // Limpia y pasa a minúsculas

                            if (!p.isEmpty() && p.length() <= 20) {
                                arbol.insertar(p, pagina); // inserta en el árbol con la página actual
                            }
                            palabra = ""; // limpia la palabra o frase para pasar a la siguiente palabra o frase
                        }
                        leyendo = !leyendo;

                    } else if (leyendo) {
                        palabra += caracter; // mientras se está leyendo, acumula los caracteres
                    }
                }
            }

        } catch (Exception e) { // si ocurre un error al leer el archivo lanza una excepcion
            System.err.println("Error!! al procesar el archivo: " + e.getMessage());
        }
    }
}



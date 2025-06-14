package Persistencia;

import java.io.BufferedReader;
import java.io.FileReader;
import modelo.*;

public class ProcesarDoc {
    private AVL arbol;

    public ProcesarDoc(AVL arbol) {
        this.arbol = arbol;
    }

    public void procesarDoc(String rutaArchivo) {
        int pagina = 1;

        try (BufferedReader doc = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            boolean leyendo = false;
            StringBuilder palabra = new StringBuilder();

            while ((linea = doc.readLine()) != null) {
                for (int i = 0; i < linea.length(); i++) {
                    char caracter = linea.charAt(i);

                    if (caracter == '|') {
                        pagina++;
                    } else if (caracter == '\\') {
                        if (leyendo) {
                            String p = palabra.toString().trim().toLowerCase();
                            if (!p.isEmpty() && p.length() <= 20) {
                                arbol.insertar(p, pagina);
                            }
                            palabra.setLength(0);
                        }
                        leyendo = !leyendo;
                    } else if (leyendo) {
                        palabra.append(caracter);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error!! al procesar el archivo: " + e.getMessage());
        }
    }
}

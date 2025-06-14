package Persistencia;
import java.io.File;
import java.util.Scanner;
import modelo.*;

public class ProcesarDoc {
    private AVL arbol;

    public ProcesarDoc(AVL arbol) {
        this.arbol = arbol;
    }

    public void procesarDoc(String rutaArchivo) {
        try (Scanner sc = new Scanner(new File(rutaArchivo))) {
            int pagina = 1;
            while (sc.hasNextLine()) {
                String linea = sc.nextLine();

                if (linea.equals("|")) {
                    pagina++;
                    continue;
                }

                int start = 0;
                while ((start = linea.indexOf("\\", start)) != -1) {
                    int end = linea.indexOf("\\", start + 1);
                    if (end == -1) break;
                    String frase = linea.substring(start + 1, end).toLowerCase();
                    arbol.insertar(frase, pagina);
                    start = end + 1;
                }
            }
        } catch (Exception e) {
            System.err.println("Error al procesar el archivo: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
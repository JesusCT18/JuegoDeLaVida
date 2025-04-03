package daw;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

public class Fichero {
    // Metodo para guardar la partida en un archivo
    public static void guardarPartida(Celula[][] celulas, String nombreFichero, int contadorGeneraciones, ArrayList<String> registroGeneraciones) {
        String tmp;

        // Dimensione de la matriz
        int n = celulas.length;  // Numero de filas
        int m = celulas[0].length; // Numero de columnas

        // Try para abrir el archivo para escribir
        try (BufferedWriter flujo = new BufferedWriter(new FileWriter(nombreFichero))){
            // Escribir la matriz actual
            flujo.write("Filas: " + n + ", Columnas: " + m);
            flujo.newLine();

            // Escribir la matriz de celulas en el archivo
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    tmp = (celulas[i][j]. isViva() ? "1" : "0"); // 1 para viva, 0 para muerta 
                    flujo.write(tmp + (j < m - 1 ? "," : ""));
                }
                flujo.newLine(); // Nueva linea despues de cada fila
            }

            // Obtener cantidad de celulas vivas en la generacion
            int celulasVivas = 0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if (celulas[i][j].isViva()) {
                        celulasVivas++;
                    }
                }
            } 

            // Escribir el numero de celulas vivas
            flujo.write("celulas vivas: " + celulasVivas);
            flujo.newLine(); // salto de linea

            // Escribir los numeros de las celulas vivas de las generaciones anteriores
            flujo.write("Generaciones anteriores: ");
            for (int i = registroGeneraciones.size() - 1; i >= 0; i--) {
                String registro = registroGeneraciones.get(i);
                // Busca el numero de vivas directamente
                int indiceVivas = registro.lastIndexOf(" "); // Encuentra el ultimo espacio
                if (indiceVivas != -1) {
                    String numVivas = registro.substring(indiceVivas + 1); // Extrae el numero
                    flujo.write(numVivas + (i > 0 ? ", " : ""));
                }
            }

            // Forzar el guardado
            flujo.flush();
            System.out.println("Fichero " + nombreFichero + " creado correctamente");

        } catch (Exception e) {
            System.out.println("Error al guardar el archivo");
        }
    }
}

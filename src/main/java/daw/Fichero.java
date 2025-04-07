package daw;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Fichero {

    // Metodo para guardar la partida en un archivo
    public static void guardarPartida(Celula[][] celulas, String nombreFichero, int contadorGeneraciones,
            ArrayList<String> registroGeneraciones) throws IOException {
        String tmp;

        // Dimensiones de la matriz
        int n = celulas.length; // Numero de filas
        int m = celulas[0].length; // Numero de columnas

        // Try para abrir el archivo para escribir
        try (BufferedWriter flujo = new BufferedWriter(new FileWriter(nombreFichero))) {
            // 1ª fila: tamaño de la matriz (solo un número para matriz cuadrada)
            flujo.write(String.valueOf(n));
            flujo.newLine();

            // 2ª fila: número de generación
            flujo.write(String.valueOf(contadorGeneraciones - 1)); // Restamos 1 porque contadorGeneraciones ya está incrementado para la siguiente
            flujo.newLine();

            // 3ª fila: matriz en el estado actual (con espacios entre valores)
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    flujo.write((celulas[i][j].isViva() ? "1" : "0") + " ");
                }
                flujo.newLine(); // Nueva línea después de cada fila de la matriz
            }
            // 4ª fila: número de células vivas en cada generación
            StringBuilder vivaPorGeneracion = new StringBuilder();
            for (String registro : registroGeneraciones) {
                // Extraer el número de células vivas de cada registro
                // Formato del registro: "Generacion X: Vivas: Y, Muertas: Z"
                int startIndex = registro.indexOf("Vivas: ") + 7;
                int endIndex = registro.indexOf(",", startIndex);
                if (startIndex > 0 && endIndex > startIndex) {
                    String vivasStr = registro.substring(startIndex, endIndex).trim();
                    vivaPorGeneracion.append(vivasStr).append(" ");
                }
            }
            flujo.write(vivaPorGeneracion.toString().trim());

            flujo.flush(); // Forzar el guardado
            System.out.println("Fichero " + nombreFichero + " creado correctamente");
        }
    }

    public static PartidaCargada cargarPartida(String nombreFichero) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(nombreFichero))) {
            // 1ª línea: tamaño de la matriz
            String tamanoStr = reader.readLine();
            if (tamanoStr == null) {
                System.out.println("Archivo vacío o formato incorrecto");
                return null;
            }

            int tamano = Integer.parseInt(tamanoStr.trim());

            // 2ª línea: número de generación
            String generacionStr = reader.readLine();
            if (generacionStr == null) {
                System.out.println("Formato incorrecto - falta número de generación");
                return null;
            }

            int generacionCargada = Integer.parseInt(generacionStr.trim());

            // 3ª línea en adelante: matriz
            Celula[][] celulasCargadas = new Celula[tamano][tamano];
            for (int i = 0; i < tamano; i++) {
                String lineaMatriz = reader.readLine();
                if (lineaMatriz == null) {
                    System.out.println("Formato incorrecto - matriz incompleta");
                    return null;
                }

                String[] valores = lineaMatriz.trim().split(" ");
                if (valores.length < tamano) {
                    System.out.println("Formato incorrecto - fila " + i + " incompleta");
                    return null;
                }

                for (int j = 0; j < tamano; j++) {
                    celulasCargadas[i][j] = new Celula(valores[j].equals("1"));
                }
            }

            // Última línea: células vivas por generación
            String vivasPorGeneracion = reader.readLine();
            ArrayList<String> registroCargado = new ArrayList<>();

            if (vivasPorGeneracion != null && !vivasPorGeneracion.trim().isEmpty()) {
                String[] vivasArray = vivasPorGeneracion.trim().split(" ");
                for (int i = 0; i < vivasArray.length; i++) {
                    int numVivas = Integer.parseInt(vivasArray[i]);
                    int numMuertas = tamano * tamano - numVivas;
                    registroCargado.add("Generacion " + (i + 1) + ": Vivas: " + numVivas + ", Muertas: " + numMuertas);
                }
            }

            return new PartidaCargada(celulasCargadas, generacionCargada, registroCargado);
        } catch (NumberFormatException nfe) {
            System.out.println("Formato incorrecto en el archivo");
            return null;
        } catch (IOException e) {
            System.out.println("Error al cargar el archivo: " + nombreFichero);
            throw e;
        }
    }

    // Clase para almacenar la partida cargada
    public record PartidaCargada(Celula[][] celulas, int generacion, ArrayList<String> registro) {

    }

}

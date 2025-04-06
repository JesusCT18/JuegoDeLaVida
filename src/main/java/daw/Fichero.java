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
            ArrayList<String> registroGeneraciones) {
        String tmp;

        // Dimensiones de la matriz
        int n = celulas.length; // Numero de filas
        int m = celulas[0].length; // Numero de columnas

        // Try para abrir el archivo para escribir
        try (BufferedWriter flujo = new BufferedWriter(new FileWriter(nombreFichero))) {
            // Escribir la matriz actual
            flujo.write("Filas: " + n + ", Columnas: " + m);
            flujo.newLine();

            // Escribir la matriz de celulas en el archivo
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    tmp = (celulas[i][j].isViva() ? "1" : "0"); // 1 para viva, 0 para muerta
                    flujo.write(tmp + (j < m - 1 ? "," : ""));
                }
                flujo.newLine(); // Nueva linea despues de cada fila
            }
            //Cambio para ajustar metodo con cargarPartida
            flujo.write("generacion: " + contadorGeneraciones);
            flujo.newLine();

            // Escribir el registro de generaciones anteriores
            flujo.write("registro: ");
            for (int i = 0; i < registroGeneraciones.size(); i++) {
                
                flujo.write(registroGeneraciones.get(i) + (i < registroGeneraciones.size() - 1 ? ";" : ""));
            }
            flujo.newLine(); // Nueva linea al final del registro
            flujo.flush(); // Forzar el guardado
            System.out.println("Fichero " + nombreFichero + " creado correctamente");
/*
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
        }*/
    }
    // Metodo para cargar la partida desde un archivo
    public static PartidaCargada cargarPartida(String nombreFichero) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(nombreFichero))) {
            String dimensiones = reader.readLine(); // Lee la primera linea con las dimensiones
            if (dimensiones == null || !dimensiones.startsWith("Filas: ") || !dimensiones.contains(" Columnas: ")) {
                System.out.println("Formato incorrecto");
                return null; // Retorna null si el formato es incorrecto
            }
            String[] partesDimensiones = dimensiones.substring("Filas: ".length()).split(", Columnas: ");
            int filas = Integer.parseInt(partesDimensiones[0].trim());
            int columnas = Integer.parseInt(partesDimensiones[1].trim());
            Celula[][] celulasCargadas = new Celula[filas][columnas]; // Crea la matriz de celulas

            // Leer el estado de las células
            for (int i = 0; i < filas; i++) {
                String lineaCelulas = reader.readLine(); // Lee la siguiente linea
                if (lineaCelulas == null) {
                    System.out.println("Error al leer el estado de las celulas");
                    return null; // Retorna null si hay un error
                }
                String[] estados = lineaCelulas.split(","); // Separa los estados por comas
                if (estados.length != columnas) {
                    System.out.println("Error en el formato de la matriz de celulas");
                    return null; // Retorna null si el formato es incorrecto
                }

                for (int j = 0; j < columnas; j++) {
                    celulasCargadas[i][j] = new Celula(estados[j].trim().equals("1")); // Crea la celula con el estado
                                                                                       // correspondiente

                }

            }

            // Leer el nº de generacion
            String lineaGeneracion = reader.readLine(); // Lee la siguiente linea
            int generacionCargada = 1; // Valor por defecto
            if (lineaGeneracion != null && lineaGeneracion.startsWith("generacion: ")) {
                generacionCargada = Integer.parseInt(lineaGeneracion.substring("generacion: ".length()).trim());
            } else {
                System.out.println(
                        "Advertencia: número de generación no encontrado, se usará el valor predeterminado (1).");
            }

            // Leer el registro de generaciones anteriores
            String lineaRegistro = reader.readLine(); // Lee la siguiente linea
            ArrayList<String> registroCargado = new ArrayList<>(); // Crea la lista para el registro
            if (lineaRegistro != null && lineaRegistro.startsWith("registro: ")) {
                String registros = lineaRegistro.substring("registro: ".length()).trim(); // Extrae el registro
                if (!registros.isEmpty()) {
                    registroCargado.addAll(Arrays.asList(registros.split(";")));
                }
            }

            return new PartidaCargada(celulasCargadas, generacionCargada, registroCargado); // Retorna la partida
                                                                                            // cargada

        } catch (NumberFormatException nfe) {
            System.out.println("Formato incorrecto en el archivo: ");
            return null;
        } catch (IOException e) {
            System.out.println("Error al cargar el archivo: " + nombreFichero);
            throw e; // Retorna null si hay un error
        }

    }

    // Clase para almacenar la partida cargada
    public record PartidaCargada(Celula[][] celulas, int generacion, ArrayList<String> registro) {
    }

}
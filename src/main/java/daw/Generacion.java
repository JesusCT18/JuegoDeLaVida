package daw;

import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;

public class Generacion {

    private Celula[][] celulas;
    private static ArrayList<String> registroGeneraciones = new ArrayList<>();
    private static int generacionCount = 1; // Contador para las generaciones

    // Constructor
    public Generacion(Celula[][] celulas) {
        this.celulas = celulas;
    }

    // Metodo para crear una matriz de tamaño n x n, con celulas vivas segun el porcentaje proporcionado
    public static Celula[][] creaMatriz(int n, int porcentajeVivas) {
        if (n > 25) {
            JOptionPane.showMessageDialog(null, "El tamano de la matriz no puede ser mayor a 25");
            return null;
        }
        if (porcentajeVivas < 0 || porcentajeVivas > 100) {
            JOptionPane.showMessageDialog(null, "El porcentaje debe estar entre 0 y 100");
            return null;
        }

        Random r = new Random();
        Celula[][] matriz = new Celula[n][n];
        int totalCelulas = n * n;
        int celulasVivas = (int) ((porcentajeVivas / 100.0) * totalCelulas);

        // Rellenar la matriz con las celulas vivas y muertas
        int contador = 0;
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                if (contador < celulasVivas) {
                    matriz[i][j] = new Celula(true);  // Celula viva
                    contador++;
                } else {
                    matriz[i][j] = new Celula(false); // Celula muerta
                }
            }
        }
        // Mezclar las celulas para que la distribución sea aleatoria
        for (int i = 0; i < totalCelulas; i++) {
            int x1 = r.nextInt(n);
            int y1 = r.nextInt(n);
            int x2 = r.nextInt(n);
            int y2 = r.nextInt(n);

            // Intercambiar celulas en (x1, y1) y (x2, y2)
            Celula temp = matriz[x1][y1];
            matriz[x1][y1] = matriz[x2][y2];
            matriz[x2][y2] = temp;
        }
        return matriz;
    }

    // Metodo para mostrar la matriz en consola
    public static void mostrarMatriz(Celula[][] matriz) {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.print(matriz[i][j].isViva() ? "1 " : "0 ");
            }
            System.out.println();
        }
    }

    // Metodo para contar las células vivas alrededor de una celula
    public static int contarCelulasVivas(Celula[][] celulas, int fila, int columna) {
        int contador = 0;
        for (int i = fila - 1; i <= fila + 1; i++) {
            for (int j = columna - 1; j <= columna + 1; j++) {
                if (i == fila && j == columna) continue;
                if (i >= 0 && i < celulas.length && j >= 0 && j < celulas[i].length) {
                    if (celulas[i][j].isViva()) contador++;
                }
            }
        }
        return contador;
    }

    // Metodo para crear una nueva generacion
    public Celula[][] nuevaGeneracion(Celula[][] celulas) {
        int n = celulas.length;
        Celula[][] nuevaGeneracion = new Celula[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int vivasAlrededor = contarCelulasVivas(celulas, i, j);

                if (celulas[i][j].isViva()) {
                    nuevaGeneracion[i][j] = new Celula(vivasAlrededor == 2 || vivasAlrededor == 3);
                } else {
                    nuevaGeneracion[i][j] = new Celula(vivasAlrededor == 3);
                }
            }
        }
        return nuevaGeneracion;
    }

    // Mostrar el registro de celulas vivas y muertas
    public static void registroCelulas(Celula[][] celulas) {
        int celulasVivas = 0;
        int celulasMuertas = 0;

        for (int i = 0; i < celulas.length; i++) {
            for (int j = 0; j < celulas[i].length; j++) {
                if (celulas[i][j].isViva()) {
                    celulasVivas++;
                } else {
                    celulasMuertas++;
                }
            }
        }
        JOptionPane.showMessageDialog(null,
                "Celulas vivas: " + celulasVivas + "\n" +
                "Celulas muertas: " + celulasMuertas);
        registroGeneraciones.add("Generacion " + generacionCount + ": Vivas: " + celulasVivas + ", Muertas: " + celulasMuertas);
        generacionCount++; // Incrementar el contador de generaciones
    }

    // Metodo para la colocacion manual de celulas
    public static Celula[][] colocacionManual(int n) {
        Celula[][] matriz = new Celula[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                String input = JOptionPane.showInputDialog("Ingrese el estado de la celula en [" + i + "][" + j + "] (1 para viva, 0 para muerta):");
                matriz[i][j] = new Celula(input.equals("1"));
            }
        }
        return matriz;
    }

    // Menu para avanzar generaciones
    public static void menuGeneraciones(Celula[][] celulas) {
        int opcion;
        try {
            do {
                String menu = """
                        MENU DE JUEGO - Avanzar Generacion y Mostrar Registros
                          1. Avanzar a la siguiente generacion
                          2. Mostrar registros de celulas vivas y muertas
                          3. Guardar partida (no implementado)
                          4. Salir
                        Elige una opcion:
                             """;

                opcion = Integer.parseInt(JOptionPane.showInputDialog(menu));

                switch (opcion) {
                    case 1 -> {
                        Generacion generacion = new Generacion(celulas);
                        celulas = generacion.nuevaGeneracion(celulas);
                        mostrarMatriz(celulas);
                        registroCelulas(celulas);
                    }
                    case 2 -> {
                        if (registroGeneraciones.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Aun no se ha avanzado ninguna generacion");
                        } else {
                            StringBuilder sb = new StringBuilder();
                            for (String registro : registroGeneraciones) {
                                sb.append(registro).append("\n");
                            }
                            JOptionPane.showMessageDialog(null, sb.toString());
                        }
                    }
                    case 3 -> JOptionPane.showMessageDialog(null, "Funcionalidad no implementada.");
                    case 4 -> JOptionPane.showMessageDialog(null, "Gracias! Hasta pronto! ");
                    default -> JOptionPane.showMessageDialog(null, "Opcion no valida.");
                }
            } while (opcion != 4);
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, "Dato no valido.");
        }
    }
}



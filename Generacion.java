
package JuegoDeLaVida;

import java.util.Random;

import javax.swing.JOptionPane;

/**
 * [][]celulas (matriz de posiciones)
 * crearGeneracionAleatoria()
 * crearGeneracion%()
 * mostrarMatriz()
 * mostrarRegistro
 * mostrarMenu
 * generacionesViva
 */
public class Generacion {

    private int[][] celulas;
    private int generacionesViva;
    private int fila;
    private int columna;

    public Generacion(int[][] celulas, int generacionesViva, int fila, int columna) {
        this.celulas = celulas;
        this.generacionesViva = generacionesViva;
        this.fila = fila;
        this.columna = columna;
    }

    public int[][] getCelulas() {
        return celulas;
    }

    public void setCelulas(int[][] celulas) {
        this.celulas = celulas;
    }

    public int getGeneracionesViva() {
        return generacionesViva;
    }

    public void setGeneracionesViva(int generacionesViva) {
        this.generacionesViva = generacionesViva;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Generacion{");
        sb.append("celulas=").append(celulas);
        sb.append(", generacionesViva=").append(generacionesViva);
        sb.append(", fila=").append(fila);
        sb.append(", columna=").append(columna);
        sb.append('}');
        return sb.toString();
    }

    /* INICIO DE METODOS */
    // Crea una matriz de n x n (máx 25) con valores aleatorios entre 0 y 1
    public static int[][] creaMatriz(int n) {
        if (n > 25) {
            JOptionPane.showMessageDialog(null, "El tamaño de la matriz no puede ser mayor a 25");
            return null;
        }
        Random r = new Random();
        int[][] matriz = new int[n][n];

        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                matriz[i][j] = r.nextInt(0, 2);// 0 o 1
            }
        }
        return matriz;
    }

    // Muestra la matriz
    public static void mostrarMatriz(int[][] matriz) {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.print(matriz[i][j] + " ");

            }
            System.out.println("");
        }
    }

    // Menu de opciones con JOptionPane
    public static void mostrarMenu() {
        int opcion;
        try {
            do {

                String menu = """
                        BIENVENIDO AL JUEGO DE LA VIDA
                          1. Cargar partida
                          2. Iniciar juego
                          3. Salir
                        Elige una opcion:
                             """;

                opcion = Integer.parseInt(JOptionPane.showInputDialog(menu));

                switch (opcion) {

                    case 1 ->
                        cargarPartida();

                    case 2 ->
                        iniciarJuego();

                    case 3 ->
                        JOptionPane.showMessageDialog(null, "Gracias! Hasta pronto! ");

                    default ->
                        JOptionPane.showMessageDialog(null, "Opción no válida");
                }

            } while (opcion != 3);
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, "Dato introducido no correcto, vuelva a intentarlo.");
        }

    }

    // Cargar partida (Leer archivo o volver al menu anterior)
    public static void cargarPartida() {
        String opcion = JOptionPane.showInputDialog("Opcion en mantenimiento, pronto disponible");
    }

    // Iniciar juego (solicitar colocacion manual, aleatoria o volver al menu
    // anterior)
    public static void iniciarJuego() {
        String opcion = "";
        int fila = 0;
        int columna = 0;
        String submenu = "";
        try {
            do {
                opcion = JOptionPane.showInputDialog("""
                        1. Colocacion manual
                        2. Colocacion aleatoria
                        3. Volver al menu anterior
                        """);

                switch (opcion) {
                    // Colocacion manual
                    case "1" -> {
                        int n = Integer
                                .parseInt(JOptionPane.showInputDialog("Ingrese el tamaño de la matriz(máx 25): "));
                        if (n > 25) {
                            JOptionPane.showMessageDialog(null, "El tamaño de la matriz no puede ser mayor a 25");
                            continue;
                        }
                        int[][] matrizManual = creaMatriz(n);
                        mostrarMatriz(matrizManual);
                        // Menu para colocacion manual, repite para nueva colocacion o volver al menu
                        // anterior
                        try {
                            do {
                                submenu = JOptionPane.showInputDialog("""
                                        1. Colocar una celula
                                        2. Volver al menu anterior
                                        """);
                                switch (submenu) {
                                    case "1" -> {
                                        // Solicitar fila y columna para colocar la celula
                                        fila = Integer.parseInt(JOptionPane
                                                .showInputDialog(
                                                        "Introduce la fila donde quieres colocar la celula: "));
                                        columna = Integer.parseInt(JOptionPane
                                                .showInputDialog(
                                                        "Introduce la columna donde quieres colocar la celula: "));
                                        if (fila < 0 || fila >= n || columna < 0 || columna >= n) {
                                            JOptionPane.showMessageDialog(null,
                                                    "Coordenadas fuera de rango. Intente nuevamente.");
                                        } else {
                                            matrizManual[fila][columna] = 1; // Coloca una celula viva
                                            JOptionPane.showMessageDialog(null,
                                                    "Celula colocada en la posicion: (" + fila + ", " + columna + ")");
                                        }

                                    }

                                    case "2" -> {
                                    }
                                }

                            } while (!submenu.equals("2"));
                        } catch (NumberFormatException nfe) {
                            JOptionPane.showMessageDialog(null, "Dato introducido no correcto, vuelva a intentarlo.");
                        }
                    }
                    // Colocacion aleatoria
                    case "2" -> {
                        // Solicitar porcentaje de celulas vivas
                        int porcentaje = Integer.parseInt(
                                JOptionPane.showInputDialog("Introduce el porcentaje de celulas vivas (0-100): "));
                        if (porcentaje < 0 || porcentaje > 100) {
                            JOptionPane.showMessageDialog(null, "Porcentaje fuera de rango. Intente nuevamente.");
                            continue;
                        }
                        // Crear matriz aleatoria
                        int n = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el tamaño de la matriz (máx 25): "));
                        // Calcular el número de celulas vivas
                        int numCelulasVivas = (n * n * porcentaje) / 100;
                        // Crear matriz
                        int[][] matrizAleatoria = new int[n][n];
                        Random r = new Random();
                        for (int i = 0; i < numCelulasVivas; i++) {
                            int filaAleatoria = r.nextInt(n);
                            int columnaAleatoria = r.nextInt(n);
                            // Verificar si la celula ya está viva
                            while (matrizAleatoria[filaAleatoria][columnaAleatoria] == 1) {
                                filaAleatoria = r.nextInt(n);
                                columnaAleatoria = r.nextInt(n);
                            }

                            mostrarMatriz(matrizAleatoria);
                        }
                    }
                    // Volver al menu anterior
                    case "3" -> mostrarMenu();

                    default -> JOptionPane.showMessageDialog(null, "Opción no válida");
                }
            } while (opcion != "3");
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, "Dato introducido no correcto, vuelva a intentarlo.");
        }
    }
}

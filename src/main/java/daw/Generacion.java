package daw;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.swing.JOptionPane;

public class Generacion {
    /*
     * INDICE
     * 1. Atributos
     * 2. Constructor
     * 3. Equals y HashCode
     * 4. Creacion de la matriz
     * 5. Mostrar matriz
     * 6. Contar celulas vivas
     * 7. Nueva generacion
     * 8. Registro de celulas
     * 9. Colocacion manual de celulas
     * 10. Menu de generaciones
     * 11. Comparar matrices
     */
    // 1. Atributos
    private Celula[][] celulas;
    private static ArrayList<String> registroGeneraciones = new ArrayList<>();
    private static int generacionCount = 1; // Contador para las generaciones
    private static Celula[][] generacionAnterior = null;
    private static ArrayList<Celula[][]> ultimasGeneraciones = new ArrayList<>();
    private static final int NUM_GENERACIONES_COMPARAR = 3;

    // 2. Constructor
    // Recibe una matriz de celulas. Representa el estado actual de la generación
    // del juego.
    public Generacion(Celula[][] celulas) {
        this.celulas = celulas;
    }

    // 3. Equals y HashCode
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.deepHashCode(celulas);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Generacion other = (Generacion) obj;
        if (!Arrays.deepEquals(celulas, other.celulas))
            return false;
        return true;
    }

    // 4. Creacion de la matriz
    // Este metodo 'creaMatriz' es llamado desde el punto de inicio del juego para
    // generar la primera generación de células.
    // La matriz retornada se utiliza como entrada para la creación de la primera
    // instancia de la clase 'Generacion'.
    public static Celula[][] creaMatriz(int n, int porcentajeVivas) {
        // control de tamaño y porcentaje
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
        // El contador se usa para controlar cuantas celulas vivas hay.
        int contador = 0;
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                if (contador < celulasVivas) {
                    matriz[i][j] = new Celula(true); // Celula viva
                    contador++;
                } else {
                    matriz[i][j] = new Celula(false); // Celula muerta
                }
            }
        }
        // Intercambios aleatorios para mezclar las celulas.
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

    // 5. Mostrar matriz
    // Este metodo 'mostrarMatriz' se utiliza para visualizar el estado de la matriz
    // en cada generación.
    // Recibe la matriz de células (ya sea creada inicialmente por 'creaMatriz' o
    // generada por 'nuevaGeneracion')
    public static void mostrarMatriz(Celula[][] matriz) {
        System.out.println("--------------------------------------------------------");
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.print(matriz[i][j].isViva() ? "1 " : "0 ");
            }
            System.out.println();
        }
        System.out.println("********************************************************");
    }

    // 6. Contar celulas vivas
    // Este metodo 'contarCelulasVivas' es llamado por el metodo 'nuevaGeneracion'
    // para determinar el estado de cada célula en la siguiente generación.
    // Recibe la matriz actual de células y las coordenadas de una célula
    // específica,
    // analiza sus vecinos y devuelve la cantidad de vecinos vivos.
    public static int contarCelulasVivas(Celula[][] celulas, int fila, int columna) {
        int contador = 0;
        for (int i = fila - 1; i <= fila + 1; i++) {
            for (int j = columna - 1; j <= columna + 1; j++) {
                if (i == fila && j == columna)
                    continue;
                if (i >= 0 && i < celulas.length && j >= 0 && j < celulas[i].length) {
                    if (celulas[i][j].isViva())
                        contador++;
                }
            }
        }
        return contador;
    }

    // 7. Nueva generacion
    // Este metodo 'nuevaGeneracion' es el corazón de la lógica del juego.
    // Recibe la matriz de la generación anterior (atributo 'celulas' de la
    // instancia actual de 'Generacion' o la matriz inicial).
    // Utiliza 'contarCelulasVivas' para analizar el vecindario de cada célula y
    // aplica las reglas para generar la siguiente configuración.
    // La matriz retornada por 'nuevaGeneracion' se convierte en la nueva matriz
    // 'celulas' para la siguiente iteración del juego.
    public Celula[][] nuevaGeneracion(Celula[][] celulas) {
        generacionAnterior = copiaMatriz(celulas); // Guardar la generación anterior
        int n = celulas.length;
        Celula[][] nuevaGeneracion = new Celula[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int vivasAlrededor = contarCelulasVivas(celulas, i, j);

                // Aplica las reglas del Juego de la Vida para determinar el estado de la célula
                // en la siguiente generación.
                // Si la celula esta viva y tiene 2 o 3 celulas vivas alrededor, permanece viva.
                // Si la celula esta muerta y tiene exactamente 3 celulas vivas alrededor,revive.
                // El algoritmo parte de si esta viva cuando el contador suma 2 o 3 sigue viva
                // Else "cuando parte de estar muerta" y cuando el contador suma 3, revive
                // Todas las demas circunstancias estan muertas
                if (celulas[i][j].isViva()) {
                    nuevaGeneracion[i][j] = new Celula(vivasAlrededor == 2 || vivasAlrededor == 3);
                } else {
                    nuevaGeneracion[i][j] = new Celula(vivasAlrededor == 3);
                }
            }
        }
        return nuevaGeneracion;
    }

    private static Celula[][] copiaMatriz(Celula[][] matriz) {
        int filas = matriz.length;
        int columnas = matriz[0].length;
        Celula[][] copia = new Celula[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                copia[i][j] = new Celula(matriz[i][j].isViva());
            }
        }
        return copia;
    }

    // 8. Registro de celulas
    // Muestra un mensaje al usuario a través de un cuadro de diálogo con la
    // cantidad de células vivas y muertas en la generación actual.
    public static void registroCelulas(Celula[][] celulas) {
        int celulasVivas = 0;
        int celulasMuertas = 0;
        // Itera a través de cada fila de la matriz.
        // Si la célula está viva, incrementa el contador de células vivas.
        // Si la célula está muerta, incrementa el contador de células muertas.
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
        registroGeneraciones
                .add("Generacion " + generacionCount + ": Vivas: " + celulasVivas + ", Muertas: " + celulasMuertas);
        generacionCount++; // Incrementar el contador de generaciones
    }

    // 9. Colocacion manual de celulas
    // Metodo para la colocacion manual de celulas
    // Este metodo 'colocacionManual' se llama al inicio del juego si el usuario
    // elige configurar la primera generación manualmente.
    // Recibe el tamaño de la matriz y luego interactúa con el usuario para
    // determinar el estado (viva o muerta) de cada célula.
    public static Celula[][] colocacionManual(int n) {
        Celula[][] matriz = new Celula[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                String input = JOptionPane.showInputDialog(
                        "Ingrese el estado de la celula en [" + i + "][" + j + "] (1 para viva, 0 para muerta):");
                if (input == null) { // Control del boton cancelar
                    return null;
                }
                if (!input.equals("0") && !input.equals("1")) {
                    JOptionPane.showMessageDialog(null, "Entrada no valida. Debe ser 0 o 1.");
                    j--; // Decrementar j para repetir la entrada
                    continue;
                }
                matriz[i][j] = new Celula(input.equals("1"));
            }
        }
        return matriz;
    }

    // 10. Menu de generaciones
    // Menu para avanzar generaciones y mostrar registros
    // Este menu aparece una vez creada una generación inicial para avanzar o
    // mostrar registros.
    public static void menuGeneraciones(Celula[][] celulas, int generacionActual,ArrayList<String> registroGeneraciones) throws IOException {
        int opcion;
        boolean juegoTerminado = false;
        try {
            do {
                String menu = """
                        MENU DE JUEGO - Avanzar Generacion y Mostrar Registros
                          1. Avanzar a la siguiente generacion
                          2. Mostrar registros de celulas vivas y muertas
                          3. Guardar partida
                          4. Salir
                        Elige una opcion:
                             """;
                String cancelarBtn = JOptionPane.showInputDialog(menu);

                if (cancelarBtn == null) { // Si el usuario pulsa Cancelar en el menú principal
                    return;
                }
                opcion = Integer.parseInt(cancelarBtn);

                switch (opcion) {
                    case 1 -> {
                        Celula[][] siguienteGeneracion = new Generacion(celulas).nuevaGeneracion(celulas);
                        mostrarMatriz(siguienteGeneracion);
                        registroCelulas(siguienteGeneracion);

                        ultimasGeneraciones.add(copiaMatriz(celulas));

                        if(ultimasGeneraciones.size() > NUM_GENERACIONES_COMPARAR) {
                            ultimasGeneraciones.remove(0); // Mantener solo las últimas 3 generaciones
                        }

                        if(ultimasGeneraciones.size() == NUM_GENERACIONES_COMPARAR) {
                            Celula[][]gen1 = ultimasGeneraciones.get(0);
                            Celula[][]gen2 = ultimasGeneraciones.get(1);
                            Celula[][]gen3 = ultimasGeneraciones.get(2);
                            if(compararMatrices(gen1, gen2) && compararMatrices(gen2, gen3)) {
                                JOptionPane.showMessageDialog(null, "Juego Terminado: Las ultimas 3 generaciones son iguales.");
                                juegoTerminado = true;
                                break;
                            } 
                        }

                        celulas = siguienteGeneracion; // Actualizar la matriz de células
                        generacionActual++;

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
                    case 3 -> {
                        // Pide nombre dle archivo
                        String nombreFichero = JOptionPane
                                .showInputDialog("Ingrese el nombre del archivo para guardar la partida: ");

                        if (nombreFichero != null && !nombreFichero.isEmpty()) {
                            // Llama al metodo guardarPartida de la clase fichero
                            Fichero.guardarPartida(celulas, nombreFichero, generacionCount, registroGeneraciones);
                        } else {
                            JOptionPane.showMessageDialog(null, "Gracias y Hasta pronto");
                        }
                    }
                    case 4 -> JOptionPane.showMessageDialog(null, "Gracias! Hasta pronto! ");
                    default -> JOptionPane.showMessageDialog(null, "Opcion no valida.");
                }
            } while (opcion != 4 && !juegoTerminado);
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, "Dato no valido.");
        }
    }

    //11. Comparar Matrices
    public static boolean compararMatrices(Celula[][]matriz1, Celula[][]matriz2){
        if(matriz1.length != matriz2.length){
            return false;
        }
        
        for (int i = 0; i < matriz1.length; i++) {
            if(matriz1[i].length != matriz2[i].length){
                return false;
            }
            
            for (int j = 0; j < matriz1[i].length; j++) {
                if(matriz1[i][j].isViva() != matriz2[i][j].isViva()){
                    return false;
                }
            }
        }
        return true;
    }
    
    
}

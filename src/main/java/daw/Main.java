package daw;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import daw.Fichero;

public class Main {

    private static Fichero.PartidaCargada partidaCargada = null; // Inicializa la variable partidaCargada

    public static void main(String[] args) {

        mostrarMenu();
    }

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
                    case 3 -> {
                        JOptionPane.showMessageDialog(null, "Gracias! Hasta pronto!");
                        break; // Esto hará que se salga del bucle al seleccionar la opción 3
                    }
                    default ->
                        JOptionPane.showMessageDialog(null, "Opcion no valida");
                }

            } while (opcion != 3); // Este bucle ahora terminará correctamente al elegir opción 3
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, "Dato introducido no correcto, vuelva a intentarlo.");
        }
    }

    // Cargar partida
    public static void cargarPartida() {
        String nombreFichero = JOptionPane.showInputDialog("Introduce el nombre del fichero a cargar: ");
        if (nombreFichero != null && !nombreFichero.isEmpty()) {
            try {
                partidaCargada = Fichero.cargarPartida(nombreFichero); //usamos variable de clase partidaCargada 
                if (partidaCargada != null) {
                    JOptionPane.showMessageDialog(null, "Partida cargada con éxito.");
                    Celula[][] tableroCargado = partidaCargada.celulas();
                    int generacionCargada = partidaCargada.generacion();
                    ArrayList<String> registroCargado = partidaCargada.registro();
                    //metodos para intentar controlar un registro por partida 
                    //en el codigo original se acumulaban las partidas seguidas.
                    Generacion.resetRegistro();
                    Generacion.setGeneracionCount(generacionCargada + 1);
                    Generacion.setRegistroGeneraciones(registroCargado);

                    //enseñar por pantalla el registro cargado, no solo la matriz.
                    Generacion.mostrarMatriz(tableroCargado);

                    StringBuilder sb = new StringBuilder("Registro de partida cargada:\n");
                    for (String registro : registroCargado) {
                        sb.append(registro).append("\n");
                    }
                    JOptionPane.showMessageDialog(null, sb.toString());
                    //continuar con el juego
                    Generacion.menuGeneraciones(tableroCargado, generacionCargada, registroCargado);

                } else {
                    JOptionPane.showMessageDialog(null, "Error al cargar la partida.");
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error al cargar el archivo: " + nombreFichero);
            } catch (NullPointerException npe) {
                JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún archivo.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se ha introducido ningún nombre de archivo.");
        }
    }

    // Iniciar juego
    public static void iniciarJuego() {
        Generacion.resetRegistro();//evita que sume generaciones de otras partidas 
        String opcion = "";
        boolean continuar = true;
        partidaCargada=null; //nos aseguramos que no haya partida cargada activa

        try {
            do {
                opcion = JOptionPane.showInputDialog("""
                        1. Colocacion manual
                        2. Colocacion aleatoria
                        3. Volver al menu anterior
                        """);

                switch (opcion) {
                    case "1" -> {
                        Generacion.resetRegistro();//controlamos que no se acumulen registros de otras partidas
                        int n = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el tamaño de la matriz (max 25): "));
                        if (n > 25) {
                            JOptionPane.showMessageDialog(null, "El tamaño de la matriz no puede ser mayor a 25");
                            break;
                        }
                        Celula[][] matrizManual = Generacion.colocacionManual(n);
                        if (matrizManual != null) {
                            Generacion.mostrarMatriz(matrizManual);
                            // Solo registrar una vez
                            Generacion.registroCelulas(matrizManual);
                            ArrayList<String> registro = Generacion.getRegistroGeneraciones();
                            try {
                                Generacion.menuGeneraciones(matrizManual, Generacion.getGeneracionCount(), registro);
                            } catch (IOException e) {
                                JOptionPane.showMessageDialog(null, "Error al guardar partida: " + e.getMessage());
                            }
                        }
                    }
                    case "2" -> {
                        Generacion.resetRegistro();//controlamos que no se acumulen registros de otras partidas
                        int n = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el tamaño de la matriz (max 25): "));
                        if (n > 25) {
                            JOptionPane.showMessageDialog(null, "El tamaño de la matriz no puede ser mayor a 25");
                            break;
                        }
                        int porcentaje = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el porcentaje de celulas vivas (0-100): "));
                        Celula[][] matrizAleatoria = Generacion.creaMatriz(n, porcentaje);
                        if (matrizAleatoria != null) {
                            Generacion.mostrarMatriz(matrizAleatoria);
                            // Solo registrar una vez
                            Generacion.registroCelulas(matrizAleatoria);
                            ArrayList<String> registro = Generacion.getRegistroGeneraciones();
                            try {
                                Generacion.menuGeneraciones(matrizAleatoria, Generacion.getGeneracionCount(), registro);
                            } catch (IOException e) {
                                JOptionPane.showMessageDialog(null, "Error al guardar partida: " + e.getMessage());
                            }
                        }
                    }
                    case "3" ->
                        continuar = false;
                    default ->
                        JOptionPane.showMessageDialog(null, "Opcion no valida");
                }
            } while (!opcion.equals("3"));
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, "Dato introducido no correcto, vuelva a intentarlo.");
        } catch (NullPointerException npe) {
        }
    }
}

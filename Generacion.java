
package JuegoDeLaVida;

import java.util.Arrays;

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

    public Generacion(int fila, int columna) {
        this.celulas = new int[fila][columna];
        this.generacionesViva = 0; // o 1?
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

    @Override
    public String toString() {
        return "Generacion [celulas=" + Arrays.toString(celulas)
                + ", generacionesViva=" + generacionesViva + "]";
    }
}

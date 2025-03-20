
package JuegoDeLaVida;

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

    
}

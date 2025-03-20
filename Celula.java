
package JuegoDeLaVida;

/**
CÉLULA (posicion)
fila
columna
boolean viva muerta
 */
public class Celula {
    private int fila;
    private int columna;
    private boolean viva;

    public Celula(int fila, int columna, boolean viva) {
        this.fila = fila;
        this.columna = columna;
        this.viva = viva;
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

    public boolean isViva() {
        return viva;
    }

    public void setViva(boolean viva) {
        this.viva = viva;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Celula{");
        sb.append("fila=").append(fila);
        sb.append(", columna=").append(columna);
        sb.append(", viva=").append(viva);
        sb.append('}');
        return sb.toString();
    }

}

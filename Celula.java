
package JuegoDeLaVida;

/**
CÉLULA (posicion)
boolean viva muerta
 */
public class Celula {

    private boolean viva;

    public boolean isViva() {
        return viva;
    }

    public void setViva(boolean viva) {
        this.viva = viva;
    }

    public Celula(boolean viva) {
        this.viva = viva;
    }

    @Override
    public String toString() {
        return "Celula [viva=" + viva + ", isViva()=" + isViva() + "]";
    }


    
    

}

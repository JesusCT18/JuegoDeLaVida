package daw;

// Clase Celula
public class Celula {
    private boolean viva; // Estado de la celula (viva o muerta)

    // Constructor
    public Celula(boolean viva) {
        this.viva = viva;
    }

    // Getter y setter
    public boolean isViva() {
        return viva;
    }

    public void setViva(boolean viva) {
        this.viva = viva;
    }
}

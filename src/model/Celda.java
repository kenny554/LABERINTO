package model;

public class Celda {
    private boolean transitable;

    public Celda(boolean transitable) {
        this.transitable = transitable;
    }

    public boolean isTransitable() {
        return transitable;
    }

    public void setTransitable(boolean transitable) {
        this.transitable = transitable;
    }
}

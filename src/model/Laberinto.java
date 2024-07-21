package model;

import utils.Pair;

public class Laberinto {
    private int[][] matriz;
    private Pair<Integer, Integer> puntoInicio;
    private Pair<Integer, Integer> puntoFin;
    private int filas; // Número de filas en el laberinto
    private int columnas; // Número de columnas en el laberinto

    public Laberinto(int[][] matriz, Pair<Integer, Integer> puntoInicio, Pair<Integer, Integer> puntoFin) {
        this.matriz = matriz;
        this.puntoInicio = puntoInicio;
        this.puntoFin = puntoFin;

    }

    public int[][] getMatriz() {
        return matriz;
    }

    public Pair<Integer, Integer> getPuntoInicio() {
        return puntoInicio;
    }

    public Pair<Integer, Integer> getPuntoFin() {
        return puntoFin;
    }

    public int getAncho() {
        return matriz[0].length;
    }

    public int getAlto() {
        return matriz.length;
    }

    public void setMatriz(int[][] matriz) {
        this.matriz = matriz;
    }

    public void setPuntoInicio(Pair<Integer, Integer> puntoInicio) {
        this.puntoInicio = puntoInicio;
    }

    public void setPuntoFin(Pair<Integer, Integer> puntoFin) {
        this.puntoFin = puntoFin;
    }

    public int getFilas() {
        return filas;
    }

    public void setFilas(int filas) {
        this.filas = filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public void setColumnas(int columnas) {
        this.columnas = columnas;
    }
}

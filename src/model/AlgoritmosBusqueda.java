package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import utils.Pair;

public class AlgoritmosBusqueda {
    private Laberinto laberinto;
    private List<List<Pair<Integer, Integer>>> soluciones;
    private Set<String> memo;

    // Constructor de la clase que inicializa el laberinto, la lista de soluciones y
    // el set de memoización
    public AlgoritmosBusqueda(Laberinto laberinto) {
        this.laberinto = laberinto;
        this.soluciones = new ArrayList<>();
        this.memo = new HashSet<>();
    }

    // Método principal para resolver el laberinto usando diferentes algoritmos
    public Resultados resolverLaberinto(String metodo, Pair<Integer, Integer> puntoInicio,
            Pair<Integer, Integer> puntoFin) {
        soluciones.clear();
        memo.clear();

        // Registrar el tiempo de inicio
        long startTime = System.currentTimeMillis();

        // Si no se especifican puntos de inicio y fin, buscar automáticamente
        if (puntoInicio == null || puntoFin == null) {
            puntoInicio = buscarPuntoInicio();
            puntoFin = buscarPuntoFin();
        }

        if (puntoInicio == null || puntoFin == null) {
            throw new IllegalArgumentException("No se pudo encontrar un punto de inicio o fin válido.");
        }

        // Selección del método de resolución
        switch (metodo) {
            case "RecursivoSimple":
                resolverRecursivoSimple(puntoInicio.getFirst(), puntoInicio.getSecond(),
                        puntoFin.getFirst(), puntoFin.getSecond(), new ArrayList<>());
                break;
            case "ProgramacionDinamica":
                resolverProgramacionDinamica(puntoInicio.getFirst(), puntoInicio.getSecond(),
                        puntoFin.getFirst(), puntoFin.getSecond());
                break;
            case "BFS":
                List<Pair<Integer, Integer>> caminoBFS = resolverBFS(puntoInicio.getFirst(),
                        puntoInicio.getSecond(), puntoFin.getFirst(), puntoFin.getSecond());
                if (!caminoBFS.isEmpty()) {
                    soluciones.add(caminoBFS);
                }
                break;
            case "DFS":
                resolverDFS(puntoInicio.getFirst(), puntoInicio.getSecond(), puntoFin.getFirst(),
                        puntoFin.getSecond(), new ArrayList<>());
                break;
            default:
                throw new IllegalArgumentException("Método de resolución no reconocido.");
        }

        // Registrar el tiempo de finalización
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        // Determinar el mejor camino encontrado
        List<Pair<Integer, Integer>> mejorCamino = soluciones.isEmpty() ? new ArrayList<>() : soluciones.get(0);
        int numPasos = mejorCamino.size() - 1;

        // Retornar los resultados
        return new Resultados(mejorCamino, numPasos, duration);
    }

    // Método para buscar el punto de inicio del laberinto
    private Pair<Integer, Integer> buscarPuntoInicio() {
        for (int i = 0; i < laberinto.getAlto(); i++) {
            for (int j = 0; j < laberinto.getAncho(); j++) {
                if (laberinto.getMatriz()[i][j] == 1) {
                    return new Pair<>(i, j);
                }
            }
        }
        return null;
    }

    // Método para buscar el punto de fin del laberinto
    private Pair<Integer, Integer> buscarPuntoFin() {
        for (int i = laberinto.getAlto() - 1; i >= 0; i--) {
            for (int j = laberinto.getAncho() - 1; j >= 0; j--) {
                if (laberinto.getMatriz()[i][j] == 1) {
                    return new Pair<>(i, j);
                }
            }
        }
        return null;
    }

    // Método recursivo simple para resolver el laberinto
    private void resolverRecursivoSimple(int x, int y, int finX, int finY,
            List<Pair<Integer, Integer>> caminoActual) {
        if (x == finX && y == finY) {
            caminoActual.add(new Pair<>(x, y));
            soluciones.add(new ArrayList<>(caminoActual));
            caminoActual.remove(caminoActual.size() - 1);
            return;
        }

        caminoActual.add(new Pair<>(x, y));
        memo.add(x + "," + y); // Usar un hash set para memoización

        int[][] movimientos = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

        for (int[] movimiento : movimientos) {
            int nuevoX = x + movimiento[0];
            int nuevoY = y + movimiento[1];
            if (esValido(nuevoX, nuevoY) && !memo.contains(nuevoX + "," + nuevoY)) {
                resolverRecursivoSimple(nuevoX, nuevoY, finX, finY, caminoActual);
            }
        }

        caminoActual.remove(caminoActual.size() - 1); // Backtracking
    }

    // Método de programación dinámica para resolver el laberinto
    private void resolverProgramacionDinamica(int inicioX, int inicioY, int finX, int finY) {
        int[][] distancias = new int[laberinto.getAlto()][laberinto.getAncho()];
        Pair<Integer, Integer>[][] previos = new Pair[laberinto.getAlto()][laberinto.getAncho()];

        for (int[] fila : distancias) {
            Arrays.fill(fila, Integer.MAX_VALUE);
        }
        distancias[inicioX][inicioY] = 0;

        Queue<Pair<Integer, Integer>> cola = new LinkedList<>();
        cola.offer(new Pair<>(inicioX, inicioY));

        int[][] movimientos = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

        while (!cola.isEmpty()) {
            Pair<Integer, Integer> actual = cola.poll();
            int x = actual.getFirst();
            int y = actual.getSecond();

            if (x == finX && y == finY) {
                break;
            }

            for (int[] movimiento : movimientos) {
                int nuevoX = x + movimiento[0];
                int nuevoY = y + movimiento[1];
                if (esValido(nuevoX, nuevoY) && distancias[x][y] + 1 < distancias[nuevoX][nuevoY]) {
                    distancias[nuevoX][nuevoY] = distancias[x][y] + 1;
                    previos[nuevoX][nuevoY] = new Pair<>(x, y);
                    cola.offer(new Pair<>(nuevoX, nuevoY));
                }
            }
        }

        List<Pair<Integer, Integer>> camino = new ArrayList<>();
        Pair<Integer, Integer> actual = new Pair<>(finX, finY);
        while (actual != null) {
            camino.add(actual);
            actual = previos[actual.getFirst()][actual.getSecond()];
        }
        Collections.reverse(camino);

        if (!camino.isEmpty()) {
            soluciones.add(camino);
        }
    }

    // Método BFS para resolver el laberinto
    private List<Pair<Integer, Integer>> resolverBFS(int inicioX, int inicioY, int finX, int finY) {
        Queue<List<Pair<Integer, Integer>>> queue = new LinkedList<>();
        List<Pair<Integer, Integer>> caminoInicial = new ArrayList<>();
        caminoInicial.add(new Pair<>(inicioX, inicioY));
        queue.offer(caminoInicial);
        memo.add(inicioX + "," + inicioY);

        int[][] movimientos = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

        while (!queue.isEmpty()) {
            List<Pair<Integer, Integer>> caminoActual = queue.poll();
            Pair<Integer, Integer> ultimaPosicion = caminoActual.get(caminoActual.size() - 1);
            int x = ultimaPosicion.getFirst();
            int y = ultimaPosicion.getSecond();

            if (x == finX && y == finY) {
                return caminoActual;
            }

            for (int[] movimiento : movimientos) {
                int nuevoX = x + movimiento[0];
                int nuevoY = y + movimiento[1];
                if (esValido(nuevoX, nuevoY) && !memo.contains(nuevoX + "," + nuevoY)) {
                    List<Pair<Integer, Integer>> nuevoCamino = new ArrayList<>(caminoActual);
                    nuevoCamino.add(new Pair<>(nuevoX, nuevoY));
                    queue.offer(nuevoCamino);
                    memo.add(nuevoX + "," + nuevoY);
                }
            }
        }

        return new ArrayList<>(); // Si no se encuentra solución, devolver un camino vacío
    }

    // Método DFS para resolver el laberinto
    private void resolverDFS(int inicioX, int inicioY, int finX, int finY,
            List<Pair<Integer, Integer>> caminoActual) {
        caminoActual.add(new Pair<>(inicioX, inicioY));
        memo.add(inicioX + "," + inicioY);

        if (inicioX == finX && inicioY == finY) {
            soluciones.add(new ArrayList<>(caminoActual));
            caminoActual.remove(caminoActual.size() - 1);
            return;
        }

        int[][] movimientos = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
        for (int[] movimiento : movimientos) {
            int nuevoX = inicioX + movimiento[0];
            int nuevoY = inicioY + movimiento[1];
            if (esValido(nuevoX, nuevoY) && !memo.contains(nuevoX + "," + nuevoY)) {
                resolverDFS(nuevoX, nuevoY, finX, finY, caminoActual);
            }
        }

        caminoActual.remove(caminoActual.size() - 1); // Backtracking
    }

    // Método para verificar si una posición es válida dentro del laberinto
    private boolean esValido(int x, int y) {
        return x >= 0 && x < laberinto.getAlto() && y >= 0 && y < laberinto.getAncho()
                && laberinto.getMatriz()[x][y] == 1;
    }

    // Clase para almacenar los resultados de la búsqueda
    public static class Resultados {
        private List<Pair<Integer, Integer>> camino;
        private int numeroPasos;
        private long tiempoEjecucion;

        public Resultados(List<Pair<Integer, Integer>> camino, int numeroPasos, long tiempoEjecucion) {
            this.camino = camino;
            this.numeroPasos = numeroPasos;
            this.tiempoEjecucion = tiempoEjecucion;
        }

        public List<Pair<Integer, Integer>> getCamino() {
            return camino;
        }

        public int getNumeroPasos() {
            return numeroPasos;
        }

        public long getTiempoEjecucion() {
            return tiempoEjecucion;
        }
    }
}

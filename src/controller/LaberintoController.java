package controller;

import java.util.List;

import javax.swing.JOptionPane;

import model.AlgoritmosBusqueda;
import model.Laberinto;
import utils.Pair;

public class LaberintoController {
    private Laberinto laberinto;
    private AlgoritmosBusqueda algoritmoBusqueda;

    public LaberintoController(Laberinto laberinto) {
        this.laberinto = laberinto;
        this.algoritmoBusqueda = new AlgoritmosBusqueda(laberinto);
    }

    public void setLaberinto(Laberinto laberinto) {
        this.laberinto = laberinto;
        this.algoritmoBusqueda = new AlgoritmosBusqueda(laberinto);
    }

    /**
     * Resuelve el laberinto usando el método especificado.
     *
     * @param metodo      El método de búsqueda a usar (por ejemplo,
     *                    "RecursivoSimple", "ProgramacionDinamica", "BFS", "DFS").
     * @param puntoInicio El punto de inicio para la búsqueda (puede ser null para
     *                    buscar automáticamente).
     * @param puntoFin    El punto final para la búsqueda (puede ser null para
     *                    buscar automáticamente).
     * @return Una lista de caminos encontrados.
     */
    public List<List<Pair<Integer, Integer>>> resolverLaberinto(String metodo, Pair<Integer, Integer> puntoInicio,
            Pair<Integer, Integer> puntoFin) {
        if (puntoInicio == null || puntoFin == null) {
            // Buscar automáticamente los puntos de inicio y fin si no se proporcionan
            puntoInicio = buscarPuntoInicioAutomaticamente();
            puntoFin = buscarPuntoFinAutomaticamente();

            // Verificar si se encontraron puntos válidos
            if (puntoInicio == null || puntoFin == null) {
                throw new IllegalArgumentException(
                        "No se pudo encontrar un punto de inicio o fin válido en el laberinto.");
            }
        }

        // Obtener los resultados del algoritmo de búsqueda
        AlgoritmosBusqueda.Resultados resultados = algoritmoBusqueda.resolverLaberinto(metodo, puntoInicio, puntoFin);

        // Verificar si se encontró algún camino
        if (resultados.getCamino() == null || resultados.getCamino().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "ESTAS ATRAPADO.",
                    "Sin Solución", JOptionPane.INFORMATION_MESSAGE);
        }

        // Devolver la lista de caminos encontrados
        return List.of(resultados.getCamino()); // Convertir el camino en una lista de una sola lista de caminos
    }

    private Pair<Integer, Integer> buscarPuntoInicioAutomaticamente() {
        // Implementa aquí la lógica para encontrar un punto de inicio automáticamente
        // Ejemplo: Buscar la primera celda libre en el laberinto
        for (int i = 0; i < laberinto.getAlto(); i++) {
            for (int j = 0; j < laberinto.getAncho(); j++) {
                if (laberinto.getMatriz()[i][j] == 1) {
                    return new Pair<>(i, j);
                }
            }
        }
        return null;
    }

    private Pair<Integer, Integer> buscarPuntoFinAutomaticamente() {
        // Implementa aquí la lógica para encontrar un punto final automáticamente
        // Ejemplo: Buscar la última celda libre en el laberinto
        for (int i = laberinto.getAlto() - 1; i >= 0; i--) {
            for (int j = laberinto.getAncho() - 1; j >= 0; j--) {
                if (laberinto.getMatriz()[i][j] == 1) {
                    return new Pair<>(i, j);
                }
            }
        }
        return null;
    }
}

package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;

import controller.LaberintoController;
import model.Laberinto;
import utils.Pair;

public class LaberintoUI extends JFrame {
    private LaberintoController controller;
    private JTextField filaInicio, columnaInicio, filaFin, columnaFin;
    private JTextField filas, columnas;
    private JButton generarLaberintoButton, resolverButton, reiniciarButton, reiniciarLaberintoButton;
    private JComboBox<String> metodoComboBox;
    private JPanel laberintoPanel;
    private JLabel[][] laberintoLabels;
    private JTextArea recorridoArea;

    private JLabel personajeLabel; // Representación del personaje
    private Pair<Integer, Integer> personajePosicion; // Posición actual del personaje
    private Timer movimientoTimer; // Timer para animar el movimiento del personaje
    private ImageIcon personajeIcon; // Icono del personaje

    public LaberintoUI(LaberintoController controller) {
        this.controller = controller;
        personajeIcon = new ImageIcon(getClass().getResource("/img/pato.gif")); // Cargar la imagen del personaje

        initUI();
    }

    private void initUI() {
        setTitle("Laberinto Solver");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Configuración del panel de configuración
        JPanel configPanel = new JPanel();
        configPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        configPanel.add(new JLabel("Filas:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        filas = new JTextField("5", 5);
        configPanel.add(filas, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        configPanel.add(new JLabel("Columnas:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        columnas = new JTextField("5", 5);
        configPanel.add(columnas, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        generarLaberintoButton = new JButton("Generar Laberinto");
        configPanel.add(generarLaberintoButton, gbc);
        generarLaberintoButton.addActionListener(new GenerarLaberintoListener());

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        configPanel.add(new JLabel("Fila Inicio:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        filaInicio = new JTextField("", 5);
        configPanel.add(filaInicio, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        configPanel.add(new JLabel("Columna Inicio:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        columnaInicio = new JTextField("", 5);
        configPanel.add(columnaInicio, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        configPanel.add(new JLabel("Fila Fin:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        filaFin = new JTextField("", 5);
        configPanel.add(filaFin, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        configPanel.add(new JLabel("Columna Fin:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        columnaFin = new JTextField("", 5);
        configPanel.add(columnaFin, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        resolverButton = new JButton("Resolver Laberinto");
        configPanel.add(resolverButton, gbc);
        resolverButton.addActionListener(new SolveButtonListener());

        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        reiniciarButton = new JButton("Reiniciar Todo");
        configPanel.add(reiniciarButton, gbc);
        reiniciarButton.addActionListener(new ReiniciarButtonListener());

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        reiniciarLaberintoButton = new JButton("Reiniciar Laberinto");
        configPanel.add(reiniciarLaberintoButton, gbc);
        reiniciarLaberintoButton.addActionListener(new ReiniciarLaberintoButtonListener());

        add(configPanel, BorderLayout.WEST);

        // Configuración del panel del laberinto
        laberintoPanel = new JPanel();
        add(laberintoPanel, BorderLayout.CENTER);

        // Configuración del área de texto de recorrido
        recorridoArea = new JTextArea();
        recorridoArea.setEditable(false);
        JScrollPane recorridoScrollPane = new JScrollPane(recorridoArea);
        recorridoScrollPane.setBorder(BorderFactory.createTitledBorder("Recorrido"));
        add(recorridoScrollPane, BorderLayout.SOUTH);

        // Configuración del JComboBox para seleccionar el método
        metodoComboBox = new JComboBox<>(new String[] { "RecursivoSimple", "ProgramacionDinamica", "BFS", "DFS" });
        JPanel metodoPanel = new JPanel();
        metodoPanel.add(new JLabel("Método:"));
        metodoPanel.add(metodoComboBox);
        add(metodoPanel, BorderLayout.NORTH);
    }

    private class GenerarLaberintoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int numFilas = Integer.parseInt(filas.getText());
                int numColumnas = Integer.parseInt(columnas.getText());
                laberintoPanel.removeAll();
                laberintoPanel.setLayout(new GridLayout(numFilas, numColumnas));
                laberintoLabels = new JLabel[numFilas][numColumnas];

                for (int i = 0; i < numFilas; i++) {
                    for (int j = 0; j < numColumnas; j++) {
                        JLabel label = new JLabel();
                        label.setOpaque(true);
                        label.setBackground(Color.WHITE);
                        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        label.addMouseListener(new LaberintoMouseListener(i, j));
                        laberintoLabels[i][j] = label;
                        laberintoPanel.add(label);
                    }
                }
                laberintoPanel.revalidate();
                laberintoPanel.repaint();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(LaberintoUI.this, "Error en el formato de filas o columnas", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class LaberintoMouseListener extends MouseAdapter {
        private int fila;
        private int columna;

        public LaberintoMouseListener(int fila, int columna) {
            this.fila = fila;
            this.columna = columna;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            JLabel label = laberintoLabels[fila][columna];
            Color currentColor = label.getBackground();

            if (currentColor.equals(Color.WHITE)) {
                // Marcar como obstáculo
                label.setBackground(Color.BLACK);
            } else if (currentColor.equals(Color.BLACK)) {
                // Marcar como punto de inicio
                label.setBackground(Color.BLUE);
            } else if (currentColor.equals(Color.BLUE)) {
                // Marcar como punto final
                label.setBackground(Color.GREEN);
            } else if (currentColor.equals(Color.GREEN)) {
                // Limpiar el color (devolver a blanco)
                label.setBackground(Color.WHITE);
            }
        }
    }

    private class SolveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int numFilas = laberintoLabels.length;
                int numColumnas = laberintoLabels[0].length;
                int[][] matriz = new int[numFilas][numColumnas];
                for (int i = 0; i < numFilas; i++) {
                    for (int j = 0; j < numColumnas; j++) {
                        matriz[i][j] = laberintoLabels[i][j].getBackground() == Color.WHITE ? 1 : 0;
                    }
                }

                Pair<Integer, Integer> inicio = filaInicio.getText().isEmpty() || columnaInicio.getText().isEmpty()
                        ? null
                        : new Pair<>(Integer.parseInt(filaInicio.getText()), Integer.parseInt(columnaInicio.getText()));
                Pair<Integer, Integer> fin = filaFin.getText().isEmpty() || columnaFin.getText().isEmpty()
                        ? null
                        : new Pair<>(Integer.parseInt(filaFin.getText()), Integer.parseInt(columnaFin.getText()));

                Laberinto laberinto = new Laberinto(matriz, inicio, fin);
                controller.setLaberinto(laberinto);
                String metodo = (String) metodoComboBox.getSelectedItem();

                long startTime = System.currentTimeMillis();
                List<List<Pair<Integer, Integer>>> soluciones = controller.resolverLaberinto(metodo, inicio, fin);
                long tiempoEjecucion = System.currentTimeMillis() - startTime;

                if (soluciones == null || soluciones.isEmpty()) {
                    JOptionPane.showMessageDialog(LaberintoUI.this,
                            "No se encontró ningún camino desde el punto de inicio al punto de fin.", "Sin Solución",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    displaySoluciones(soluciones, metodo, tiempoEjecucion);
                    moverPersonajePorCamino(soluciones.get(0)); // Mover el personaje a través del primer camino
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(LaberintoUI.this, "Error en el formato de entrada: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(LaberintoUI.this, "Error al resolver el laberinto: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class ReiniciarButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            laberintoPanel.removeAll();
            laberintoLabels = null;
            recorridoArea.setText("");
            filas.setText("");
            columnas.setText("");
            filaInicio.setText("");
            columnaInicio.setText("");
            filaFin.setText("");
            columnaFin.setText("");
            laberintoPanel.revalidate();
            laberintoPanel.repaint();
        }
    }

    private class ReiniciarLaberintoButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (laberintoLabels != null) {
                // Limpiar sólo las celdas que son parte del recorrido, dejando las posiciones
                // inicial y final
                for (int i = 0; i < laberintoLabels.length; i++) {
                    for (int j = 0; j < laberintoLabels[i].length; j++) {
                        Color color = laberintoLabels[i][j].getBackground();
                        if (color == Color.RED || color == Color.GREEN) {
                            laberintoLabels[i][j].setBackground(Color.WHITE);
                        }
                    }
                }
                recorridoArea.setText(""); // Limpiar el área de texto del recorrido
            }
        }
    }

    private void displaySoluciones(List<List<Pair<Integer, Integer>>> soluciones, String metodo, long tiempoEjecucion) {
        recorridoArea.setText(""); // Limpiar el área de texto de recorrido
        recorridoArea.append("Tiempo de ejecución: " + tiempoEjecucion + " ms\n\n");

        // Marcar todas las celdas blancas como rojas antes de marcar el camino
        for (int i = 0; i < laberintoLabels.length; i++) {
            for (int j = 0; j < laberintoLabels[i].length; j++) {
                if (laberintoLabels[i][j].getBackground() == Color.WHITE) {
                    laberintoLabels[i][j].setBackground(Color.RED);
                }
            }
        }

        for (List<Pair<Integer, Integer>> solucion : soluciones) {
            recorridoArea.append("Recorrido realizado por el método: " + metodo + "\n");
            for (Pair<Integer, Integer> posicion : solucion) {
                laberintoLabels[posicion.getFirst()][posicion.getSecond()].setBackground(Color.GREEN);
                recorridoArea.append("(" + posicion.getFirst() + ", " + posicion.getSecond() + ") ");
            }
            recorridoArea.append("\n\n");
        }
    }

    private void moverPersonajePorCamino(List<Pair<Integer, Integer>> camino) {
        if (camino == null || camino.isEmpty()) {
            return;
        }

        // Inicializar el personaje en la primera posición del camino
        personajePosicion = camino.get(0);
        actualizarPosicionPersonaje();

        // Configurar el timer para mover el personaje a lo largo del camino
        movimientoTimer = new Timer(500, new ActionListener() {
            private int paso = 1;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (paso < camino.size()) {
                    personajePosicion = camino.get(paso);
                    actualizarPosicionPersonaje();
                    paso++;
                } else {
                    movimientoTimer.stop();
                }
            }
        });
        movimientoTimer.start();
    }

    private void actualizarPosicionPersonaje() {
        if (personajePosicion == null) {
            return;
        }

        // Limpiar la posición anterior del personaje
        for (int i = 0; i < laberintoLabels.length; i++) {
            for (int j = 0; j < laberintoLabels[i].length; j++) {
                if (laberintoLabels[i][j].getIcon() == personajeIcon) {
                    laberintoLabels[i][j].setIcon(null);
                }
            }
        }

        // Actualizar la nueva posición del personaje
        laberintoLabels[personajePosicion.getFirst()][personajePosicion.getSecond()].setIcon(personajeIcon);
    }

    public static void main(String[] args) {
        LaberintoController controller = new LaberintoController(null);
        LaberintoUI ui = new LaberintoUI(controller);
        ui.setVisible(true);
    }
}

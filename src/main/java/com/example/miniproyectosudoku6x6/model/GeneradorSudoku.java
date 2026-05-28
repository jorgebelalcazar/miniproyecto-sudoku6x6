package com.example.miniproyectosudoku6x6.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Generates random valid 6x6 Sudoku boards.
 * <p>
 * The generation algorithm is based on classic backtracking: the generator
 * iterates over every cell of an empty board and tries to place numbers
 * from 1 to 6 in a randomized order. If a number breaks any Sudoku rule
 * at the current position, the generator backtracks to the previous cell
 * and tries a different value. Once every cell holds a valid number, the
 * board is fully solved.
 * </p>
 *
 * <p>After producing a solved board, the generator selects exactly two
 * random cells per 2x3 block as the initial clues that the player will
 * see when the game starts. Those cells are marked as fixed (non-editable)
 * and the remaining cells are cleared so the player can fill them in.</p>
 *
 * <p>Because the order in which numbers are tried is randomized, every
 * call to {@link #generarNuevoTablero()} produces a different starting
 * configuration, satisfying user story HU-1.</p>
 *
 * <p>This class belongs to the model layer and remains completely
 * independent of any UI framework.</p>
 */
public class GeneradorSudoku {

    /** Number of fixed clues placed per 2x3 block. */
    public static final int PISTAS_POR_BLOQUE = 2;

    /** Random number generator used throughout the generation process. */
    private final Random aleatorio;

    /**
     * Constructs a new generator with a fresh random source.
     */
    public GeneradorSudoku() {
        this.aleatorio = new Random();
    }

    /**
     * Generates a brand-new Sudoku board ready to be played.
     * <p>
     * The returned board has exactly 2 fixed clues per 2x3 block (12 in
     * total), and the remaining cells are empty and editable by the player.
     * </p>
     *
     * @return a new playable Sudoku board
     */
    public TableroSudoku generarNuevoTablero() {
        TableroSudoku tablero = new TableroSudoku();
        llenarTableroRecursivo(tablero, 0, 0);
        seleccionarPistasIniciales(tablero);
        return tablero;
    }

    /**
     * Generates a fully solved Sudoku board with no empty cells and no
     * fixed clues. This is useful for hint generation in later phases.
     *
     * @return a new fully solved Sudoku board
     */
    public TableroSudoku generarTableroResuelto() {
        TableroSudoku tablero = new TableroSudoku();
        llenarTableroRecursivo(tablero, 0, 0);
        return tablero;
    }

    /**
     * Recursive backtracking method that fills the board with a valid
     * configuration. Numbers from 1 to 6 are tried in a random order at
     * each cell to guarantee that successive calls produce different
     * boards.
     *
     * @param tablero the board being filled
     * @param fila    the row of the current cell being processed
     * @param columna the column of the current cell being processed
     * @return {@code true} if the board could be filled from this point
     *         onwards, {@code false} otherwise (which triggers backtracking)
     */
    private boolean llenarTableroRecursivo(TableroSudoku tablero, int fila, int columna) {
        if (fila == TableroSudoku.TOTAL_FILAS) {
            return true;
        }

        int siguienteFila = (columna == TableroSudoku.TOTAL_COLUMNAS - 1)
                ? fila + 1 : fila;
        int siguienteColumna = (columna == TableroSudoku.TOTAL_COLUMNAS - 1)
                ? 0 : columna + 1;

        List<Integer> ordenNumeros = generarOrdenAleatorio();
        ValidadorSudoku validador = new ValidadorSudoku(tablero);
        Celda celdaActual = tablero.obtenerCelda(fila, columna);

        for (int valor : ordenNumeros) {
            if (validador.esMovimientoValido(fila, columna, valor)) {
                celdaActual.establecerValor(valor);
                if (llenarTableroRecursivo(tablero, siguienteFila, siguienteColumna)) {
                    return true;
                }
                celdaActual.limpiar();
            }
        }
        return false;
    }

    /**
     * Selects exactly {@link #PISTAS_POR_BLOQUE} random cells per 2x3 block
     * and marks them as fixed clues. All remaining cells are cleared so
     * that the player can fill them in.
     *
     * @param tablero the board to prepare for play
     */
    private void seleccionarPistasIniciales(TableroSudoku tablero) {
        // Save current values before clearing, then restore the chosen clues
        int[][] valoresOriginales = new int[TableroSudoku.TOTAL_FILAS][TableroSudoku.TOTAL_COLUMNAS];
        for (int fila = 0; fila < TableroSudoku.TOTAL_FILAS; fila++) {
            for (int columna = 0; columna < TableroSudoku.TOTAL_COLUMNAS; columna++) {
                valoresOriginales[fila][columna] =
                        tablero.obtenerCelda(fila, columna).obtenerValor();
            }
        }

        // Clear every cell
        for (int fila = 0; fila < TableroSudoku.TOTAL_FILAS; fila++) {
            for (int columna = 0; columna < TableroSudoku.TOTAL_COLUMNAS; columna++) {
                tablero.obtenerCelda(fila, columna).limpiar();
            }
        }

        // For each block, randomly pick PISTAS_POR_BLOQUE cells as fixed clues
        for (int indiceBloque = 0; indiceBloque < TableroSudoku.TOTAL_BLOQUES; indiceBloque++) {
            List<Celda> celdasBloque = new ArrayList<>(tablero.obtenerBloque(indiceBloque));
            Collections.shuffle(celdasBloque, aleatorio);

            for (int i = 0; i < PISTAS_POR_BLOQUE; i++) {
                Celda celdaElegida = celdasBloque.get(i);
                int valorOriginal = valoresOriginales
                        [celdaElegida.obtenerFila()][celdaElegida.obtenerColumna()];
                celdaElegida.marcarComoFija(valorOriginal);
            }
        }
    }

    /**
     * Produces a list with the numbers from 1 to 6 in a random order.
     * Used by the backtracking algorithm to ensure each generated board
     * is different from the previous ones.
     *
     * @return a shuffled list containing the integers from 1 to 6
     */
    private List<Integer> generarOrdenAleatorio() {
        List<Integer> numeros = new ArrayList<>();
        for (int i = Celda.VALOR_MINIMO; i <= Celda.VALOR_MAXIMO; i++) {
            numeros.add(i);
        }
        Collections.shuffle(numeros, aleatorio);
        return numeros;
    }
}
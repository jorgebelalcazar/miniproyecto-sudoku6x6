package com.example.miniproyectosudoku6x6.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Provides hints for empty cells of a Sudoku board.
 * <p>
 * Given the current state of the board, this class finds an empty cell
 * and proposes a valid number for it. The validity of the suggestion is
 * delegated to a {@link ValidadorSudoku}, ensuring that the hint never
 * breaks the row, column or block rules at the moment it is requested.
 * </p>
 *
 * <p>The provider does NOT solve the entire board. It only suggests a
 * single value for a single empty cell, leaving the puzzle for the
 * player to finish. This satisfies user story HU-4 of the project.</p>
 *
 * <p>Hints are unlimited: every call to {@link #sugerirAyuda()} produces
 * a new suggestion based on the current board state.</p>
 *
 * <p>This class belongs to the model layer of the MVC architecture and
 * is independent of any UI framework.</p>
 */
public class ProveedorAyuda {

    /** The board this provider analyzes. */
    private final TableroSudoku tablero;

    /** Validator used to check which candidate values are legal. */
    private final ValidadorSudoku validador;

    /** Random source used to pick an empty cell and a candidate value. */
    private final Random aleatorio;

    /**
     * Constructs a new hint provider bound to a specific board.
     *
     * @param tablero the board on which hints will be suggested
     * @throws IllegalArgumentException if the board is {@code null}
     */
    public ProveedorAyuda(TableroSudoku tablero) {
        if (tablero == null) {
            throw new IllegalArgumentException(
                    "El tablero no puede ser nulo"
            );
        }
        this.tablero = tablero;
        this.validador = new ValidadorSudoku(tablero);
        this.aleatorio = new Random();
    }

    /**
     * Generates a hint for an empty cell of the board.
     * <p>
     * The method picks a random empty cell, then collects every legal
     * value (1 to 6) for that cell at the current board state, and
     * finally returns one of those valid values chosen at random.
     * </p>
     *
     * @return a {@link Sugerencia} describing the suggested cell and
     *         value, or {@code null} if no hint can be provided (either
     *         because the board has no empty cells or because the only
     *         empty cells have no valid candidates)
     */
    public Sugerencia sugerirAyuda() {
        List<Celda> celdasVacias = obtenerCeldasVacias();
        if (celdasVacias.isEmpty()) {
            return null;
        }

        Collections.shuffle(celdasVacias, aleatorio);

        for (Celda celda : celdasVacias) {
            List<Integer> candidatos = obtenerCandidatosValidos(celda);
            if (!candidatos.isEmpty()) {
                int valorElegido = candidatos.get(aleatorio.nextInt(candidatos.size()));
                return new Sugerencia(
                        celda.obtenerFila(),
                        celda.obtenerColumna(),
                        valorElegido
                );
            }
        }
        return null;
    }

    /**
     * Collects every cell of the board that is currently empty.
     *
     * @return a list of empty cells; never {@code null}
     */
    private List<Celda> obtenerCeldasVacias() {
        List<Celda> vacias = new ArrayList<>();
        for (int fila = 0; fila < TableroSudoku.TOTAL_FILAS; fila++) {
            for (int columna = 0; columna < TableroSudoku.TOTAL_COLUMNAS; columna++) {
                Celda celda = tablero.obtenerCelda(fila, columna);
                if (celda.estaVacia()) {
                    vacias.add(celda);
                }
            }
        }
        return vacias;
    }

    /**
     * Computes the list of values from 1 to 6 that can be legally placed
     * in the given cell according to the current board state.
     *
     * @param celda the empty cell to analyze
     * @return a list of candidate values; may be empty if no number fits
     */
    private List<Integer> obtenerCandidatosValidos(Celda celda) {
        List<Integer> candidatos = new ArrayList<>();
        for (int valor = Celda.VALOR_MINIMO; valor <= Celda.VALOR_MAXIMO; valor++) {
            if (validador.esMovimientoValido(
                    celda.obtenerFila(), celda.obtenerColumna(), valor)) {
                candidatos.add(valor);
            }
        }
        return candidatos;
    }

    /**
     * Immutable value object that represents a single hint suggestion.
     * <p>
     * Carries the coordinates of the cell where the hint applies and the
     * value that should be placed in it.
     * </p>
     */
    public static class Sugerencia {

        /** Row index of the suggested cell. */
        private final int fila;

        /** Column index of the suggested cell. */
        private final int columna;

        /** Suggested value (between 1 and 6). */
        private final int valor;

        /**
         * Creates a new immutable hint.
         *
         * @param fila    the row of the suggested cell
         * @param columna the column of the suggested cell
         * @param valor   the suggested value
         */
        public Sugerencia(int fila, int columna, int valor) {
            this.fila = fila;
            this.columna = columna;
            this.valor = valor;
        }

        /**
         * Returns the row index of the suggested cell.
         *
         * @return the row index (0-5)
         */
        public int obtenerFila() {
            return fila;
        }

        /**
         * Returns the column index of the suggested cell.
         *
         * @return the column index (0-5)
         */
        public int obtenerColumna() {
            return columna;
        }

        /**
         * Returns the suggested value.
         *
         * @return the value (1-6)
         */
        public int obtenerValor() {
            return valor;
        }
    }
}